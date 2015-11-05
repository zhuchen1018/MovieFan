package com.myapp.storage.entity;

import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class CommentEntity extends TextBase 
{
	public CommentEntity()
	{
		
	}
	
	public CommentEntity(String username, String info)
	{
		this.creator = username;
		this.body = info;
		this.releaseTime = (new Date()).getTime();
	}
}
