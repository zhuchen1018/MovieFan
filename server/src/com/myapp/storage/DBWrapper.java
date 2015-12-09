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
	private MoviePageAccessor moviepageEA; 

	private boolean is_close;

	public DBWrapper() 
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
			e.printStackTrace();
			return;
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
		moviepageEA = new MoviePageAccessor(store);

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
		/*
		store.sync();
		env.sync();
		*/
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
	public UserAccessor getUserAccessor() 
	{
		return userEA;
	}

	public void addNextNewsId()
	{
		idEA.getNextNewsId();
	}


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
	}

	/*All kinds of add news functions*/
	public void addNewsTwitter(String username, String tweet) 
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
	private void storeMailBox(NewsEntity news_obj) 
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

	public void userAddNewsFollowUser(String username, String othername) 
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

	public void userAddFollow(String username, String friendname) 
	{
		userEA.followUser(username, friendname);	
	}

	public void userUnfollow(String username, String targetName) 
	{
		userEA.unfollowUser(username, targetName);	
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
	 * Show Followings list
	 * @param username
	 * @param request
	 * @param response
	 */
	public UserListView loadFollowingsList(String username) 
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
	public UserListView getUserViewListFromNameList(ArrayList<String>nameList) 
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
	public UserListView loadFansList(String username) 
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



	private ArrayList<String> getUserFollowings(String username) 
	{
		UserEntity user = getUserEntity(username);
		if(user != null)
		{
			return user.getFollowings();
		}
		return null;
	}

	private ArrayList<String> getUserFans(String username) 
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
	public NewsListView loadMyNews(String username) 
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

	/**
	 * may trigger MoviePageEntity to create: 
	 * mainly store user's like and reviews
	 * @param username
	 * @param movieId
	 */
	public void userLikeMovie(String username, String movieId) 
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
			user.upSettings(head_url, profile_url, description, genres);
			userEA.putEntity(user);
		}		
	}

	public UserSettingView loadUserSettingView(String username) 
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


	public ArrayList<String> loadMoviePagePosterUrl(ArrayList<String>movieId)
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
	public UserInfoView loadUserInfoView(String username, boolean isMyPage, boolean isMyFriend) 
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
	public GroupPageView loadGroupPageView(Long gid, String username, int showTab) 
	{
		GroupEntity gobj = groupEA.getEntity(gid);
		if(gobj != null) 
		{
			boolean inGroup = gobj.hasMember(username);
			return new GroupPageView(gid, gobj.getName(), gobj.getCreator(), gobj.getHeadUrl(), 
					gobj.getProfileUrl(), gobj.getDescription(), inGroup, showTab); 
		}
		return null;
	}

	public GroupListView loadSearchGroupList(String name) 
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

	public NewsListView loadSearchHashTag(String search) 
	{
		ArrayList<HashTagEntity>taglist = hashtagEA.searchHashTag(search);
		NewsListView nlv = new NewsListView(); 

		for(HashTagEntity tag: taglist)
		{
			for(Long newsid: tag.getNews())
			{
				NewsEntity news = newsEA.getNewsEntityById(newsid);
				String userUrl = getUserEntity(news.getCreator()).getHeadUrl();
				NewsObjectView newsViewObj = new NewsObjectView(news, userUrl);
				nlv.addNews(newsViewObj);
			}
		}
		return nlv;
	}

	public void createFBUser(String name, String password, String fbid) 
	{
		userEA.add(name, password, fbid);
	}

	public NewsListView getNewsListViewFromNewsIds(ArrayList<Long> newsList) 
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
	public void addGroupNews(String username, Long gid, String info) 
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

	public void upGroupSettings(Long gid, String head_url, String profile_url, String description) 
	{
		GroupEntity obj = getGroupEntity(gid); 
		if(obj != null)
		{
			obj.upSettings(head_url, profile_url, description);
			groupEA.putEntity(obj);
		}		
	}

	public NewsListView loadMyMailBoxNews(String username) 
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

	private ArrayList<Long> getUserMailBox(String username) 
	{
		UserEntity user = userEA.getEntity(username);
		if(user != null)
		{
			return user.getMailBox();
		}
		return null;
	}
}
