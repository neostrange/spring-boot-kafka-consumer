package com.example;

import java.util.List;
import java.util.Map;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

public class MessageProcessor {

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
		for (String item : payload.keySet()) {
			key = (String) item; // topic

			Map<Integer, List<String>> messages = payload.get(key);
			// Integer i;
			for (Integer partition : messages.keySet()) { // partitions

				List<String> values = (List<String>) messages.get(partition);

				for (String v : values) {
					System.out.println("Partition ["+ partition +"], Topic ["+ key +"], Data [" +v+"]" );

					
//					if (v.getClass().getName().contains("Vote")) {
//						System.out.println("the type of the object is : " + v.getClass().getName() + "  ");
//						Vote object = (Vote) v;
//						System.out.println(" getting key as " + key + " partition as : " + partition + " and value is "
//								+ object.getName() + " within thread:" + Thread.currentThread().getName());
//					}
//					
//					if (v.getClass().getName().contains("Voter")) {
//						System.out.println("the type of the object is : " + v.getClass().getName() + "  ");
//						Voter object = (Voter) v;
//						System.out.println(" getting key as " + key + " partition as : " + partition + " and value is "
//								+ object.getName() + " within thread:" + Thread.currentThread().getName());
//					}
				}
			}
		}

	}

}
