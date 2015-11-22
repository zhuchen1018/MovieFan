package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Hashtable;
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
import com.myapp.utils.Const;
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
		System.out.println("doPost: " + url);
		if(url.equals(ServletConst.SEARCH_MOVIE_RES))
		{
			handleSearchMoviePost(request, response);
		}
		else if(url.equals(ServletConst.SEARCH_MOVIE_RES_ADVANCED))
		{
			handleSearchMovieAdvancedPost(request, response);
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

	private void handleSearchMovieAdvancedPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String genre = request.getParameter("formDoor[]");
		String order = request.getParameter("OrderBy");

		SQLDBMovieQuery sql=null;
		try
		{
			sql = new SQLDBMovieQuery(order, genre);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			ServletCommon.redirect404(response);
			return;
		}
		request.setAttribute("MovieListView", null); 
		request.setAttribute("MovieListView", sql.getMovieObject());

		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/MovieList.jsp");
		try 
		{
			rd.forward(request, response);
		} 
		catch (IOException | ServletException e) 
		{
			e.printStackTrace();
			ServletCommon.redirect404(response);
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
			ServletCommon.redirect404(response);
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
			ServletCommon.redirect404(response);
		}
	}

	/**
	 * User normal movie search
	 * @param request
	 * @param response
	 */
	private void handleSearchMoviePost(HttpServletRequest request, HttpServletResponse response) 
	{
		String key = request.getParameter("search_movie");
		if(key == null || key.isEmpty())
		{
			ServletCommon.redirect404(response);
			return;
		}
		SQLDBMovieQuery sql=null;
		try
		{
			sql = new SQLDBMovieQuery(key,Const.NAME_SEARCH);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			ServletCommon.redirect404(response);
			return;
		}

		request.setAttribute("MovieListView", null); 
		request.setAttribute("MovieListView", sql.getMovieObject());

		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/MovieList.jsp");
		try 
		{
			rd.forward(request, response);
		} 
		catch (IOException | ServletException e) 
		{
			e.printStackTrace();
			ServletCommon.redirect404(response);
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
		else
		{
			ServletCommon.redirect404(response);
		}
	}

	private void handleSearchGroupGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/htmls/SearchGroupPage.html";
		ServletCommon.sendRedirect(response, location);
	}

	private void handleSearchUserGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/htmls/SearchUserPage.html";
		ServletCommon.sendRedirect(response, location);
	}

	private void handleSearchMovieGet(HttpServletRequest request, HttpServletResponse response) 
	{
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		//search movie by movie_id: from click movie ref 
		if(query != null && !query.isEmpty())
		{
			String movie_id = query.get("movie_id");
			System.out.println("jason query.get " + movie_id);

			if(movie_id != null && !movie_id.isEmpty())
			{
				SQLDBMovieQuery sql=null;
				try
				{
					sql = new SQLDBMovieQuery(movie_id,Const.ID_SEARCH);

				}
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
					ex.printStackTrace();
					ServletCommon.redirect404(response);
					return;
				}

				request.setAttribute("MovieListView", null); 
				request.setAttribute("MovieListView", sql.getMovieObject());

				RequestDispatcher rd= request.getRequestDispatcher ("/jsp/MoviePage.jsp");
				try 
				{
					rd.forward(request, response);
				} 
				catch (IOException | ServletException e) 
				{
					e.printStackTrace();
					ServletCommon.redirect404(response);
				}
			}
			else
			{
				ServletCommon.redirect404(response);
				return;
			}
		}
		else
		{
			String location = "/htmls/SearchMoviePage.html";
			ServletCommon.sendRedirect(response, location);
		}
	}
}

