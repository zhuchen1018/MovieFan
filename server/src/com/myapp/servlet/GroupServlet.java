package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.utils.Const;

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
		initDB();
		String url = request.getServletPath();

		if(!ServletCommon.hasLoginSession(request))
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}

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
	}

	private void handleCreateGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String creator = (String) request.getSession().getAttribute("username");
		if(creator == null)
		{
			ServletCommon.PrintErrorPage("Cannot find username in the session. " + request.getSession().getId(), response); 
			return;
		}

		String name = request.getParameter("NewGroupName");
		if(name == null)
		{
			ServletCommon.PrintErrorPage("Please enter a group name.",  response);
			return;
		}

		if(db.hasGroupByName(name))
		{
			ServletCommon.PrintErrorPage("Sorry, this group name has been registered.",  response);
			return;
		}

		db.createNewGroup(name,  creator);
		db.sync();

		/*
		 * result page:
		 */
		response.setContentType("text/html");
		PrintWriter out;
		try 
		{
			out = response.getWriter();
			out.println("<HTML><HEAD><TITLE>Create Group</TITLE></HEAD><BODY>");
			String res = "Create successed!";
			out.println("<P>" + res + "</P>");
			out.println("<P>" + "\n" + "</P>");

			ServletCommon.gotoHome(response);
			out.println("</BODY></HTML>");		
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		String url = request.getServletPath();
		if(!ServletCommon.hasLoginSession(request))
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}
		if(url.equals(Const.CREATE_GROUP_URL))
		{
			handleCreateGroupGet(request, response);
		}
	}


	private void handleLeaveGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.SESSION_USERNAME_NULL_INFO,  response);
			return;
		}
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.PrintErrorPage(Const.CAN_NOT_JOIN_GROUP_INFO,  response);
			return;
		}
		Long gid = Long.parseLong(group);
		db.userLeaveGroup(username, gid);
		db.sync();
	}

	private void handleJoinGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{
		initDB();

		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.SESSION_USERNAME_NULL_INFO,  response);
			return;
		}

		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.PrintErrorPage(Const.CAN_NOT_JOIN_GROUP_INFO,  response);
			return;
		}

		Long gid = Long.parseLong(group);
		db.userAddGroup(username, gid);
		db.sync();
	}

	private void handleCreateGroupGet(HttpServletRequest request, HttpServletResponse response) 
	{ 
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.SESSION_USERNAME_NULL_INFO,  response);
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


