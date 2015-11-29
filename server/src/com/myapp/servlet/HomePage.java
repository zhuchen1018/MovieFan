package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.myapp.storage.DBWrapper;
import com.myapp.utils.Const;
import com.myapp.utils.ServletCommon;


/**
 * 
 * @author Haoyun 
 *
 */
public class HomePage  extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8696988485281625971L;
	private DBWrapper db; 

	public HomePage () 
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String url = request.getServletPath();
		if(url.equals(Const.HOME_URL))
		{
			handleHomeGet(request, response);
		}
		else if(url.equals(Const.HOME_TEST_URL))
		{
			handleHomeTestGet(request, response);
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void handleHomeGet(HttpServletRequest request, HttpServletResponse response) 
	{
		boolean isLogin = ServletCommon.hasLoginSession(request);
		if(!isLogin)
		{
			String location = "/jsp/Login.jsp";
			ServletCommon.sendRedirect(response, location);
		}
		else
		{
			initDB();

			String username = ServletCommon.getSessionUsername(request);
			db.sendAllNews(username, request, response);
			db.sendFriendList(username, request, response);
			db.sendGroupList(username, request, response);
			
			print("news: " + request.getAttribute("NewsListView"));
			print("friends: " + request.getAttribute("FriendListView"));
			print("groups: " + request.getAttribute("GroupListView"));

			String location = "/jsp/home.jsp";
			ServletCommon.sendRedirect(response, location);
		}
	}

	private void handleHomeTestGet(HttpServletRequest request, HttpServletResponse response) 
	{
		response.setContentType("text/html");
		PrintWriter out;
		try 
		{
			out = response.getWriter();
			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/register\" class=\"button\">Register</a>");

			if(!ServletCommon.hasLoginSession(request))
			{
				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/login\" class=\"button\">Login</a>");
			}
			else
			{
				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/logoff\" class=\"button\">Logoff</a>");

				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/user_page" + "?" + "user=" + ServletCommon.getSessionUsername(request) 
				+ "\" class=\"button\">My Page</a>");

				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/test_news \" class=\"button\">Test News Here</a>");

				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/test_friends \" class=\"button\">Test Friends Here</a>");

				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/create_group \" class=\"button\">Test Creater Group </a>");
			}

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/search_movie\" class=\"button\">Search Movie</a>");

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/search_group\" class=\"button\">Search Group</a>");

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/hometest\" class=\"button\">Facebook login</a>");	

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/search_google\" class=\"button\">Google it</a>"); 

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/voice_search\" class=\"button\">Voice Search</a>");

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		/*
		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/homeTest.jsp");
		try 
		{
			rd.forward(request, response);
		} 
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
		*/
	}
	private static void print(String a)
	{
		System.out.println(a);
	}
}


