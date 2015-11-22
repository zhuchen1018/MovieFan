package com.myapp.SQL;
import com.myapp.view.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import com.myapp.utils.*;

public class SQLDBMovieQuery {
	private Connection conn;
	private Statement state;
	private ResultSet rs;
	private String name;
	private String movieId;
	private int length;
	private int year;
	private int votes;
	private String sql;
	private String orderBy;
	private String genre;
	private MovieListView list;
	private MoviePageView homepage;
	private HashMap<String,Integer> map;
	
	private void setMovieGenreId(){
		map=new HashMap<String,Integer>();
		map.put("Adventure", 0);
		map.put("Animation", 1);
		map.put("Children", 2);
		map.put("Comedy", 3);
		map.put("Fantasy", 4);
		map.put("Romance", 5);
		map.put("Drama", 6);
		map.put("Action", 7);
		map.put("Crime", 8);
		map.put("Thriller", 9);
		map.put("Horror", 10);
		map.put("Mystery", 11);
		map.put("Sci-Fi", 12);
		map.put("Documentary", 14);
		map.put("War", 15);
		map.put("Musical", 16);
	}
	
	public SQLDBMovieQuery(String value,int searchMode){
		conn=SQLDBWrapper.getConnection();
		setMovieGenreId();
		if(searchMode==Const.ID_SEARCH){ 
			this.movieId=value;
			searchMovieByMovieId();
		}
		else if(searchMode==Const.NAME_SEARCH){
			this.name=value;
			searchMovieByName();
		}
	}
	
	public SQLDBMovieQuery(String orderBy,String genre){
		conn=SQLDBWrapper.getConnection();
		setMovieGenreId();
		this.orderBy=orderBy;
		this.genre=genre;
		advancedSearch();
	}
	
	private boolean QueryMovieObject(){
		try{
			state=conn.createStatement();
			rs=state.executeQuery(sql);
			list=new MovieListView();
			int count=0;
			while(rs.next()&&count<30){
				MovieObjectView movieObj=new MovieObjectView();
				movieObj.setMovieId(rs.getString("movieId"));
				movieObj.setName(rs.getString("title"));
				movieObj.setOverview(rs.getString("overview"));
				movieObj.setPoster(rs.getString("poster"));
				movieObj.setReleaseDate(rs.getString("releaseDate"));
				movieObj.setVotes(rs.getInt("vote"));
				movieObj.setLength(rs.getInt("runtime"));
				movieObj.setRating(rs.getDouble("userrating"));
				list.addMovie(movieObj);
				count++;
			}
			//System.out.println(list.getMovieNumber());
			return true;
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("search fail!");
			return false;
		}
	}
	
	private boolean QueryMoviePage(){
		try{
			state=conn.createStatement();
			rs=state.executeQuery(sql);
			homepage=new MoviePageView();
			while(rs.next()){
				homepage.setMovieId(rs.getString("movieId"));
				homepage.setName(rs.getString("title"));
				homepage.setOverview(rs.getString("overview"));
				homepage.setPoster(rs.getString("poster"));
				homepage.setReleaseDate(rs.getString("releaseDate"));
				homepage.setHomepage(rs.getString("homepage"));
				homepage.setVotes(rs.getInt("vote"));
				homepage.setLength(rs.getInt("runtime"));
				homepage.setRating(rs.getDouble("userrating"));
			}
			return true;
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("search fail!");
			return false;
		}
	}
	
	private void searchMovieByName(){
		conn=SQLDBWrapper.getConnection();
		sql="select * from basicTMDBInfo where title='"+name+"'";
		System.out.println(sql);
		QueryMovieObject();
	}
	
	private void searchMovieByMovieId(){
		conn=SQLDBWrapper.getConnection();
		sql="select * from basicTMDBInfo where movieId='"+movieId+"'";
		System.out.println(sql);
		QueryMoviePage();
	}
	
	private void advancedSearch(){
		conn=SQLDBWrapper.getConnection();
		sql="select * from basicTMDBInfo b join movieGenre m on b.movieId=m.movieId where m.genreId='";
		sql+=String.valueOf(map.get(genre))+"' order by ";
		sql+=orderBy;
		sql+=" DESC";
		System.out.println(sql);
		QueryMovieObject();
	}
	
	public MovieListView getMovieObject(){
		return list;
	}
	
	public MoviePageView getMovieHomepage(){
		return homepage;
	}
	
	public void closeConnection(){
		if(conn!=null){
			try{
				conn.close();
			}
			catch(SQLException ex){
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//SQLDBMovieQuery sql=new SQLDBMovieQuery("In the Mood for Love",Const.NAME_SEARCH);
		SQLDBMovieQuery sql=new SQLDBMovieQuery("USERRATING","Adventure");
		MovieListView m=sql.getMovieObject();
		for(int i=0;i<m.getMovieNumber();++i){
			System.out.println(m.getMovies().get(i).getName());
			System.out.println(m.getMovies().get(i).getOverview());
		}
		/*SQLDBMovieQuery sql=new SQLDBMovieQuery("843",Const.ID_SEARCH);
		MoviePageView v=sql.getMovieHomepage();
		System.out.println(v.getName());
		System.out.println(v.getOverview());*/
	}

}
