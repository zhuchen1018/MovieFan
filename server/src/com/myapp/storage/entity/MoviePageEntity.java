package com.myapp.storage.entity;

import java.util.*;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class MoviePageEntity 
{
	@PrimaryKey
	private String movieId;

	private String name;
	private String posterUrl; 
	private int likeNum;

	//news id
	private ArrayList<Long>reviewList;
	
	public MoviePageEntity()
	{
	}
	
	public MoviePageEntity(String id)
	{
		this.movieId = id;
		this.likeNum = 0;
		this.reviewList = new ArrayList<Long>();
	}

	/*get funcs*/
	public String getId()
	{
		return movieId;
	}

	/*set funcs*/
	private static void print(String s)
	{
		System.out.println(s);
	}

	synchronized public void addReview(Long newsId) 
	{
		reviewList.add(newsId);
	}

	synchronized public void addLike() 
	{
		likeNum ++;
	}	
	
	synchronized public void removeLike() 
	{
		if(likeNum > 0)
		{
			likeNum --;
		}
	}	
	
	public void setPoster(String url)
	{
		posterUrl = url; 
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getPoster() 
	{
		return posterUrl;
	}

	public String getName() 
	{
		return name;
	}
}
