package com.myapp.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Pattern;

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
		showAllRelatedNews(username, response);
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
			showAllRelatedNews(username, response);
		}
		else
		{
			showNews(target_name, response);
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
			return;
		}

		initDB();

		//twitter
		String tweet = "hello world";
		db.addNewsTwitter(username, tweet);
		
		//movie review
		String  title = "This is title";
		String  review_body = "this is the review body";
		String  receive_movie_id = "843";
		String url = "http://thesource.com/wp-content/uploads/2015/02/Pablo_Picasso1.jpg";
		db.addNewsMovieReview(username, title, review_body, receive_movie_id, url);

		//make friends
		String  receiver = "jason123";
		db.addNewsMakeFriends(username, receiver); 
		
		//add group
		String  groupid = "123";
		db.addNewsAddGroup(username, groupid); 
		
		//share movies
		String  movie_id = "843";
		ArrayList<String>friend_list = new ArrayList<String>();
		friend_list.add("jason1");
		friend_list.add("jason2");
		friend_list.add("jason3");
		db.addNewsShareMovies(username, movie_id, url, friend_list); 
		
		//like movie 
		db.addNewsLikeMovie(username, movie_id, url); 
	}

	/**
	 * display a user's all news
	 * TODO:
	 * 
	 * @param username
	 * @param response
	 */
	private void showNews(String username, HttpServletResponse response) 
	{
		response.setContentType("text/html");
		PrintWriter out;
		try 
		{
			out = response.getWriter();

			initDB();

			ArrayList<Long>newsId = db.getUserNews(username); 
			if(newsId == null)
			{
				ServletCommon.PrintErrorPage(ServletConst.NO_THIS_USER_INFO,  response);
				return;
			}

			ArrayList<NewsEntity>newsList = db.getNewsEntityByIds(newsId); 
			System.out.println("news list:" + newsList.size());
			if(newsList != null && newsList.size() > 0)
			{
				Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(int i = newsList.size() - 1; i >= 0; --i)
				{
					NewsEntity t = newsList.get(i);
					String time_str = formatter.format(t.getReleaseTime());
					out.println("<P>" + "tid:" + t.getId() + "</P>");
					out.println("<P>" + "news type:" + t.getNewsType() + "</P>");
					out.println("<P>" + "time: " + time_str  + "</P>");
					out.println("<P>" + "body: " + t.getBody() + "</P>");
				}
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * TODO:
	 * should also show my friends' news
	 * @param username
	 * @param response
	 */
	private void showAllRelatedNews(String username, HttpServletResponse response) 
	{
		initDB();
		ArrayList<String>friends = db.getFriends(username);
		showNews(username, response);
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

