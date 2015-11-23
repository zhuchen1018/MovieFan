package com.myapp.utils;

public class Const 
{
	public final static String ROOT = "./database/"; 
	//public final static String WebPageRoot = ROOT + "web_pages/"; 
	
	/*db store*/
	public final static String DB_STORE_NAME = "entity_store"; 

	//public final static String CHANNEL_STORE_NAME = "channel_store"; 
	//public final static String WEBPAGE_STORE_NAME = "webpage_store"; 
	
	/*db accessor*/
	/*
	public final static String USER_ACC = "UserAcc"; 
	public final static String CHANNEL_ACC = "ChannelAcc"; 
	public final static String LOGIN_ACC = "LoginAcc"; 
	public final static String URL_ACC = "UrlAcc"; 
	public final static String WEBPAGE_ACC = "WebPageAcc"; 

	public final static String[] ALL_STORES = {DB_STORE_NAME, 
			CHANNEL_STORE_NAME,  WEBPAGE_STORE_NAME}; 
	*/
	
	
	
	/*
	public final static String[] ALL_ACCESSORS = {
			URL_STORE_NAME, WEBPAGE_STORE_NAME}; 
	 */
	
	//public final static String USER_ID_TYPE = "USER_ID_TYPE"; 
	public final static String TWEET_ID_TYPE = "TWEET_ID_TYPE"; 
	public final static String ARTICLE_ID_TYPE = "ARTICLE_ID_TYPE"; 
	public final static String COMMENT_ID_TYPE = "COMMENT_ID_TYPE"; 
	public final static String NEWS_ID_TYPE = "NEWS_ID_TYPE"; 
	public final static String GROUP_ID_TYPE = "GROUP_ID_TYPE"; 
	
	
	public static final int ORDERBY_RATING=0,ORDERBY_TIME=1,ORDERBY_VOTES=2;//orderby
	public static final int ID_SEARCH=0,NAME_SEARCH=1;
	

	/**
	 * news type
	 */
	public final static String NEWS_TWITTER = "news_twitter"; 
	public final static String NEWS_MOVIE_REVIEW = "news_movie_review"; 
	public final static String NEWS_MAKE_FRIENDS = "news_make_friends"; 
	public final static String NEWS_ADD_GROUP = "news_add_group"; 
	public final static String NEWS_SHARE_MOVIE = "news_share_movie"; 
	public final static String NEWS_LIKE_MOVIE = "news_like_movie"; 
}
