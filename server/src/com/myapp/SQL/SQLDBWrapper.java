package com.myapp.SQL;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * SQL connection pool for sql connection
 * Cause: new a connection to DB is a bottleneck, usually takes seconds.
 * @author Haoyun 
 *
 */
public class SQLDBWrapper 
{
	private static ComboPooledDataSource cpds; 

	//init the connection pool
	static 
	{
		//long st = System.currentTimeMillis();
		cpds = new ComboPooledDataSource();
		try 
		{
			cpds.setDriverClass( "oracle.jdbc.driver.OracleDriver" ); 
		} 
		catch (PropertyVetoException e) 
		{
			e.printStackTrace();
		} 

		String host = "moviefans.cudyjofu9j3z.us-west-2.rds.amazonaws.com";
		String port = "1521";
		String sid = "EBDB";
		String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid; 
		String username = "jason";
		String password = "962464cis550";

		cpds.setJdbcUrl(url);
		cpds.setUser(username);
		cpds.setPassword(password);

		cpds.setMinPoolSize(100);
		cpds.setAcquireIncrement(20);
		cpds.setMaxPoolSize(500);
		
		//System.out.println("Init connection pool time:" + (System.currentTimeMillis() - st));
	}

	public static Connection getConnection() 
	{
		try 
		{
			return cpds.getConnection();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}





