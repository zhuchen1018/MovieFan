package com.myapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.myapp.utils.Const;
import com.myapp.view.NewsListView;
import com.myapp.view.UserListView;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.storage.entity.UserEntity;

public class UserPage extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6232737047776432110L;
	public UserPage() 
	{
	}

	public DBWrapper initDB()
	{
		return new DBWrapper();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String url = request.getServletPath();
		if(url.equals(Const.USER_TWEET_HOME_URL))
		{
			handleTweetPost(request, response, Const.TWEET_FROM_HOME);
		}
		else if(url.equals(Const.USER_TWEET_URL))
		{
			handleTweetPost(request, response, Const.TWEET_FROM_USER);
		}
		else if(url.equals(Const.USER_TWEET_GROUP_URL))
		{
			handleTweetPost(request, response, Const.TWEET_FROM_GROUP);
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
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO, request, response);
			return;
		}

		String targetName = query.get("user");
		if(targetName == null || targetName.isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO, request, response);
			return;
		}

		//DBWrapper DBWrapper = initDB();

		UserEntity user = DBWrapper.getUserEntity(targetName);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO,  request, response);
			return;
		}

		DBWrapper.userUnfollow(username, targetName);
		//DBWrapper.close();

		ServletCommon.RedirectToUserPage(request, response, username, targetName);
	}

	/*
	private void handleArticlePost(HttpServletRequest request, HttpServletResponse response) 
	{


	}

	private void handleCommentPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}
	 */

	private void handleTweetPost(HttpServletRequest request, HttpServletResponse response, int From) throws IOException
	{
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		if(username == null || username.isEmpty())
		{
			ServletCommon.redirectToLoginPage(request, response);
			return;
		}	

		String info = request.getParameter("TWEET"); 
		if(info == null || info.trim().isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.PLEASE_ENTER_SOMETHING,  request, response);
			return;
		}

		if(info.length() > Const.MAX_TWEET_LENGTH)
		{
			ServletCommon.PrintErrorPage("Your tweet is too long....",  request, response);
			return;
		}

		//DBWrapper DBWrapper = initDB();

		if(From == Const.TWEET_FROM_HOME)
		{
			DBWrapper.addNewsTwitter(username, info); 
			ServletCommon.RedirectToHome(request, response);
		}
		else if(From == Const.TWEET_FROM_USER)
		{
			DBWrapper.addNewsTwitter(username, info); 
			ServletCommon.RedirectToUserPage(request, response, username, username);
		}
		else if(From == Const.TWEET_FROM_GROUP) //tweet_group?group=123
		{
			Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
			String group = query.get("group");
			if(group == null)
			{
				//DBWrapper.close();
				ServletCommon.PrintErrorPage(Const.NO_THIS_GROUP_INFO,  request, response);
				return;
			}

			Long gid = Long.parseLong(group);
			if(!DBWrapper.hasGroup(gid))
			{
				//DBWrapper.close();
				ServletCommon.PrintErrorPage(Const.NO_THIS_GROUP_INFO,  request, response);
				return;
			}
	
			if(!DBWrapper.canUserTweetInGroup(username, gid))
			{
				//DBWrapper.close();
				ServletCommon.PrintErrorPage("You cannot discuss in this group",  request, response);
				return;
			}

			//write DBWrapper
			DBWrapper.addGroupNews(username, gid, info); 
			
			GroupEntity gobj = DBWrapper.getGroupEntity(gid);

			//refresh group info
			ArrayList<Long>news = gobj.getNews();
			NewsListView nlv = DBWrapper.getNewsListViewFromNewsIds(news);	
			//System.out.println("NewsListView size: " + nlv.getNewsNumber());

			ArrayList<String>members = gobj.getMembers();
			UserListView ulv = DBWrapper.getUserViewListFromNameList(members);	
				
			//DBWrapper.close();

			ServletCommon.RedirectToGroupPage(request, response, username, gid, nlv, ulv, 1);	
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	{
		if(!ServletCommon.hasLoginSession(request))
		{
			ServletCommon.redirectToLoginPage(request, response);
			return;
		}
		String url = request.getServletPath();

		if(url.equals(Const.USER_PAGE_URL))
		{
			handleUserPageGet(request, response);
		}	
		else if(url.equals(Const.USER_FOLLOWING_URL))
		{
			handleFollowingGet(request, response);
		}	
		else if(url.equals(Const.USER_FANS_URL))
		{
			handleUserFansGet(request, response);
		}	
		else if(url.equals(Const.USER_NEWS_URL))
		{
			handleUserNewsGet(request, response);
		}	
		else
		{
			ServletCommon.redirect404(request, response);
		}
	}

	private void handleUserNewsGet(HttpServletRequest request, HttpServletResponse response) 
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
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO, request, response);
			return;
		}

		String targetName = query.get("user");
		ServletCommon.RedirectToUserPage(request, response, username, targetName);
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
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO, request, response);
			return;
		}

		//DBWrapper DBWrapper = initDB();

		String targetName = query.get("user");
		UserListView ulv = DBWrapper.loadFansList(targetName);
		request.setAttribute("UserListView", null); 
		request.setAttribute("UserListView", ulv); 

		//DBWrapper.close();

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
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO, request, response);
			return;
		}

		//DBWrapper DBWrapper = initDB();

		String targetName = query.get("user");
		UserListView ulv = DBWrapper.loadFollowingsList(targetName); 
		request.setAttribute("UserListView", null); 
		request.setAttribute("UserListView", ulv); 
		
		//DBWrapper.close();

		String location = "/jsp/UserList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	private void handleFollowUserPost(HttpServletRequest request, HttpServletResponse response) 
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
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO, request, response);
			return;
		}

		String targetName = query.get("user");
		if(targetName == null || targetName.isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO, request, response);
			return;
		}

		//DBWrapper DBWrapper = initDB();

		UserEntity user = DBWrapper.getUserEntity(targetName);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO,  request, response);
			return;
		}

		DBWrapper.userAddFollow(username, targetName);
		DBWrapper.userAddNewsFollowUser(username, targetName);
		DBWrapper.userAddFans(targetName, username);
		
		//DBWrapper.close();

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
			ServletCommon.redirectToLoginPage(request, response);
			return;
		}

		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		if(query == null || query.get("user") == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO, request, response);
			return;
		}

		String targetName = query.get("user");
		if(targetName.isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO, request, response);
			return;
		}

		//DBWrapper DBWrapper = initDB();

		UserEntity user = DBWrapper.getUserEntity(targetName);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO,  request, response);
			return;
		}

		//DBWrapper.close();

		ServletCommon.RedirectToUserPage(request, response, username, targetName);
	}
}

