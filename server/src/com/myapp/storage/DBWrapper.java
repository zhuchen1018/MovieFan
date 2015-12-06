package com.myapp.storage;

import java.io.File;
import java.io.IOException;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentFailureException;
import com.sleepycat.persist.StoreConfig;
import com.myapp.storage.accessor.*;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.storage.entity.HashTagEntity;
import com.myapp.storage.entity.NewsEntity;
import com.myapp.storage.entity.UserEntity;
import com.sleepycat.je.Environment;
import com.sleepycat.persist.EntityStore;
import com.myapp.utils.Const;
import com.myapp.view.FriendListView;
import com.myapp.view.FriendObjectView;
import com.myapp.view.GroupListView;
import com.myapp.view.GroupObjectView;
import com.myapp.view.GroupPageView;
import com.myapp.view.NewsListView;
import com.myapp.view.NewsObjectView;
import com.myapp.view.UserListView;
import com.myapp.view.UserObjectView;
import com.myapp.view.UserSettingView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;



/**
 * NoSQL part using Berkeley DB
 * store data of User, Group
 * @author Wingszero
 *
 */
public class DBWrapper 
{
	private Environment env;
	private File env_home;
	private EntityStore store;

	/*
	 * Entity Accessors to db
	 */
	private UserAccessor userEA;
	private GroupAccessor groupEA;
	private NewsAccessor newsEA;
	private IdGeneratorAccessor idEA;
	private HashTagAccessor hashtagEA;

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
		hashtagEA = new HashTagAccessor(store);

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

	public void createUser(String name, String password) 
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

	public GroupEntity createNewGroup(String gname, String username)
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			GroupEntity gobj  = groupEA.add(idEA.getNextGroupId(), gname, username);
			user.addCreateGroup(gobj.getId());
			userEA.putEntity(user);
			addNewsCreateGroup(username, gobj.getName()); 
			return gobj;
		}		
		return null;
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
		storeHashTag(news_obj);
	}

	/**
	 * extract hashtag from news
	 * @param news_obj
	 */
	private void storeHashTag(NewsEntity news_obj) 
	{
		String body = news_obj.getBody();
		HashSet<String>tags = Const.extractHashTag(body);
		for(String tag: tags)
		{
			hashtagEA.addHashTag(tag, news_obj.getId());
		}
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
		storeHashTag(news_obj);
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

	public void addNewsJoinGroup(String username, String groupname) 
	{
		UserEntity user = getUserEntity(username);
		if(user == null)
		{
			print("addNewsJoinGroup: user is null " + username);
			return;
		}
		ArrayList<String>receivers = new ArrayList<String>();
		receivers.add(groupname);
		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), receivers, Const.NEWS_ADD_GROUP);
		storeNews(news_obj, user);
	}

	public void addNewsCreateGroup(String username, String groupname) 
	{
		UserEntity user = getUserEntity(username);
		if(user == null)
		{
			print("addNewsCreateGroup: user is null " + username);
			return;
		}
		ArrayList<String>receivers = new ArrayList<String>();
		receivers.add(groupname);
		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), receivers, Const.NEWS_CREATE_GROUP);
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
			userEA.putEntity(user);
		}
	}

	public void userRemoveFriend(String username, String targetName) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			user.removeFriend(targetName);
			userEA.putEntity(user);
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
			return user.getJoinGroups();
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

	/**
	 * user join group
	 * @param username
	 * @param id
	 */
	public void userJoinGroup(String username, Long id) 
	{
		UserEntity user = getUserEntity(username);
		GroupEntity gobj = getGroupEntity(id);
		if(user != null && gobj != null)
		{
			user.joinGroup(id);
			userEA.putEntity(user);

			groupEA.addMember(id, username);
			addNewsJoinGroup(username, gobj.getName()); 
		}	
	}

	public void userLeaveGroup(String username, Long id) 
	{
		UserEntity user = getUserEntity(username);
		GroupEntity gobj = getGroupEntity(id);
		if(user != null && gobj != null)
		{
			userEA.leaveGroup(username, id);
			groupEA.removeMember(id, username);
		}		
	}

	public boolean hasFBUser(String fbid) 
	{
		return userEA.hasFBUser(fbid);
	}

	public String getUserFBId(String name) 
	{
		return userEA.getUserFBId(name);
	}		

	/**
	 * Show Friend list
	 * @param username
	 * @param request
	 * @param response
	 */
	public FriendListView loadFriendList(String username) 
	{
		FriendListView flv = new FriendListView(); 
		ArrayList<String>friends = getUserFriends(username);
		if(friends != null)
		{
			for(String fname: friends)
			{
				UserEntity friend = getUserEntity(fname);
				if(friend == null) continue;
				String url = friend.getHeadUrl();
				FriendObjectView fov = new FriendObjectView(url, fname);
				flv.addFriendObject(fov);
			}
		}
		return flv;
	}

	/**
	 * display a user's all news
	 * use in user page
	 * @param username
	 * @param response
	 */
	public NewsListView loadMyNews(String username) 
	{
		NewsListView nlv = new NewsListView();

		ArrayList<Long>newids = getUserNews(username);
		for(long id: newids)
		{
			NewsEntity newsEntity = getNewsEntityByIds(id);
			if(newsEntity == null) continue;
			int type = newsEntity.getNewsType();
			String text = newsEntity.getBody(); 
			String url = newsEntity.getMoviePosterUrl() ;
			String title = newsEntity.getTitle(); 
			String movieId = newsEntity.getMovidId(); 
			String movieName = newsEntity.getMovieName(); 
			long releaseTime = newsEntity.getReleaseTime(); 
			int likeNums = newsEntity.getLikeNums(); 
			ArrayList<String>ToList = newsEntity.getReceivers();
			NewsObjectView newsViewObj = new NewsObjectView(username, text, url, title, movieId, movieName, ToList, type, releaseTime, likeNums);
			nlv.addNews(newsViewObj);
		}
		return nlv;
	}

	/**
	 * display a user and his/her all related 
	 * use in home page
	 * @param username
	 * @param response
	 */
	public NewsListView loadAllNews(String username) 
	{
		NewsListView nlv = new NewsListView();

		//all
		ArrayList<Long>allnews = new ArrayList<Long>(); 

		//my
		ArrayList<Long>mynews = getUserNews(username);
		if(mynews != null)
		{
			allnews.addAll(mynews);
		}
		print("my news size: " + allnews.size());

		//friends
		ArrayList<String>friends = getUserFriends(username);
		if(friends != null)
		{
			for(String fname: friends)
			{
				ArrayList<Long>fn = getUserNews(fname);
				if(fn!= null)
				{
					allnews.addAll(fn);
				}
			}
		}

		print("all news size: " + allnews.size());

		for(long id: allnews)
		{
			NewsEntity newsEntity = getNewsEntityByIds(id);
			if(newsEntity == null) continue;
			NewsObjectView newsViewObj = new NewsObjectView(newsEntity);
			nlv.addNews(newsViewObj);
		}
		return nlv;

	}

	/**
	 * 
	 * @param username
	 * @param request
	 * @param response
	 */
	public GroupListView loadGroupList(String username)
	{
		GroupListView glv = new GroupListView(); 
		ArrayList<Long>groups = getUserGroup(username);
		if(groups != null)
		{
			for(long gid: groups)
			{
				GroupEntity groupEntity = getGroupEntity(gid);
				if(groupEntity == null) continue;
				String url = groupEntity.getHeadUrl();
				String gname = getGroupName(gid);
				GroupObjectView gov = new GroupObjectView(groupEntity.getId(), url, gname);
				glv.addGroupObject(gov);
			}
		}
		return glv;
	}

	public boolean isMyFriend(String username, String targetName) 
	{
		UserEntity user  = getUserEntity(username);
		if(user != null)
		{
			return user.isMyFriend(targetName);
		}
		return false;
	}

	public boolean isUserLikeMovie(String username, String movie_id) 
	{
		UserEntity user  = getUserEntity(username);
		if(user != null)
		{
			return user.isLikeMovie(movie_id);
		}
		return false;
	}

	public boolean canCreateGroup(String username) 
	{
		UserEntity user  = getUserEntity(username);
		if(user != null)
		{
			return user.canCreateGroup();
		}
		return false;
	}

	public HashTagEntity getHashTagEntity(String tag) 
	{
		if(tag == null) return null;
		tag = tag.trim().toLowerCase();
		return hashtagEA.getHashTags(tag);
	}

	public void RemoveHashTagNews(String tag, long id) 
	{
		HashTagEntity entity = hashtagEA.getHashTags(tag);
		if(entity == null) return;

		entity.removeNews(id);
		hashtagEA.put(entity);
	}

	public void userAddFans(String friend, String username) 
	{
		UserEntity user = getUserEntity(friend);
		if(user != null)
		{
			user.addFans(username);
			userEA.putEntity(user);
		}
	}

	public void userAddHeadUrl(String friend, String url) 
	{
		UserEntity user = getUserEntity(friend);
		if(user != null)
		{
			user.addHeadUrl(url);
			userEA.putEntity(user);
		}	
	}

	public void addGroupHeadUrl(Long id, String url) 
	{
		GroupEntity g = getGroupEntity(id);
		if(g != null)
		{
			g.addHeadUrl(url);
			groupEA.putEntity(g);
		}
	}

	public boolean hasGroup(Long gid) 
	{
		GroupEntity g = getGroupEntity(gid);
		return g != null;
	}

	public UserListView loadSearchUserByName(String tarname) 
	{
		ArrayList<UserEntity>userList = userEA.searchSimilarUserName(tarname);

		UserListView ulv = new UserListView(); 
		for(UserEntity user: userList)
		{
			String url = user.getHeadUrl();
			UserObjectView uov = new UserObjectView(url, user.getName());
			ulv.add(uov);
		}
		return ulv;
	}

	public void userLikeMovie(String username, String movieId) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			user.likeMovie(movieId);
			userEA.putEntity(user);
		}	
	}	

	public void userUnlikeMovie(String username, String movieId) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			user.unlikeMovie(movieId);
			userEA.putEntity(user);
		}	
	}

	public void upUserSettings(String username, String head_url, String profile_url, String description, Integer[] genres) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			user.upSettings(username, head_url, profile_url, description, genres);
			userEA.putEntity(user);
		}		
	}

	public UserSettingView loadUserSettingView(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			String head_url = user.getHeadUrl(); 
			String profile_url = user.getProfileUrl(); 
			String description = user.getDescription();
			return new UserSettingView(head_url, profile_url, null, description);
		}		
		return null;
	}

	public GroupPageView loadGroupPageView(Long gid, String username) 
	{
		GroupEntity gobj = groupEA.getEntity(gid);
		if(gobj != null) 
		{
			boolean inGroup = gobj.hasMember(username);
			return new GroupPageView(gid, gobj.getName(), gobj.getCreator(), gobj.getMembers(), inGroup); 
		}
		return null;
	}

	public ArrayList<GroupEntity> loadSearchGroupList(String name) 
	{
		return groupEA.getSearchGroup(name);
	}

	public NewsListView loadSearchHashTag(String search) 
	{
		ArrayList<HashTagEntity>taglist = hashtagEA.searchHashTag(search);
		NewsListView nlv = new NewsListView(); 

		for(HashTagEntity tag: taglist)
		{
			for(Long newsid: tag.getNews())
			{
				NewsEntity news = newsEA.getNewsEntityById(newsid);
				NewsObjectView newsViewObj = new NewsObjectView(news);
				nlv.addNews(newsViewObj);
			}
		}
		return nlv;
	}
}
