package com.myapp.view;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import org.ocpsoft.prettytime.PrettyTime;

import com.myapp.storage.entity.NewsEntity;
import com.myapp.utils.Const;

public class NewsObjectView {
	private String text;
	private String username;
	private String url;
	private String userUrl;
	private String title;
	private String movieId;
	private String movieName;
	private ArrayList<String> ToList;
	private int type;
	private long releaseTime;
	private int likeNums;	

	public NewsObjectView(String username, String text, String url, String userUrl, String title, String movieId,String movieName, 
			ArrayList<String>ToList, int type, long releaseTime, int likeNums)
	{
		this.username = username;
		this.text = text;
		this.url = url;
		this.userUrl=userUrl;
		this.title = title;
		this.movieId = movieId;
		this.movieName=movieName;
		this.ToList = ToList;
		this.type = type;
		this.releaseTime = releaseTime;
		this.likeNums = likeNums;
	}

	public NewsObjectView(NewsEntity newsEntity, String userUrl)
	{
		if(newsEntity == null)
		{
			return;
		}
		username = newsEntity.getCreator();
		type = newsEntity.getNewsType();
		text = newsEntity.getBody(); 
		//transfer to href
		if(type == Const.NEWS_TWITTER || type == Const.NEWS_TWEET_IN_GROUP)
		{
			text = Const.transferTextToLink(text);
		}
		url = newsEntity.getMoviePosterUrl() ;
		this.userUrl = userUrl;
		title = newsEntity.getTitle(); 
		movieId = newsEntity.getMovidId(); 
		movieName = newsEntity.getMovieName(); 
		releaseTime = newsEntity.getReleaseTime(); 
		likeNums = newsEntity.getLikeNums(); 
		ToList = newsEntity.getReceivers();
	}

	public void setText(String text){
		this.text=text;
	}

	public void setUsername(String username){
		this.username=username;
	}

	public void setUrl(String url){
		this.url=url;
	}

	public void setTitle(String title){
		this.title=title;
	}

	public void setMovieId(String movieId){
		this.movieId=movieId;
	}

	public void setMovieName(String movieName){
		this.movieName=movieName;
	}

	public void setToList(ArrayList<String> ToList){
		this.ToList=ToList;
	}

	public void setType(int type){
		this.type=type;
	}
	
	public void setUserUrl(String userUrl){
		this.userUrl=userUrl;
	}

	public void setReleaseTime(long releaseTime){
		this.releaseTime=releaseTime;
	}

	public String getText(){
		return text;
	}

	public String getUsername(){
		return username;
	}

	public String getUrl(){
		return url;
	}
	
	public String getUserUrl(){
		return userUrl;
	}

	public String getTitle(){
		return title;
	}

	public String getMovieId(){
		return movieId;
	}

	public String getMovieName(){
		return movieName;
	}

	public ArrayList<String> getToList(){
		return ToList;
	}

	public int getType(){
		return type;
	}

	/**
	 * use relative time
	 * @return
	 */
	public String getReleaseTime()
	{
		//Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//return formatter.format(releaseTime);
		return Const.PRETTY_TIME.format(new Date(releaseTime));
	}

	public long getReleaseTimeLong(){
		return releaseTime;
	}

	public String getNote(){
		if(type==Const.NEWS_TWITTER) return "said ";
		if(type==Const.NEWS_MAKE_FRIENDS) return "follow ";
		if(type==Const.NEWS_LIKE_MOVIE) return "liked ";
		if(type==Const.NEWS_MOVIE_REVIEW) return "review movie";
		if(type==Const.NEWS_SHARE_MOVIE) return "shared movie";
		if(type==Const.NEWS_ADD_GROUP) return "joined group";
		if(type==Const.NEWS_CREATE_GROUP) return "created group";
		if(type==Const.NEWS_TWEET_IN_GROUP) return "said in group";
		return "";
	}
}
