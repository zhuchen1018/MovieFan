package com.myapp.utils;

public class ServletConst 
{
	public final static int COOKIE_AGE = 24  * 3600 * 7; 

	/*message*/
	public final static String LOGIN_FIRST_INFO = "Please login first.";
	public final static String SESSION_USERNAME_NULL_INFO = "Session username is null!";
	public static final String CAN_NOT_JOIN_GROUP_INFO = "You cannot join this group!";

	/*URL*/
	public final static String HOME_URL = "/home"; 
	public final static String LOGIN_URL = "/login"; 
	public final static String LOGOFF_URL = "/logoff";

	public static final String CREATE_GROUP_URL = "/create_group";

	public static final String JOIN_GROUP_URL = "/join_group";

	public static final String LEAVE_GROUP_URL = "leave_group";


}
