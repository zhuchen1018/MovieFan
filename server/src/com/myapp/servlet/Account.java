package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.MD5Encryptor;
import com.myapp.view.NewsListView;
import com.myapp.view.UserSettingView;
import com.myapp.utils.Const;


public class Account extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3178943864946251513L;

	public Account() throws IOException
	{
	}

	public DBWrapper initDB()
	{
		return  new DBWrapper();
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
		else if(url.equals(Const.USER_SETTINGS_URL))
		{
			handleUserSettingsPost(request, response);
		}
	}

	private void handleUserSettingsPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			System.out.println("handleFollowUser username is null"); 
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  request, response);
			return;
		}

		DBWrapper db = initDB();

		UserEntity user = db.getUserEntity(username);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO,  request, response);
			return;
		}

		String head_url = request.getParameter("HEAD_URL");
		String profile_url = request.getParameter("PROFILE_URL");
		String description = request.getParameter("DESC");
		String[] genres = request.getParameterValues("GENRES");
		Integer[] genres_integer = null; 
		if(genres != null)
		{
			genres_integer = new Integer[genres.length];
			for(int i = 0; i < genres_integer.length; ++i)
			{
				genres_integer[i] = Const.GENRE_MAP.get(genres[i]);
			}
		}
		db.upUserSettings(username, head_url, profile_url, description, genres_integer);

		ServletCommon.RedirectToUserPage(request, response, username, username);
		
		db.sync();
	} 

	private void handleRegisterPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String name = request.getParameter("USERNAME");
		if(name == null)
		{
			ServletCommon.PrintErrorPage("Please enter a username.",  request, response);
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
			ServletCommon.PrintErrorPage("Please enter a password.",  request, response);
			return;
		}
		password = password.trim();
		if(!checkPasswordLegal(password, request, response))
		{
			return;
		}

		DBWrapper db = initDB();

		if(db.hasUser(name))
		{
			ServletCommon.PrintErrorPage("Sorry, this username has already been registered!",  request, response);
			return;
		}

		/*: DB add new user*/
		db.createUser(name, MD5Encryptor.crypt(password));
		db.sync();

		/*auto login*/
		ServletCommon.addLoginSession(request, response, name);

		UserEntity user = db.getUserEntity(name);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO,  request, response);
			return;
		}

		String head_url = user.getHeadUrl();
		String profile_url = user.getProfileUrl();
		Integer[] genres = user.getLikeGenres();
		String description = user.getDescription();

		UserSettingView usv =  new UserSettingView(name, head_url,profile_url, genres, description);
		request.setAttribute("UserSettingView", usv);

		String location = "/jsp/UserSettings.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	private void handleUserSettingsGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			System.out.println("handleFollowUser username is null"); 
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  request, response);
			return;
		}

		DBWrapper db = initDB();

		UserEntity user = db.getUserEntity(username);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(Const.NO_THIS_USER_INFO,  request, response);
			return;
		}

		String head_url = user.getHeadUrl();
		String profile_url = user.getProfileUrl();
		Integer[] genres = user.getLikeGenres();
		String description = user.getDescription();

		UserSettingView usv =  new UserSettingView(username, head_url,profile_url, genres, description);
		request.setAttribute("UserSettingView", usv);

		String location = "/jsp/UserSettings.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}
	
	private void handleLoginPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String name = request.getParameter("USERNAME");
		if(name == null)
		{
			ServletCommon.PrintErrorPage("Please enter a username.",  request, response);
			return;
		}

		DBWrapper db = initDB();
		/* Check db if the user exists*/
		if(!db.hasUser(name))
		{
			ServletCommon.PrintErrorPage("Sorry, this username has not been registered!",  request, response);
			return;
		}

		String password = request.getParameter("PASSWORD");
		if(password == null)
		{
			ServletCommon.PrintErrorPage("Please enter a password.",  request, response);
			return;
		}

		String real_password = MD5Encryptor.crypt(password);
		if(!db.checkLoginPassword(name, real_password))
		{
			ServletCommon.PrintErrorPage("Your password is wrong!",  request, response);
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
		else if(url.equals(Const.USER_SETTINGS_URL))
		{
			handleUserSettingsGet(request, response);
		}
		
		else if(url.equals(Const.USER_MAILBOX_URL))
		{
			handleUserMailBoxGet(request, response);
		}
	}
	
	private void handleUserMailBoxGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.redirectToLoginPage(request, response);
			return;
		}

		DBWrapper db = initDB();
	
		NewsListView nlv = db.loadMyMailBoxNews(username);
		request.setAttribute("NewsListView", null); 
		request.setAttribute("NewsListView", nlv); 
		
		db.sync();
	
		String location = "/jsp/NewsList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
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
		ServletCommon.redirectToLoginPage(request, response);
	}

	public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ServletCommon.redirectToLoginPage(request, response);
	}

	private boolean checkNameLegal(String name, HttpServletRequest request, HttpServletResponse response)
	{
		if(name.isEmpty())
		{
			ServletCommon.PrintErrorPage("Please enter a username.",  request, response);
			return false;
		}
		if(name.length() < 6)
		{
			ServletCommon.PrintErrorPage("Username length should be >= 6.",  request, response);
			return false;
		}
		if(!name.matches("[A-Za-z_][\\w]*"))
		{
			ServletCommon.PrintErrorPage("Username should begin with letters and only contain numbers and letters.",  request, response);
			return false;
		}
		return true;
	}

	private boolean checkPasswordLegal(String password, HttpServletRequest request, HttpServletResponse response) 
	{
		if(password.isEmpty())
		{
			ServletCommon.PrintErrorPage("Please enter a password.",  request, response);
			return false;
		}
		if(password.length() < 6)
		{
			ServletCommon.PrintErrorPage("Password length should be >= 6.",  request, response);
			return false;
		}
		if(password.matches(".*\\s+.*"))
		{
			ServletCommon.PrintErrorPage("Password should not contain spaces.",  request, response);
			return false;
		}
		return true;
	}
}

