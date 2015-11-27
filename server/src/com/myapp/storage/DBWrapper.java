package com.myapp.storage;

import java.io.File;
import java.io.IOException;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentFailureException;
import com.sleepycat.persist.StoreConfig;
import com.myapp.storage.accessor.*;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.storage.entity.NewsEntity;
import com.myapp.storage.entity.UserEntity;
import com.sleepycat.je.Environment;
import com.sleepycat.persist.EntityStore;
import com.myapp.utils.Const;

import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;

public class DBWrapper 
{
	private Environment env;
	private File env_home;
	private EntityStore store;

	/*
	 * Accessors to db
	 */
	private UserAccessor userEA;
	private GroupAccessor groupEA;
	private NewsAccessor newsEA;
	private IdGeneratorAccessor idEA;
	
	private boolean is_close;
	
	//static final Logger logger = Logger.getLogger(DBWrapper.class);	
	
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

		env_home = createDir(Const.ROOT);
		try
		{
			env = new Environment(env_home, ec);
		}
		catch(EnvironmentFailureException e)
		{
			//logger.error(e.getCause());
			System.exit(-1);
		}

		/*store config*/
		StoreConfig sc = new StoreConfig();
		sc.setAllowCreate(true);
		sc.setReadOnly(false);
		sc.setTransactional(true);
		store = new EntityStore(env, Const.DB_STORE_NAME, sc);
		
		userEA = new UserAccessor(store);
		groupEA = new GroupAccessor(store);
		newsEA = new NewsAccessor(store);
		idEA = new IdGeneratorAccessor(store);
		
		//logger.info("db setup!!");
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
	
	public void sync()
	{
		store.sync();
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
			//logger.info("creating directory: " + dir_name);
			boolean res = false;
			try
			{
				dir.mkdir();
				res = true;
			} 
			catch(SecurityException se)
			{
				//logger.error("Create dir " + dir_name + " failed");
			}        
			if(res) 
			{    
				//logger.info("Create dir " + dir_name + " successed");
			}
		}
		return dir;
	}

	/*
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
	*/

	/**
	 ******************* Accessor funcs****************************
	 */
	public UserAccessor getUserAccessor() 
	{
		return userEA;
	}

	public void addNextNewsId()
	{
		idEA.getNextNewsId();
	}

	/*
	public void addTweet(String username, String body) throws IOException 
	{
		UserEntity user  = getUserEntity(username);
		if(user != null)
		{
			NewsEntity t = newsEA.addTweet(idEA.getNextTweetId(), username, body);
			userEA.addTweet(username, t.getId());
		}
		else
		{
			//TODO: error log
		}
	}
	*/

	public void addUser(String name, String password) 
	{
		UserAccessor ua = getUserAccessor();
		ua.add(name, password);
	}

	public UserEntity getUserEntity(String name) 
	{
		UserAccessor ua = getUserAccessor();
		return ua.getEntity(name); 
	}
	
	
	public boolean hasUser(String name) 
	{
		UserAccessor ua;
		ua = getUserAccessor();
		return ua.hasUser(name);
	}

	public boolean checkLoginPassword(String name, String password) 
	{
		UserAccessor ua;
		ua = getUserAccessor();
		return ua.checkPassword(name, password);
		
	}

	public boolean userLogin(String name) throws IOException 
	{
		UserAccessor ua = getUserAccessor();
		ua.Login(name);
		return true;
	}

	public boolean isClose()
	{
		return is_close;
	}
	
	public List<GroupEntity>  getAllGroups() 
	{
		return  groupEA.getAllEntities(); 
	}

	public GroupEntity storeGroup(String name, String creator)
	{
		return groupEA.add(idEA.getNextGroupId(), name, creator);
	}
	
	public GroupEntity getGroupEntity(Long id)
	{
		return groupEA.getEntity(id);
	}

	public boolean hasGroupByName(String name)
	{
		return groupEA.containsByName(name);
	}

	public String getGroupCreator(Long id) 
	{
		GroupEntity g = getGroupEntity(id);
		if(g == null)
		{
			return null;
		}
		return g.getCreator();
	}
	
	public static void print(String s)
	{
		System.out.println(s);
	}

	/**
	 * Get a user's news with max of limit
	 * @param limit
	 * @return
	 */
	public ArrayList<Long> getUserNews(String username, int limit)
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			ArrayList<Long>news = user.getNews();
			if(news != null)
			{
				List<Long> list = news.subList(Math.max(0, news.size() - limit), news.size());
				return (ArrayList<Long>) list;
			}
		}
		return null;
	}
	
	/**
	 * Get a user's all news 
	 * @param limit
	 * @return
	 */
	public ArrayList<Long> getUserNews(String username)
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getNews();
		}
		return null;
	}
		
	public ArrayList<NewsEntity> getNewsEntityByIds(ArrayList<Long> ids)
	{
		return newsEA.getNewsEntityByIds(ids);
	}
	
	public NewsEntity getNewsEntityByIds(long id)
	{
		return newsEA.getNewsEntityById(id);
	}

	public void storeNews(NewsEntity news_obj, UserEntity user)
	{
		//update news store
		newsEA.addNews(news_obj);
		
		//update user store
		user.addNews(news_obj.getId());
		userEA.putEntity(user);	
	
		store.sync();
	}

	/*All kinds of add news functions*/
	
	public void addNewsTwitter(String username, String tweet) 
	{
		UserEntity user = getUserEntity(username);
		if(user == null)
		{
			print("addNewsTwitter: user is null " + username);
			return;
		}
		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), tweet, Const.NEWS_TWITTER);
		storeNews(news_obj, user);
	}

	public void addNewsMovieReview(String username, String title, String body, String movie_id, String movie_name, String movie_url) 
	{
		UserEntity user = getUserEntity(username);
		if(user == null)
		{
			print("addNewsMovieReview: user is null " + username);
			return;
		}
		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), title, body, movie_id, movie_name, movie_url, Const.NEWS_MOVIE_REVIEW);
		storeNews(news_obj, user);
	}

	public void userAddNewsMakeFriends(String username, String receiver) 
	{
		UserEntity user = getUserEntity(username);
		if(user == null)
		{
			print("addNewsMakeFriends: user is null " + username);
			return;
		}
		ArrayList<String>receivers = new ArrayList<String>();
		receivers.add(receiver);
		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), receivers, Const.NEWS_MAKE_FRIENDS);
		storeNews(news_obj, user);
	}

	public void addNewsAddGroup(String username, String groupid) 
	{
	
		UserEntity user = getUserEntity(username);
		if(user == null)
		{
			print("addNewsAddGroup: user is null " + username);
			return;
		}
		ArrayList<String>receivers = new ArrayList<String>();
		receivers.add(groupid);
		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), receivers, Const.NEWS_ADD_GROUP);
		storeNews(news_obj, user);
	}

	public void addNewsShareMovies(String username, String movie_id, String movie_name, String url, ArrayList<String> friend_list) 
	{
		UserEntity user = getUserEntity(username);
		if(user == null)
		{
			print("addNewsShareMovies: user is null " + username);
			return;
		}
		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), movie_id, movie_name, url, friend_list, Const.NEWS_SHARE_MOVIE);
		storeNews(news_obj, user);
	}

	public void addNewsLikeMovie(String username, String movie_id, String url) 
	{
		UserEntity user = getUserEntity(username);
		if(user == null)
		{
			print("addNewsTwitter: user is null " + username);
			return;
		}
		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), movie_id, url, Const.NEWS_LIKE_MOVIE);
		storeNews(news_obj, user);
		
	}

	public ArrayList<String> getFriends(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getFriends();
		}
		return null;
	}

	public void userAddFriend(String username, String friendname) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			user.addFriend(friendname);
		}
	}

	public ArrayList<String> getUserFriends(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getFriends();
		}
		return null;
	}

	public ArrayList<Long> getUserGroup(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getGroups();
		}
		return null;
	}

	public String getGroupName(long gid) 
	{
		GroupEntity gobj = getGroupEntity(gid);
		if(gobj != null)
		{
			return gobj.getName();
		}
		return null;
	}

	public void userAddGroup(String username, Long id) 
	{
		UserEntity user = getUserEntity(username);
		GroupEntity gobj = getGroupEntity(id);
		if(user != null && gobj != null)
		{
			user.addGroup(id);
			gobj.addMember(username);
		}	
	}		
}
