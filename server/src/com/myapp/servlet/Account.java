package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.myapp.storage.DBWrapper;
import com.myapp.utils.MD5Encryptor;
import com.myapp.utils.Const;


public class Account extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3178943864946251513L;
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
		if(url.equals(Const.REGISTER_URL))
		{
			handleRegisterPost(request, response);
		}
		else if(url.equals(Const.LOGIN_URL))
		{
			handleLoginPost(request, response);
		}
		else if(url.equals(Const.ACCOUNT_SETTING_URL))
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
		db.createUser(name, MD5Encryptor.crypt(password));
		db.sync();

		/*auto login*/
		ServletCommon.addLoginSession(request, response, name);

		ServletCommon.RedirectToHome(request, response);	
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

		ServletCommon.addLoginSession(request, response, name);

		//ServletCommon.addLoginCookies(name, response);
			
		ServletCommon.RedirectToHome(request, response);	
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String url = request.getServletPath();
		if(url.equals(Const.LOGIN_URL))
		{
			handleLogin(request, response);
		}
		else if(url.equals(Const.LOGOFF_URL))
		{
			handleLogoff(request, response);
		}
		else if(url.equals(Const.REGISTER_URL))
		{
			handleRegister(request, response);
		}
		else if(url.equals(Const.ACCOUNT_SETTING_URL))
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
		String location = "/htmls/RegisterPage.html";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	private void handleLogoff(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		if(ServletCommon.hasLoginSession(request))
		{
			ServletCommon.delSession(request, response);
		}

		ServletCommon.RedirectToHome(request, response);
	}

	public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String location = "/jsp/Login.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
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

