package com.myapp.storage;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentFailureException;
import com.sleepycat.persist.StoreConfig;
import com.myapp.storage.accessor.*;
import com.myapp.storage.entity.GroupEntity;

import com.sleepycat.je.Environment;
import com.sleepycat.persist.EntityStore;

import java.util.List;


import org.apache.log4j.Logger;
public class DBWrapper 
{
	private static String envDirectory = null;
	private Environment env;
	private File env_home;
	private EntityStore store;

	/*
	 * Accessors to db
	 */
	private UserAccessor userEA;
	private GroupAccessor groupEA;
	
	private boolean is_close;
	
	static final Logger logger = Logger.getLogger(DBWrapper.class);	
	
	public DBWrapper() throws IOException
	{
		setup();
	}

	public void setup()
	{
		is_close = false;
		/*env config*/
		EnvironmentConfig ec = new EnvironmentConfig();
		ec.setAllowCreate(true);
		ec.setReadOnly(false);
		ec.setTransactional(true);

		env_home = createDir(DBConst.ROOT);
		try
		{
			env = new Environment(env_home, ec);
		}
		catch(EnvironmentFailureException e)
		{
			logger.error(e.getCause());
			System.exit(-1);
		}

		/*store config*/
		StoreConfig sc = new StoreConfig();
		sc.setAllowCreate(true);
		sc.setReadOnly(false);
		sc.setTransactional(true);
		store = new EntityStore(env, DBConst.DB_STORE_NAME, sc);
		
		userEA = new UserAccessor(store);
		groupEA = new GroupAccessor(store);
	}

	private void initAccessors()
	{
		
	}

	/*
	public EntityStore getStore(String name) throws DatabaseException
	{
		return store_table.get(name);
	}
	*/

	// Return a handle to the environment
	public Environment getEnv() 
	{
		return env;
	}

	// Close the store and environment.
	public void close() 
	{		
		is_close = true;
		/*
		for(String name: store_table.keySet())
		{
			EntityStore store = store_table.get(name);
			if (store != null) 
			{
				try 
				{
					store.close();
					//print(name + " store close");
				} 
				catch(DatabaseException dbe) 
				{
					System.err.println("Error closing store: " + dbe.toString());
					System.exit(-1);
				}
			}
		}
		*/
		if (store != null) 
		{
			try 
			{
				store.close();
				//print(name + " store close");
			} 
			catch(DatabaseException dbe) 
			{
				System.err.println("Error closing store: " + dbe.toString());
				System.exit(-1);
			}
		}
		
		if (env != null) 
		{
			try 
			{
				env.close();
				//print("env close");
			} 
			catch(DatabaseException dbe) 
			{
				System.err.println("Error closing MyDbEnv: " + dbe.toString());
				System.exit(-1);
			}
		}
	}	

	private static File createDir(String dir_name)
	{
		File dir = new File(dir_name);
		if (!dir.exists()) 
		{
			logger.info("creating directory: " + dir_name);
			boolean res = false;
			try
			{
				dir.mkdir();
				res = true;
			} 
			catch(SecurityException se)
			{
				logger.error("Create dir " + dir_name + " failed");
			}        
			if(res) 
			{    
				logger.info("Create dir " + dir_name + " successed");
			}
		}
		return dir;
	}

	private static File createFile(String root, String name) throws IOException
	{
		print("DBWrapper createFile: root: " + root + ", file: " + name);
		File file = new File(root, name);
		if(!file.exists())
		{
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		return file;
	}
	
	public UserAccessor getUserAccessor() throws IOException
	{
		//userEA = new UserAccessor(getStore(DBConst.USER_STORE_NAME));
		return userEA;
	}

	/*
	 * Accessor funcs
	 */
	public void addUser(String name, String password) throws IOException 
	{
		UserAccessor ua = getUserAccessor();
		if(ua == null)
		{
			logger.error("get UserEntityAccessor failed!");
			return;
		}
		ua.add(name, password);
	}

	public boolean hasUser(String name) throws IOException
	{
		UserAccessor ua = getUserAccessor();
		if(ua == null)
		{
			logger.error("get UserEntityAccessor failed!");
			return false;
		}
		return ua.hasUser(name);
	}

	public boolean checkLoginPassword(String name, String password) throws IOException 
	{
		UserAccessor ua = getUserAccessor();
		if(ua == null)
		{
			logger.error("get UserEntityAccessor failed!");
			return false;
		}
		return ua.checkPassword(name, password);
	}

	public boolean userLogin(String name) throws IOException 
	{
		UserAccessor ua = getUserAccessor();
		if(ua == null)
		{
			logger.error("get UserAccessor failed! " + name);
			return false;
		}
		ua.Login(name);
		return true;
	}

	

	public boolean isClose()
	{
		return is_close;
	}
	public static void print(String s)
	{
		System.out.println(s);
	}

	public boolean hasChannel(String name) 
	{
		return groupEA.contains(name);
	}
	
	public List<GroupEntity>  getAllChannels() 
	{
		return  groupEA.getAllEntities(); 
	}

	public void StoreGroup(String name, String creator)
	{
		groupEA.add(name, creator);
	}
	
	public boolean hasGroup(String name)
	{
		return groupEA.contains(name);
	}

	public void DelChannel(String name) 
	{
		groupEA.delEntity(name);
	}

	public GroupEntity getChannel(String name) 
	{
		return groupEA.getEntity(name);
	}

	public String getChannelCreator(String name) 
	{
		GroupEntity ch = getChannel(name);
		if(ch == null)
		{
			return null;
		}
		return ch.getCreator();
	}

	public void addUserTweet(String username, String info) 
	{
		// TODO Auto-generated method stub
		
	}


}
