package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.myapp.SQL.SQLDBMovieQuery;
import com.myapp.storage.DBWrapper;
import com.myapp.utils.Const;
import com.myapp.view.GoogleListView;
import com.myapp.view.GoogleObjectView;
import com.myapp.view.GroupListView;
import com.myapp.view.GroupObjectView;
import com.myapp.utils.Const;

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
