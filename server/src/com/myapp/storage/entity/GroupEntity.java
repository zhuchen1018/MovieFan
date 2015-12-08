package com.myapp.storage.entity;

import java.util.*;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class GroupEntity 
{
	@PrimaryKey
	private Long id;
	private String name;
	private String head_url;
	private String profile_url;
	private String creator;
	//private ArrayList<String> keywords = new ArrayList<String>();
	private ArrayList<String> members = new ArrayList<String>();
	private ArrayList<Long> news = new ArrayList<Long>();
	private String description;
	
	public GroupEntity()
	{
	}
	
	public GroupEntity(Long id, String name, String creator)
	{
		this.id = id;
		this.name = name;
		this.creator = creator;
		this.members = new ArrayList<String>();
	}

	/*get funcs*/
	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}
	
	public String getCreator()
	{
		return creator;
	}

	public ArrayList<String> getMembers()
	{
		return members;
	}
	
	/*set funcs*/
	
	
	private static void print(String s)
	{
		System.out.println(s);
	}

	public String getHeadUrl() 
	{
		return head_url;
	}
	
	public ArrayList<Long>getNews() 
	{
		return news;
	}

	public void addMember(String username) 
	{
		if(!members.contains(username))
		{
			members.add(username);
		}
	}

	public void removeMember(String username) 
	{
		members.remove(username);
	}

	public boolean hasMember(String username) 
	{
		return members.contains(username);
	}

	public void addHeadUrl(String url) 
	{
		head_url = url;
	}

	public void setHeadUrl(String data) 
	{
		head_url = data;
	}

	public void addNews(long id) 
	{
		news.add(id);
	}
	
	public String getProfileUrl()
	{
		return profile_url;
	}
	
	public void setProfileUrl(String data)
	{
		profile_url = data;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String data)
	{
		description = data; 
	}

	public void upSettings(String head_url , String profile_url , String description) 
	{
		this.head_url = head_url;
		this.profile_url = profile_url; 
		this.description = description;
	}

	public boolean isCreator(String username) 
	{
		return creator.equals(username);
	}
}
