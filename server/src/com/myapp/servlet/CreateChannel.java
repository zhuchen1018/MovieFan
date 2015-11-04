package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashSet;
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


public class CreateChannel  extends HttpServlet 
{
	private DBWrapper db; 

	public CreateChannel () throws IOException
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		db = ServletCommon.initDB(db);
		
		if(!ServletCommon.isSessionValid(request))
		{
			ServletCommon.PrintErrorPage("Please login first.",  response);
			return;
		}
	
		String creator = (String) request.getSession().getAttribute("username");
		if(creator == null)
		{
			ServletCommon.PrintErrorPage("Cannot find username in the session. " + request.getSession().getId(), response); 
			return;
		}

		String name = request.getParameter("NewChannelName");
		if(name == null)
		{
			ServletCommon.PrintErrorPage("Please enter a channel name.",  response);
			return;
		}

		/* Check db if the channel name exists*/
		if(hasChannel(name))
		{
			ServletCommon.PrintErrorPage("Sorry, this channel name has been registered.",  response);
			return;
		}
		
		String xpath_value = request.getParameter("NewChannelXPaths");
		if(xpath_value == null)
		{
			ServletCommon.PrintErrorPage("Please enter xpaths.", response);
			return;
		}
		xpath_value = URLDecoder.decode(xpath_value, "UTF-8");
		String[] xpaths = xpath_value.split(";"); 
		
		db.StoreChannel(name, xpaths, creator);
		
		/*
		 * result page:
		 */
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>Create Channel</TITLE></HEAD><BODY>");
		String res = "Create successed!";
		out.println("<P>" + res + "</P>");
		out.println("<P>" + "\n" + "</P>");

		ServletCommon.gotoHome(response);
		out.println("</BODY></HTML>");		
		
		db.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		if(!ServletCommon.isSessionValid(request))
		{
			ServletCommon.PrintErrorPage("Please login first.", response);
			return;
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<form action=\"/create_channel\" method=\"post\">");

		out.println("<P>" + "Create New Channel:\n" + "</P>");
		out.println("<P>" + "Channel Name:" + "</P>");
		out.println("<input type=\"text\" name=\"NewChannelName\" value=\"\" />");
		
		out.println("<P>" + "XPaths:" + "</P>");
		out.println("<P>" + "Several XPaths should be separated by \";\":" + "</P>");
		out.println("<input type=\"text\" name=\"NewChannelXPaths\" value=\"\" />");

		out.println("<input type=\"submit\" />");
		out.println("</form>");
	}

	private boolean hasChannel(String name) throws IOException 
	{
		return db.hasChannel(name);
	}

}


