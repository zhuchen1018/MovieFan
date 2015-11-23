package com.myapp.view;
import java.util.*;

public class MoviePageView{
	private String movieId;
	private String name;
	private String overview;
	private String poster;
	private String releaseDate;
	private String homepage;
	private String tagline;
	private int votes;
	private int length;
	private double rating;
	private ArrayList<String> youtube_trailer;
	private ArrayList<String> alternate_title;
	private PersonObjectView director;
	private PersonListView list;
	
	public MoviePageView(){
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
	
	public void setHomepage(String homepage){
		this.homepage=homepage;
	}
	
	public void setTagline(String tagline){
		this.tagline=tagline;
	}
	
	public void setAlternate_title(ArrayList<String> alternate_title){
		this.alternate_title=alternate_title;
	}
	
	public void setYoutube_Trailer(ArrayList<String> youtube_trailer){
		this.youtube_trailer=youtube_trailer;
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
	
	public void setDirector(PersonObjectView director){
		this.director=director;
	}
	
	public void setCast(PersonListView list){
		this.list=list;
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
	
	public String getHomepage(){
		return homepage;
	}
	
	public String getTagline(){
		return tagline;
	}
	
	public ArrayList<String> getAlternate_title(){
		return alternate_title;
	}
	
	public ArrayList<String> getYoutube_trailer(){
		return youtube_trailer;
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
	
	public PersonObjectView getDirector(){
		return director;
	}
	
	public PersonListView getCast(){
		return list;
	}
}