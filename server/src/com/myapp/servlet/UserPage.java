package com.myapp.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.myapp.storage.entity.TweetEntity;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.ServletCommon;
import com.myapp.utils.ServletConst;

public class UserPage extends HttpServlet 
{
	private DBWrapper db; 
	public UserPage() throws IOException
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String url = request.getServletPath();
		if(url.equals(ServletConst.USER_TWEET_URL))
		{
			handleTweetPost(request, response);
		}
		else
		{
			
		}
	}

	private void handleTweetPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		if(username == null || username.isEmpty())
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
		db.addTweet(username, info);
		db.close();
		
		showTweetWindow(username, response);
		showMyNews(username, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String username = ServletCommon.getSessionUsername(request);
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		if(query == null || query.get("user") == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.NO_THIS_USER_INFO,  response);
			return;
		}

		String target_name = query.get("user");
		db = ServletCommon.initDB(db);
		UserEntity user = db.getUserEntity(target_name);

		if(user == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.NO_THIS_USER_INFO,  response);
			return;
		}

		boolean is_mine = username != null && username.equals(target_name);
		//is my page
		if(is_mine)
		{
			showTweetWindow(username, response);
			showMyNews(username, response);
		}
		else
		{
			showTweets(target_name, response);
		}
	}

	private void showTweets(String username, HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		db = ServletCommon.initDB(db);
		UserEntity user = db.getUserEntity(username);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.NO_THIS_USER_INFO,  response);
			return;
		}

		ArrayList<Long>tweets_id = user.getAllTweets(); 
		ArrayList<TweetEntity>tweets = db.getTweetEntityByIds(tweets_id); 
		if(tweets != null && tweets.size() > 0)
		{
			Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int i = tweets.size() - 1; i >= 0; --i)
			{
				TweetEntity t = tweets.get(i);
				String time_str = formatter.format(t.getReleaseTime());
				out.println("<P>" + time_str  + "</P>");
				out.println("<P>" + t.getBody() + "</P>");
			}
		}
	}

	private void showMyNews(String username, HttpServletResponse response) throws IOException 
	{
		showTweets(username, response);
	}

	private void showTweetWindow(String username, HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<form action=\"/tweet\" method=\"post\">");
			
		out.println("<P>" + "Say something..." + "</P>");
		out.println("<input type=\"text\" name=\"TWEET\" value=\"\" />");

		out.println("<input type=\"submit\" />");
		out.println("</form>");
	}
}

