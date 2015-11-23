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
	private int diff;
	
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
	
	private int calculateNameDiff(int[] v1,int[] v2){
		int result=0;
		for(int i=0;i<v1.length;++i){
			result+=Math.abs(v1[i]-v2[i]);
		}
		return result;
	}
	
	private int[] getCharacterGroup(String s){
		int[] group=new int[36];
		s=s.toLowerCase();
		for(int i=0;i<36;++i) group[i]=0;
		for(int i=0;i<s.length();++i){
			if(s.charAt(i)>='a'&&s.charAt(i)<='z'){
				group[s.charAt(i)-'a']++;
			}
			else if(s.charAt(i)>='0'&&s.charAt(i)<='9')group[26+s.charAt(i)-'0']++;
		}
		return group;
	}
	
	public void setDiff(String name){
		int[] v1=getCharacterGroup(name);
		int[] v2=getCharacterGroup(this.name);
		this.diff=calculateNameDiff(v1,v2);
	}
	
	public int getDiff(){
		return diff;
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
