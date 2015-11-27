package com.myapp.storage.entity;

import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;
import static com.sleepycat.persist.model.Relationship.*;
import java.util.Date;
import static com.sleepycat.persist.model.DeleteAction.*;

/**
 *  User release text message, like article, twitter, comment
 * @author Wingszero
 *
 */
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
	
	public TextBase() 
	{
		
	}

	public TextBase(long id, String body) 
	{ 
		this.id = id;
		this.body = body;
		this.releaseTime = (new Date()).getTime();
		this.like_nums = 0;
	}	
	
	public TextBase(String username, long id, String body) 
	{ 
		this.id = id;
		this.body = body;
		this.releaseTime = (new Date()).getTime();
		this.like_nums = 0;
		this.creator = username;
	}	

	public void addLikeNum() 
	{
		like_nums++;
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
	
	public String getCreator() 
	{
		return creator;
	}
	
	public int getLikeNums() 
	{
		return like_nums;
	}
}
