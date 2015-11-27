package com.myapp.utils;

public class ServletConst 
{
	// 7 days
	public final static int LOGIN_SESSION_AGE = 24  * 3600 * 7; 

	/*message*/
	public final static String LOGIN_FIRST_INFO = "Please login first.";
	public final static String SESSION_USERNAME_NULL_INFO = "Session username is null!";
	public static final String CAN_NOT_JOIN_GROUP_INFO = "You cannot join this group!";
	public static final String NO_THIS_USER_INFO = "No this user!";

	/*URL*/
	public final static String HOME_URL = "/home"; 

	//Account
	public static final String REGISTER_URL = "/register";
	public final static String LOGIN_URL = "/login"; 
	public final static String LOGOFF_URL = "/logoff";
	public static final String ACCOUNT_SETTING_URL = "/setting";
	
	//User
	public static final String USER_PAGE_URL = "/user_page";
	public static final String USER_TWEET_URL = "/tweet";

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





}
