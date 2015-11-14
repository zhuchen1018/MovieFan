package com.myapp.SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 *   Interface for the Relational DB
 *   for Movie data
 * @author Haoyun 
 *
 */
public class SQLDBWrapper 
{
	public static Connection getConnection() throws SQLException 
	{
		String host = "moviefans.cudyjofu9j3z.us-west-2.rds.amazonaws.com";
		String port = "1521";
		String sid = "EBDB";
		String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid; 
		String username = "jason";
		String password = "962464cis550";
		
		Properties props = new Properties();
		props.put("user", username);
		props.put("password", password);
		
		//get connection
		Connection conn = DriverManager.getConnection(url, props);
		return conn;
	
		/*
		//test query
		String sql ="select count(*) from BASICTMDBINFO";
		Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet result = st.executeQuery(sql);

		while(result.next())
		{
			System.out.println("Result: " +  result.getString(1));
		}
		*/
	}
}



	

