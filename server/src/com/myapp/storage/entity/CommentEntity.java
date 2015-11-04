package com.myapp.storage.entity;

import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;

@Persistent
public class CommentEntity 
{
	public CommentEntity()
	{
		
	}
	@PrimaryKey
	long id;
	String username;
	long releaseTime;
	String info;
	
	public CommentEntity(String username, String info)
	{
		this.username = username;
		this.info = info;
		this.releaseTime = (new Date()).getTime();
	}
}
