package com.myapp.storage.entity;

import java.util.*;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class MoviePageEntity 
{
	@PrimaryKey
	private String movieId;
	
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

	public void addReview(Long newsId) 
	{
		reviewList.add(newsId);
	}

	public void addLike() 
	{
		likeNum ++;
	}	
	
	public void removeLike() 
	{
		if(likeNum > 0)
		{
			likeNum --;
		}
	}	
}
