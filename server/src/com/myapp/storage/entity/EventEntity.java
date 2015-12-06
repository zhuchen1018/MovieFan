package com.myapp.storage.entity;

import java.util.*;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class EventEntity 
{
	@PrimaryKey
	private Long id;
	private String body;
	//username
	private String creator;
	//username
	private ArrayList<String> members = new ArrayList<String>();
	
	public EventEntity()
	{
	}
	
	public EventEntity(Long id, String creator, String body)
	{
		this.id = id;
		this.creator = creator;
		this.members = new ArrayList<String>();
	}

	/*get funcs*/
	public Long getId()
	{
		return id;
	}

	public String getCreator()
	{
		return creator;
	}
	
	/*set funcs*/
	private static void print(String s)
	{
		System.out.println(s);
	}

	public ArrayList<String>getMembers() 
	{
		return members;
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
}
