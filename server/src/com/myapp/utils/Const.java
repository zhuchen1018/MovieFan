package com.myapp.utils;

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
	public static final String ACCOUNT_SETTING_URL = "/setting";
	
	//User
	public static final String USER_PAGE_URL = "/UserPage";
	//public static final String USER_TWEET_URL = "/tweet";

	public static final String USER_COMMENT_URL = "/comment";
	public static final String USER_ARTICLE_URL = "/article";
	public static final String USER_NEWS_URL = "/news";
	public static final String TEST_USER_NEWS_URL = "/test_news";
	public static final String TEST_FRIENDS_URL = "/test_friends";
	public static final String TEST_GROUPS_URL = "/test_groups";

	//Group
	public static final String CREATE_GROUP_URL = "/create_group";
	public static final String JOIN_GROUP_URL = "/join_group";
	public static final String LEAVE_GROUP_URL = "/leave_group";
	

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

	/**
	 * User news type
	 */
	public final static int NEWS_TWITTER = 1; 
	public final static int NEWS_MOVIE_REVIEW = 2; 
	public final static int NEWS_MAKE_FRIENDS = 3; 
	public final static int NEWS_ADD_GROUP = 4; 
	public final static int NEWS_SHARE_MOVIE = 5; 
	public final static int NEWS_LIKE_MOVIE = 6; 
	
	public final static int MOVIE_LENGTH_0_30=0;
	public final static int MOVIE_LENGTH_30_60=1;
	public final static int MOVIE_LENGTH_60_90=2;
	public final static int MOVIE_LENGTH_90_120=3;
	public final static int MOVIE_LENGTH_120_MORE=4;



}
