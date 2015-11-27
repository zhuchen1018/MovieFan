package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;
import static  com.sleepycat.persist.model.Relationship.*;
import com.sleepycat.persist.model.SecondaryKey;

@Entity
public class UserEntity
{
	@PrimaryKey
	private String name;

	private String password;
	//jpeg
	private String head_url;

	private long login_time; 

	
	//news' id
	private ArrayList<Long>news= new ArrayList<Long>(); 

	//friends username 
	private ArrayList<String>friends = new ArrayList<String>(); 

	/*
	//tweet's id
	private ArrayList<Long>tweets = new ArrayList<Long>(); 
	//article 's id
	private ArrayList<Long>articles = new ArrayList<Long>(); 
	*/
	
	//comment's id
	private ArrayList<Long>comments = new ArrayList<Long>(); 

	//group 's id
	private ArrayList<Long>groups = new ArrayList<Long>(); 
	
	public UserEntity()
	{
		
	}

	public UserEntity(String n, String p, long time) 
	{
		name = n;
		password = p;
		login_time = time;
	}

	/*get funcs*/
	public String getName()
	{
		return name;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getHeadUrl() 
	{
		return head_url;
	}

	public long getLogin() 
	{
		return login_time;
	}

	public ArrayList<Long> getGroups() 
	{
		return groups;
	}
	
	public ArrayList<String> getFriends() 
	{
		return friends;
	}
	
	public ArrayList<Long> getComments() 
	{
		return comments;
	}

	public ArrayList<Long> getNews() 
	{
		return news;
	}	
	
	/*set funcs*/
	public void setName(String data)
	{
		name = data;
	}

	public void setPassword(String data)
	{
		password = data;
	}
	
	public void setLogin(long time)
	{
		login_time = time;
	}

	public void Login()
	{
		long time = (new Date()).getTime();
		login_time = time;
	}	
	
	private void print(String s)
	{
		System.out.println(s);
	}

	
	public void addNews(Long id)
	{
		news.add(id);
	}


	/*
	public void addTweet(Long id)
	{
		tweets.add(id);
	}

	public void addArticle(Long id)
	{
		articles.add(id);
	}

	public ArrayList<Long> getAllTweets() 
	{
		return tweets;
	}
	
	public ArrayList<Long> getAllArticles() 
	{
		return articles;
	}
	*/
	
	
}
