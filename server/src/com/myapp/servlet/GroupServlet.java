package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

import com.myapp.storage.DBConst;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.MD5Encryptor;
import com.myapp.storage.accessor.UserAccessor;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.ServletCommon;
import com.myapp.utils.ServletConst;

public class GroupServlet extends HttpServlet 
{
	private DBWrapper db; 

	public GroupServlet () throws IOException
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		db = ServletCommon.initDB(db);

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

		String xpath_value = request.getParameter("NewGroupXPaths");
		if(xpath_value == null)
		{
			ServletCommon.PrintErrorPage("Please enter xpaths.", response);
			return;
		}
		xpath_value = URLDecoder.decode(xpath_value, "UTF-8");
		String[] xpaths = xpath_value.split(";"); 

		db.StoreGroup(name,  creator);

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

		db.close();
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
		return db.hasGroup(name);
	}

}


