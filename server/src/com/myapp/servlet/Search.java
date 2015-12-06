package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import com.myapp.storage.entity.GroupEntity;
import com.myapp.utils.Const;
import com.myapp.view.GoogleListView;
import com.myapp.view.GoogleObjectView;
import com.myapp.view.GroupListView;
import com.myapp.view.GroupObjectView;
import com.myapp.view.NewsListView;
import com.myapp.view.UserListView;
import com.myapp.utils.Const;

public class Search extends HttpServlet 
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

	public void init()
	{
		/*Load first time, avoid latency*/
		//SQLDBWrapper.getConnection();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String url = request.getServletPath();
		if(url.equals(Const.SEARCH_MOVIE_RES))
		{
			handleSearchMoviePost(request, response);
		}
		else if(url.equals(Const.SEARCH_MOVIE_RES_ADVANCED))
		{
			handleSearchMovieAdvancedPost(request, response);
		}
		else if(url.equals(Const.SEARCH_USER))
		{
			handleSearchUserPost(request, response);
		}
		else if(url.equals(Const.SEARCH_GROUP_RES))
		{
			handleSearchGroupPost(request, response);
		}
		else if(url.equals(Const.SEARCH_HASHTAG_RES))
		{
			handleSearchHashTagPost(request, response);
		}
		/*
		else if(url.equals(Const.SEARCH_GOOGLE_RES))
		{
			handleSearchGooglePost(request, response);
		}
		*/
	}

	/**
	 * search the news containing some HashTag
	 * @param request
	 * @param response
	 */
	private void handleSearchHashTagPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String search = request.getParameter("search_hashtag"); 
		if(search == null || search.isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.PLEASE_ENTER_SOMETHING, response);
			return;
		}
		
		initDB();
		
		NewsListView nlv = db.loadSearchHashTag(search);
		request.setAttribute("NewsListView", null); 
		request.setAttribute("NewsListView", nlv); 
		ServletCommon.retrieveGoogleResult(search, request, response);

		String location = "/jsp/NewsList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	private void handleSearchGooglePost(HttpServletRequest request, HttpServletResponse response) 
	{
		String search = request.getParameter("search_google"); 
		if(search == null || search.isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.PLEASE_ENTER_SOMETHING, response);
			return;
		}
		ServletCommon.retrieveGoogleResult(search, request, response);
		String location = "/jsp/GoogleList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
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
			ServletCommon.redirect404(request, response);
			return;
		}
		request.setAttribute("MovieListView", null); 
		request.setAttribute("MovieListView", sql.getMovieObject());

		
		String location = "/jsp/MovieList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);	
	}

	private void handleSearchGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String search = request.getParameter("search_group"); 
		if(search == null || search.isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.PLEASE_ENTER_SOMETHING, response);
			return;
		}
		
		GroupListView glv = new GroupListView();
		ArrayList<GroupEntity>glist = db.loadSearchGroupList(search);
		for(GroupEntity gobj: glist)
		{
			GroupObjectView gov = new GroupObjectView(gobj.getId(), gobj.getHeadUrl(), gobj.getName());
			glv.addGroupObject(gov);
		}

		request.setAttribute("GroupListView", null); 
		request.setAttribute("GroupListView", glv); 
		

		String location = "/jsp/GroupList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	private void handleSearchUserPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String tarname = request.getParameter("USER");
		if(tarname == null || tarname.isEmpty())
		{
			ServletCommon.PrintErrorPage("Please input search user name",  response);
			return;
		}

		initDB();

		UserListView ulv = db.loadSearchUserByName(tarname); 
		request.setAttribute("UserListView", null); 
		request.setAttribute("UserListView", ulv); 

		String location = "/jsp/UserList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
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
			ServletCommon.PrintErrorPage(Const.PLEASE_ENTER_SOMETHING, response);
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
			ServletCommon.redirect404(request, response);
			return;
		}

		request.setAttribute("MovieListView", null); 
		request.setAttribute("MovieListView", sql.getMovieObject());
		
		ServletCommon.retrieveGoogleResult(key, request, response);

		String location = "/jsp/MovieList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
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
		if(url.equals(Const.SEARCH_USER))
		{
			handleSearchUserGet(request, response);
		}
		else if(url.equals(Const.SEARCH_MOVIE))
		{
			handleSearchMovieGet(request, response);
		}
		else if(url.equals(Const.SEARCH_GROUP))
		{
			handleSearchGroupGet(request, response);
		}
		/*
		else if(url.equals(Const.SEARCH_GOOGLE))
		{
			handleSearchGoogleGet(request, response);
		}
		else if(url.equals(Const.VOICE_SEARCH))
		{
			handleVoiceSearchGet(request, response);
		}
		*/
		else
		{
			ServletCommon.redirect404(request, response);
		}
	}

	private void handleSearchMovieGet(HttpServletRequest request, HttpServletResponse response) 
	{
		//String location = "/htmls/SearchMoviePage.html";
		String location = "/jsp/SearchMoviePage.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	/*
	private void handleVoiceSearchGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/htmls/speech/speech_sample.html";
		ServletCommon.forwardRequestDispatch(request, response, location);	
	}

	private void handleSearchGoogleGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/htmls/SearchGooglePage.html";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}
	*/

	private void handleSearchGroupGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/jsp/SearchGroupPage.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	private void handleSearchUserGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/jsp/SearchUserPage.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}	
}

