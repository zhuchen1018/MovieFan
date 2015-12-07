package com.myapp.view;

public class UserInfoView extends UserSettingView 
{
	private int fansNum; 
	private int followingNum; 
	private int newsNum; 
	Boolean isMyPage; 
	Boolean isMyFriend; 
	
	public UserInfoView()
	{
		
	}
	
	public UserInfoView(String head_url,String profile_url, Integer[] genres, 
			String description, int fansNum, int followingNum, int newsNum, Boolean isMyPage, Boolean isMyFriend)
	{
		this.head_url = head_url;
		this.profile_url = profile_url;
		this.genres = genres;
		this.description = description;
		this.fansNum = fansNum;
		this.followingNum = followingNum;
		this.newsNum = newsNum;
		this.isMyPage = isMyPage;
		this.isMyFriend = isMyFriend;
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
	
	public Boolean isMyPage()
	{
		return isMyPage;
	}
		
	public Boolean isMyFriend()
	{
		return isMyFriend;
	}
	
}
