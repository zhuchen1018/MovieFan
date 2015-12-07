package com.myapp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.PrettyTime;

public class Const 
{
	public final static String ROOT = "./database/"; 
	
	/*db store*/
	public final static String DB_STORE_NAME = "entity_store"; 

	
	//public final static String USER_ID_TYPE = "USER_ID_TYPE"; 
	public final static String TWEET_ID_TYPE = "TWEET_ID_TYPE"; 
	public final static String ARTICLE_ID_TYPE = "ARTICLE_ID_TYPE"; 
	public final static String COMMENT_ID_TYPE = "COMMENT_ID_TYPE"; 
	public final static String NEWS_ID_TYPE = "NEWS_ID_TYPE"; 
	public final static String GROUP_ID_TYPE = "GROUP_ID_TYPE"; 
	public final static String EVENT_ID_TYPE = "EVENT_ID_TYPE"; 
	
	
	public static final int ORDERBY_RATING=0,ORDERBY_TIME=1,ORDERBY_VOTES=2;//orderby
	public static final int ID_SEARCH=0,NAME_SEARCH=1;
	// 7 days
	public final static int LOGIN_SESSION_AGE = 24  * 3600 * 7; 

	/*message*/
	public final static String LOGIN_FIRST_INFO = "Please login first.";
	public final static String SESSION_USERNAME_NULL_INFO = "Session username is null!";
	public static final String CAN_NOT_JOIN_GROUP_INFO = "You cannot join this group!";
	public static final String NO_THIS_USER_INFO = "No this user!";

	/*URL*/
	public final static String HOME_URL = "/home"; 
	public static final String HOME_TEST_URL = "/hometest";

	//Account
	public static final String REGISTER_URL = "/register";
	public final static String LOGIN_URL = "/login"; 
	public final static String LOGOFF_URL = "/logoff";
	public static final String USER_SETTINGS_URL = "/usersettings";
	
	//User
	public static final String USER_PAGE_URL = "/userpage";
	public static final String USER_TWEET_HOME_URL = "/tweet_home";
	public static final String USER_TWEET_URL = "/tweet_user";
	public static final String USER_MAILBOX_URL = "/mailbox";

	public static final String USER_COMMENT_URL = "/comment";
	public static final String USER_ARTICLE_URL = "/article";
	public static final String TEST_USER_NEWS_URL = "/test_news";
	public static final String TEST_FRIENDS_URL = "/test_friends";
	public static final String TEST_GROUPS_URL = "/test_groups";
	//dopost
	public static final String FOLLOW_USER_URL = "/follow";
	public static final String UNFOLLOW_USER_URL = "/unfollow";
	//doget
	public static final String USER_NEWS_URL = "/usernews";
	public static final String USER_FOLLOWING_URL = "/userfollowings";
	public static final String USER_FANS_URL = "/userfans";

	
	//Movie
	public static final String MOVIE_PAGE_URL = "/moviepage";
	public static final String MOVIE_SHARE_URL = "/sharemovie"; 
	public static final String MOVIE_LIKE_URL = "/likemovie"; 
	public static final String MOVIE_REVIEW_URL = "/moviereview";
	public static final String MOVIE_UNLIKE_URL = "/unlikemovie";

	//Group
	public static final String GROUP_PAGE_URL = "/grouppage";
	public static final String CREATE_GROUP_URL = "/group/create_group";
	public static final String JOIN_GROUP_URL = "/group/join_group";
	public static final String LEAVE_GROUP_URL = "/group/leave_group";
	public static final String JOIN_EVENT_URL = "/group/join_event";
	public static final String LEAVE_EVENT_URL = "/group/leave_event";
	public static final String CREATE_EVENT_URL = "/group/create_event";

	//Search
	public static final String SEARCH_MOVIE = "/search_movie";
	public static final String SEARCH_MOVIE_RES_ADVANCED = "/search_movie_result_advanced";
	public static final String SEARCH_MOVIE_RES = "/search_movie_result";

	public static final String SEARCH_USER = "/search_user";
	public static final String SEARCH_USER_RES = "/search_user_result"; 

	public static final String SEARCH_GROUP = "/search_group";
	public static final String SEARCH_GROUP_RES = "/search_group_result";

	public static final String SEARCH_GOOGLE = "/search_google";
	public static final String SEARCH_GOOGLE_RES = "/search_google_result";
	

	public static final String VOICE_SEARCH = "/voice_search";
	
	public static final String HASHTAG_URL = "/hashtag";
	public static final String SEARCH_HASHTAG_RES = "/search_hashtag_result";

	/**
	 * User news type
	 */
	public final static int NEWS_TWITTER = 1; 
	public final static int NEWS_MOVIE_REVIEW = 2; 
	public final static int NEWS_MAKE_FRIENDS = 3; 
	public final static int NEWS_ADD_GROUP = 4; 
	public final static int NEWS_SHARE_MOVIE = 5; 
	public final static int NEWS_LIKE_MOVIE = 6; 
	public final static int NEWS_CREATE_GROUP = 7; 
	
	public final static int MOVIE_LENGTH_0_30=0;
	public final static int MOVIE_LENGTH_30_60=1;
	public final static int MOVIE_LENGTH_60_90=2;
	public final static int MOVIE_LENGTH_90_120=3;
	public final static int MOVIE_LENGTH_120_MORE=4;


	/**
	 * movie genre
	 */

	/*
	public final static int GENRE_ACTION 			= 1;
	public final static int GENRE_ADVENTURE = 2;
	public final static int GENRE_HORROR 		= 3;
	public final static int GENRE_ROMANCE 	= 4;
	public final static int GENRE_WAR 				= 5;
	public final static int GENRE_DOCUMENTARY = 6;
	public final static int GENRE_DRAMA 			= 7;
	public final static int GENRE_THRILLER 		= 8;
	public final static int GENRE_CRIME 			= 9;
	public final static int GENRE_MYSTERY 		= 10; 
	public final static int GENRE_ANIMAITON 	= 11; 
	public final static int GENRE_FANTASY 		= 12;
	public final static int GENRE_COMEDY  		= 13;
	public final static int GENRE_CHILDREN 	= 14;
	public final static int GENRE_SCIFI 				= 15;
	public final static int GENRE_MUSICAL 		= 16;
	*/

	public static HashMap<String,Integer> GENRE_MAP;

	public static String PLEASE_ENTER_SOMETHING = "Please enter something....";

	static {
		GENRE_MAP = new HashMap<String,Integer>();
		GENRE_MAP.put("Adventure", 0);
		GENRE_MAP.put("Animation", 1);
		GENRE_MAP.put("Children", 2);
		GENRE_MAP.put("Comedy", 3);
		GENRE_MAP.put("Fantasy", 4);
		GENRE_MAP.put("Romance", 5);
		GENRE_MAP.put("Drama", 6);
		GENRE_MAP.put("Action", 7);
		GENRE_MAP.put("Crime", 8);
		GENRE_MAP.put("Thriller", 9);
		GENRE_MAP.put("Horror", 10);
		GENRE_MAP.put("Mystery", 11);
		GENRE_MAP.put("Sci-Fi", 12);
		GENRE_MAP.put("Documentary", 14);
		GENRE_MAP.put("War", 15);
		GENRE_MAP.put("Musical", 16);
	}
	
	
	/**
	 * regex
	 */
	
	//for hashtag: #word
	public final static Pattern HASHTAG_PATTERN = Pattern.compile("#\\w+");

	//for @username
	public final static Pattern AT_PATTERN = Pattern.compile("@\\w+");

	public static final int MAX_TWEET_LENGTH = 200;

	public static final int MOVIEPAGEVIEW_CACHE_SIZE = 10000;

	public static final PrettyTime PRETTY_TIME  = new PrettyTime();


	/**
	 *  match all hashtag: ignoecaese, no duplicates
	 * @param text
	 * @return
	 */
	public static HashSet<String> extractHashTag(String text) 
	{
        Matcher matcher = Const.HASHTAG_PATTERN.matcher(text);
        HashSet<String>result = new HashSet<String>();
        while(matcher.find()) 
        {
        	//remove #
        	String tag = text.substring(matcher.start() + 1, matcher.end());
        	result.add(tag.toLowerCase());
        }
		return result;
	}
	
	public static HashSet<String> extractAtUser(String text) 
	{
        Matcher matcher = Const.AT_PATTERN.matcher(text);
        HashSet<String>result = new HashSet<String>();
        while(matcher.find()) 
        {
        	//remove @ 
        	String tag = text.substring(matcher.start() + 1, matcher.end());
        	result.add(tag.toLowerCase());
        }
		return result;
	}
}
