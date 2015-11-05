package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ArticleEntity extends TextBase 
{
	@PrimaryKey
	long id;
	String username;
	String head;
	String body;
	long releaseTime;
	int like_nums;
	int repost_nums;
	ArrayList<Long>comments; 
	
	public ArticleEntity()
	{
		this.id = getNewArticleId();
		this.username = "";
		this.head = "";
		this.body = "";
		this.releaseTime = (Long) null; 
		this.like_nums = 0;
		this.repost_nums = 0;
		this.comments = new ArrayList<Long>(); 
	}
	
	public ArticleEntity(String username, String head, String body)
	{
		this.id = getNewArticleId();
		this.username = username;
		this.head = head;
		this.body = body;
		this.releaseTime = (new Date()).getTime();
		this.like_nums = 0;
		this.repost_nums = 0;
		this.comments = new ArrayList<Long>(); 
	}

	private long getNewArticleId() 
	{
		return 0;
	}

	public void addComment(Long id)
	{
		comments.add(id);
	}
	
	public void addLikeNum() 
	{
		like_nums++;
	}

	public void addRepostNum()
	{
		repost_nums++;
	}

	public long getReleaseTime() 
	{
		return releaseTime;
	}
}


