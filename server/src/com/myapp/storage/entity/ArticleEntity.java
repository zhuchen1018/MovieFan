package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ArticleEntity extends TextBase 
{
	String head;
	ArrayList<Long>comments = new ArrayList<Long>(); 
	int repost_nums;

	public ArticleEntity()
	{
		
	}
	
	public ArticleEntity(String username, String head, String body)
	{
		this.id = getNewArticleId();
		this.creator = username;
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


