package com.myapp.SQL;
import com.myapp.view.*;
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
	private int date;
	private int votes;
	private String sql;
	private String orderBy;
	private String genre;
	private MovieListView movieList;
	private MoviePageView homepage;
	private PersonObjectView director;
	private PersonListView personList;
	private static HashMap<String,Integer> map;
	private ArrayList<String> names;
	private ArrayList<Integer> genres;
	private ArrayList<String> simple_url,simple_movie_id;
	private HashMap<String,Integer> simple_movie_id_map;
	private String pairId;
		
	static {
		map = Const.GENRE_MAP; 
	}

	public SQLDBMovieQuery(ArrayList<Integer> genres) throws Exception{
		if(genres == null || genres.size() == 0)
		{
			return;
		}
		conn=SQLDBWrapper.getConnection();
		if(conn==null) throw new Exception("connection not created!");
		this.genres=genres;
		searchMovieByGenres();
		conn.close();
	}
	
	public SQLDBMovieQuery(String value,int searchMode) throws Exception
	{
		if(value == null || value.isEmpty())
		{
			return;
		}
		conn=SQLDBWrapper.getConnection();
		if(conn==null) 
			throw new Exception("connection not created!");
		if(searchMode==Const.ID_SEARCH){ 
			this.movieId=value;
			if(!searchMovieByMovieId()){
				conn.close();
				throw new Exception("Id search fail!");
			}
		}
		else if(searchMode==Const.NAME_SEARCH){
			this.name=value;
			if(!searchMovieByName()){
				conn.close();
				throw new Exception("Name search fail!");
			}
		}
		else if(searchMode==Const.PAIR_SEARCH){
			this.pairId=value;
			if(!searchMovieByPairId()){
				conn.close();
				throw new Exception("Name search fail!");
			}
		}
		conn.close();
	}
	
	public SQLDBMovieQuery(String orderBy,String genre,int length) throws Exception{
		conn=SQLDBWrapper.getConnection();
		if(conn==null) throw new Exception("connection not created!");
		this.orderBy=orderBy;
		this.genre=genre;
		this.length=length;
		if(!advancedSearch()){
			conn.close();
			throw new Exception("Advanced search fail!");
		}
		conn.close();
	}
	
	private boolean QuerySimpleMovieObjectByGenre(){
		try{
			state=conn.createStatement();
			rs=state.executeQuery(sql);
			int count=0;
			while(rs.next()&&count<5){
				String s=rs.getString("movieId");
				if(simple_movie_id_map.containsKey(s)) continue;
				simple_movie_id_map.put(s, 0);
				simple_url.add(rs.getString("poster"));
				simple_movie_id.add(s);
				count++;
			}
			return true;
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("QuerySimpleMovieObjectByGenre search fail!");
			return false;
		}
	}
	
	private boolean QuerySimpleMovieObjectByPairId(int n){
		try{
			state=conn.createStatement();
			Statement tmpState=conn.createStatement();
			rs=state.executeQuery(sql);
			int count=0;
			while(rs.next()&&count<5){
				String s=rs.getString("movieId"+n);
				if(simple_movie_id_map.containsKey(s)) continue;
				simple_movie_id_map.put(s, 0);
				simple_movie_id.add(s);
				
				sql="select poster from basicTMDBInfo where movieId='"+s+"'";
				ResultSet rtmp=tmpState.executeQuery(sql);
				rtmp.next();
				simple_url.add(rtmp.getString("poster"));
				count++;
			}
			return true;
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("QuerySimpleMovieObjectByPairId search fail!");
			return false;
		}
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
			System.out.println("QueryMovieObject search fail!");
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
			System.out.println("QueryMoviePage search fail!");
			return false;
		}
	}
	
	private boolean searchMovieByGenres() throws Exception{
		conn=SQLDBWrapper.getConnection();
		if(conn==null) throw new Exception("connection not created!");
		
		simple_url=new ArrayList<String>();
		simple_movie_id=new ArrayList<String>();
		simple_movie_id_map=new HashMap<String,Integer>();
		for(int i=0;i<genres.size();++i){
			sql="select * from basicTMDBInfo b join movieGenre m on b.movieId=m.movieId where m.genreId='";
			sql=sql+genres.get(i)+"' order by userrating Desc";
			if(!QuerySimpleMovieObjectByGenre()) return false;
			System.out.println(sql);
		}
		return true;
	}
	
	private boolean searchMovieByPairId() throws Exception{
		conn=SQLDBWrapper.getConnection();
		if(conn==null) throw new Exception("connection not created!");
		simple_url=new ArrayList<String>();
		simple_movie_id=new ArrayList<String>();
		simple_movie_id_map=new HashMap<String,Integer>();
		
		sql ="select * from movieConnection where movieId1='";
		sql+=pairId+"' order by connection Desc";
		System.out.println(sql);
		if(!QuerySimpleMovieObjectByPairId(2)) return false;
		
		sql ="select * from movieConnection where movieId2='";
		sql+=pairId+"' order by connection Desc";
		System.out.println(sql);
		return QuerySimpleMovieObjectByPairId(1);
	}
	
	private boolean searchMovieByName() throws Exception{
		conn=SQLDBWrapper.getConnection();
		if(conn==null) throw new Exception("connection not created!");
		String[] tokens = name.trim().toLowerCase().split(" ");
		names = new ArrayList<String>(Arrays.asList(tokens)); 
		sql ="select * from basicTMDBInfo where lower(title) like '%";
		for(String s: names)
		{
			sql = sql.concat(s.trim().concat("%"));
		}
		sql += "'";
		System.out.println(sql);
		return QueryMovieObject();
	}
	
	private boolean searchMovieByMovieId(){
		conn=SQLDBWrapper.getConnection();
		return QueryMoviePage();
	}
	
	private boolean advancedSearch(){
		conn=SQLDBWrapper.getConnection();
		int low,high;
		if(length==Const.MOVIE_LENGTH_0_30){
			low=0;high=30;
		}
		else if(length==Const.MOVIE_LENGTH_30_60){
			low=30;high=60;
		}
		else if(length==Const.MOVIE_LENGTH_60_90){
			low=60;high=90;
		}
		else if(length==Const.MOVIE_LENGTH_90_120){
			low=90;high=120;
		}
		else if(length==Const.MOVIE_LENGTH_120_MORE){
			low=120;high=10000;
		}
		else{
			low=-1;high=-1;
		}
		sql="select * from basicTMDBInfo b join movieGenre m on b.movieId=m.movieId where m.genreId='";
		sql+=String.valueOf(map.get(genre))+"'";
		switch(low){
		case -1: break;
		case 0:
		case 30:
		case 60:
		case 90:
			sql+=" and b.runtime>="+String.valueOf(low)+" and b.runtime<"+String.valueOf(high);
			break;
		case 120:
			sql+=" and b.runtime>="+String.valueOf(low);
		}
		sql+=" order by ";
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
	
	public ArrayList<String> getSimpleUrl(){
		return simple_url;
	}
	
	public ArrayList<String> getSimpleMovieId(){
		return simple_movie_id;
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
			//sql=new SQLDBMovieQuery("USERRATING","Adventure",5);
			//sql=new SQLDBMovieQuery("A P",Const.NAME_SEARCH);
			/*ArrayList<Integer> input=new ArrayList<Integer>();
			input.add(1);
			input.add(2);
			sql=new SQLDBMovieQuery(input);*/
			sql=new SQLDBMovieQuery("863",Const.PAIR_SEARCH);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		ArrayList<String> id=sql.getSimpleMovieId();
		ArrayList<String> url=sql.getSimpleUrl();
		for(int i=0;i<id.size();++i){
			System.out.println(id.get(i));
			System.out.println(url.get(i));
			System.out.println("----------");
		}
		
		/*MovieListView m=sql.getMovieObject();
		for(int i=0;i<m.getMovieNumber();++i){
			System.out.println("-------------------");
			System.out.println(m.getMovies().get(i).getName());
			System.out.println(m.getMovies().get(i).getOverview());
			System.out.println("-------------------");
		}
		System.out.println(m.getMovieNumber());*/
		/*
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
