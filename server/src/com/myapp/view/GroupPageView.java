package com.myapp.view;
import java.util.*;

public class GroupPageView
{
	private long gid;
	private String gname;
	private String creator;
	private Boolean isJoined; 
	private String head_url;
	private String profile_url;
	private String description; 

		
	public GroupPageView(long gid, String gname, String creator, String head_url, 
			String profile_url, String description, boolean inGroup)
	{
		this.gid = gid;
		this.gname = gname;
		this.creator = creator;
		this.head_url = head_url;
		this.profile_url = profile_url;
		this.description = description;
		this.isJoined = new Boolean(inGroup);
		
	}	
	
	public long getId()
	{
		return gid; 
	}
	
	public String getName()
	{
		return gname; 
	}
	
	public String getCreator()
	{
		return creator; 
	}
	
	public Boolean isJoined() 
	{
		return isJoined; 
	}

	public String getHeadUrl()
	{
		return head_url;
	}
	
	public String getProfileUrl()
	{
		return profile_url;
	}
	
	public String getDescription()
	{
		return description;
	}
}