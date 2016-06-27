package com.example.feedgeneration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;

import com.example.dao.FeedRepository;
import com.example.model.Feed;
import com.example.model.IncidentStats;
import com.example.model.ThreatType;
import com.example.utils.CustomObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class MessageProcessor {

	public static final Logger log = LoggerFactory.getLogger(MessageProcessor.class);

	@Autowired
	FeedRepository feedRepo;

	@Autowired
	CustomObjectMapper objectMapper;

	@ServiceActivator
	public void processKafkaMessage(Map<String, Map<Integer, List<String>>> payload) {
		JsonNode node;
		String key = null;
		String ip, md5, url = null;
		String dateTime = null;
		Feed tmpFeed = null;
		String category = null;
		LocalDateTime incTime = null, timestamp = null;
		int severity = 0;
		for (String item : payload.keySet()) {
			key = (String) item;

			Map<Integer, List<String>> messages = payload.get(key);
			// Integer i;
			for (Integer partition : messages.keySet()) {
				// partitions
				List<String> values = (List<String>) messages.get(partition);

				for (String v : values) {

					log.debug("Partition [" + partition + "], Topic [" + key + "], Data [" + v + "]");

					node = null;
					try {
						node = CustomObjectMapper.getInstance().readTree(v).get("source");
					} catch (IOException e) {
						log.error("", e);
					}

					//is Incident?
					if (node.has("origin")) {
						
						category = node.has("category") ? node.get("category").asText() : "Reconnaissance";
						dateTime = node.get("dateTime").asText();
						severity = (int) node.get("severityScore").asDouble();
						// timestamp now
						try {
							timestamp = LocalDateTime
									.parse(LocalDateTime.now().format(FeedGenerationUtil.formatter));
						} catch (DateTimeParseException e) {
							log.error("Error occurred while trying to parse dateTime [{}]", dateTime);
						}
						// incident time parse
						try {
							incTime = LocalDateTime.parse(dateTime, FeedGenerationUtil.formatter);
						} catch (DateTimeParseException e) {
							log.error("Error occurred while trying to parse dateTime [{}]", dateTime);
							incTime = timestamp;
						}
						
						// ip
						if (node.get("origin").has("ip")) {
							

							ip = node.get("origin").get("ip").asText();
							log.debug("Feed IP [" + ip + "]");
							tmpFeed = feedRepo.findFeed(ip);
							if (tmpFeed != null) {
								log.debug("Feed with ip [{}] exists", tmpFeed.getIndicator());
								tmpFeed.getThreatType().update(category);
								tmpFeed.getIncidentStats().update(severity);
								tmpFeed = FeedGenerationUtil.evaluateFeed(tmpFeed);
								tmpFeed.setLastSeen(incTime);
								tmpFeed.setTimestamp(timestamp);
								feedRepo.updateFeed(tmpFeed);
							} else {
								tmpFeed = new Feed(ip, "ip");
								tmpFeed.setIncidentStats(new IncidentStats());
								tmpFeed.setThreatType(new ThreatType());
								tmpFeed.getThreatType().update(category);
								tmpFeed.getIncidentStats().update(severity);
								tmpFeed.getTimestamp();
								tmpFeed.setFirstSeen(incTime);
								tmpFeed.setLastSeen(incTime);

								tmpFeed = FeedGenerationUtil.evaluateFeed(tmpFeed);
								feedRepo.saveFeed(tmpFeed);
							}

						}

						// download
						if (node.has("download")) {
							// in case of ssh
							if (node.get("download").isArray()) {
								md5 = node.get("download").get(0).get("md5Hash").asText();
								url = node.get("download").get(0).get("url").asText();
							}
							// in case of smb
							else {
								md5 = node.get("download").get("md5Hash").asText();
								url = node.get("download").get("url").asText();
							}

							log.debug("Feed MD5 [" + md5 + "]");
							log.debug("Feed URL [" + url + "]");
							tmpFeed = feedRepo.findFeed(md5);
							if (tmpFeed != null) {
								log.debug("Feed with md5 [{}] exists", tmpFeed.getIndicator());
								tmpFeed.setTimestamp(timestamp);
								tmpFeed.setLastSeen(incTime);
								feedRepo.updateFeed(tmpFeed);
							} else {
								tmpFeed = new Feed(md5, "md5");
								tmpFeed.setTimestamp(timestamp);
								tmpFeed.setFirstSeen(incTime);
								tmpFeed.setLastSeen(incTime);

								feedRepo.saveFeed(tmpFeed);
							}

							// url
							tmpFeed = feedRepo.findFeed(url);
							if (tmpFeed != null) {
								log.debug("Feed with url [{}] exists", tmpFeed.getIndicator());
								tmpFeed.setTimestamp(timestamp);
								tmpFeed.setLastSeen(incTime);
								feedRepo.updateFeed(tmpFeed);
							} else {

								tmpFeed = new Feed(url, "url");
								tmpFeed.setTimestamp(timestamp);
								tmpFeed.setFirstSeen(incTime);
								tmpFeed.setLastSeen(incTime);

								feedRepo.saveFeed(tmpFeed);
							}

						}
					}
				}
			}
		}

	}

}
