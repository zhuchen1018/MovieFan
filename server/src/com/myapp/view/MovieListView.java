package com.myapp.view;
import java.util.*;

public class MovieListView {
	private ArrayList<MovieObjectView> movies;
	private int movieCount;
	
	public MovieListView(){
		movies=new ArrayList<MovieObjectView>();
		movieCount=0;
	}
	
	public MovieListView(ArrayList<MovieObjectView> movies){
		this.movies=movies;
		this.movieCount=movies.size();
	}
	
	public void setMovies(ArrayList<MovieObjectView> movies){
		this.movies=movies;
		this.movieCount=movies.size();
	}
	
	public void addMovie(MovieObjectView movie){
		movies.add(movie);
		movieCount++;
	}
	
	public ArrayList<MovieObjectView> getMovies(){
		return movies;
	}
	
	public int getMovieNumber(){
		return movieCount;
	}
}
