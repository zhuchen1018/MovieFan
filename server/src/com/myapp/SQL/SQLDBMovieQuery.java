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
	private MovieListView movieList;
	private MoviePageView homepage;
	private PersonObjectView director;
	private PersonListView personList;
	private HashMap<String,Integer> map;
	private ArrayList<String> names;
	
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
	
	public SQLDBMovieQuery(String value,int searchMode) throws Exception{
		conn=SQLDBWrapper.getConnection();
		if(conn==null) throw new Exception("connection not created!");
		setMovieGenreId();
		if(searchMode==Const.ID_SEARCH){ 
			this.movieId=value;
			if(!searchMovieByMovieId()){
				throw new Exception("Id search fail!");
			}
		}
		else if(searchMode==Const.NAME_SEARCH){
			this.name=value;
			if(!searchMovieByName()){
				throw new Exception("Name search fail!");
			}
		}
		conn.close();
	}
	
	public SQLDBMovieQuery(String orderBy,String genre) throws Exception{
		conn=SQLDBWrapper.getConnection();
		if(conn==null) throw new Exception("connection not created!");
		setMovieGenreId();
		this.orderBy=orderBy;
		this.genre=genre;
		if(!advancedSearch()){
			throw new Exception("Advanced search fail!");
		}
		conn.close();
	}
	
	private boolean QueryMovieObject(){
		try{
			state=conn.createStatement();
			rs=state.executeQuery(sql);
			movieList=new MovieListView();
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
				movieList.addMovie(movieObj);
				count++;
			}
			//System.out.println(list.getMovieNumber());
			if(names!=null){
				String s="";
				for(int i=0;i<names.size();++i) s+=names.get(i);
				for(int i=0;i<movieList.getMovieNumber();++i){
					movieList.getMovies().get(i).setDiff(s);
				}
				Collections.sort(movieList.getMovies(), new Comparator(){
					public int compare(Object s1,Object s2){
						int n1=((MovieObjectView)s1).getDiff();
						int n2=((MovieObjectView)s2).getDiff();
						return n1-n2;
					}
				});
			}
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
			sql="select * from basicTMDBInfo where movieId='"+movieId+"'";
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
			//basic info
			sql="select b.movieId,y.url from basicTMDBInfo b join YoutubeTrailer y on b.movieId=y.movieId ";
			sql+="where y.movieId='"+movieId+"'";
			rs=state.executeQuery(sql);
			ArrayList<String> youtubeTrailer=new ArrayList<String>();
			while(rs.next()){
				youtubeTrailer.add(rs.getString("Url"));
			}
			homepage.setYoutube_Trailer(youtubeTrailer);
			//youtube trailer
			sql="select b.movieId,a.name from basicTMDBInfo b join AlternateTitle a on b.movieId=a.movieId ";
			sql+="where a.movieId='"+movieId+"'";
			rs=state.executeQuery(sql);
			ArrayList<String> alternateTitle=new ArrayList<String>();
			while(rs.next()){
				alternateTitle.add(rs.getString("Name"));
			}
			homepage.setAlternate_title(alternateTitle);
			//alternate title
			sql="select p.profile,p.name,p.personId,p.dayOfBirth,p.dayOfDeath,p.biography ";
			sql+="from basicTMDBInfo b join MoviePerson m on b.movieId=m.movieId ";
			sql+="join Person p on p.personId=m.personId ";
			sql+="where b.movieId='"+movieId+"'";
			rs=state.executeQuery(sql);
			personList=new PersonListView();
			while(rs.next()){
				PersonObjectView p=new PersonObjectView();
				p.setProfile(rs.getString("profile"));
				p.setName(rs.getString("name"));
				p.setPersonId(rs.getString("personId"));
				p.setDayOfBirth(rs.getString("dayOfBirth"));
				p.setDayOfDeath(rs.getString("dayOfDeath"));
				p.setBiography(rs.getString("biography"));
				personList.addPerson(p);
			}
			homepage.setCast(personList);
			//cast
			sql="select p.profile,p.name,p.personId,p.dayOfBirth,p.dayOfDeath,p.biography ";
			sql+="from basicTMDBInfo b join Person p on b.directorId=p.personId "; 
			sql+="where b.movieId='"+movieId+"'";
			rs=state.executeQuery(sql);
			director=new PersonObjectView();
			while(rs.next()){
				director.setProfile(rs.getString("profile"));
				director.setName(rs.getString("name"));
				director.setPersonId(rs.getString("personId"));
				director.setDayOfBirth(rs.getString("dayOfBirth"));
				director.setDayOfDeath(rs.getString("dayOfDeath"));
				director.setBiography(rs.getString("biography"));
			}
			homepage.setDirector(director);
			return true;
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("search fail!");
			return false;
		}
	}
	
	private boolean searchMovieByName(){
		conn=SQLDBWrapper.getConnection();
		names=new ArrayList<String>();
		String s=name;
		s=s.trim();
		while(s.indexOf(" ")!=-1){
			names.add(s.substring(0, s.indexOf(" ")));
			s=s.substring(s.indexOf(" "), s.length());
			s=s.trim();
		}
		names.add(s);
		sql="select * from basicTMDBInfo where title like '%";
		for(int i=0;i<names.size();++i){
			sql+=names.get(i)+"%";
		}
		sql+="'";
		System.out.println(sql);
		return QueryMovieObject();
	}
	
	private boolean searchMovieByMovieId(){
		conn=SQLDBWrapper.getConnection();
		return QueryMoviePage();
	}
	
	private boolean advancedSearch(){
		conn=SQLDBWrapper.getConnection();
		sql="select * from basicTMDBInfo b join movieGenre m on b.movieId=m.movieId where m.genreId='";
		sql+=String.valueOf(map.get(genre))+"' order by ";
		sql+=orderBy;
		sql+=" DESC";
		System.out.println(sql);
		return QueryMovieObject();
	}
	
	public MovieListView getMovieObject(){
		return movieList;
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
		SQLDBMovieQuery sql=null;
		try{
			//SQLDBMovieQuery sql=new SQLDBMovieQuery("USERRATING","Adventure");
			sql=new SQLDBMovieQuery("A P",Const.NAME_SEARCH);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		MovieListView m=sql.getMovieObject();
		for(int i=0;i<m.getMovieNumber();++i){
			System.out.println("-------------------");
			System.out.println(m.getMovies().get(i).getName());
			System.out.println(m.getMovies().get(i).getOverview());
			System.out.println("-------------------");
		}
		System.out.println(m.getMovieNumber());
		/*SQLDBMovieQuery sql=null;
		try{
			sql=new SQLDBMovieQuery("843",Const.ID_SEARCH);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		MoviePageView v=sql.getMovieHomepage();
		System.out.println(v.getName());
		System.out.println(v.getOverview());
		for(int i=0;i<v.getYoutube_trailer().size();++i){
			System.out.println(v.getYoutube_trailer().get(i));
		}
		for(int i=0;i<v.getAlternate_title().size();++i){
			System.out.println(v.getAlternate_title().get(i));
		}
		System.out.println(v.getDirector().getName());
		for(int i=0;i<v.getCast().getPersonNumber();++i){
			System.out.println(v.getCast().getPersons().get(i).getName());
		}*/
	}

}
