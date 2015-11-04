package com.myapp.storage.accessor;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

//import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.UserEntity;

import java.util.Date;

import org.apache.log4j.Logger;
/*
 * DB Accessor for UserEntity 
 * @author Haoyun 
 * 
 */

public class UserAccessor
{
	private PrimaryIndex<String, UserEntity> users;
	
	//static final Logger logger = Logger.getLogger(DBWrapper.class);	
	
	public UserAccessor(EntityStore store)
	{
		users = store.getPrimaryIndex(String.class, UserEntity.class);
	}

	public void add(String name, String password)
	{
		long time = (new Date()).getTime();
		UserEntity user = new UserEntity(name, password, time);
		putEntity(user);
	}

	public boolean hasUser(String name)
	{
		return users.contains(name);
	}

	public boolean del(String name)
	{
		return users.delete(name);
	}

	public PrimaryIndex<String, UserEntity> getPrimaryIndex() 
	{
		return users;
	}

	public UserEntity getEntity(String name)
	{
		return users.get(name);
	}
	
	public void putEntity(UserEntity user)
	{
		users.put(user);
	}
	
	public boolean checkPassword(String name, String password) 
	{
		UserEntity ue = getEntity(name);
		if(ue == null)
		{
			//logger.info("checkPassword: user: " + name + " is not exists");
			return false;
		}
		return ue.getPassword().equals(password);
	}

	public boolean Login(String name) 
	{
		UserEntity user = getEntity(name);
		if(user == null)
		{
			//logger.error("Login cannot find user: " + name);
			return false;
		}
		user.Login();
		putEntity(user);
		return true;
	}
	
	public boolean Logoff(String name) 
	{
		UserEntity user = getEntity(name);
		if(user == null)
		{
			return false;
		}
		user.Logoff();
		putEntity(user);
		return true;
	}

	public boolean isLogin(String name) 
	{
		UserEntity user = getEntity(name);
		if(user == null)
		{
			//logger.error("hasLogin cannot find user: " + name);
			return false;
		}
		return user.isLogin();
	}
}

