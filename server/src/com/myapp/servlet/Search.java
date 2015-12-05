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
		else if(url.equals(Const.SEARCH_GOOGLE_RES))
		{
			handleSearchGooglePost(request, response);
		}
	}

	private void handleSearchGooglePost(HttpServletRequest request, HttpServletResponse response) 
	{
		String search = request.getParameter("search_google"); 
		if(search == null || search.isEmpty())
		{
			ServletCommon.redirect404(request, response);
			return;
		}
		String google = "http://www.google.com/search?q=";
		String charset = "UTF-8";
		String userAgent = "cis455"; 
		try 
		{
			GoogleListView glv = new GoogleListView();
			Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select("li.g>h3>a");
			System.out.println("google links size: " + links.size());
			for (Element link : links) 
			{
				String title = link.text();
				//Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
				String url = link.absUrl("href"); 
				url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
				if (!url.startsWith("http")) 
				{
					continue; 
				}
				GoogleObjectView gov = new GoogleObjectView(title, url);
				glv.addResult(gov);
			}
			request.setAttribute("GoogleListView", null); 
			request.setAttribute("GoogleListView", glv); 

			RequestDispatcher rd= request.getRequestDispatcher ("/jsp/GoogleList.jsp");
			try 
			{
				rd.forward(request, response);
			} 
			catch (IOException | ServletException e) 
			{
				e.printStackTrace();
				ServletCommon.redirect404(request, response);
			}
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
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
			ServletCommon.redirect404(request, response);
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
			ServletCommon.redirect404(request, response);
		}
	}

	private void handleSearchGroupPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String search = request.getParameter("search_group"); 
		if(search == null || search.isEmpty())
		{
			ServletCommon.redirect404(request, response);
			return;
		}
		GroupListView glv = new GroupListView();

		//test
		String url = "http://andreakihlstedt.com/wpsys/wp-content/uploads/2014/04/Say-Hello1.jpg";
		String name1 = "group1";

		GroupObjectView gov = new GroupObjectView(url, name1);
		glv.addGroupObject(gov);

		//test
		url = "http://andreakihlstedt.com/wpsys/wp-content/uploads/2014/04/Say-Hello1.jpg";
		String name2 = "group2";

		GroupObjectView gov2 = new GroupObjectView(url, name2);
		glv.addGroupObject(gov2);

		request.setAttribute("GroupListView", null); 
		request.setAttribute("GroupListView", glv); 

		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/GroupList.jsp");
		try 
		{
			rd.forward(request, response);
		} 
		catch (IOException | ServletException e) 
		{
			e.printStackTrace();
			ServletCommon.redirect404(request, response);
		}
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
			ServletCommon.redirect404(request, response);
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

		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/MovieList.jsp");
		try 
		{
			rd.forward(request, response);
		} 
		catch (IOException | ServletException e) 
		{
			e.printStackTrace();
			ServletCommon.redirect404(request, response);
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
		else if(url.equals(Const.SEARCH_GOOGLE))
		{
			handleSearchGoogleGet(request, response);
		}
		else if(url.equals(Const.VOICE_SEARCH))
		{
			handleVoiceSearchGet(request, response);
		}
		else
		{
			ServletCommon.redirect404(request, response);
		}
	}

	private void handleSearchMovieGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/htmls/SearchMoviePage.html";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

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

	private void handleSearchGroupGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/htmls/SearchGroupPage.html";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	private void handleSearchUserGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/htmls/SearchUserPage.html";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}	
}

