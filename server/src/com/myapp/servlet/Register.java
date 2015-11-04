package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.myapp.storage.DBConst;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.MD5Encryptor;
import com.myapp.storage.accessor.UserAccessor;
import com.myapp.storage.entity.UserEntity;

public class Register extends HttpServlet 
{
	private DBWrapper db; 
	public Register() throws IOException
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		db = ServletCommon.initDB(db);
		/*
		 *name 
		 */
		String name = request.getParameter("USERNAME");
		if(name == null)
		{
			ServletCommon.PrintErrorPage("Please enter a username.",  response);
			return;
		}
		name = name.trim();
		if(! checkName(name, request, response))
		{
			return;
		}
		/*
		 *password 
		 */
		String password = request.getParameter("PASSWORD");
		if(password == null)
		{
			ServletCommon.PrintErrorPage("Please enter a password.",  response);
			return;
		}
		password = password.trim();
		if(! checkPassword(password, request, response))
		{
			return;
		}

		/* Check db if the name is unique*/
		if(hasUser(name))
		{
			ServletCommon.PrintErrorPage("Sorry, this username has already been registered!",  response);
			return;
		}

		/*: DB add new user*/
		addUser(name, MD5Encryptor.crypt(password));
	
		/*auto login*/
		ServletCommon.addLoginCookie(request, response, name);

		/*
		 * result page:
		 */
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>Register </TITLE></HEAD><BODY>");
		String res = "Register successed!";
		out.println("<P>" + res + "</P>");
		out.println("<P>" + "\n" + "</P>");
		
		ServletCommon.gotoHome(response);
		out.println("</BODY></HTML>");		
		
		db.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		//print a form for user to input the arguments.
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<form action=\"/register\" method=\"post\">");
		
		out.println("<P>" + "Create an account:\n" + "</P>");
		out.println("<P>" + "Username:" + "</P>");
		out.println("<input type=\"text\" name=\"USERNAME\" value=\"\" />");
		
		out.println("<P>" + "Password:" + "</P>");
		out.println("<input type=\"text\" name=\"PASSWORD\" value=\"\" />");

		out.println("<input type=\"submit\" />");
		out.println("</form>");
	}

	private void addUser(String name, String password) throws IOException 
	{
		db.addUser(name, password);
	}

	private boolean hasUser(String name) throws IOException 
	{
		return db.hasUser(name);
	}

	private boolean checkName(String name, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		if(name.isEmpty())
		{
			ServletCommon.PrintErrorPage("Please enter a username.",  response);
			return false;
		}
		if(name.length() < 6)
		{
			ServletCommon.PrintErrorPage("Username length should be >= 6.",  response);
			return false;
		}
		if(!name.matches("[A-Za-z_][\\w]*"))
		{
			ServletCommon.PrintErrorPage("Username should begin with letters and only contain numbers and letters.",  response);
			return false;
		}
		return true;
	}

	private boolean checkPassword(String password, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		if(password.isEmpty())
		{
			ServletCommon.PrintErrorPage("Please enter a password.",  response);
			return false;
		}
		if(password.length() < 6)
		{
			ServletCommon.PrintErrorPage("Password length should be >= 6.",  response);
			return false;
		}
		if(password.matches(".*\\s+.*"))
		{
			ServletCommon.PrintErrorPage("Password should not contain spaces.",  response);
			return false;
		}
		return true;
	}
}
