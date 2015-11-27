package com.myapp.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.myapp.utils.Const;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.storage.entity.NewsEntity;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.ServletCommon;
import com.myapp.utils.ServletConst;
import com.myapp.view.FriendListView;
import com.myapp.view.FriendObjectView;
import com.myapp.view.GroupListView;
import com.myapp.view.GroupObjectView;
import com.myapp.view.NewsListView;
import com.myapp.view.NewsObjectView;


public class UserPage extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6232737047776432110L;
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String url = request.getServletPath();
		if(url.equals(ServletConst.TEST_USER_NEWS_URL))
		{
			handleTestNewsGet(request, response);
			return;
		}
		else if(url.equals(ServletConst.TEST_FRIENDS_URL))
		{
			handleTestFriendsGet(request, response);
			return;
		}
		else if(url.equals(ServletConst.TEST_GROUPS_URL))
		{
			handleTestGroupsGet(request, response);
			return;
		}
		else if(url.equals(ServletConst.USER_PAGE_URL))
		{
			handleUserPageGet(request, response);
		}
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
			ServletCommon.PrintErrorPage(ServletConst.LOGIN_FIRST_INFO,  response);
			return;
		}

		initDB();
		GroupListView flv = new GroupListView(); 
		ArrayList<Long>groups = db.getUserGroup(username);
		if(groups == null)
		{
			System.out.println("showGroupList friends is null");
			return;
		}

		for(long gid: groups)
		{
			GroupEntity groupEntity = db.getGroupEntity(gid);
			if(groupEntity == null) continue;
			String url = groupEntity.getHeadUrl();
			String gname = db.getGroupName(gid);
			GroupObjectView gov = new GroupObjectView(url, gname);
			flv.addGroupObject(gov);
		}

		//new group
		String gname1 = "group name 1";
		String gname2 = "group name 2";
		String gname3 = "group name 3";
		GroupEntity g1 = db.storeGroup(gname1, "creator 1");

		GroupEntity g2 = db.storeGroup(gname2, "creator 2");
		GroupEntity g3 = db.storeGroup(gname3, "creator 3");

		//add group
		db.userAddGroup(username, g1.getId());
		db.userAddGroup(username, g2.getId());
		db.userAddGroup(username, g3.getId());

		ArrayList<Long>groupids = db.getUserGroup(username);
		if(groupids == null)
		{
			System.out.println("db.getUserGroup null");
			return;
		}
		for(Long gid: groupids)
		{
			GroupEntity groupEntity = db.getGroupEntity(gid);
			String url = groupEntity.getHeadUrl();
			String gname = db.getGroupName(gid);
			GroupObjectView gov = new GroupObjectView(url, gname);
			flv.addGroupObject(gov);
		}

		//send it to jsp
		request.setAttribute("GroupListView", null); 
		request.setAttribute("GroupListView", flv); 

		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/GroupList.jsp");
		try 
		{
			rd.forward(request, response);
		} 
		catch (ServletException e1) 
		{
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}	
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void handleUserPageGet(HttpServletRequest request, HttpServletResponse response) 
	{
		//get query string for which user page
		Hashtable<String, String>query = ServletCommon.parseQueryString(request.getQueryString());
		if(query == null || query.get("user") == null)
		{
			ServletCommon.redirect404(response);
			return;
		}

		String targetName = query.get("user");
		if(targetName == null || targetName.isEmpty())
		{
			System.out.println("doGet UserPage: targetName is null");
			ServletCommon.redirect404(response);
			return;
		}

		initDB();
		UserEntity user = db.getUserEntity(targetName);
		if(user == null)
		{
			ServletCommon.PrintErrorPage(ServletConst.NO_THIS_USER_INFO,  response);
			return;
		}

		//is my page
		if(ServletCommon.isMyPage(targetName, request))
		{
			String username = ServletCommon.getSessionUsername(request);
			showTweetWindow(username, response);
			showAllRelatedNews(username, request, response);
			showFriendList(username, request, response);
			showGroupList(username, request, response);
		}
		else
		{
			showNews(targetName, request, response);
			showFriendList(targetName, request, response);
			showGroupList(targetName, request, response);
		}
	}

	/**
	 * 
	 * @param username
	 * @param request
	 * @param response
	 */
	private void showGroupList(String username, HttpServletRequest request, HttpServletResponse response) 
	{
		try 
		{
			initDB();
			GroupListView flv = new GroupListView(); 
			ArrayList<Long>groups = db.getUserGroup(username);
			if(groups == null)
			{
				System.out.println("showGroupList friends is null");
				return;
			}
			for(long gid: groups)
			{
				GroupEntity groupEntity = db.getGroupEntity(gid);
				if(groupEntity == null) continue;
				String url = groupEntity.getHeadUrl();
				String gname = db.getGroupName(gid);
				GroupObjectView gov = new GroupObjectView(url, gname);
				flv.addGroupObject(gov);
			}

			//send it to jsp
			request.setAttribute("GroupListView", null); 
			request.setAttribute("GroupListView", flv); 

			RequestDispatcher rd= request.getRequestDispatcher ("/jsp/GroupList.jsp");
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
	 * Test friends
	 * @param request
	 * @param response
	 */
	private void handleTestFriendsGet(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = ServletCommon.getSessionUsername(request);
		if(username == null || username.isEmpty())
		{
			ServletCommon.PrintErrorPage(ServletConst.LOGIN_FIRST_INFO,  response);
			return;
		}

		initDB();

		FriendListView flv = new FriendListView(); 

		String friend = "a_friend";
		db.userAddFriend(username, friend);
		db.userAddNewsMakeFriends(username, friend);
		String url = "http://thesource.com/wp-content/uploads/2015/02/Pablo_Picasso1.jpg";
		FriendObjectView fov = new FriendObjectView(url, friend);
		flv.addFriendObject(fov);


		friend = "b_friend";
		db.userAddFriend(username, friend); 
		db.userAddNewsMakeFriends(username, friend);
		url = "http://thesource.com/wp-content/uploads/2015/02/Pablo_Picasso1.jpg";
		FriendObjectView fov2 = new FriendObjectView(url, friend);
		flv.addFriendObject(fov2);

		friend = "c_friend";
		db.userAddFriend(username, friend); 
		db.userAddNewsMakeFriends(username, friend);
		url = "http://thesource.com/wp-content/uploads/2015/02/Pablo_Picasso1.jpg";
		FriendObjectView fov3 = new FriendObjectView(url, friend);
		flv.addFriendObject(fov3);

		//send it to jsp
		request.setAttribute("FriendListView", null); 
		request.setAttribute("FriendListView", flv); 

		RequestDispatcher rd= request.getRequestDispatcher ("/jsp/FriendList.jsp");
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
		try 
		{
			rd.forward(request, response);
		} 
		catch (ServletException e1) 
		{
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}

	/**
	 * Show Friend list
	 * @param username
	 * @param request
	 * @param response
	 */
	private void showFriendList(String username, HttpServletRequest request, HttpServletResponse response) 
	{
		try 
		{
			initDB();
			FriendListView flv = new FriendListView(); 
			ArrayList<String>friends = db.getUserFriends(username);
			if(friends == null)
			{
				System.out.println("showFriendList friends is null");
				return;
			}
			for(String fname: friends)
			{
				UserEntity friend = db.getUserEntity(fname);
				if(friend == null) continue;
				String url = friend.getHeadUrl();
				FriendObjectView fov = new FriendObjectView(url, fname);
				flv.addFriendObject(fov);
			}

			//send it to jsp
			request.setAttribute("FriendListView", null); 
			request.setAttribute("FriendListView", flv); 

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
	 * display a user's all news
	 * TODO:
	 * 
	 * @param username
	 * @param response
	 */
	private void showNews(String username, HttpServletRequest request, HttpServletResponse response) 
	{
		try 
		{
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

	private void showTweetWindow(String username, HttpServletResponse response)
	{
		response.setContentType("text/html");
		try 
		{
			PrintWriter out = response.getWriter();
			out.println("<form action=\"/tweet\" method=\"post\">");

			out.println("<P>" + "Say something..." + "</P>");
			out.println("<input type=\"text\" name=\"TWEET\" value=\"\" />");

			out.println("<input type=\"submit\" />");
			out.println("</form>");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

