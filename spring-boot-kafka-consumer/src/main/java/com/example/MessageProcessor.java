package com.example;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageProcessor {

	@Autowired
	FeedRepository feedRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	public static Calendar calendar = Calendar.getInstance();
	
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public void processKafkaMessage(Message<String> message) {

		String receivedMessage;

		receivedMessage = message.getPayload();

		System.out.println("message sent via http request: " + receivedMessage);

	}

	public <K, V> void processKafkaMessage(Map<String, String> payload) throws InterruptedException {
		String key = null;
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
		Feed tmpFeed = null;
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
						node = objectMapper.readTree(v);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (node.has("origin")) {
						ip = node.get("origin").get("ip").asText();
						tmpFeed = feedRepo.findFeed(ip);
						if (tmpFeed != null) {
							System.out.println("FOUND IT!:" + tmpFeed.getIndicator());
							tmpFeed.setLastSeen(node.get("dateTime").asText());
							feedRepo.updateFeed(tmpFeed);
						} else {
							tmpFeed = new Feed(ip, "ip", node.get("dateTime").asText(), node.get("dateTime").asText(), format.format(calendar.getTime()));
							feedRepo.saveFeed(tmpFeed);
						}
						System.out.println("IP [" + ip + "]");
					}
					if (node.has("download")) {
						md5 = node.get("download").get(0).get("md5Hash").asText();
						
						tmpFeed = feedRepo.findFeed(md5);
						if (tmpFeed != null) {
							System.out.println("FOUND IT!:" + tmpFeed.getIndicator());
							tmpFeed.setLastSeen(node.get("dateTime").asText());
							feedRepo.updateFeed(tmpFeed);
						} else {
							tmpFeed = new Feed(md5, "md5", node.get("dateTime").asText(), node.get("dateTime").asText(), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime()));
							feedRepo.saveFeed(tmpFeed);
						}
						//url
						url = node.get("download").get(0).get("url").asText();
						tmpFeed = feedRepo.findFeed(url);
						if (tmpFeed != null) {
							System.out.println("FOUND IT!:" + tmpFeed.getIndicator());
							tmpFeed.setLastSeen(node.get("dateTime").asText());
							feedRepo.updateFeed(tmpFeed);
						} else {
							tmpFeed = new Feed(md5, "url", node.get("dateTime").asText(), node.get("dateTime").asText(), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime()));
							feedRepo.saveFeed(tmpFeed);
						}
						
						System.out.println("md5 [" + md5 + "], url [" + url + "]");

					}
				}
			}
		}

	}

}
