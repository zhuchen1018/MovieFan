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

import com.myapp.utils.Const;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.accessor.UserAccessor;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.MD5Encryptor;
import com.myapp.utils.ServletCommon;
import com.myapp.utils.ServletConst;


public class Account extends HttpServlet 
{
	private DBWrapper db; 

	public Account() throws IOException
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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String url = request.getServletPath();
		if(url.equals(ServletConst.REGISTER_URL))
		{
			handleRegisterPost(request, response);
		}
		else if(url.equals(ServletConst.LOGIN_URL))
		{
			handleLoginPost(request, response);
		}
		else if(url.equals(ServletConst.ACCOUNT_SETTING_URL))
		{
			handleSettingPost(request, response);
		}
	}

	private void handleRegisterPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String name = request.getParameter("USERNAME");
		if(name == null)
		{
			ServletCommon.PrintErrorPage("Please enter a username.",  response);
			return;
		}
		name = name.trim();
		if(! checkNameLegal(name, request, response))
		{
			return;
		}
		String password = request.getParameter("PASSWORD");
		if(password == null)
		{
			ServletCommon.PrintErrorPage("Please enter a password.",  response);
			return;
		}
		password = password.trim();
		if(!checkPasswordLegal(password, request, response))
		{
			return;
		}

		initDB();

		if(db.hasUser(name))
		{
			ServletCommon.PrintErrorPage("Sorry, this username has already been registered!",  response);
			return;
		}

		/*: DB add new user*/
		db.addUser(name, MD5Encryptor.crypt(password));

		/*auto login*/
		ServletCommon.addSession(request, response, name);

		/*
		 * result page:
		 */
		response.setContentType("text/html");
		PrintWriter out;
		try 
		{
			out = response.getWriter();
			out.println("<HTML><HEAD><TITLE>Register </TITLE></HEAD><BODY>");
			String res = "Register successed!";
			out.println("<P>" + res + "</P>");
			out.println("<P>" + "\n" + "</P>");

			ServletCommon.gotoHome(response);
			out.println("</BODY></HTML>");		
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		db.sync();
	}

	private void handleSettingPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}

	private void handleLoginPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		PrintWriter out = response.getWriter();

		String name = request.getParameter("USERNAME");
		if(name == null)
		{
			ServletCommon.PrintErrorPage("Please enter a username.",  response);
			return;
		}

		initDB();
		/* Check db if the user exists*/
		if(!db.hasUser(name))
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
		if(!db.checkLoginPassword(name, real_password))
		{
			ServletCommon.PrintErrorPage("Your password is wrong!",  response);
			return;
		}

		ServletCommon.addSession(request, response, name);

		response.setContentType("text/html");
		out.println("<HTML><HEAD><TITLE>Login</TITLE></HEAD><BODY>");
		String res = "Login successed!"; 
		out.println("<P>" + res + "</P>");
		out.println("<P>" + "\n" + "</P>");

		ServletCommon.gotoHome(response);
		out.println("</BODY></HTML>");		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String url = request.getServletPath();
		if(url.equals(ServletConst.LOGIN_URL))
		{
			handleLogin(request, response);
		}
		else if(url.equals(ServletConst.LOGOFF_URL))
		{
			handleLogoff(request, response);
		}
		else if(url.equals(ServletConst.REGISTER_URL))
		{
			handleRegister(request, response);
		}
		else if(url.equals(ServletConst.ACCOUNT_SETTING_URL))
		{
			handleSetting(request, response);
		}
	}

	private void handleSetting(HttpServletRequest request, HttpServletResponse response) 
	{
		// TODO Auto-generated method stub

	}

	private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String location = "/htmls/register.html";
		ServletCommon.sendRedirect(response, location);
	}

	private void handleLogoff(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		if(!ServletCommon.isSessionValid(request))
		{
			ServletCommon.PrintErrorPage("You are not logined now", response);
			return;
		}

		ServletCommon.delSession(request, response);

		out.println("<HTML><HEAD><TITLE>Log off</TITLE></HEAD><BODY>");
		String res = "Logoff successed!"; 
		out.println("<P>" + res + "</P>");
		out.println("<P>" + "\n" + "</P>");

		ServletCommon.gotoHome(response);
		out.println("</BODY></HTML>");		
	}

	public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String location = "/htmls/login.html";
		ServletCommon.sendRedirect(response, location);
	}

	private boolean checkNameLegal(String name, HttpServletRequest request, HttpServletResponse response)
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

	private boolean checkPasswordLegal(String password, HttpServletRequest request, HttpServletResponse response) 
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

