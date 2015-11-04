package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
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


public class Login extends HttpServlet 
{
	private DBWrapper db; 

	public Login() throws IOException
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		db = ServletCommon.initDB(db);

		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("USERNAME");
		if(name == null)
		{
			ServletCommon.PrintErrorPage("Please enter a username.",  response);
			return;
		}

		/* Check db if the user exists*/
		if(!hasUser(name))
		{
			ServletCommon.PrintErrorPage("Sorry, this username has not been registered!",  response);
			ServletCommon.ShowLink("/register", "register", response); 
			ServletCommon.ShowLink("/login", "Login", response); 
			return;
		}
		
		String password = request.getParameter("PASSWORD");
		if(password == null)
		{
			ServletCommon.PrintErrorPage("Please enter a password.",  response);
			return;
		}

		String real_password = MD5Encryptor.crypt(password);
		if(!checkPassword(name, real_password)) 
		{
			ServletCommon.PrintErrorPage("Your password is wrong!",  response);
			return;
		}

		/*
		// write to DB
		if(!userLogin(name))
		{
			ServletUtils.PrintErrorPage("Sorry, database error, login failed.",  response);
			return;
		}
		*/
		
		ServletCommon.addLoginCookie(request, response, name);
	
		/*
		 * result page:
		 */
		response.setContentType("text/html");
		out.println("<HTML><HEAD><TITLE>Login</TITLE></HEAD><BODY>");
		String res = "Login successed!"; 
		out.println("<P>" + res + "</P>");
		out.println("<P>" + "\n" + "</P>");
		
		ServletCommon.gotoHome(response);
		out.println("</BODY></HTML>");		
		
		db.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		db = ServletCommon.initDB(db);

		//print a form for user to input the arguments.
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<form action=\"/login\" method=\"post\">");
		
		out.println("<P>" + "Login:\n" + "</P>");
		out.println("<P>" + "Username:" + "</P>");
		out.println("<input type=\"text\" name=\"USERNAME\" value=\"\" />");
		
		out.println("<P>" + "Password:" + "</P>");
		out.println("<input type=\"text\" name=\"PASSWORD\" value=\"\" />");

		out.println("<input type=\"submit\" />");
		out.println("</form>");
	
		db.close();
	}

	private boolean hasUser(String name) throws IOException 
	{
		return db.hasUser(name);
	}

	private boolean checkPassword(String name, String password) throws IOException 
	{
		return db.checkLoginPassword(name, password);
	}

	/*
	private boolean userLogin(String name) throws IOException 
	{
		boolean res = db.userLogin(name);
		return res;
	}
	*/
}

