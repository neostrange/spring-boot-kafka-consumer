package com.example.dao;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Feed;

@Repository
public class FeedRepositoryImpl implements FeedRepository {

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
		// TODO Auto-generated method stub
		
		return hashOps.entries(KEY);
	}

}
