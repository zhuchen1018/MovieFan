package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myapp.storage.DBWrapper;
import com.myapp.utils.Const;
import com.myapp.view.FriendListView;
import com.myapp.view.FriendObjectView;
import com.myapp.view.GroupListView;
import com.myapp.view.NewsListView;

public class ServletCommon 
{
	public static void PrintErrorPage(String info, HttpServletResponse response) 
	{
		response.setContentType("text/html");
		PrintWriter out;
		try 
		{
			out = response.getWriter();
			out.println("<HTML><HEAD><TITLE>ERROR</TITLE></HEAD><BODY>");
			out.println("<P>" + "Sorry:"+ "</P>");
			out.println("<P>" + info + "</P>");

			showHomeLink(response);

			out.println("</BODY></HTML>");		
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public static boolean isSessionValid(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if(session == null)
		{
			return false;
		}
		Date curr = new Date();
		if(session.getLastAccessedTime() - curr.getTime() <= session.getMaxInactiveInterval())
		{
			return true;
		}
		return false;
	}

	public static String getSessionUsername(HttpServletRequest request )
	{
		HttpSession session = request.getSession(false);
		if(session == null)
		{
			return null;
		}
		return (String) session.getAttribute("username");
	}

	public static boolean hasLoginSession(HttpServletRequest request)
	{
		String name = getSessionUsername(request);
		if(name == null || name.isEmpty())
		{
			return false;
		}
		return true;
	}

	public static boolean isMyPage(String username, HttpServletRequest request)
	{
		String name = getSessionUsername(request);
		return name != null && name.equals(username);
	}

	public static void addLoginSession(HttpServletRequest request, HttpServletResponse response, String username) 
	{
		HttpSession session = request.getSession();
		session.setAttribute("username", username); 
		session.setMaxInactiveInterval(Const.LOGIN_SESSION_AGE);

		/*
		String host = request.getServerName();
		String path = request.getServletPath();

		Cookie c = new Cookie("JESSIONID", session.getId());
		c.setDomain(host);
		c.setPath(path);
		c.setMaxAge(COOKIE_AGE);
		response.addCookie(c);	

		PrintWriter out = response.getWriter();
		out.println("<P>" + "Set-Cookie:" + "</P>");
		out.println("<P>" + "Host: " + host + "</P>");
		out.println("<P>" + "Path: " + path + "</P>");
		out.println("<P>" + "\n" + "</P>");
		 */
	}

	public static void delSession(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		session.invalidate();

		/*
		Cookie c; 
		Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
        	for(Cookie cookie : cookies)
        	{
        		if(cookie.getName().equalsIgnoreCase("JSESSIONID"))
        		{
        			c = cookie;
        			c.setMaxAge(0);
        			response.addCookie(c);	
        			break;
        		}
        	}
        }
		 */
	}

	public static void showHomeLink(HttpServletResponse res) 
	{
		ShowLink(Const.HOME_URL, "Go to Home", res);
	}

	public static void ShowLink(String link, String name,  HttpServletResponse res) 
	{
		PrintWriter out;
		try {
			out = res.getWriter();

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href= \"" + link +  "\" class=\"button\">" + name + "</a>");
			out.println("<P>" + "\n" + "</P>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	public static DBWrapper initDB(DBWrapper db) throws IOException 
	{
		if(db == null || db.isClose())
		{
			db = new DBWrapper();
		}
		return db;
	}
	 */

	/**
	 * eg:field1=value1&field2=value2&field3=value3...
	 * @param query
	 * @return key-value of query string
	 */
	public static Hashtable<String, String> parseQueryString(String query)
	{
		if(query == null || query.trim().isEmpty())
		{
			return null;
		}
		Hashtable<String, String>kv = new Hashtable<String, String>();
		//field=value
		String[] sp = query.split("&");
		for(int i = 0; i < sp.length; ++i)
		{
			String[] pair = sp[i].split("=");
			String key = pair[0].trim();
			String value = ""; 
			if(pair.length == 2)
			{
				value = pair[1].trim(); 
			}
			kv.put(key, value);
		}
		return kv;
	}

	public static void forwardRequestDispatch(HttpServletRequest request, HttpServletResponse response, String location) 
	{
		RequestDispatcher rd= request.getRequestDispatcher (location);
		try {
			rd.forward(request, response);
		} 
		catch (ServletException e1) 
		{
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}	
	}

	public static void redirect404(HttpServletRequest request,HttpServletResponse response)
	{
		forwardRequestDispatch(request, response, "/htmls/404.html");
	}

	public static void redirect500(HttpServletRequest request, HttpServletResponse response)
	{
		forwardRequestDispatch(request, response, "/htmls/500.html");
	}

	public static void RedirectToHome(HttpServletRequest request, HttpServletResponse response) 
	{
		DBWrapper db = null; 
		try 
		{
			db = new DBWrapper();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}

		String username = getSessionUsername(request);
		if(username == null)
		{
			PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}

		NewsListView nlv = db.loadAllNews(username);
		request.setAttribute("NewsListView", null); 
		request.setAttribute("NewsListView", nlv); 

		FriendListView flv = db.loadFriendList(username); 
		request.setAttribute("FriendListView", null); 
		request.setAttribute("FriendListView", flv); 

		GroupListView glv = db.loadGroupList(username);
		request.setAttribute("GroupListView", null); 
		request.setAttribute("GroupListView", glv); 

		print("news size: " + nlv.getNewsNumber());
		print("friends size: " + flv.getFriendCount());
		print("groups size: " + glv.getGroupCount()); 

		String location = "/jsp/home.jsp";
		forwardRequestDispatch(request, response, location);
	}

	public static void RedirectToUserPage(HttpServletRequest request, HttpServletResponse response, 
			String username, String targetName) 
	{
		DBWrapper db = null; 
		try 
		{
			db = new DBWrapper();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		
		NewsListView nlv = db.loadMyNews(targetName);
		request.setAttribute("NewsListView", null); 
		request.setAttribute("NewsListView", nlv); 

		FriendListView flv = db.loadFriendList(targetName); 
		request.setAttribute("FriendListView", null); 
		request.setAttribute("FriendListView", flv); 

		GroupListView glv = db.loadGroupList(targetName);
		request.setAttribute("GroupListView", null); 
		request.setAttribute("GroupListView", glv); 
	
		boolean isMyPage = username.equals(targetName);
		request.setAttribute("isMyPage", isMyPage);

		boolean isMyFriend = db.isMyFriend(username, targetName); 
		request.setAttribute("isMyFriend", isMyFriend);

		print("news size: " + nlv.getNewsNumber());
		print("friends size: " + flv.getFriendCount());
		print("groups size: " + glv.getGroupCount()); 
	
		String location = "/jsp/UserPage.jsp";
		forwardRequestDispatch(request, response, location);
	}

	private static void print(String a)
	{
		System.out.println(a);
	}

	public static void addLoginCookies(String name, HttpServletResponse res) 
	{
		Cookie cookie = new Cookie("usename", name); 
		cookie.setMaxAge(24 * 60 * 60);  // 24 hours. 
		res.addCookie(cookie);
	}
	
}
