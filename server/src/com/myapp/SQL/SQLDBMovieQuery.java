package com.myapp.SQL;
import com.myapp.view.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SQLDBMovieQuery {
	private Connection conn;
	private Statement state;
	private ResultSet rs;
	private String name;
	private String movieId;
	private int length;
	private int orderBy;
	private int year;
	private int votes;
	private String sql;
	private String[] genres;
	private MovieListView list;
	private HashMap<String,Integer> map;
	public static final int ORDERBY_RATING=0,ORDERBY_TIME=1,ORDERBY_VOTES=2;
	
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
	
	public SQLDBMovieQuery(){
		conn=SQLDBWrapper.getConnection();
		name=null;
		movieId=null;
		length=0;
		setMovieGenreId();
	}
	
	public SQLDBMovieQuery(String name){
		conn=SQLDBWrapper.getConnection();
		this.name=name;
		setMovieGenreId();
		searchMovie();
	}
	
	public SQLDBMovieQuery(String name,String movieId,int length,String orderbyName,String[] genres){
		conn=SQLDBWrapper.getConnection();
		this.name=name;
		this.movieId=movieId;
		this.length=length;
		if(orderbyName.equals("USERRATING")) orderBy=ORDERBY_RATING;
		else if(orderbyName.equals("RELEASEDATE")) orderBy=ORDERBY_TIME;
		else orderBy=ORDERBY_VOTES;
		this.genres=genres;
		setMovieGenreId();
		searchMovie();
	}
	
	public void searchMovie(){
		if(conn==null) return;
		String cond[]=new String[3+genres.length];
		for(int i=0;i<3+genres.length;++i) cond[i]=null;
		boolean emptySearch=true,firstCondition=true,genreRequired=true;
		if(genres==null||genres.length==0) genreRequired=false;
		if(name!=null){ 
			cond[0]="title='"+name+"' ";
			emptySearch=false;
		}
		if(movieId!=null){ 
			cond[1]="movieId='"+movieId+"' ";
			emptySearch=false;
		}
		if(length!=0){ 
			cond[2]="Runtime="+String.valueOf(length)+" ";
			emptySearch=false;
		}
		for(int i=0;i<genres.length;++i){
			cond[3+i]="genreId='"+String.valueOf((int)map.get(genres[i])+"' ");
		}
		//if(emptySearch) return;
		if(!genreRequired)
			sql="select * from basicTMDBInfo where ";
		else
			sql="select * from basicTMDBInfo b join MovieGenre m on b.movieId=m.movieId where ";
		for(int i=0;i<3+genres.length;++i){
			if(cond[i]!=null){
				if(firstCondition){
					firstCondition=false;
					sql+=cond[i];
				}
				else{
					sql+="and ";
					sql+=cond[i];
				}
			}
		}
		if(orderBy==ORDERBY_RATING) sql+="order by userrating DESC";
		else if(orderBy==ORDERBY_TIME) sql+="order by releaseDate DESC";
		else if(orderBy==ORDERBY_TIME) sql+="order by votes DESC";
		sql=sql.trim();
		System.out.println("in");
		System.out.println(sql);
		try{
			state=conn.createStatement();
			rs=state.executeQuery(sql);
			list=new MovieListView();
			int count=0;
			while(rs.next()&&count<10){
				MovieObjectView movieObj=new MovieObjectView();
				movieObj.setName(rs.getString("title"));
				movieObj.setOverview(rs.getString("overview"));
				movieObj.setPageUrl(rs.getString("Homepage"));
				movieObj.setPoster(rs.getString("poster"));
				movieObj.setRating(rs.getDouble("userrating"));
				list.addMovie(movieObj);
				count++;
			}
			System.out.println(list.getMovieNumber());
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("search fail!");
		}
	}
	
	public MovieListView getMovieObject(){
		return list;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setLength(int length){
		this.length=length;
	}
	
	public void setMovieId(String movieId){
		this.movieId=movieId;
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
		//SQLDBMovieQuery sql=new SQLDBMovieQuery("In the Mood for Love");
		String[] genres={"Action","War"};
		SQLDBMovieQuery sql=new SQLDBMovieQuery(null,null,0,"USERRATING",genres);
		MovieListView m=sql.getMovieObject();
		for(int i=0;i<m.getMovieNumber();++i){
			System.out.println(m.getMovies().get(i).getName());
			System.out.println(m.getMovies().get(i).getOverview());
		}
	}

}
