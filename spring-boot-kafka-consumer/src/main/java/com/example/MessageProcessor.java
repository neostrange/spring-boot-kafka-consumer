package com.example;

import java.util.List;
import java.util.Map;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

public class MessageProcessor {

	public void processKafkaMessage(Message<Vote> message) {

		Vote receivedMessage;

		receivedMessage = message.getPayload();

		System.out.println("message sent via http request: " + receivedMessage.getName());

	}
	public <K, V> void processKafkaMessage(Map<String, Vote> payload) throws InterruptedException {
		String key = null;
		// for (K item : payload.keySet()) {
		// key = (String) item;
		// }

		// Map<K, V> topic = (Map<K, V>) payload.get(key);
		Vote vote = payload.get("votes");
		// List<Vote> votes = (List<Vote>) topic.get(0);

		Thread.sleep(500);

		System.out.println("vote: " + vote.getName() + "\n in thread:" + Thread.currentThread().getName());

	}

	@ServiceActivator
	public void processKafkaVoteMessage(Map<String, Map<Integer, List<Vote>>> payload) {
		String key = null;
		for (String item : payload.keySet()) {
			key = (String) item; // topic

			Map<Integer, List<Vote>> messages = payload.get(key);
			// Integer i;
			for (Integer partition : messages.keySet()) { // partitions

				// i = (Integer)partition;

				List<Vote> values = (List<Vote>) messages.get(partition);

				for (Object v : values) {
					if (v.getClass().getName().contains("Vote")) {
						System.out.println("the type of the object is : " + v.getClass().getName() + "  ");
						Vote object = (Vote) v;
						System.out.println(" getting key as " + key + " partition as : " + partition + " and value is "
								+ object.getName() + " within thread:" + Thread.currentThread().getName());
					}
					
					if (v.getClass().getName().contains("Voter")) {
						System.out.println("the type of the object is : " + v.getClass().getName() + "  ");
						Voter object = (Voter) v;
						System.out.println(" getting key as " + key + " partition as : " + partition + " and value is "
								+ object.getName() + " within thread:" + Thread.currentThread().getName());
					}
				}
			}
		}

	}

}
