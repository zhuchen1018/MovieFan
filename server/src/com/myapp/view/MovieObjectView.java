package com.myapp.view;
import java.util.*;

public class MovieObjectView {
	private String movieId;
	private String name;
	private String overview;
	private String poster;
	private String releaseDate;
	private int votes;
	private int length;
	private double rating;
	
	public MovieObjectView(){
		
	}
	
	public MovieObjectView(String movieId,String name,String overview,String poster,String releaseDate,
			int votes,int length,double rating){
		this.movieId=movieId;
		this.name=name;
		this.overview=overview;
		this.poster=poster;
		this.releaseDate=releaseDate;
		this.votes=votes;
		this.length=length;
		this.rating=rating;
	}
	
	public void setMovieId(String movieId){
		this.movieId=movieId;
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
	
	public void setReleaseDate(String releaseDate){
		this.releaseDate=releaseDate;
	}
	
	public void setVotes(int votes){
		this.votes=votes;
	}
	
	public void setLength(int length){
		this.length=length;
	}
	
	public void setRating(double rating){
		this.rating=rating;
	}
	
	public String getMovieId(){
		return movieId;
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
	
	public String getReleaseDate(){
		return releaseDate;
	}
	
	public int getVotes(){
		return votes;
	}
	
	public int getLength(){
		return length;
	}
	
	public double getRating(){
		return rating;
	}
}
