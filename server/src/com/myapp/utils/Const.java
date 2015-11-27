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
	

	/**
	 * User news type
	 */
	public final static byte NEWS_TWITTER = 1; 
	public final static byte NEWS_MOVIE_REVIEW = 2; 
	public final static byte NEWS_MAKE_FRIENDS = 3; 
	public final static byte NEWS_ADD_GROUP = 4; 
	public final static byte NEWS_SHARE_MOVIE = 5; 
	public final static byte NEWS_LIKE_MOVIE = 6; 
}
