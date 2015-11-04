package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Date;

import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;

@Persistent
public class TweetEntity 
{
	public TweetEntity()
	{
		
	}
	@PrimaryKey
	long id;
	long releaseTime;
	String info;
	static ArrayList<CommentEntity>comments = new ArrayList<CommentEntity>(); 

	public TweetEntity(String info)
	{
		this.info = info;
		this.releaseTime = (new Date()).getTime();
	}
	
	public void addComment(String username, String info)
	{
		comments.add(new CommentEntity(username, info));
	}

	public long getReleaseTime() 
	{
		return releaseTime;
	}

	public String getInfo()
	{
		return info;
	}
}
