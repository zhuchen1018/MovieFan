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
	private String creator;
	private ArrayList<String> keywords = new ArrayList<String>();
	private ArrayList<String> members = new ArrayList<String>();
	private ArrayList<Long> news = new ArrayList<Long>();
	
	public GroupEntity()
	{
	}
	
	public GroupEntity(Long id, String name, String creator)
	{
		this.id = id;
		this.name = name;
		this.creator = creator;
		this.keywords = new ArrayList<String>();
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
	public void addKeyword(String word)
	{
		keywords.add(word);
	}
	
	public void delKeyword(String word)
	{
		if(keywords.contains(word))
		{
			keywords.remove(word);
		}
	}
	
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
}
