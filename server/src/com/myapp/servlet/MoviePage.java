package com.myapp.servlet;

import java.io.IOException;
import java.util.Hashtable;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.myapp.SQL.SQLDBMovieQuery;
import com.myapp.storage.DBWrapper;
import com.myapp.utils.Const;
import com.myapp.view.MoviePageView;

public class MoviePage extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2835467381465359391L;
	private DBWrapper db; 

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

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null)
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}

		String url = request.getServletPath();
		if(url.equals(Const.MOVIE_PAGE_URL))
		{
			handleSearchMovieGet(request, response);
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
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}

		String url = request.getServletPath();
		if(url.equals(Const.MOVIE_LIKE_URL))
		{
			handleLikeMoviePost(request, response);
		}
		else if(url.equals(Const.MOVIE_UNLIKE_URL))
		{
			handleUnlikeMoviePost(request, response);
		}
		else if(url.equals(Const.MOVIE_SHARE_URL))
		{
			handleShareMoviePost(request, response);
		}
		else if(url.equals(Const.MOVIE_REVIEW_URL))
		{
			handleReviewMoviePost(request, response);
		}
		else 
		{
			ServletCommon.redirect404(request, response);
		}
	}

	private void handleUnlikeMoviePost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void handleReviewMoviePost(HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		if(username == null || username.isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}	

		String title = request.getParameter("TITLE"); 
		String body = request.getParameter("BODY"); 
		if(title == null || body == null)
		{
			ServletCommon.PrintErrorPage("Title and review should not be empty.",  response);
			return;
		}	

		MoviePageView mpv = (MoviePageView)request.getAttribute("MoviePageView"); 
		if(mpv == null)
		{
			ServletCommon.PrintErrorPage("MoviePageView object is null.",  response);
			return;
		}	
		
		initDB();
			
		db.addNewsMovieReview(username, title, body, mpv.getMovieId(), mpv.getName(), mpv.getPoster()); 
		db.sync();

		//request.setAttribute("LikedMovie", db.isUserLikeMovie(username, movie_id));

		String location = "/jsp/MoviePage.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	private void handleShareMoviePost(HttpServletRequest request, HttpServletResponse response) 
	{
		
	}

	private void handleLikeMoviePost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void handleSearchMovieGet(HttpServletRequest request, HttpServletResponse response) 
	{
		initDB();

		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		//search movie by movie_id: from click movie ref 
		if(query != null && !query.isEmpty())
		{
			String movie_id = query.get("movie_id");
			if(movie_id != null && !movie_id.isEmpty())
			{
				SQLDBMovieQuery sql=null;
				try
				{
					sql = new SQLDBMovieQuery(movie_id,Const.ID_SEARCH);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					ServletCommon.redirect404(request, response);
					return;
				}

				String username = ServletCommon.getSessionUsername(request);
				request.setAttribute("MoviePageView", null); 
				request.setAttribute("MoviePageView", sql.getMovieHomepage());

				request.setAttribute("LikedMovie", db.isUserLikeMovie(username, movie_id));

				String location = "/jsp/MoviePage.jsp";
				ServletCommon.forwardRequestDispatch(request, response, location);
			}
			else
			{
				ServletCommon.redirect404(request, response);
				return;
			}
		}
		else
		{
			String location = "/htmls/SearchMoviePage.html";
			ServletCommon.forwardRequestDispatch(request, response, location);
		}
	}
}

