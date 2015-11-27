package com.myapp.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

import com.myapp.utils.Const;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.accessor.UserAccessor;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.storage.entity.UserEntity;

import org.w3c.dom.Document;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentFailureException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;

/*
 * test DB 
 */
public class TestDB extends TestCase 
{
	DBWrapper db; 
	
	@Test
	public void testRegister() throws Exception 
	{
		/*
		db = new DBWrapper();
		String name = "jason";
		String password = "jason123";
		db.addUser(name, password);
		assertTrue(db.hasUser(name));	
		db.close();
		*/
	}
	
	@Test
	public void testLogin() throws Exception 
	{
		db = new DBWrapper();
		String name = "jason";
		String password = "jason123";
	
		//register
		db.addUser(name, password);
		assertTrue(db.hasUser(name));	

		//login checkpassword
		assertTrue(db.checkLoginPassword(name, password));

	}

	@Test
	public void testWebPage() throws Exception 
	{
		/*
		String url = "jason";
		String content = "123ahsihaisdhiahdhai";

		DBWrapper db = new DBWryyapper();
		db.storeWebPage(url, content); 
	
		String content2 = db.getWebPage(url);
		print("content2: " + content2);
		*/
	}

	
	private void print(String s)
	{
		System.out.println(s);
	}

}

