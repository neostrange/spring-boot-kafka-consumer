package com.example;

import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
@ImportResource("classpath:/spring/spring-boot-kafka-consumer-example.xml")

public class SpringBootKafkaConsumerApplication {

	@RequestMapping("/vote")
	public Status vote(@RequestBody Vote vote) throws ExecutionException, InterruptedException {

//		Message<Vote> message = MessageBuilder.withPayload(vote).setHeader("Topic", "votes").build();
//		inputToKafka.send(message);

		return new Status("ok");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaConsumerApplication.class, args);
	}
}
