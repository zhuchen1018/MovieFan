package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Date;

import com.sleepycat.persist.model.Entity;

@Entity
public class NewsEntity extends TextBase 
{
	String title = null;
	
	String newsType = null;
	
	String movie_id = null;
	String movie_poster_url = null;
	
	ArrayList<Long>comments = null;

	ArrayList<String>receiver = null;

	int repost_nums;

	public NewsEntity()
	{
	}

	/**
	 * For twitter type
	 * @param body
	 * @param type
	 */
	public NewsEntity(long id, String body, String type) 
	{ 
		super(id, body);
		newsType = type;
		comments = new ArrayList<Long>();
		repost_nums = 0;
	}	

	/**
	 * For movie review type
	 * @param newsMovieReview 
	 * @param String
	 */
	public NewsEntity(long id, String t, String body, String mid, String url, String type) 
	{ 
		super(id, body);
		title = t;
		newsType = type;
		movie_id = mid;
		movie_poster_url = url;
		comments = new ArrayList<Long>();
		receiver = new ArrayList<String>();
		repost_nums = 0;
	}	
	
	/**
	 * For make friends, add group 
	 * @param body
	 * @param type
	 */
	public NewsEntity(long id,  ArrayList<String>receivers, String type) 
	{ 
		super(id, null);
		newsType = type;
		comments = new ArrayList<Long>();
		receiver = receivers; 
		repost_nums = 0;
	}	
	/**
	 * For share movies 
	 * @param body
	 * @param type
	 */
	public NewsEntity(long id,  String mid, String url, ArrayList<String>friends, String type) 
	{ 
		super(id, null);
		newsType = type;
		movie_id = mid;
		movie_poster_url = url;
		comments = new ArrayList<Long>();
		receiver = friends;
		repost_nums = 0;
	}	
	

	/**
	 * For like movie
	 * @param nextNewsId
	 * @param movie_id2
	 * @param url
	 * @param newsLikeMovie
	 */
	public NewsEntity(long id, String mid, String url, String type) 
	{
		super(id, null);
		newsType = type;
		movie_id = mid;
		movie_poster_url = url;
		comments = new ArrayList<Long>();
		receiver = null;
		repost_nums = 0;
	}

	public void addComment(Long id)
	{
		comments.add(id);
	}
	
	
	public void addRepostNum()
	{
		repost_nums++;
	}
}

