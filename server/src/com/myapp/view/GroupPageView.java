package com.myapp.view;
import java.util.*;

public class GroupPageView
{
	private long gid;
	private String gname;
	private String creator;
	private Boolean isJoined; 
	private NewsListView nlv;
	private UserListView ulv;
		
	public GroupPageView(long gid, String gname, String creator, NewsListView nlv, UserListView ulv, boolean inGroup)
	{
		this.gid = gid;
		this.gname = gname;
		this.creator = creator;
		this.isJoined = new Boolean(inGroup);
		this.nlv = nlv;
		this.ulv = ulv;
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
	
	public NewsListView getNewsListView() 
	{
		return nlv; 
	}
	
	public UserListView getUserListView()
	{
		return ulv;
	}
	
	public Boolean isJoined() 
	{
		return isJoined; 
	}
}