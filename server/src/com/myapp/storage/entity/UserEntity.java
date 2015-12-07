package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;


@Entity
public class UserEntity
{
	@PrimaryKey
	private String name;

	private String password;
	//jpeg
	private String head_url;

	//jpeg
	private String profile_url;

	private long login_time; 

	//news' id
	private ArrayList<Long>news= new ArrayList<Long>(); 

	//like movie' id
	private ArrayList<String>likeMovies = new ArrayList<String>(); 

	//friends username: following 
	private ArrayList<String>friends = new ArrayList<String>(); 

	//fans username: followers 
	private ArrayList<String>fans = new ArrayList<String>(); 

	//comment's id
	private ArrayList<Long>comments = new ArrayList<Long>(); 

	//join group 's id
	private ArrayList<Long>joinGroups = new ArrayList<Long>(); 

	//create group 's id
	private ArrayList<Long>createGroups = new ArrayList<Long>(); 

	//contain private message: 
	//user follow you, user tag you
	//private ArrayList<Long>mailbox= new ArrayList<Long>(); 

	//description
	private String description;

	//like genres
	private ArrayList<Integer>likeGenres = new ArrayList<Integer>(); 
	
	public UserEntity()
	{
		
	}

	public UserEntity(String n, String p, long time) 
	{
		name = n;
		password = p;
		login_time = time;
	}

	/*get funcs*/
	public String getName()
	{
		return name;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getHeadUrl() 
	{
		return head_url;
	}

	public long getLogin() 
	{
		return login_time;
	}

	public ArrayList<Long> getJoinGroups() 
	{
		return joinGroups;
	}
	
	public ArrayList<String> getFriends() 
	{
		return friends;
	}
	
	public ArrayList<Long> getComments() 
	{
		return comments;
	}

	public ArrayList<Long> getNews() 
	{
		return news;
	}	
	
	
	/*set funcs*/
	public void setName(String data)
	{
		name = data;
	}

	public void setPassword(String data)
	{
		password = data;
	}
	
	public void setLogin(long time)
	{
		login_time = time;
	}

	public void Login()
	{
		long time = (new Date()).getTime();
		login_time = time;
	}	
	
	public void addNews(Long id)
	{
		news.add(id);
	}

	public void addFriend(String friendname) 
	{
		if(friends.contains(friendname))
		{
			return;
		}
		friends.add(friendname);
	}
	
	public void removeFriend(String friendname) 
	{
		friends.remove(friendname);
	}

	public void joinGroup(Long id) 
	{
		if(!joinGroups.contains(id))
		{
			joinGroups.add(id);
		}
	}

	public void leaveGroup(Long id) 
	{
		joinGroups.remove(id);
	}

	public boolean isMyFriend(String targetName) 
	{
		return friends.contains(targetName);
	}

	public boolean isLikeMovie(String movie_id) 
	{
		return likeMovies.contains(movie_id); 
	}

	public boolean canCreateGroup() 
	{
		return createGroups.size() < 3;
	}

	public void addFans(String username) 
	{
		fans.add(username);
	}

	public void addHeadUrl(String url) 
	{
		head_url = url;
	}

	public void likeMovie(String movieId) 
	{
		if(!isLikeMovie(movieId))
		{
			likeMovies.add(movieId);
		}
	}
	
	public void unlikeMovie(String movieId) 
	{
		if(isLikeMovie(movieId))
		{
			likeMovies.remove(movieId);
		}
	}

	public String getProfileUrl() 
	{
		return profile_url;
	}

	public Integer[] getLikeGenres() 
	{
		return likeGenres.toArray(new Integer[likeGenres.size()]);
	}

	public String getDescription() 
	{
		return description;
	}

	public void upSettings(String head_url, String profile_url, String description, Integer[] genres) 
	{
		if(head_url != null && !head_url.isEmpty())
		{
			this.head_url = head_url;
		}
		if(profile_url != null && !profile_url.isEmpty())
		{
			this.profile_url = profile_url;
		}
		if(description != null && !description.isEmpty())
		{
			this.description = description;
		}
		if(genres == null)
		{
			this.likeGenres = new ArrayList<Integer>();
		}
		else
		{
			this.likeGenres = new ArrayList<Integer>(Arrays.asList(genres));
		}
	}

	public void addCreateGroup(Long id) 
	{
		createGroups.add(id);
		joinGroups.add(id);
	}
	
	public void addNewMail(Long mailId)
	{
		//mailbox.add(mailId);
	}
	
	public int getFansNum()
	{
		return fans.size();
	}
	
	public int getFollowingNum()
	{
		return friends.size();
	}
	
	public int getNewsNum()
	{
		return news.size();
	}

	public ArrayList<String> getFollowings() 
	{
		return friends;
	}
	
	public ArrayList<String> getFans() 
	{
		return fans;
	}
}
