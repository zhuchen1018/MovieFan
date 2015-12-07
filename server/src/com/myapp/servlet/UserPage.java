package com.myapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.myapp.utils.Const;
import com.myapp.view.UserListView;
import com.myapp.view.UserSettingView;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.UserEntity;



public class UserPage extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6232737047776432110L;
	private DBWrapper db; 
	public UserPage() 
	{
	}

	public void initDB()
	{
		if(db != null) return;
		try 
		{
			db = new DBWrapper();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String url = request.getServletPath();
		if(url.equals(Const.USER_TWEET_HOME_URL))
		{
			handleTweetPost(request, response, true);
		}
		else if(url.equals(Const.USER_TWEET_URL))
		{
			handleTweetPost(request, response, false);
		}
		else if(url.equals(Const.FOLLOW_USER_URL))
		{
			handleFollowUserPost(request, response);
		}
		else if(url.equals(Const.UNFOLLOW_USER_URL))
		{
			handleUnFollowUserPost(request, response);
		}
		/*
		else if(url.equals(Const.USER_COMMENT_URL))
		{
			handleCommentPost(request, response);
		}
		else if(url.equals(Const.USER_ARTICLE_URL))
		{
			handleArticlePost(request, response);
		}
		 */
	}

	private void handleUnFollowUserPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.redirectToLoginPage(request, response);
			return;
		}

		//url:     /follow_user?user=jason
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		if(query == null || query.get("user") == null)
		{
			System.out.println("handleFollowUser query user is null"); 
			ServletCommon.redirect404(request, response);
			return;
		}

		String targetName = query.get("user").trim().toLowerCase();
		if(targetName == null || targetName.isEmpty())
		{
			System.out.println("handleFollowUser targetName is null"); 
			ServletCommon.redirect404(request, response);
			return;
		}

		initDB();

		UserEntity user = db.getUserEntity(targetName);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO,  response);
			return;
		}

		db.userRemoveFriend(username, targetName);
		db.sync();	

		ServletCommon.RedirectToUserPage(request, response, username, targetName);
	}


	private void handleArticlePost(HttpServletRequest request, HttpServletResponse response) 
	{


	}

	private void handleCommentPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}

	private void handleTweetPost(HttpServletRequest request, HttpServletResponse response, boolean fromHome) throws IOException
	{
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		if(username == null || username.isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}	

		String info = request.getParameter("TWEET"); 
		if(info == null || info.trim().isEmpty())
		{
			ServletCommon.PrintErrorPage("Please say something.",  response);
			return;
		}

		if(info.length() > Const.MAX_TWEET_LENGTH)
		{
			ServletCommon.PrintErrorPage("Your tweet is too long....",  response);
			return;
		}

		initDB();
		db.addNewsTwitter(username, info); 
		db.sync();

		if(fromHome)
		{
			ServletCommon.RedirectToHome(request, response);
		}
		else
		{
			ServletCommon.RedirectToUserPage(request, response, username, username);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	{
		if(!ServletCommon.hasLoginSession(request))
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}
		String url = request.getServletPath();

		if(url.equals(Const.USER_PAGE_URL))
		{
			handleUserPageGet(request, response);
		}	
		else if(url.equals(Const.USER_MAILBOX_URL))
		{
			handleMailBoxGet(request, response);
		}	
		else if(url.equals(Const.USER_FOLLOWING_URL))
		{
			handleFollowingGet(request, response);
		}	
		else if(url.equals(Const.USER_FANS_URL))
		{
			handleUserFansGet(request, response);
		}	
		else
		{
			ServletCommon.redirect404(request, response);
		}
	}

	private void handleUserFansGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.redirectToLoginPage(request, response);
			return;
		}
		//url:     /follow_user?user=jason
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		if(query == null || query.get("user") == null)
		{
			System.out.println("handleFollowUser query user is null"); 
			ServletCommon.redirect404(request, response);
			return;
		}
		
		String targetName = query.get("user");
		UserListView ulv = db.loadFansList(targetName);
		request.setAttribute("UserListView", null); 
		request.setAttribute("UserListView", ulv); 

		String location = "/jsp/UserList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	private void handleFollowingGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.redirectToLoginPage(request, response);
			return;
		}
		//url:     /follow_user?user=jason
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		if(query == null || query.get("user") == null)
		{
			System.out.println("handleFollowUser query user is null"); 
			ServletCommon.redirect404(request, response);
			return;
		}
		
		String targetName = query.get("user");
		UserListView ulv = db.loadFollowingsList(targetName); 
		request.setAttribute("UserListView", null); 
		request.setAttribute("UserListView", ulv); 

		String location = "/jsp/UserList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);

	}

	private void handleMailBoxGet(HttpServletRequest request, HttpServletResponse response) 
	{
		//TODO:
		ServletCommon.PrintErrorPage("handleMailBoxGet is developing......",  response);
	}





	private void handleFollowUserPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			System.out.println("handleFollowUser username is null"); 
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}

		//url:     /follow_user?user=jason
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		if(query == null || query.get("user") == null)
		{
			System.out.println("handleFollowUser query user is null"); 
			ServletCommon.redirect404(request, response);
			return;
		}

		String targetName = query.get("user").trim().toLowerCase();
		if(targetName == null || targetName.isEmpty())
		{
			System.out.println("handleFollowUser targetName is null"); 
			ServletCommon.redirect404(request, response);
			return;
		}

		initDB();

		UserEntity user = db.getUserEntity(targetName);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO,  response);
			return;
		}

		db.userAddFriend(username, targetName);
		db.userAddNewsMakeFriends(username, targetName);
		db.userAddFans(targetName, username);
		db.sync();

		ServletCommon.RedirectToUserPage(request, response, username, targetName);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void handleUserPageGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			System.out.println("handleUserPageGet username is null"); 
			ServletCommon.redirect404(request, response);
		}

		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		if(query == null || query.get("user") == null)
		{
			System.out.println("handleUserPageGet query is null"); 
			ServletCommon.redirect404(request, response);
			return;
		}

		String targetName = query.get("user").trim().toLowerCase();
		if(targetName == null || targetName.isEmpty())
		{
			System.out.println("handleUserPageGet targetName is null"); 
			ServletCommon.redirect404(request, response);
			return;
		}

		initDB();

		UserEntity user = db.getUserEntity(targetName);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO,  response);
			return;
		}

		ServletCommon.RedirectToUserPage(request, response, username, targetName);
	}


	/*
	private void showTweetWindow(String username, HttpServletResponse response)
	{
		response.setContentType("text/html");
		try 
		{
			PrintWriter out = response.getWriter();
			out.println("<form action=\"/tweet\" method=\"post\">");

			out.println("<P>" + "Say something..." + "</P>");
			out.println("<input type=\"text\" name=\"TWEET\" value=\"\" />");

			out.println("<input type=\"submit\" />");
			out.println("</form>");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	 */
}

