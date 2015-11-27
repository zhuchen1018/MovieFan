package com.myapp.storage.entity;

import java.util.Date;

import com.sleepycat.persist.model.Entity;

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
