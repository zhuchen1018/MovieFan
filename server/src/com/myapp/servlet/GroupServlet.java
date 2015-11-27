package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.storage.DBWrapper;
import com.myapp.utils.ServletCommon;
import com.myapp.utils.ServletConst;

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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		initDB();

		if(!ServletCommon.isSessionValid(request))
		{
			ServletCommon.PrintErrorPage(ServletConst.LOGIN_FIRST_INFO,  response);
			return;
		}

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

		/* Check db if the group name exists*/
		if(hasGroup(name))
		{
			ServletCommon.PrintErrorPage("Sorry, this group name has been registered.",  response);
			return;
		}

		db.storeGroup(name,  creator);

		/*
		 * result page:
		 */
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>Create Group</TITLE></HEAD><BODY>");
		String res = "Create successed!";
		out.println("<P>" + res + "</P>");
		out.println("<P>" + "\n" + "</P>");

		ServletCommon.gotoHome(response);
		out.println("</BODY></HTML>");		
		
		db.sync();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String url = request.getServletPath();
		if(!ServletCommon.isSessionValid(request))
		{
			ServletCommon.PrintErrorPage(ServletConst.LOGIN_FIRST_INFO,  response);
			return;
		}
		if(url.equals(ServletConst.CREATE_GROUP_URL))
		{
			handleCreateGroupReq(request, response);
		}
		else if(url.equals(ServletConst.JOIN_GROUP_URL))
		{
			handleJoinGroup(request, response);
		}
		else if(url.equals(ServletConst.LEAVE_GROUP_URL))
		{
			handleLeaveGroup(request, response);
		}
	}


	private void handleLeaveGroup(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.SESSION_USERNAME_NULL_INFO,  response);
			return;
		}
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.CAN_NOT_JOIN_GROUP_INFO,  response);
			return;
		}
	}

	private void handleJoinGroup(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.SESSION_USERNAME_NULL_INFO,  response);
			return;
		}
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String group = query.get("group");
		if(group == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.CAN_NOT_JOIN_GROUP_INFO,  response);
			return;
		}
	}

	private void handleCreateGroupReq(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{ 
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.SESSION_USERNAME_NULL_INFO,  response);
			return;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<form action=\"" + ServletConst.CREATE_GROUP_URL + "\" method=\"post\">");

		out.println("<P>" + "Create a new Group!\n" + "</P>");
		out.println("<P>" + "Group Name:" + "</P>");
		out.println("<input type=\"text\" name=\"NewGroupName\" value=\"\" />");

		out.println("<input type=\"submit\" />");
		out.println("</form>");
	}

	private boolean hasGroup(String name) throws IOException 
	{
		return db.hasGroupByName(name);
	}

}


