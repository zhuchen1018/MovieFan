package com.myapp.storage.entity;

import java.util.ArrayList;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class HashTagEntity 
{
    @PrimaryKey
	String tag = null;

    ArrayList<Long>news;
	
	public HashTagEntity()
	{
		
	}

	/**
	 * 
	 * @param body
	 * @param type
	 */
	public HashTagEntity(String tag) 
	{ 
		this.tag = tag;
		this.news = new ArrayList<Long>();
	}	

	public String getKey()
	{
		return tag;
	}

	public int getCount()
	{
		return news.size(); 
	}
	
	public ArrayList<Long> getNews() 
	{
		return news;
	}
	
	/*SET FUNCTIONS*/
	public void addNews(Long newsId)
	{
		news.add(newsId);
	}

	public void removeNews(long id) 
	{
		news.remove(id);
	}	
}

