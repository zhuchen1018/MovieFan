package com.myapp.view;

public class UserSettingView 
{
	protected String head_url;
	protected String profile_url;
	protected Integer[] genres;
	protected String description; 
	
	public UserSettingView()
	{
		
	}
	
	public UserSettingView(String head_url,String profile_url, Integer[] genres, String description) 
	{
		this.head_url = head_url;
		this.profile_url = profile_url;
		this.genres = genres;
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
	
	public Integer[] getGenres()
	{
		return genres;
	}
	
	public String getDescription()
	{
		return description;
	}
	
}
