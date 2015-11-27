package com.myapp.storage.accessor;

import java.util.ArrayList;

import com.myapp.storage.entity.NewsEntity;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

public class NewsAccessor 
{
	private PrimaryIndex<Long, NewsEntity> news;
	
	public NewsAccessor(EntityStore store)
	{
		news = store.getPrimaryIndex(Long.class, NewsEntity.class);
	}

	public void addNews(NewsEntity obj)
	{
		news.put(obj);
	}

	public ArrayList<NewsEntity> getNewsEntityByIds(ArrayList<Long> news_id)
	{
		ArrayList<NewsEntity>res = new ArrayList<NewsEntity>();
		for(Long id: news_id)
		{
			NewsEntity t = news.get(id);
			if(t != null)
			{
				res.add(t);
			}
		}
		return res;
	}

}
