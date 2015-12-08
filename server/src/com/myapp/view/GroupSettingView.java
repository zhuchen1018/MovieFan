package com.myapp.view;

public class GroupSettingView 
{
	protected String head_url;
	protected String profile_url;
	protected String description; 
	
	public GroupSettingView()
	{
		
	}
	
	public GroupSettingView(String head_url,String profile_url, String description) 
	{
		this.head_url = head_url;
		this.profile_url = profile_url;
		this.description = description;
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
