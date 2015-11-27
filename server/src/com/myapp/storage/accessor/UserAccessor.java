package com.myapp.storage.accessor;

import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.myapp.storage.entity.UserEntity;
import java.util.Date;

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

	/*
	public void addTweet(String username, Long tid) 
	{
		UserEntity user = getEntity(username);
		if(user == null)
		{
			return;
		}
		user.addTweet(tid);
		putEntity(user);
	}
	*/
}

