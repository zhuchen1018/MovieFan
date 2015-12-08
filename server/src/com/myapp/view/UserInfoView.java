package com.myapp.view;

import java.util.ArrayList;
import com.myapp.utils.*;

public class UserInfoView extends UserSettingView 
{
	private int fansNum; 
	private int followingNum; 
	private int newsNum; 
	private Boolean isMyPage; 
	private Boolean isMyFriend; 
	private ArrayList<String>posterUrl = new ArrayList<String>(); 
	private ArrayList<String>movieId = new ArrayList<String>(); 
	
	public UserInfoView()
	{
		
	}
	
	public UserInfoView(String head_url,String profile_url, Integer[] genres, 
			String description, int fansNum, int followingNum, int newsNum, 
			Boolean isMyPage, Boolean isMyFriend, ArrayList<String>movieId, ArrayList<String>posterUrl)
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
		this.movieId = movieId;
		this.posterUrl = posterUrl;
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
	
	public String[] getString_Genres()
	{
		String[] string_genres=new String[genres.length];
		for(int i=0;i<string_genres.length;++i){
			string_genres[i]=Const.REVERSE_GENRE_MAP.get(genres[i]);
		}
		return string_genres;
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
	
	public ArrayList<String> getPosterUrl()
	{
		return posterUrl;
	}	
	
	public ArrayList<String> getMovieId()
	{
		return movieId;
	}	
}
