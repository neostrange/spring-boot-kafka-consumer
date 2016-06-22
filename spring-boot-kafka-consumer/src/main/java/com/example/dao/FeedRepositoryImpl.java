package com.example.dao;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Feed;

@Repository
public class FeedRepositoryImpl implements FeedRepository {
	
	public static final Logger log = LoggerFactory.getLogger(FeedRepositoryImpl.class);

	private static final String KEY = "Feed";
	private HashOperations hashOps;

	@Autowired
	private RedisTemplate redisTemplate;

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public Feed findFeed(String id) {
		log.info("Find feed with id [{}]", id);
		return (Feed) hashOps.get(KEY, id);
	}

	@Override
	public void updateFeed(Feed feed) {
		
		hashOps.put(KEY, feed.getIndicator(), feed);

	}

	@Override
	public void saveFeed(Feed feed) {
		
		 hashOps.put(KEY, feed.getIndicator(), feed);

	}

	@Override
	public Map<Object, Object> findAllFeeds() {
		
		return hashOps.entries(KEY);
	}

}
