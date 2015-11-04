package com.myapp.storage.entity;

import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;

@Persistent
public class ArticleEntity 
{
	public ArticleEntity()
	{
		
	}
	@PrimaryKey
	long id;
	String username;
	long releaseTime;
	String head;
	String body;
	
	public ArticleEntity(String username, String head, String body)
	{
		this.username = username;
		this.head = head;
		this.body = body;
		this.releaseTime = (new Date()).getTime();
	}
}

