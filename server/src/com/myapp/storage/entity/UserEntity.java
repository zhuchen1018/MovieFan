package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;


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
	
	public void addNews(Long id)
	{
		news.add(id);
	}

	public void addFriend(String friendname) 
	{
		friends.add(friendname);
	}

	public void addGroup(Long id) 
	{
		if(!groups.contains(id))
		{
			groups.add(id);
		}
	}

	public void leaveGroup(Long id) 
	{
		groups.remove(id);
	}
}
