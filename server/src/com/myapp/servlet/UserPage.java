package com.myapp.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

import com.myapp.utils.Const;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.accessor.UserAccessor;
import com.myapp.storage.entity.NewsEntity;
import com.myapp.storage.entity.TweetEntity;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.MD5Encryptor;
import com.myapp.utils.ServletCommon;
import com.myapp.utils.ServletConst;
import com.myapp.view.NewsListView;
import com.myapp.view.NewsObjectView;

import oracle.sql.DATE;

public class UserPage extends HttpServlet 
{
	private DBWrapper db; 
	public UserPage() throws IOException
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
		if(url.equals(ServletConst.USER_TWEET_URL))
		{
			handleTweetPost(request, response);
		}
		else if(url.equals(ServletConst.USER_COMMENT_URL))
		{
			handleCommentPost(request, response);
		}
		else if(url.equals(ServletConst.USER_ARTICLE_URL))
		{
			handleArticlePost(request, response);
		}

	}

	private void handleArticlePost(HttpServletRequest request, HttpServletResponse response) 
	{

	}

	private void handleCommentPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}

	private void handleTweetPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		if(username == null || username.isEmpty())
		{
			ServletCommon.PrintErrorPage(ServletConst.LOGIN_FIRST_INFO,  response);
			return;
		}	

		String info = request.getParameter("TWEET"); 
		if(info == null)
		{
			ServletCommon.PrintErrorPage("Please say something.",  response);
			return;
		}

		initDB();
		db.addNewsTwitter(username, info); 
		db.sync();

		showTweetWindow(username, response);
		showAllRelatedNews(username, request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{

		String url = request.getServletPath();
		if(url.equals(ServletConst.TEST_USER_NEWS_URL))
		{
			handleTestNewsGet(request, response);
			return;
		}

		String username = ServletCommon.getSessionUsername(request);
		if(username == null || username.isEmpty())
		{
			return;
		}


		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		if(query == null || query.get("user") == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.NO_THIS_USER_INFO,  response);
			return;
		}


		String target_name = query.get("user");
		initDB();
		UserEntity user = db.getUserEntity(target_name);

		if(user == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.NO_THIS_USER_INFO,  response);
			return;
		}
		//is my page
		if(ServletCommon.isMyPage(target_name, request))
		{
			showTweetWindow(username, response);
			showAllRelatedNews(username, request, response);
		}
		else
		{
			showNews(target_name, request, response);
		}
	}

	/**
	 * test news list
	 * @param request
	 * @param response
	 */
	private void handleTestNewsGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null || username.isEmpty())
		{
			ServletCommon.PrintErrorPage(ServletConst.LOGIN_FIRST_INFO,  response);
			return;
		}

		initDB();

		NewsListView newsListView = new NewsListView();

		//twitter
		String text = "hello world";
		String url = null; 
		String title = null; 
		String movieId = null; 
		String movieName = null; 
		ArrayList<String>ToList = null;
		int type = Const.NEWS_TWITTER; 
		long releaseTime = (new Date()).getTime(); 
		int likeNums = 2; 
		NewsObjectView newsViewObj = new NewsObjectView(username, text, url, title, movieId, movieName, ToList, type, releaseTime, likeNums);
		newsListView.addNews(newsViewObj);
		db.addNewsTwitter(username, text);

		//movie review
		text = "this is the moviee review body";
		url = "http://thesource.com/wp-content/uploads/2015/02/Pablo_Picasso1.jpg";
		title = "This is title";
		movieId = "843";
		movieName = "this is movie name"; 
		ToList = null;
		type = Const.NEWS_MOVIE_REVIEW; 
		newsViewObj = new NewsObjectView(username, text, url, title, movieId, movieName, ToList, type, releaseTime, likeNums);
		newsListView.addNews(newsViewObj);
		db.addNewsMovieReview(username, title, text, movieId, movieName, url);

		//make friends
		text = null;
		url = null;
		title = null;
		movieId = null;
		movieName = null; 
		String  receiver = "jason123";
		ToList = new ArrayList<String>();
		ToList.add(receiver);
		type = Const.NEWS_MAKE_FRIENDS; 
		newsViewObj = new NewsObjectView(username, text, url, title, movieId, movieName, ToList, type, releaseTime, likeNums);
		newsListView.addNews(newsViewObj);
		db.addNewsMakeFriends(username, receiver); 

		//add group
		text = null;
		url = null;
		title = null;
		movieId = null;
		movieName = null; 
		String  groupid = "123";
		ToList = new ArrayList<String>();	
		ToList.add(groupid);
		type = Const.NEWS_ADD_GROUP; 
		newsViewObj = new NewsObjectView(username, text, url, title, movieId, movieName, ToList, type, releaseTime, likeNums);
		newsListView.addNews(newsViewObj);
		db.addNewsAddGroup(username, groupid); 

		//share movies
		text = null;
		url = "http://thesource.com/wp-content/uploads/2015/02/Pablo_Picasso1.jpg";
		title = null;
		movieId = "843";
		movieName = "This is a moviename"; 
		ToList = new ArrayList<String>();
		ToList.add("jason1");
		ToList.add("jason2");
		ToList.add("jason3");
		type = Const.NEWS_SHARE_MOVIE; 
		newsViewObj = new NewsObjectView(username, text, url, title, movieId, movieName, ToList, type, releaseTime, likeNums);
		newsListView.addNews(newsViewObj);
		db.addNewsShareMovies(username, movieId, movieName, url, ToList); 

		//like movies
		text = null;
		url = "http://thesource.com/wp-content/uploads/2015/02/Pablo_Picasso1.jpg";
		title = null;
		movieId = "843";
		movieName = null; 
		ToList = null;
		type = Const.NEWS_LIKE_MOVIE; 
		newsViewObj = new NewsObjectView(username, text, url, title, movieId, movieName, ToList, type, releaseTime, likeNums);
		newsListView.addNews(newsViewObj);
		db.addNewsLikeMovie(username, movieId, url); 


		//send it to jsp
		request.setAttribute("NewsListView", null); 
		request.setAttribute("NewsListView", newsListView); 

		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/NewsList.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * display a user's all news
	 * TODO:
	 * 
	 * @param username
	 * @param response
	 */
	private void showNews(String username, HttpServletRequest request, HttpServletResponse response) 
	{
		response.setContentType("text/html");
		PrintWriter out;
		try 
		{
			out = response.getWriter();

			initDB();
			NewsListView newsListView = new NewsListView();
			
			ArrayList<Long>newids = db.getUserNews(username);
			for(long id: newids)
			{
				NewsEntity newsEntity = db.getNewsEntityByIds(id);
				if(newsEntity == null) continue;
				int type = newsEntity.getNewsType();
				String text = newsEntity.getBody(); 
				String url = newsEntity.getMoviePosterUrl() ;
				String title = newsEntity.getTitle(); 
				String movieId = newsEntity.getMovidId(); 
				String movieName = newsEntity.getMovieName(); 
				long releaseTime = newsEntity.getReleaseTime(); 
				int likeNums = newsEntity.getLikeNums(); 
				ArrayList<String>ToList = newsEntity.getReceivers();
				NewsObjectView newsViewObj = new NewsObjectView(username, text, url, title, movieId, movieName, ToList, type, releaseTime, likeNums);
				newsListView.addNews(newsViewObj);
			}

			//send it to jsp
			request.setAttribute("NewsListView", null); 
			request.setAttribute("NewsListView", newsListView); 

			RequestDispatcher rd= request.getRequestDispatcher ("/jsp/NewsList.jsp");
			try 
			{
				rd.forward(request, response);
			} 
			catch (ServletException e1) 
			{
				e1.printStackTrace();
			} 
		}
		catch(IOException e)
		{
			e.printStackTrace();
			ServletCommon.redirect404(response);
		} 
	}

	/**
	 * TODO:
	 * should also show my friends' news
	 * @param username
	 * @param response
	 */
	private void showAllRelatedNews(String username, HttpServletRequest request, HttpServletResponse response) 
	{
		initDB();
		//TODO:
		ArrayList<String>friends = db.getFriends(username);
		showNews(username, request, response);
	}

	private void showTweetWindow(String username, HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<form action=\"/tweet\" method=\"post\">");

		out.println("<P>" + "Say something..." + "</P>");
		out.println("<input type=\"text\" name=\"TWEET\" value=\"\" />");

		out.println("<input type=\"submit\" />");
		out.println("</form>");
	}
}

