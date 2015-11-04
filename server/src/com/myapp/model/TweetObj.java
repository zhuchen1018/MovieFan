package com.myapp.model;

import java.util.ArrayList;
import java.util.Date;

public class TweetObj 
{
	long releaseTime;
	String info;
	ArrayList<CommentObj>comments = new ArrayList<CommentObj>(); 

	public TweetObj(String info)
	{
		this.info = info;
		this.releaseTime = (new Date()).getTime();
	}
	
	public void addComment(String username, String info)
	{
		comments.add(new CommentObj(username, info));
	}
}
