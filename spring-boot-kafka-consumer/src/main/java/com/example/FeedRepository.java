package com.example;

public interface FeedRepository {

	Feed findFeed(String id);
	void updateFeed(Feed feed);
	void saveFeed(Feed feed);

}