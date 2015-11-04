package com.myapp.storage.entity;

import java.util.*;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ChannelEntity 
{
	@PrimaryKey
	private String name;

	private String[] xpaths; 
	private HashMap<String, Long>urls; 
	private String creator;
	
	public ChannelEntity()
	{
		urls = new HashMap<String, Long>();
	}
	
	public ChannelEntity(String name, String[] paths, String creator)
	{
		this.name = name;
		this.xpaths = paths;
		this.creator = creator;
		urls = new HashMap<String, Long>();
	}

	/*get funcs*/
	public String getName()
	{
		return name;
	}
	
	public String getCreator()
	{
		return creator;
	}

	public String[] getXPaths()
	{
		return xpaths;
	}
	
	/*set funcs*/
	public void setName(String data)
	{
		name = data;
	}
	
	public void setCreator(String data)
	{
		creator = data;
	}

	public void setXPaths(String[] data)
	{
		xpaths  = data;
	}
	
	public void addUrl(String url, Long crawl_time)
	{
		if(urls == null)
		{
			urls = new HashMap<String, Long>();
		}
		urls.put(url, crawl_time);
	}

	public HashMap<String, Long> getUrls() 
	{
		return urls;
	}
	
	private static void print(String s)
	{
		System.out.println(s);
	}
}
