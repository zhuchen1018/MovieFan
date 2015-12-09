package com.myapp.servlet;

import java.io.IOException;
import java.util.Hashtable;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.myapp.storage.DBWrapper;
import com.myapp.utils.Const;
import com.myapp.view.MoviePageView;
import com.myapp.view.MoviePageViewCache;

public class MoviePage extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2835467381465359391L;

	public DBWrapper initDB()
	{
		return new DBWrapper();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  null, response);
			return;
		}

		String url = request.getServletPath();
		if(url.equals(Const.MOVIE_PAGE_URL))
		{
			handleMoviePageGet(request, response, username);
		}
		else
		{
			ServletCommon.redirect404(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  null, response);
			return;
		}

		String url = request.getServletPath();
		if(url.equals(Const.MOVIE_LIKE_URL))
		{
			handleLikeMoviePost(request, response, username);
		}
		else if(url.equals(Const.MOVIE_UNLIKE_URL))
		{
			handleUnlikeMoviePost(request, response, username);
		}
		else if(url.equals(Const.MOVIE_SHARE_URL))
		{
			handleShareMoviePost(request, response, username);
		}
		else if(url.equals(Const.MOVIE_REVIEW_URL))
		{
			handleReviewMoviePost(request, response, username);
		}
		else 
		{
			ServletCommon.redirect404(request, response);
		}
	}

	/**
	 * UNLIKE MOVIE
	 * @param request
	 * @param response
	 * @param username
	 */
	private void handleUnlikeMoviePost(HttpServletRequest request, HttpServletResponse response, String username) 
	{
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String movie_id = null;
		if(query != null) 
		{
			movie_id = query.get("movie_id");
		}
		if(movie_id == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}

		//DBWrapper DBWrapper = initDB();
		DBWrapper.userUnlikeMovie(username, movie_id); 
		//DBWrapper.close();

		ServletCommon.RedirectToMoviePage(request, response, username, movie_id);
	}

	/**
	 * REVIEW
	 * @param request
	 * @param response
	 * @param username
	 */
	private void handleReviewMoviePost(HttpServletRequest request, HttpServletResponse response, String username) 
	{
		String title = "";
		String body = request.getParameter("Review"); 
		if(title == null || body == null)
		{
			ServletCommon.PrintErrorPage("Title and review should not be empty.",  null, response);
			return;
		}	

		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String movie_id = null;
		if(query != null) 
		{
			movie_id = query.get("movie_id");
		}
		if(movie_id == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}

		MoviePageView mpv = MoviePageViewCache.get(movie_id);	
		if(mpv == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}
		//DBWrapper DBWrapper = initDB();
		DBWrapper.addNewsMovieReview(username, title, body, mpv.getMovieId(), mpv.getName(), mpv.getPoster()); 
		//DBWrapper.close();
		
		ServletCommon.RedirectToMoviePage(request, response, username, movie_id);
	}

	/**
	 * SHARE MOVIE
	 * @param request
	 * @param response
	 * @param username
	 */
	private void handleShareMoviePost(HttpServletRequest request, HttpServletResponse response, String username) 
	{
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String movie_id = null;
		if(query != null) 
		{
			movie_id = query.get("movie_id");
		}
		if(movie_id == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}
		MoviePageView mpv = MoviePageViewCache.get(movie_id);	
		if(mpv == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}
		//DBWrapper DBWrapper = initDB();
		DBWrapper.addNewsShareMovies(username, movie_id, mpv.getName(), mpv.getPoster(), DBWrapper.getFriends(username)); 
		//DBWrapper.close();
		
		ServletCommon.RedirectToMoviePage(request, response, username, movie_id);
	}

	/**
	 * LIKE MOVIE
	 * @param request
	 * @param response
	 * @param username
	 */
	private void handleLikeMoviePost(HttpServletRequest request, HttpServletResponse response, String username) 
	{
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		String movie_id = null;
		if(query != null && !query.isEmpty())
		{
			movie_id = query.get("movie_id");
		}
		if(movie_id == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}

		//DBWrapper DBWrapper = initDB();
		DBWrapper.userLikeMovie(username, movie_id); 
		//DBWrapper.close();
			
		ServletCommon.RedirectToMoviePage(request, response, username, movie_id);
	}

	/**
	 * GET MOVIEPAGE
	 * @param request
	 * @param response
	 * @param username
	 */
	private void handleMoviePageGet(HttpServletRequest request, HttpServletResponse response, String username) 
	{
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		if(query != null && !query.isEmpty())
		{
			String movie_id = query.get("movie_id");
			ServletCommon.RedirectToMoviePage(request, response, username, movie_id);
		}
		else
		{
			ServletCommon.redirect404(request, response);
		}
	}
}

