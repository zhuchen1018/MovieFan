package com.myapp.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myapp.storage.DBWrapper;

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

			gotoHome(response);

			out.println("</BODY></HTML>");		
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static void addSession(HttpServletRequest request, HttpServletResponse response, String username) 
	{
		HttpSession session = request.getSession();
		session.setAttribute("username", username); 
		session.setMaxInactiveInterval(ServletConst.LOGIN_SESSION_AGE);

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

	public static void gotoHome(HttpServletResponse res) throws IOException
	{
		ShowLink(ServletConst.HOME_URL, "Go to Home", res);
	}

	public static void ShowLink(String link, String name,  HttpServletResponse res) throws IOException 
	{
		PrintWriter out = res.getWriter();
		out.println("<P>" + "\n" + "</P>");
		out.println("<a href= \"" + link +  "\" class=\"button\">" + name + "</a>");
		out.println("<P>" + "\n" + "</P>");
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

	public static void sendRedirect(HttpServletResponse response, String location) 
	{
		try 
		{
			response.sendRedirect(location);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
	}
	
	public static void redirect404(HttpServletResponse response)
	{
		sendRedirect(response, "/htmls/404.html");
	}
}
