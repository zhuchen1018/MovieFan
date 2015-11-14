package com.myapp.storage.entity;

import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;
import static com.sleepycat.persist.model.Relationship.*;

import java.util.ArrayList;
import java.util.Date;

import static com.sleepycat.persist.model.DeleteAction.*;

@Persistent
class TextBase
{
    @PrimaryKey
    long id;

    @SecondaryKey(relate=MANY_TO_ONE, relatedEntity=UserEntity.class, onRelatedEntityDelete=CASCADE)
    String creator;

    String body;
	long releaseTime;
	int like_nums;
	int repost_nums;
	
	public TextBase() 
	{ 
		this.body = body;
		this.releaseTime = (new Date()).getTime();
		this.like_nums = 0;
		this.repost_nums = 0;
	}	
	public void addLikeNum() 
	{
		like_nums++;
	}

	public void addRepostNum()
	{
		repost_nums++;
	}
	
	public long getId()
	{
		return id;
	}

	public long getReleaseTime() 
	{
		return releaseTime;
	}

	public String getBody() 
	{
		return body;
	}
}
