package com.myapp.view;
import java.util.*;

public class GroupPageView
{
	private long gid;
	private String gname;
	private String creator;
	private Boolean isJoined; 

		
	public GroupPageView(long gid, String gname, String creator, boolean inGroup)
	{
		this.gid = gid;
		this.gname = gname;
		this.creator = creator;
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
}