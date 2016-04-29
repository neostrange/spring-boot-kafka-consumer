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

	@ServiceActivator
	public <K, V> void processKafkaMessage(Map<K, V> payload) {
		String key = null;
		for (K item : payload.keySet()) {
			key = (String) item;
		}
		Map<K, V> topic = (Map<K, V>) payload.get(key);
		List<Vote> votes = (List<Vote>) topic.get(0);
		
		for (Vote v : votes){
			System.out.println("vote: " + v.getName()+ "\n");
		}
	}

}
