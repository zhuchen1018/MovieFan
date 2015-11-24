package com.myapp.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

import com.myapp.SQL.SQLDBWrapper;

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
		long start = System.currentTimeMillis();
		
		//get connection
		Connection conn = SQLDBWrapper.getConnection(); 
		System.out.println("get conn used time: " + (System.currentTimeMillis() - start)/1000F);
		
		start = System.currentTimeMillis();
		//test query
		String sql ="select count(*) from BASICTMDBINFO";
		Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet result = st.executeQuery(sql);

		System.out.println("sql used time: " + (System.currentTimeMillis() - start)/1000F);

		while(result.next())
		{
			System.out.println("Result: " +  result.getString(1));
		}
	}
}
