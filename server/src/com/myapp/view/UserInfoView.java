package com.myapp.view;

import java.util.ArrayList;

public class UserInfoView extends UserSettingView 
{
	private int fansNum; 
	private int followingNum; 
	private int newsNum; 
	private Boolean isMyPage; 
	private Boolean isMyFriend; 
	private ArrayList<String>likeMovies = new ArrayList<String>(); 
	
	public UserInfoView()
	{
		
	}
	
	public UserInfoView(String head_url,String profile_url, Integer[] genres, 
			String description, int fansNum, int followingNum, int newsNum, 
			Boolean isMyPage, Boolean isMyFriend, ArrayList<String>likeMovies)
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
		this.likeMovies = likeMovies;
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
	
	public int getFansNum()
	{
		return fansNum;
	}
	
	public int getFollowingNum()
	{
		return followingNum;
	}

	public int getNewsNum()
	{
		return newsNum;
	}
	
	public ArrayList<String> getLikeMovies()
	{
		return likeMovies;
	}	
}
