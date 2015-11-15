package com.myapp.SQL;
import java.io.IOException;
import java.sql.*;

public class SQLDBMovieQuery {
	private Connection conn;
	private Statement state;
	private ResultSet rs;
	private String name;
	private String movieId;
	private int length;
	private double rating;
	private String sql;
	
	public SQLDBMovieQuery(){
		conn=SQLDBWrapper.getConnection();
		name=null;
		movieId=null;
		length=0;
		rating=0.0;
	}
	
	public SQLDBMovieQuery(String name,String movieId,int length,double rating){
		conn=SQLDBWrapper.getConnection();
		this.name=name;
		this.movieId=movieId;
		this.length=length;
		this.rating=rating;
	}
	
	public void searchMovie(){
		String cond[]=new String[4];
		for(int i=0;i<4;++i) cond[i]=null;
		boolean emptySearch=true,firstCondition=true;
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
		if(rating!=0.0){ 
			cond[3]="Userrating="+String.valueOf(rating)+" ";
			emptySearch=false;
		}
		if(emptySearch) return;
		sql="select * from basicTMDBInfo where ";
		for(int i=0;i<4;++i){
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
		sql+=";";
		System.out.println(sql);
		try{
			state=conn.createStatement();
			rs=state.executeQuery(sql);
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("search fail!");
		}
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setLength(int length){
		this.length=length;
	}
	
	public void setRating(double rating){
		this.rating=rating;
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
		SQLDBMovieQuery sql=new SQLDBMovieQuery();
		sql.setMovieId("843");
		sql.searchMovie();
		sql.closeConnection();
	}

}
