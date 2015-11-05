package com.myapp.storage.accessor;

import java.util.ArrayList;

import com.myapp.storage.entity.TweetEntity;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

public class TweetAccessor 
{
	private PrimaryIndex<Long, TweetEntity> tweets;
	
	public TweetAccessor(EntityStore store)
	{
		tweets = store.getPrimaryIndex(Long.class, TweetEntity.class);
	}

	public TweetEntity addTweet(String username, String body) 
	{
		TweetEntity t = new TweetEntity(username, body);
		tweets.put(t);
		return t;
	}

	public ArrayList<TweetEntity> getTweetEntityByIds(ArrayList<Long> tweets_id)
	{
		ArrayList<TweetEntity>res = new ArrayList<TweetEntity>();
		for(Long id: tweets_id)
		{
			TweetEntity t = tweets.get(id);
			if(t != null)
			{
				res.add(t);
			}
		}
		return res;
	}

}
