package com.example.feedgeneration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

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

	public static Calendar calendar = Calendar.getInstance();

	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public ThreatType getThreatType(String category, ThreatType type) {
		switch (category) {
		case "Reconnaissance":
			type.setRecon(true);
			break;
		case "Malware":
			type.setMalware(true);
			break;
		case "Web Attack":
			type.setWeb(true);
			break;
		case "SIP Attack":
			type.setSip(true);
			break;
		case "DB-MSSQL Attack":
			type.setDb(true);
			break;
		case "DB-MYSQL Attack":
			type.setDb(true);
			break;
		case "SSH Brute-Force Attempt":
			type.setBruteForce(true);
			break;
		case "SSH Possible Compromise":
			type.setPossibleCompromise(true);
			break;
		default:
			type.setRecon(true);
			break;
		}
		return type;
	}

	public IncidentStats updateStats(int severity, IncidentStats stats) {

		switch (severity) {
		case 1:
			stats.incrSev1();
			break;
		case 2:
			stats.incrSev2();
			break;
		case 3:
			stats.incrSev3();
			break;
		case 4:
			stats.incrSev4();
			break;
		case 5:
			stats.incrSev5();
			break;
		default:
			stats.incrSev1();
			break;
		}
		stats.incrTotal();
		return stats;
	}
	

	public void processKafkaMessage(Message<String> message) {

		String receivedMessage;

		receivedMessage = message.getPayload();

		System.out.println("message sent via http request: " + receivedMessage);

	}

	public <K, V> void processKafkaMessage(Map<String, String> payload) throws InterruptedException {
		// String key = null;
		// for (K item : payload.keySet()) {
		// key = (String) item;
		// }

		// Map<K, V> topic = (Map<K, V>) payload.get(key);
		String vote = payload.get("votes");
		// List<Vote> votes = (List<Vote>) topic.get(0);

		Thread.sleep(500);

		System.out.println("vote: " + vote + "\n in thread:" + Thread.currentThread().getName());

	}

	@ServiceActivator
	public void processKafkaVoteMessage(Map<String, Map<Integer, List<String>>> payload) {
		String key = null;
		String ip, md5, url = null;
		String dateTime = null;
		Feed tmpFeed = null;
		ThreatType type = null;
		String category = null;
		int severity = 0;
		for (String item : payload.keySet()) {
			key = (String) item;

			Map<Integer, List<String>> messages = payload.get(key);
			// Integer i;
			for (Integer partition : messages.keySet()) {
				// partitions

				List<String> values = (List<String>) messages.get(partition);

				for (String v : values) {

					System.out.println("Partition [" + partition + "], Topic [" + key + "], Data [" + v + "]");

					JsonNode node = null;
					try {
						node = CustomObjectMapper.getInstance().readTree(v);
					} catch (IOException e) {
						e.printStackTrace();
					}

					category = node.has("category") ? node.get("category").asText() : "Recon";

					dateTime = node.get("dateTime").asText();
//					dateTime = dateFormat.format(dateTime);

					// ip
					if (node.get("origin").has("ip")) {
						ip = node.get("origin").get("ip").asText();
						tmpFeed = feedRepo.findFeed(ip);
						if (tmpFeed != null) {
							log.debug("Feed with url [{}] exists", tmpFeed.getIndicator());
							tmpFeed.setLastSeen(dateTime);
							tmpFeed.setThreatType(getThreatType(category, tmpFeed.getThreatType()));
							tmpFeed.setIncidentStats(updateStats(severity, tmpFeed.getIncidentStats()));
							tmpFeed.setRiskFactor(tmpFeed.getIncidentStats().riskFactor());
							tmpFeed.setTimestamp(dateFormat.format(calendar.getTime()));
							feedRepo.updateFeed(tmpFeed);
						} else {
							tmpFeed = new Feed(ip, "ip", dateTime, dateTime, dateFormat.format(calendar.getTime()));
							tmpFeed.setThreatType(getThreatType(category, new ThreatType()));
							tmpFeed.setIncidentStats(updateStats(severity, tmpFeed.getIncidentStats()));
							tmpFeed.setRiskFactor(tmpFeed.getIncidentStats().riskFactor());
							feedRepo.saveFeed(tmpFeed);
						}
						System.out.println("IP [" + ip + "]");
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

						tmpFeed = feedRepo.findFeed(md5);
						if (tmpFeed != null) {
							log.debug("Feed with md5 [{}] exists", tmpFeed.getIndicator());
							tmpFeed.setLastSeen(dateTime);
							tmpFeed.setThreatType(getThreatType(category, tmpFeed.getThreatType()));
							tmpFeed.setIncidentStats(updateStats(severity, tmpFeed.getIncidentStats()));
							tmpFeed.setTimestamp(dateFormat.format(calendar.getTime()));
							tmpFeed.setRiskFactor(tmpFeed.getIncidentStats().riskFactor());
							feedRepo.updateFeed(tmpFeed);
						} else {
							tmpFeed = new Feed(md5, "md5", dateTime, dateTime, dateFormat.format(calendar.getTime()));
							tmpFeed.setThreatType(getThreatType(category, new ThreatType()));
							feedRepo.saveFeed(tmpFeed);
						}

						// url
						tmpFeed = feedRepo.findFeed(url);
						if (tmpFeed != null) {
							log.debug("Feed with url [{}] exists", tmpFeed.getIndicator());
							tmpFeed.setLastSeen(dateTime);
							tmpFeed.setThreatType(getThreatType(category, tmpFeed.getThreatType()));
							tmpFeed.setIncidentStats(updateStats(severity, tmpFeed.getIncidentStats()));
							tmpFeed.setRiskFactor(tmpFeed.getIncidentStats().riskFactor());
							tmpFeed.setTimestamp(dateFormat.format(calendar.getTime()));
							feedRepo.updateFeed(tmpFeed);
						} else {

							tmpFeed = new Feed(url, "url", dateTime, dateTime, dateFormat.format(calendar.getTime()));
							tmpFeed.setThreatType(getThreatType(category, new ThreatType()));
							tmpFeed.setIncidentStats(updateStats(severity, tmpFeed.getIncidentStats()));
							tmpFeed.setRiskFactor(tmpFeed.getIncidentStats().riskFactor());
							feedRepo.saveFeed(tmpFeed);
						}
						System.out.println("MD5 [" + md5 + "], URL [" + url + "]");

					}
				}
			}
		}

	}

}
