package com.myapp.model;

import java.util.Date;

public class CommentObj 
{
	String username;
	long releaseTime;
	String info;
	
	public CommentObj(String username, String info)
	{
		this.username = username;
		this.info = info;
		this.releaseTime = (new Date()).getTime();
	}
}
