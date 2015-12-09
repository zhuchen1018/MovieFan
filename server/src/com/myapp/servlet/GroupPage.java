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
import com.myapp.utils.Const;
import com.myapp.view.GroupSettingView;
import com.myapp.view.NewsListView;
import com.myapp.view.UserListView;

public class GroupPage extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1849777603528766598L;

	public GroupPage () throws IOException
	{
	}

	public DBWrapper initDB()
	{
		return new DBWrapper();
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
		else if(url.equals(Const.GROUP_SETTINGS_URL))
		{
			handleGroupSettingsPost(request, response);
		}
		/*
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
		 */
	}

	private void handleGroupSettingsPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			System.out.println("handleFollowUser username is null"); 
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  request, response);
			return;
		}

		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}
		Long gid = null; try
		{
			gid = Long.parseLong(group);
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			ServletCommon.PrintErrorPage(Const.NO_THIS_GROUP_INFO, request, response);
			return;
		}

		////DBWrapper DBWrapper = initDB();

		GroupEntity gobj = DBWrapper.getGroupEntity(gid);
		if(gobj == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_GROUP_INFO, request, response);
			////DBWrapper.close();
			return;
		}

		if(!gobj.isCreator(username))
		{
			ServletCommon.PrintErrorPage(Const.HAVE_NO_RIGHT_TO_CHANEG_GROUP_SETTINGS, request, response);
			////DBWrapper.close();
			return;
		}

		String head_url = request.getParameter("HEAD_URL");
		String profile_url = request.getParameter("PROFILE_URL");
		String description = request.getParameter("DESC");

		DBWrapper.upGroupSettings(gid, head_url, profile_url, description);

		ArrayList<Long>news = gobj.getNews();
		NewsListView nlv = DBWrapper.getNewsListViewFromNewsIds(news);	

		ArrayList<String>members = gobj.getMembers();
		UserListView ulv = DBWrapper.getUserViewListFromNameList(members);	
	
		////DBWrapper.close();

		ServletCommon.RedirectToGroupPage(request, response, username, gid, nlv, ulv, 1); 
	}

	private void handleLeaveGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  request, response);
			return;
		}
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.PrintErrorPage(Const.CAN_NOT_JOIN_GROUP_INFO,  request, response);
			return;
		}

		////DBWrapper DBWrapper = initDB();

		Long gid = Long.parseLong(group);
		if(!DBWrapper.hasGroup(gid))
		{
			ServletCommon.PrintErrorPage(Const.CAN_NOT_JOIN_GROUP_INFO,  request, response);
			////DBWrapper.close();
			return;
		}

		DBWrapper.userLeaveGroup(username, gid);
		////DBWrapper.close();

		ServletCommon.RedirectToHome(request, response);
	}

	private void handleJoinGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  request, response);
			return;
		}

		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.PrintErrorPage(Const.CAN_NOT_JOIN_GROUP_INFO,  request, response);
			return;
		}

		////DBWrapper DBWrapper = initDB();
		Long gid = Long.parseLong(group);
		DBWrapper.userJoinGroup(username, gid);

		GroupEntity gobj = DBWrapper.getGroupEntity(gid);
		if(gobj == null)
		{
			////DBWrapper.close();
			ServletCommon.redirect404(request, response);
			return;
		}

		ArrayList<Long>news = gobj.getNews();
		NewsListView nlv = DBWrapper.getNewsListViewFromNewsIds(news);	

		ArrayList<String>members = gobj.getMembers();
		UserListView ulv = DBWrapper.getUserViewListFromNameList(members);	

		////DBWrapper.close();

		ServletCommon.RedirectToGroupPage(request, response, username, gid, nlv, ulv, 1);
	}

	/*
	private void handleCreateEventPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}

	private void handleLeaveEventPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}

	private void handleJoinEventPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}
	 */

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

		////DBWrapper DBWrapper = initDB();

		boolean canCreateGroup = DBWrapper.canCreateGroup(username);
		if(canCreateGroup)
		{
			GroupEntity gobj = DBWrapper.createNewGroup(name,  username);
			if(gobj != null)
			{
				ArrayList<Long>news = gobj.getNews();
				NewsListView nlv = DBWrapper.getNewsListViewFromNewsIds(news);	

				ArrayList<String>members = gobj.getMembers();
				UserListView ulv = DBWrapper.getUserViewListFromNameList(members);	

				////DBWrapper.close();

				ServletCommon.RedirectToGroupPage(request, response, username, gobj.getId(), nlv, ulv, 1);
			}
			else
			{
				ServletCommon.redirect500(request, response);

			}
		}
		else
		{
			////DBWrapper.close();
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
			//http://localhost:8080/grouppage?group=123&showtab=1
			handleGroupPageGet(request, response);
		}	
		else if(url.equals(Const.GROUP_SETTINGS_URL))
		{
			handleGroupSettingsGet(request, response);
		}	
		else if(url.equals(Const.CREATE_GROUP_URL))
		{
			handleCreateGroupGet(request, response);
		}	
	}

	private void handleGroupSettingsGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.redirectToLoginPage(request, response);
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

		//DBWrapper DBWrapper = initDB();

		GroupEntity gobj = DBWrapper.getGroupEntity(gid);
		if(gobj == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_GROUP_INFO, request, response);
			////DBWrapper.close();
			return;
		}

		if(!gobj.isCreator(username))
		{
			ServletCommon.PrintErrorPage(Const.HAVE_NO_RIGHT_TO_CHANEG_GROUP_SETTINGS, request, response);
			//DBWrapper.close();
			return;
		}

		String head_url = gobj.getHeadUrl();
		String profile_url = gobj.getProfileUrl();
		String description = gobj.getDescription();

		GroupSettingView usv =  new GroupSettingView(head_url,profile_url, description);
		request.setAttribute("GroupSettingView", usv);
			
		//DBWrapper.close();

		String location = "/jsp/GroupSettings.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	private void handleGroupPageGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  request, response);
			return;
		}
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}

		String showTabStr = query.get("showtab");
		int showTab;
		if(showTabStr == null)
		{
			showTab = 1;
		}
		else
		{
			showTab = Integer.parseInt(showTabStr);
		}
		System.out.println("showtab: " + showTab);

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

		////DBWrapper DBWrapper = initDB();

		GroupEntity gobj = DBWrapper.getGroupEntity(gid);
		if(gobj == null)
		{
			////DBWrapper.close();
			ServletCommon.PrintErrorPage(Const.NO_THIS_GROUP_INFO, request, response);
			return;
		}

		ArrayList<Long>news = new ArrayList<Long>(); 
		ArrayList<String>members = new ArrayList<String>(); 
		if(gobj.hasMember(username))
		{
			//can see infomation 
			news = gobj.getNews();
			members = gobj.getMembers();
		}
		
		NewsListView nlv = new NewsListView();
		UserListView ulv = new UserListView();
		if(showTab == 1)
		{
			nlv = DBWrapper.getNewsListViewFromNewsIds(news);	
		}
		else if(showTab == 2)
		{
			ulv = DBWrapper.getUserViewListFromNameList(members);	
		}

		////DBWrapper.close();
		
		ServletCommon.RedirectToGroupPage(request, response, username, gid, nlv, ulv, showTab);		
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void handleCreateGroupGet(HttpServletRequest request, HttpServletResponse response) 
	{ 
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  request, response);
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


