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
	private Set<String> keywords = new HashSet<String>();
	private Set<String> members = new HashSet<String>();
	
	public GroupEntity()
	{
	}
	
	public GroupEntity(Long id, String name, String creator)
	{
		this.id = id;
		this.name = name;
		this.creator = creator;
		this.keywords = new HashSet<String>();
		this.members = new HashSet<String>();
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

	public void addMember(String username) 
	{
		members.add(username);
	}
}
