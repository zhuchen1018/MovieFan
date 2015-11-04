package com.myapp.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
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

public class Twitter extends HttpServlet 
{
	private DBWrapper db; 
	public Twitter() throws IOException
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		if(!ServletCommon.isSessionValid(request))
		{
			ServletCommon.PrintErrorPage(ServletConst.LOGIN_FIRST_INFO,  response);
			return;
		}
		
		String info = request.getParameter("TWEET"); 
		if(info == null)
		{
			ServletCommon.PrintErrorPage("Please say something.",  response);
			return;
		}

		db = ServletCommon.initDB(db);
		
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		if(username == null || username.isEmpty())
		{
			ServletCommon.PrintErrorPage("Session username is null.",  response);
			return;
		}

		/*: DB add new user*/
		addUserTweet(username, info); 
	
		/*
		 * result page:
		 */
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>Twitter </TITLE></HEAD><BODY>");
		String res = "Twitter successed!";
		out.println("<P>" + res + "</P>");
		out.println("<P>" + "\n" + "</P>");
		
		ServletCommon.gotoHome(response);
		out.println("</BODY></HTML>");		
		
		db.close();
	}

	private void addUserTweet(String username, String info) 
	{
		db.addUserTweet(username, info);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		//print a form for user to input the arguments.
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<form action=\"/user_tweet\" method=\"post\">");
			
		out.println("<P>" + "Say something..." + "</P>");
		out.println("<input type=\"text\" name=\"TWEET\" value=\"\" />");

		out.println("<input type=\"submit\" />");
		out.println("</form>");
		
		
		//TODO:
		//list all the tweets user sent...
	}

}

