package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.utils.Const;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import com.myapp.utils.MD5Encryptor;
import com.myapp.storage.entity.NewsEntity;
import com.myapp.storage.entity.UserEntity;
import com.myapp.view.FriendListView;
import com.myapp.view.FriendObjectView;
import com.myapp.view.GroupListView;
import com.myapp.view.GroupObjectView;
import com.myapp.view.NewsListView;
import com.myapp.view.NewsObjectView;

public class TestServlet extends HttpServlet 
{
	private DBWrapper db; 

	public TestServlet()
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

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		if(!ServletCommon.hasLoginSession(request))
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}
		String url = request.getServletPath();
		System.out.println("TestServlet doGet url: " + url);

		if(url.equals(Const.TEST_USER_NEWS_URL))
		{
			handleTestNewsGet(request, response);
			return;
		}
		else if(url.equals(Const.TEST_FRIENDS_URL))
		{
			handleTestFriendsGet(request, response);
			return;
		}
		else if(url.equals(Const.TEST_GROUPS_URL))
		{
			handleTestGroupsGet(request, response);
			return;
		}
		else if(url.equals(Const.HOME_TEST_URL))
		{
			handleHomeTestGet(request, response);
		}
	}

	private void handleHomeTestGet(HttpServletRequest request, HttpServletResponse response) 
	{
		response.setContentType("text/html");
		PrintWriter out;
		try 
		{
			out = response.getWriter();
			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/register\" class=\"button\">Register</a>");

			if(!ServletCommon.hasLoginSession(request))
			{
				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/login\" class=\"button\">Login</a>");
			}
			else
			{
				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/logoff\" class=\"button\">Logoff</a>");

				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/user_page" + "?" + "user=" + ServletCommon.getSessionUsername(request) 
				+ "\" class=\"button\">My Page</a>");

				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/test_news \" class=\"button\">Test News Here</a>");

				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/test_friends \" class=\"button\">Test Friends Here</a>");

				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/test_groups \" class=\"button\">Test Groups Here</a>");

				out.println("<P>" + "\n" + "</P>");
				out.println("<a href=\"/create_group \" class=\"button\">Test Creater Group </a>");
			}

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/search_movie\" class=\"button\">Search Movie</a>");

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/search_group\" class=\"button\">Search Group</a>");

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/hometest\" class=\"button\">Facebook login</a>");	

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/search_google\" class=\"button\">Google it</a>"); 

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href=\"/voice_search\" class=\"button\">Voice Search</a>");

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
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
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
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
		db.userAddNewsMakeFriends(username, receiver); 

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
		db.addNewsJoinGroup(username, groupid); 

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

		db.sync();

		ServletCommon.RedirectToHome(request, response);
	}
	/**
	 * Test friends
	 * @param request
	 * @param response
	 */
	private void handleTestFriendsGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null || username.isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}

		initDB();


		String friend = "a_friend";
		String url = "http://thesource.com/wp-content/uploads/2015/02/Pablo_Picasso1.jpg";
		db.createUser(friend, MD5Encryptor.crypt(friend));
		db.userAddHeadUrl(friend, url);

		db.userAddFriend(username, friend);
		db.userAddNewsMakeFriends(username, friend);
		db.userAddFans(friend, username);
	

		friend = "b_friend";
		db.createUser(friend, MD5Encryptor.crypt(friend));
		db.userAddHeadUrl(friend, url);

		db.userAddFriend(username, friend); 
		db.userAddNewsMakeFriends(username, friend);
		db.userAddFans(friend, username);
		

		friend = "c_friend";
		db.createUser(friend, MD5Encryptor.crypt(friend));
		db.userAddHeadUrl(friend, url);

		db.userAddFriend(username, friend); 
		db.userAddNewsMakeFriends(username, friend);
		db.userAddFans(friend, username);

		db.sync();
		ServletCommon.RedirectToHome(request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void handleTestGroupsGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null || username.isEmpty())
		{
			ServletCommon.PrintErrorPage(Const.LOGIN_FIRST_INFO,  response);
			return;
		}

		initDB();

		//new group
		String gname1 = "group name 1";
		String gname2 = "group name 2";
		String gname3 = "group name 3";
		GroupEntity g1 = db.createNewGroup(gname1, "creator 1");
		GroupEntity g2 = db.createNewGroup(gname2, "creator 2");
		GroupEntity g3 = db.createNewGroup(gname3, "creator 3");

		//add group
		db.userJoinGroup(username, g1.getId());
		db.userJoinGroup(username, g2.getId());
		db.userJoinGroup(username, g3.getId());

		db.sync();

		ServletCommon.RedirectToHome(request, response);
	}

}

