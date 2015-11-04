package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class UserEntity
{
	@PrimaryKey
	private String name;

	private String password;
	private long login_time; 
	static private ArrayList<TweetEntity>tweets = new ArrayList<TweetEntity>(); 
	
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

	public long getLogin() 
	{
		return login_time;
	}
	
	public void addTweet(String info)
	{
		tweets.add(new TweetEntity(info));
	}

	public ArrayList<TweetEntity> getAllTweets() 
	{
		return tweets;
	}
}
