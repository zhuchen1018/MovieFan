package com.myapp.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.SQL.SQLDBMovieQuery;
import com.myapp.utils.Const;
import com.myapp.utils.ServletCommon;
import com.myapp.utils.ServletConst;

public class Search extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2835467381465359391L;

	public void init()
	{
		/*Load first time, avoid latency*/
		//SQLDBWrapper.getConnection();
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
		String lengthJudge = request.getParameter("MovieLength");
		int length;
		if(lengthJudge==null){
			length = -1;
		}
		else
			length = Integer.valueOf(lengthJudge);

		SQLDBMovieQuery sql=null;
		try
		{
			sql = new SQLDBMovieQuery(order, genre,length);
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

				request.setAttribute("MoviePageView", null); 
				request.setAttribute("MoviePageView", sql.getMovieHomepage());

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

