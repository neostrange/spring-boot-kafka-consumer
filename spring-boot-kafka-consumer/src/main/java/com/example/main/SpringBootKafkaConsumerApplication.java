package com.example.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.FeedRepository;
import com.example.model.Feed;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
@ImportResource("classpath:/spring/spring-boot-kafka-consumer-example.xml")

public class SpringBootKafkaConsumerApplication {
	@Autowired
	FeedRepository feedRepo;
	

	@RequestMapping("/feeds")
	public @ResponseBody List<Feed> getFeeds() throws ExecutionException, InterruptedException {
		
		Map<Object,Object> feeds = feedRepo.findAllFeeds();
		
		//final List<Feed> list = objectMapper.convertValue(feeds, Feed.class);
		
		
		List<Feed> feedList = new ArrayList<Feed>();
		
		for(Map.Entry<Object, Object> entry: feeds.entrySet()){
			
			feedList.add((Feed) entry.getValue());
		}

		return feedList;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaConsumerApplication.class, args);
	}
}
