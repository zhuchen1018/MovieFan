package com.myapp.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

import junit.framework.TestCase;

/**
 *  test Oracle SE connection
 *  need jar: JUnit and ojdbc7
 *   
 * @author Haoyun 
 *
 */
public class testJDBC extends TestCase
{
	@Test
	public void testConnect() throws SQLException 
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
		
		//test query
		String sql ="select count(*) from BASICTMDBINFO";
		Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet result = st.executeQuery(sql);

		while(result.next())
		{
			System.out.println("Result: " +  result.getString(1));
		}
	}
}
