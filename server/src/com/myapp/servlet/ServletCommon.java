package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myapp.storage.DBWrapper;

public class ServletCommon 
{
	private final static int COOKIE_AGE = 24  * 3600 * 7; 
	private final static String HOME_URL = "/home"; 

	public static void PrintErrorPage(String info, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>ERROR</TITLE></HEAD><BODY>");
		out.println("<P>" + "ERROR:"+ "</P>");
		out.println("<P>" + info + "</P>");

		gotoHome(response);

		out.println("</BODY></HTML>");		
	}

	public static boolean isSessionValid(HttpServletRequest request )
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
	
	public static void addLoginCookie(HttpServletRequest request, HttpServletResponse response, String username) throws IOException
	{
		HttpSession session = request.getSession();
		session.setAttribute("username", username); 
		session.setMaxInactiveInterval(COOKIE_AGE);
	
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

	public static void delLoginCookie(HttpServletRequest request, HttpServletResponse response)
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

	public static void gotoHome(HttpServletResponse res) throws IOException
	{
		ShowLink(HOME_URL, "Go to Home", res);
	}

	public static void ShowLink(String link, String name,  HttpServletResponse res) throws IOException 
	{
		PrintWriter out = res.getWriter();
		out.println("<P>" + "\n" + "</P>");
		out.println("<a href= \"" + link +  "\" class=\"button\">" + name + "</a>");
		out.println("<P>" + "\n" + "</P>");
	}

	public static DBWrapper initDB(DBWrapper db) throws IOException 
	{
		if(db == null || db.isClose())
		{
			db = new DBWrapper();
		}
		return db;
	}
}
