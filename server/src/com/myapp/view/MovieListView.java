package com.myapp.view;
import java.util.*;

public class MovieListView {
	private MovieObjectView[] movies;
	private int movieCount;
	
	public MovieListView(){
		movieCount=0;
	}
	
	public MovieListView(MovieObjectView[] movies){
		this.movies=movies;
		this.movieCount=movies.length;
	}
	
	public void setMovies(MovieObjectView[] movies){
		this.movies=movies;
		this.movieCount=movies.length;
	}
	
	public MovieObjectView[] getMovies(){
		return movies;
	}
	
	public int getMovieNumber(){
		return movieCount;
	}
}
