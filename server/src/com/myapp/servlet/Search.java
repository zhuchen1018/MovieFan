package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

import com.myapp.SQL.SQLDBMovieQuery;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.accessor.UserAccessor;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.MD5Encryptor;
import com.myapp.utils.ServletCommon;
import com.myapp.utils.ServletConst;

public class Search extends HttpServlet 
{
	private DBWrapper db; 

	public Search() throws IOException
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String url = request.getServletPath();
		if(url.equals(ServletConst.SEARCH_MOVIE_RES))
		{
			handleSearchMoviePost(request, response);
		}
		else if(url.equals(ServletConst.SEARCH_USER_RES))
		{
			handleSearchUserPost(request, response);
		}
		else if(url.equals(ServletConst.SEARCH_GROUP_RES))
		{
			handleSearchGroupPost(request, response);
		}
	}

	private void handleSearchGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{
		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/GroupList.jsp");
		try 
		{
			rd.forward(request, response);
		} 
		catch (IOException | ServletException e) 
		{
			e.printStackTrace();
		}
	}

	private void handleSearchUserPost(HttpServletRequest request, HttpServletResponse response) 
	{
		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/UserList.jsp");
		try 
		{
			rd.forward(request, response);
		} 
		catch (IOException | ServletException e) 
		{
			e.printStackTrace();
		}
	}

	private void handleSearchMoviePost(HttpServletRequest request, HttpServletResponse response) 
	{
		String key = request.getParameter("MOVIE_KEY");
		SQLDBMovieQuery sql = new SQLDBMovieQuery(key);
		request.setAttribute("MovieListView", sql.getMovieObject());

		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/MovieList.jsp");
		try 
		{
			rd.forward(request, response);
		} 
		catch (IOException | ServletException e) 
		{
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String url = request.getServletPath();
		if(url.equals(ServletConst.SEARCH_MOVIE))
		{
			handleSearchMovieGet(request, response);
		}
		else if(url.equals(ServletConst.SEARCH_USER))
		{
			handleSearchUserGet(request, response);
		}
		else if(url.equals(ServletConst.SEARCH_GROUP))
		{
			handleSearchGroupGet(request, response);
		}
	}

	private void handleSearchGroupGet(HttpServletRequest request, HttpServletResponse response) 
	{
		RequestDispatcher rd= request.getRequestDispatcher ("/htmls/search_group.html");
		try 
		{
			rd.forward(request, response);
		} 
		catch (IOException | ServletException e) 
		{
			e.printStackTrace();
		}
	}

	private void handleSearchUserGet(HttpServletRequest request, HttpServletResponse response) 
	{
		RequestDispatcher rd= request.getRequestDispatcher ("/htmls/search_user.html");
		try 
		{
			rd.forward(request, response);
		} 
		catch (IOException | ServletException e) 
		{
			e.printStackTrace();
		}
	}

	private void handleSearchMovieGet(HttpServletRequest request, HttpServletResponse response) 
	{
		RequestDispatcher rd= request.getRequestDispatcher ("/htmls/search_movie.html");
		try 
		{
			rd.forward(request, response);
		} 
		catch (IOException | ServletException e) 
		{
			e.printStackTrace();
		}
	}
}
