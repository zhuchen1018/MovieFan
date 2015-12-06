package com.myapp.view;
import java.util.*;

public class GroupPageView
{
	private long gid;
	private String gname;
	private String creator;
	private ArrayList<String>members; 
	private Boolean isJoined; 
		
	public GroupPageView(long gid, String gname, String creator, ArrayList<String>members, boolean inGroup)
	{
		this.gid = gid;
		this.gname = gname;
		this.creator = creator;
		this.members = members;
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
	
	public ArrayList<String>getMembers()
	{
		return members; 
	}
	
	public Boolean isJoined() 
	{
		return isJoined; 
	}
}