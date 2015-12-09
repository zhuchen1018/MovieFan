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
import com.myapp.storage.entity.MoviePageEntity;
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
import com.myapp.view.MoviePageView;
import com.myapp.view.MoviePageViewCache;
import com.myapp.view.NewsListView;
import com.myapp.view.NewsObjectView;
import com.myapp.view.UserInfoView;
import com.myapp.view.UserListView;
import com.myapp.view.UserObjectView;
import com.myapp.view.UserSettingView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Berkeley DB Wrapper/Handle
 * For external interface read/write
 * store data:
 * 		===User 
 * 		===Group
 * 		===News
 * 		===ID
 * 		===HashTag
 * 		===MoviePage
 * 
 * @author Haoyun 
 *
 */
public class DBWrapper 
{
	private static Environment env;
	private static File env_home;
	private static EntityStore store;

	/*
	 * Entity Accessors to db
	 */
	private static UserAccessor userEA;
	private static GroupAccessor groupEA;
	private static NewsAccessor newsEA;
	private static IdGeneratorAccessor idEA;
	private static HashTagAccessor hashtagEA;
	private static MoviePageAccessor moviepageEA; 

	private static boolean is_close;

	public DBWrapper() 
	{
		print("DBWrapper setup....");
		setup();
	}

	public void setup()
	{
		is_close = false;
		/*env config*/
		EnvironmentConfig ec = new EnvironmentConfig();
		ec.setAllowCreate(true);
		ec.setReadOnly(false);
		ec.setCachePercent(50);
		ec.setTransactional(true);

		System.out.println("EnvironmentConfig cache size: " + ec.getCacheSize());

		env_home = createDir(Const.ROOT);
		try
		{
			env = new Environment(env_home, ec);
		}
		catch(EnvironmentFailureException e)
		{
			e.printStackTrace();
			return;
		}

		/*store config*/
		StoreConfig sc = new StoreConfig();
		sc.setAllowCreate(true);
		sc.setReadOnly(false);
		//sc.setDeferredWrite(true);
		sc.setTransactional(true);
		store = new EntityStore(env, Const.DB_STORE_NAME, sc);

		userEA = new UserAccessor(env, store);
		groupEA = new GroupAccessor(env, store);
		newsEA = new NewsAccessor( store);
		idEA = new IdGeneratorAccessor( store);
		hashtagEA = new HashTagAccessor( store);
		moviepageEA = new MoviePageAccessor(store);
	}


	/*
	public static void sync()
	{
		store.sync();
		env.sync();
	}
	 */
	/*
	public EntityStore getStore(String name) throws DatabaseException
	{
		return store_table.get(name);
	}
	 */

	// Return a handle to the environment
	public static Environment getEnv() 
	{
		return env;
	}

	// Close the store and environment.
	public static void close() 
	{		
		is_close = true;
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
			boolean res = false;
			try
			{
				dir.mkdir();
				res = true;
			} 
			catch(SecurityException se)
			{
			}        
			if(res) 
			{    
			}
		}
		return dir;
	}


	/**
	 ******************* Accessor funcs****************************
	 */
	public static UserAccessor getUserAccessor() 
	{
		return userEA;
	}

	public static void addNextNewsId()
	{
		idEA.getNextNewsId();
	}


	public static void createUser(String name, String password) 
	{
		UserAccessor ua = getUserAccessor();
		ua.add(name, password);
	}

	public static UserEntity getUserEntity(String name) 
	{
		UserAccessor ua = getUserAccessor();
		return ua.getEntity(name); 
	}


	public static boolean hasUser(String name) 
	{
		UserAccessor ua = getUserAccessor();
		return ua.hasUser(name);
	}

	public static boolean checkLoginPassword(String name, String password) 
	{
		UserAccessor ua;
		ua = getUserAccessor();
		return ua.checkPassword(name, password);

	}

	public static boolean userLogin(String name) throws IOException 
	{
		UserAccessor ua = getUserAccessor();
		ua.Login(name);
		return true;
	}

	public static boolean isClose()
	{
		return is_close;
	}

	public static List<GroupEntity>  getAllGroups() 
	{
		return  groupEA.getAllEntities(); 
	}

	public static GroupEntity createNewGroup(String gname, String username)
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

	public static GroupEntity getGroupEntity(Long id)
	{
		return groupEA.getEntity(id);
	}

	public static boolean hasGroupByName(String name)
	{
		return groupEA.containsByName(name);
	}

	public static String getGroupCreator(Long id) 
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
	public static ArrayList<Long> getUserNews(String username, int limit)
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
	public static ArrayList<Long> getUserNews(String username)
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getNews();
		}
		return null; 
	}

	public static ArrayList<NewsEntity> getNewsEntityByIds(ArrayList<Long> ids)
	{
		return newsEA.getNewsEntityByIds(ids);
	}

	public static NewsEntity getNewsEntityByIds(long id)
	{
		return newsEA.getNewsEntityById(id);
	}

	public static void storeNews(NewsEntity news_obj, UserEntity user)
	{
		//update news store
		newsEA.addNews(news_obj);

		//update user store
		user.addNews(news_obj.getId());
		userEA.putEntity(user);	
	}

	/*All kinds of add news functions*/
	public static void addNewsTwitter(String username, String tweet) 
	{
		UserEntity user = getUserEntity(username);
		if(user == null)
		{
			return;
		}
		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), tweet, Const.NEWS_TWITTER);
		storeNews(news_obj, user);
		storeHashTag(news_obj);
		storeMailBox(news_obj);
	}

	//extract tag user in news body, put news in user's mailbox to notfify them.
	private static void storeMailBox(NewsEntity news_obj) 
	{
		int type = news_obj.getNewsType();
		Long newsId = news_obj.getId();
		if(type == Const.NEWS_TWITTER || type == Const.NEWS_TWEET_IN_GROUP) 
		{
			HashSet<String> tags = Const.extractTagUser(news_obj.getBody()); 
			for(String name: tags)
			{
				userEA.addMail(name, newsId);
			}
		}
	}

	/**
	 * extract hashtag from news
	 * @param news_obj
	 */
	private static void storeHashTag(NewsEntity news_obj) 
	{
		String body = news_obj.getBody();
		HashSet<String>tags = Const.extractHashTag(body);
		for(String tag: tags)
		{
			hashtagEA.addHashTag(tag, news_obj.getId());
		}
	}

	public static void addNewsMovieReview(String username, String title, String body, String movie_id, String movie_name, String movie_url) 
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

	public static void userAddNewsFollowUser(String username, String othername) 
	{
		UserEntity user = getUserEntity(username);
		if(user == null)
		{
			return;
		}

		UserEntity other = getUserEntity(othername);
		if(other  == null)
		{
			return;
		}

		ArrayList<String>receivers = new ArrayList<String>();
		receivers.add(othername);

		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), receivers, Const.NEWS_MAKE_FRIENDS);
		storeNews(news_obj, user);

		userEA.addMail(othername, news_obj.getId());
	}

	public static void addNewsJoinGroup(String username, String groupname) 
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

	public static void addNewsCreateGroup(String username, String groupname) 
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

	public static void addNewsShareMovies(String username, String movie_id, String movie_name, String url, ArrayList<String> friend_list) 
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

	public static void addNewsLikeMovie(String username, String movie_id, String url) 
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

	public static ArrayList<String> getFriends(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getFriends();
		}
		return null;
	}

	public static void userAddFollow(String username, String friendname) 
	{
		userEA.followUser(username, friendname);	
	}

	public static void userUnfollow(String username, String targetName) 
	{
		userEA.unfollowUser(username, targetName);	
	}	

	public static ArrayList<String> getUserFriends(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getFriends();
		}
		return null;
	}

	public static ArrayList<Long> getUserGroup(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getJoinGroups();
		}
		return null;
	}

	public static String getGroupName(long gid) 
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
	public static void userJoinGroup(String username, Long id) 
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

	public static void userLeaveGroup(String username, Long id) 
	{
		UserEntity user = getUserEntity(username);
		GroupEntity gobj = getGroupEntity(id);
		if(user != null && gobj != null)
		{
			userEA.leaveGroup(username, id);
			groupEA.removeMember(id, username);
		}		
	}

	public static boolean hasFBUser(String fbid) 
	{
		return userEA.hasFBUser(fbid);
	}

	public static String getUserFBId(String name) 
	{
		return userEA.getUserFBId(name);
	}		

	/**
	 * Show Friend list
	 * @param username
	 * @param request
	 * @param response
	 */
	public static FriendListView loadFriendList(String username) 
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
	 * Show Followings list
	 * @param username
	 * @param request
	 * @param response
	 */
	public static UserListView loadFollowingsList(String username) 
	{
		UserListView ulv = new UserListView(); 
		ArrayList<String>fans = getUserFollowings(username);
		if(fans != null)
		{
			for(String fname: fans)
			{
				UserEntity friend = getUserEntity(fname);
				if(friend == null) continue;
				String url = friend.getHeadUrl();
				UserObjectView fov = new UserObjectView(url, fname);
				ulv.add(fov);
			}
		}
		return ulv;
	}

	/**
	 * Show Followings list
	 * @param username
	 * @param request
	 * @param response
	 */
	public static UserListView getUserViewListFromNameList(ArrayList<String>nameList) 
	{
		UserListView ulv = new UserListView(); 
		if(nameList != null)
		{
			for(String name: nameList)
			{
				UserEntity friend = getUserEntity(name);
				if(friend == null) continue;
				String url = friend.getHeadUrl();
				UserObjectView fov = new UserObjectView(url, name);
				ulv.add(fov);
			}
		}
		return ulv;
	}

	/**
	 * Show Fans list
	 * @param username
	 * @param request
	 * @param response
	 */
	public static UserListView loadFansList(String username) 
	{
		UserListView ulv = new UserListView(); 
		ArrayList<String>fans = getUserFans(username);
		if(fans != null)
		{
			for(String fname: fans)
			{
				UserEntity friend = getUserEntity(fname);
				if(friend == null) continue;
				String url = friend.getHeadUrl();
				UserObjectView fov = new UserObjectView(url, fname);
				ulv.add(fov);
			}
		}
		return ulv;
	}



	private static ArrayList<String> getUserFollowings(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getFollowings();
		}
		return null;
	}

	private static ArrayList<String> getUserFans(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getFans();
		}
		return null;
	}



	/**
	 * display a user's all news
	 * use in user page
	 * @param username
	 * @param response
	 */
	public static NewsListView loadMyNews(String username) 
	{
		NewsListView nlv = new NewsListView();

		ArrayList<Long>newids = getUserNews(username);

		for(long id: newids)
		{
			NewsEntity newsEntity = getNewsEntityByIds(id);
			if(newsEntity == null) continue;	
			String userUrl = getUserEntity(newsEntity.getCreator()).getHeadUrl();
			NewsObjectView newsViewObj = new NewsObjectView(newsEntity, userUrl);
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
	public static NewsListView loadAllNews(String username) 
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
		//groups
		UserEntity user = userEA.getEntity(username);
		ArrayList<Long>groups = user.getJoinGroups(); 
		for(Long gid: groups)
		{
			GroupEntity group = groupEA.getEntity(gid);
			if(group != null)
			{
				allnews.addAll(group.getNews());
			}
		}

		print("all news size: " + allnews.size());

		for(long id: allnews)
		{
			NewsEntity newsEntity = getNewsEntityByIds(id);
			if(newsEntity == null) continue;
			//user head
			String userUrl = getUserEntity(newsEntity.getCreator()).getHeadUrl();
			NewsObjectView newsViewObj = new NewsObjectView(newsEntity, userUrl);
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
	public static GroupListView loadGroupList(String username)
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

	public static boolean isMyFriend(String username, String targetName) 
	{
		UserEntity user  = getUserEntity(username);
		if(user != null)
		{
			return user.isMyFriend(targetName);
		}
		return false;
	}

	public static boolean isUserLikeMovie(String username, String movie_id) 
	{
		UserEntity user  = getUserEntity(username);
		if(user != null)
		{
			return user.isLikeMovie(movie_id);
		}
		return false;
	}

	public static boolean canCreateGroup(String username) 
	{
		UserEntity user  = getUserEntity(username);
		if(user != null)
		{
			return user.canCreateGroup();
		}
		return false;
	}

	public static HashTagEntity getHashTagEntity(String tag) 
	{
		if(tag == null) return null;
		tag = tag.trim().toLowerCase();
		return hashtagEA.getHashTags(tag);
	}

	public static void RemoveHashTagNews(String tag, long id) 
	{
		HashTagEntity entity = hashtagEA.getHashTags(tag);
		if(entity == null) return;

		entity.removeNews(id);
		hashtagEA.put(entity);
	}

	public static void userAddFans(String friend, String username) 
	{
		UserEntity user = getUserEntity(friend);
		if(user != null)
		{
			user.addFans(username);
			userEA.putEntity(user);
		}
	}

	public static void userAddHeadUrl(String friend, String url) 
	{
		UserEntity user = getUserEntity(friend);
		if(user != null)
		{
			user.addHeadUrl(url);
			userEA.putEntity(user);
		}	
	}

	public static void addGroupHeadUrl(Long id, String url) 
	{
		GroupEntity g = getGroupEntity(id);
		if(g != null)
		{
			g.addHeadUrl(url);
			groupEA.putEntity(g);
		}
	}

	public static boolean hasGroup(Long gid) 
	{
		GroupEntity g = getGroupEntity(gid);
		return g != null;
	}

	public static UserListView loadSearchUserByName(String tarname) 
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

	/**
	 * may trigger MoviePageEntity to create: 
	 * mainly store user's like and reviews
	 * @param username
	 * @param movieId
	 */
	public static void userLikeMovie(String username, String movieId) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			user.likeMovie(movieId);
			userEA.putEntity(user);

			MoviePageEntity movie = moviepageEA.getEntity(movieId);
			//is new
			if(movie == null)
			{
				movie = moviepageEA.createEntity(movieId);
				MoviePageView mpv = MoviePageViewCache.get(movieId);
				if(mpv != null)
				{
					movie.setName(mpv.getName());
					movie.setPoster(mpv.getPoster());
				}
			}
			movie.addLike();
			moviepageEA.putEntity(movie);
		}	
	}	

	public static void userUnlikeMovie(String username, String movieId) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			user.unlikeMovie(movieId);
			userEA.putEntity(user);
		}	
	}

	public static void upUserSettings(String username, String head_url, String profile_url, String description, Integer[] genres) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			user.upSettings(head_url, profile_url, description, genres);
			userEA.putEntity(user);
		}		
	}

	public static UserSettingView loadUserSettingView(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return new UserSettingView(
					user.getName(),
					user.getHeadUrl(), 
					user.getProfileUrl(), 
					user.getLikeGenres(),
					user.getDescription());
		}		
		return null;
	}


	public static ArrayList<String> loadMoviePagePosterUrl(ArrayList<String>movieId)
	{
		ArrayList<String>posterUrl = new ArrayList<String>(); 
		for(String mid: movieId)
		{
			MoviePageEntity mpe = moviepageEA.getEntity(mid);
			if(mpe != null)
			{
				posterUrl.add(mpe.getPoster());
				//print("movie : " + mpe.getName() + " " + posterUrl);
			}
			else
			{
				posterUrl.add("/images/not-found.png");
			}
		}
		return posterUrl;
	}

	/**
	 * 
	 * @param username
	 * @param isMyPage
	 * @param isMyFriend
	 * @return
	 */
	public static UserInfoView loadUserInfoView(String username, boolean isMyPage, boolean isMyFriend) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			ArrayList<String>movieId = user.getLikeMovies(); 
			ArrayList<String>posterUrl = loadMoviePagePosterUrl(movieId); 

			return new UserInfoView(
					user.getName(),
					user.getHeadUrl(), 
					user.getProfileUrl(), 
					user.getLikeGenres(),
					user.getDescription(),
					user.getFansNum(),
					user.getFollowingNum(),
					user.getNewsNum(),
					new Boolean(isMyPage), 
					new Boolean(isMyFriend), 
					movieId,
					posterUrl
					);

		}		
		return null;
	}

	/**
	 * 
	 * @param gid
	 * @param username
	 * @param showTab
	 * @return
	 */
	public static GroupPageView loadGroupPageView(Long gid, String username, int showTab) 
	{
		GroupEntity gobj = groupEA.getEntity(gid);
		if(gobj != null) 
		{
			boolean inGroup = gobj.hasMember(username);
			return new GroupPageView(gobj.getId(), gobj.getName(), gobj.getCreator(), gobj.getHeadUrl(), 
					gobj.getProfileUrl(), gobj.getDescription(), inGroup, showTab); 
		}
		return null;
	}

	public static GroupListView loadSearchGroupList(String name) 
	{
		GroupListView glv = new GroupListView();
		ArrayList<GroupEntity>glist = groupEA.getSearchGroup(name);
		for(GroupEntity gobj: glist)
		{
			GroupObjectView gov = new GroupObjectView(gobj.getId(), gobj.getHeadUrl(), gobj.getName());
			glv.addGroupObject(gov);
		}
		return glv;
	}

	public static NewsListView loadSearchHashTag(String search) 
	{
		ArrayList<HashTagEntity>taglist = hashtagEA.searchHashTag(search);
		NewsListView nlv = new NewsListView(); 

		HashSet<Long>vis = new HashSet<Long>();
		for(HashTagEntity tag: taglist)
		{
			for(Long newsid: tag.getNews())
			{
				if(!vis.contains(newsid))
				{
					NewsEntity news = newsEA.getNewsEntityById(newsid);
					if(news == null) continue;
					String userUrl = getUserEntity(news.getCreator()).getHeadUrl();
					NewsObjectView newsViewObj = new NewsObjectView(news, userUrl);
					nlv.addNews(newsViewObj);
					vis.add(news.getId());
				}
			}
		}
		return nlv;
	}

	public static void createFBUser(String name, String password, String fbid) 
	{
		userEA.add(name, password, fbid);
	}

	public static NewsListView getNewsListViewFromNewsIds(ArrayList<Long> newsList) 
	{
		NewsListView nlv = new NewsListView(); 	
		for(Long newsid: newsList) 
		{
			NewsEntity news = newsEA.getNewsEntityById(newsid);
			String userUrl = getUserEntity(news.getCreator()).getHeadUrl();
			NewsObjectView newsViewObj = new NewsObjectView(news, userUrl);
			nlv.addNews(newsViewObj);
		}
		return nlv;
	}

	/**
	 * group news store in groupEntity, not userEntity
	 * @param username
	 * @param gid
	 * @param info
	 */
	public static void addGroupNews(String username, Long gid, String info) 
	{
		GroupEntity gobj = getGroupEntity(gid);
		if(gobj == null)
		{
			return;
		}

		NewsEntity news_obj = new NewsEntity(username, idEA.getNextNewsId(), info, Const.NEWS_TWEET_IN_GROUP);
		newsEA.addNews(news_obj);

		gobj.addNews(news_obj.getId());
		groupEA.putEntity(gobj);

		storeMailBox(news_obj);
	}

	public static void upGroupSettings(Long gid, String head_url, String profile_url, String description) 
	{
		GroupEntity obj = getGroupEntity(gid); 
		if(obj != null)
		{
			obj.upSettings(head_url, profile_url, description);
			groupEA.putEntity(obj);
		}		
	}

	public static NewsListView loadMyMailBoxNews(String username) 
	{
		NewsListView nlv = new NewsListView();

		ArrayList<Long>newids = getUserMailBox(username);

		for(long id: newids)
		{
			NewsEntity newsEntity = getNewsEntityByIds(id);
			if(newsEntity == null) continue;	
			String userUrl = getUserEntity(newsEntity.getCreator()).getHeadUrl();
			NewsObjectView newsViewObj = new NewsObjectView(newsEntity, userUrl);
			nlv.addNews(newsViewObj);
		}

		UserEntity user = getUserEntity(username);
		user.clearUnReadMail();
		userEA.putEntity(user);

		return nlv;
	}

	private static ArrayList<Long> getUserMailBox(String username) 
	{
		UserEntity user = userEA.getEntity(username);
		if(user != null)
		{
			return user.getMailBox();
		}
		return null;
	}

	public static boolean canUserTweetInGroup(String username, Long gid) 
	{
		GroupEntity gobj = groupEA.getEntity(gid);
		return gobj != null && gobj.canUserTweet(username);
	}

	public static ArrayList<Integer> getUserLikeGenres(String username) 
	{
		UserEntity user = userEA.getEntity(username);
		if(user != null)
		{
			return user.getLikeGenresArrayList(); 
		}
		return null;
	}

	public static ArrayList<String>  getUserlikeMovies(String username) 
	{
		UserEntity user = userEA.getEntity(username);
		if(user != null)
		{
			return user.getLikeMovies();
		}
		return null;
	}
}
