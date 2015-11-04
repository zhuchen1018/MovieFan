package com.myapp.storage.entity;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.Date;

/*
 * Url DB entity:
 * url addr: name
 * last modified: to check if a url need to be re-downloaded 
 */
@Entity
public class WebPageEntity
{
	@PrimaryKey
	private String url = "";
	private String content = "";
	private long last_modified; 

	public WebPageEntity()
	{
		
	}

	public WebPageEntity(String addr, String content, long date)
	{
		this.url = addr;
		this.content = content;
		this.last_modified = date;
	}
	
	/*get funcs*/
	public String getName()
	{
		return url;
	}
	
	public String getContent()
	{
		return content;
	}

	public long getLastModifiedTime()
	{
		return last_modified;
	}

	/*set funcs*/
	
	public void setName(String data)
	{
		url = data;
	}
		
	public void setContent(String data)
	{
		content = data;
	}

	public void setCrawlTime(long data)
	{
		last_modified = data;
	}	
}
