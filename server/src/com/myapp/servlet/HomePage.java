package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.myapp.utils.Const;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.accessor.UserAccessor;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.MD5Encryptor;
import com.myapp.utils.ServletCommon;


/**
 * 
 * @author Haoyun 
 *
 */
public class HomePage  extends HttpServlet 
{
	private DBWrapper db; 

	public HomePage () throws IOException
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
			
		out.println("<P>" + "\n" + "</P>");
		out.println("<a href=\"/register\" class=\"button\">Register</a>");
			

		if(!ServletCommon.isSessionValid(request))
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

			out.println("<a href=\"/test_news \" class=\"button\">Test News Here</a>");
			out.println("<a href=\"/test_friends \" class=\"button\">Test Friends Here</a>");
		}

		out.println("<a href=\"/search_movie\" class=\"button\">SearchMovie</a>");
		/*
				
		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/home.jsp");
		try 
		{
			rd.forward(request, response);
		} 
		catch (ServletException e) 
		{
			e.printStackTrace();
		}
		*/
	}
}


