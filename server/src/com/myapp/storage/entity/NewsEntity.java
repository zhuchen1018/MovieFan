package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Date;

import com.sleepycat.persist.model.Entity;

@Entity
public class NewsEntity extends TextBase 
{
	String title = null;
	
	int newsType;
	
	String movie_id = null;
	String movie_name = null;
	String movie_poster_url = null;
	
	ArrayList<Long>comments = null;

	//for make friends, share movie to friends etc.
	ArrayList<String>receiver = null;

	int repost_nums ;

	public NewsEntity()
	{
		
	}

	/**
	 * 
	 * For twitter type
	 * @param body
	 * @param type
	 */
	public NewsEntity(String username,long id, String body, int type) 
	{ 
		super(username, id, body);
		newsType = type;
		comments = new ArrayList<Long>();
		repost_nums = 0;
	}	

	/**
	 * For movie review type
	 * @param newsMovieReview 
	 * @param String
	 */
	public NewsEntity(String username, long id, String t, String body, String mid, String mname, String url, int type) 
	{ 
		super(username, id, body);
		title = t;
		newsType = type;
		movie_id = mid;
		movie_name = mname;
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
	public NewsEntity(String username, long id,  ArrayList<String>receivers, int type) 
	{ 
		super(username, id, null);
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
	public NewsEntity(String username, long id,  String mid, String mname, String url, ArrayList<String>friends, int type) 
	{ 
		super(username, id, null);
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
	public NewsEntity(String username, long id, String mid, String url, int type) 
	{
		super(username, id, null);
		newsType = type;
		movie_id = mid;
		movie_poster_url = url;
		comments = new ArrayList<Long>();
		receiver = null;
		repost_nums = 0;
	}

	
	/*GET FUNCTIONS*/

	public int getNewsType() 
	{
		return newsType;
	}
	
	public String getMovidId()
	{
		return movie_id;
	}
	
	public String getMoviePosterUrl()
	{
		return movie_poster_url;
	}

	public ArrayList<Long> getComments()
	{
		return comments;
	}

	public ArrayList<String>getReceivers()
	{
		return receiver;
	}

	public int getRepostNums()
	{
		return repost_nums;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getMovieName() 
	{
		return movie_name;
	}
	
	/*SET FUNCTIONS*/
	public void addRepostNums()
	{
		repost_nums++;
	}
	
	public void addComment(long cid)
	{
		comments.add(cid);
	}
}

