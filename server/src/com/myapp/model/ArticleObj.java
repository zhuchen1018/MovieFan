package com.myapp.model;

import java.util.Date;

public class ArticleObj 
{
	String username;
	long releaseTime;
	String head;
	String body;
	
	public ArticleObj(String username, String head, String body)
	{
		this.username = username;
		this.head = head;
		this.body = body;
		this.releaseTime = (new Date()).getTime();
	}
}

