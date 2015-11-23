package com.myapp.view;
import java.util.*;

public class NewsObjectView {
	private String text;
	private String username;
	private String url;
	private String title;
	private String movieId;
	private ArrayList<String> ToList;
	private int type;
	
	public NewsObjectView(){
		text=null;
		username=null;
		url=null;
		title=null;
		movieId=null;
		ToList=null;
		type=-1;
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
	
	public void setToList(ArrayList<String> ToList){
		this.ToList=ToList;
	}
	
	public void setType(int type){
		this.type=type;
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
	
	public String getTitle(){
		return title;
	}
	
	public String getMovieId(){
		return movieId;
	}
	
	public ArrayList<String> getToList(){
		return ToList;
	}
	
	public int getType(){
		return type;
	}
}
