package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class TweetEntity extends TextBase 
{
	ArrayList<Long>comments = new ArrayList<Long>(); 
	
	public TweetEntity()
	{
		this.like_nums = 0;
		this.repost_nums = 0;
	}

	public TweetEntity(String creator, String body)
	{
		this.creator = creator;
		this.body = body;
		this.releaseTime = (new Date()).getTime();
		this.like_nums = 0;
		this.repost_nums = 0;
	}
	
	public void addComment(Long id)
	{
		comments.add(id); 
	}
	
}
