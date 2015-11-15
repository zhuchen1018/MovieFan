package com.myapp.view;
import java.util.*;

public class MoviePageView{
	private String pageUrl;
	private String name;
	private String overview;
	private String poster;
	private double rating;
	
	public MoviePageView(){
		
	}
	
	public MoviePageView(String pageUrl,String name,String overview,String poster,double rating){
		this.pageUrl=pageUrl;
		this.name=name;
		this.overview=overview;
		this.poster=poster;
		this.rating=rating;
	}
	
	public void setPageUrl(String pageUrl){
		this.pageUrl=pageUrl;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setOverview(String overview){
		this.overview=overview;
	}
	
	public void setPoster(String poster){
		this.poster=poster;
	}
	
	public void setRating(double rating){
		this.rating=rating;
	}
	
	public String getPageUrl(){
		return pageUrl;
	}
	
	public String getName(){
		return name;
	}
	
	public String getOverview(){
		return overview;
	}
	
	public String getPoster(){
		return poster;
	}
	
	public double getRating(){
		return rating;
	}
}
