package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.Const;
import com.myapp.view.NewsListView;
import com.myapp.view.UserListView;
import com.myapp.view.UserObjectView;

public class GroupServlet extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1849777603528766598L;
	private DBWrapper db; 

	public GroupServlet () throws IOException
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

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	{
		if(!ServletCommon.hasLoginSession(request))
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  request, response);
			return;
		}
		
		String url = request.getServletPath();
		
		if(url.equals(Const.JOIN_GROUP_URL))
		{
			handleJoinGroupPost(request, response);
		}
		else if(url.equals(Const.LEAVE_GROUP_URL))
		{
			handleLeaveGroupPost(request, response);
		}
		else if(url.equals(Const.CREATE_GROUP_URL))
		{
			handleCreateGroupPost(request, response);
		}
		else if(url.equals(Const.JOIN_EVENT_URL))
		{
			handleJoinEventPost(request, response);
		}
		else if(url.equals(Const.LEAVE_EVENT_URL))
		{
			handleLeaveEventPost(request, response);
		}
		else if(url.equals(Const.CREATE_EVENT_URL))
		{
			handleCreateEventPost(request, response);
		}
	}

	private void handleLeaveGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.SESSION_USERNAME_NULL_INFO,  request, response);
			return;
		}
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.PrintErrorPage(Const.CAN_NOT_JOIN_GROUP_INFO,  request, response);
			return;
		}

		Long gid = Long.parseLong(group);
		if(!db.hasGroup(gid))
		{
			ServletCommon.PrintErrorPage(Const.CAN_NOT_JOIN_GROUP_INFO,  request, response);
			return;
		}

		db.userLeaveGroup(username, gid);
		db.sync();

		ServletCommon.RedirectToHome(request, response);
	}

	private void handleJoinGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{

		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.SESSION_USERNAME_NULL_INFO,  request, response);
			return;
		}

		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.PrintErrorPage(Const.CAN_NOT_JOIN_GROUP_INFO,  request, response);
			return;
		}

		initDB();
		Long gid = Long.parseLong(group);
		db.userJoinGroup(username, gid);
		db.sync();
	
		GroupEntity gobj = db.getGroupEntity(gid);
		if(gobj == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}

		ArrayList<Long>news = gobj.getNews();
		NewsListView nlv = db.getNewsListViewFromNewsIds(news);	

		ArrayList<String>members = gobj.getMembers();
		UserListView ulv = db.getUserViewListFromNameList(members);	

		ServletCommon.RedirectToGroupPage(request, response, username, gid, nlv, ulv);
	}

	private void handleCreateEventPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}

	private void handleLeaveEventPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}

	private void handleJoinEventPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}

	private void handleCreateGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO + request.getSession().getId(), request, response); 
			return;
		}

		String name = request.getParameter("NewGroupName");
		if(name == null)
		{
			ServletCommon.PrintErrorPage("Please enter a group name.",  request, response);
			return;
		}

		boolean canCreateGroup = db.canCreateGroup(username);
		if(canCreateGroup)
		{
			GroupEntity gobj = db.createNewGroup(name,  username);
			if(gobj != null)
			{
				ArrayList<Long>news = gobj.getNews();
				NewsListView nlv = db.getNewsListViewFromNewsIds(news);	

				ArrayList<String>members = gobj.getMembers();
				UserListView ulv = db.getUserViewListFromNameList(members);	

				ServletCommon.RedirectToGroupPage(request, response, username, gobj.getId(), nlv, ulv);
				
				db.sync();
			}
			else
			{
				ServletCommon.redirect500(request, response);
			}
		}
		else
		{
			ServletCommon.PrintErrorPage("You create too much groups! Maximum: 3",  request, response);
			return;
		}	
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		String url = request.getServletPath();
		if(!ServletCommon.hasLoginSession(request))
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  request, response);
			return;
		}

		if(url.equals(Const.GROUP_PAGE_URL))
		{
			//http://localhost:8080/grouppage?group=123
			handleGroupPageGet(request, response);
		}	
	}

	private void handleGroupPageGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.SESSION_USERNAME_NULL_INFO,  request, response);
			return;
		}
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}

		Long gid = null;
		try
		{
			gid = Long.parseLong(group);
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			ServletCommon.PrintErrorPage(Const.NO_THIS_GROUP_INFO, request, response);
			return;
		}
		
		initDB();

		GroupEntity gobj = db.getGroupEntity(gid);
		if(gobj == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_GROUP_INFO, request, response);
			return;
		}


		ArrayList<Long>news = gobj.getNews();
		NewsListView nlv = db.getNewsListViewFromNewsIds(news);	

		ArrayList<String>members = gobj.getMembers();
		UserListView ulv = db.getUserViewListFromNameList(members);	
		
		ServletCommon.RedirectToGroupPage(request, response, username, gid, nlv, ulv);	
	}

	private void handleCreateGroupGet(HttpServletRequest request, HttpServletResponse response) 
	{ 
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.SESSION_USERNAME_NULL_INFO,  request, response);
			return;
		}

		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();

			out.println("<form action=\"" + Const.CREATE_GROUP_URL + "\" method=\"post\">");

			out.println("<P>" + "Create a new Group!\n" + "</P>");
			out.println("<P>" + "Group Name:" + "</P>");
			out.println("<input type=\"text\" name=\"NewGroupName\" value=\"\" />");

			out.println("<input type=\"submit\" />");
			out.println("</form>");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}	
}


