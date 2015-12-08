package com.myapp.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.regex.Matcher;

import org.junit.Test;

import com.myapp.SQL.SQLDBWrapper;
import com.myapp.utils.Const;

import junit.framework.TestCase;

/**
 *  test Oracle SE connection
 *  need jar: JUnit and ojdbc7
 *   
 * @author Haoyun 
 *
 */
public class TestOther extends TestCase
{
	@Test
	public void testHashTag()
	{
		String[] words = {
				"  #nihao #NIHAO    #jason @ashidahsd             jasojn #hello    hhh",
				"hello", 
		};

		for(String a: words)
		{
			//HashSet<String> tags = Const.extractHashTag(a); 
			//System.out.println(a + " include tags: " );
			/*
			for(String tag: tags)
			{
				System.out.println(tag);
			}
			*/
			String res = Const.transferTextToLink(a);
			System.out.println(res);
		}
	}
}
