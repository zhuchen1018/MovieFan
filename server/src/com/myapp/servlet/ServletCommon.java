package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.myapp.SQL.SQLDBMovieQuery;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.Const;
import com.myapp.view.FriendListView;
import com.myapp.view.GoogleListView;
import com.myapp.view.GoogleObjectView;
import com.myapp.view.GroupListView;
import com.myapp.view.GroupPageView;
import com.myapp.view.MoviePageView;
import com.myapp.view.MoviePageViewCache;
import com.myapp.view.NewsListView;
import com.myapp.view.UserListView;
import com.myapp.view.UserSettingView;

public class ServletCommon 
{
	public static DBWrapper initDB()
	{
		return new DBWrapper();
	}

	public static void PrintErrorPage(String info, HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/jsp/ErrorPage.jsp";
		request.setAttribute("ERROR_MESSAGE", info);
		forwardRequestDispatch(request, response, location);
	}

	public static boolean isSessionValid(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if(session == null)
		{
			return false;
		}
		Date curr = new Date();
		if(session.getLastAccessedTime() - curr.getTime() <= session.getMaxInactiveInterval())
		{
			return true;
		}
		return false;
	}

	public static String getSessionUsername(HttpServletRequest request )
	{
		HttpSession session = request.getSession(false);
		if(session == null)
		{
			return null;
		}
		return (String) session.getAttribute("username");
	}

	public static boolean hasLoginSession(HttpServletRequest request)
	{
		String name = getSessionUsername(request);
		if(name == null || name.isEmpty())
		{
			return false;
		}
		return true;
	}

	public static boolean isMyPage(String username, HttpServletRequest request)
	{
		String name = getSessionUsername(request);
		return name != null && name.equals(username);
	}

	public static void addLoginSession(HttpServletRequest request, HttpServletResponse response, String username) 
	{
		HttpSession session = request.getSession();
		session.setAttribute("username", username); 
		session.setMaxInactiveInterval(Const.LOGIN_SESSION_AGE);

		/*
		String host = request.getServerName();
		String path = request.getServletPath();

		Cookie c = new Cookie("JESSIONID", session.getId());
		c.setDomain(host);
		c.setPath(path);
		c.setMaxAge(COOKIE_AGE);
		response.addCookie(c);	

		PrintWriter out = response.getWriter();
		out.println("<P>" + "Set-Cookie:" + "</P>");
		out.println("<P>" + "Host: " + host + "</P>");
		out.println("<P>" + "Path: " + path + "</P>");
		out.println("<P>" + "\n" + "</P>");
		 */
	}

	public static void delSession(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		if(session != null)
		{
			session.invalidate();
		}

		/*
		Cookie c; 
		Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
        	for(Cookie cookie : cookies)
        	{
        		if(cookie.getName().equalsIgnoreCase("JSESSIONID"))
        		{
        			c = cookie;
        			c.setMaxAge(0);
        			response.addCookie(c);	
        			break;
        		}
        	}
        }
		 */
	}

	public static void ShowLink(String link, String name,  HttpServletResponse res) 
	{
		PrintWriter out;
		try {
			out = res.getWriter();

			out.println("<P>" + "\n" + "</P>");
			out.println("<a href= \"" + link +  "\" class=\"button\">" + name + "</a>");
			out.println("<P>" + "\n" + "</P>");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}


	/**
	 * eg:field1=value1&field2=value2&field3=value3...
	 * @param query
	 * @return key-value of query string
	 */
	public static Hashtable<String, String> parseQueryString(String query)
	{
		if(query == null || query.trim().isEmpty())
		{
			return null;
		}
		Hashtable<String, String>kv = new Hashtable<String, String>();
		//field=value
		String[] sp = query.split("&");
		for(int i = 0; i < sp.length; ++i)
		{
			String[] pair = sp[i].split("=");
			String key = pair[0].trim();
			String value = ""; 
			if(pair.length == 2)
			{
				value = pair[1].trim(); 
			}
			kv.put(key, value);
		}
		return kv;
	}

	public static void forwardRequestDispatch(HttpServletRequest request, HttpServletResponse response, String location) 
	{
		RequestDispatcher rd= request.getRequestDispatcher (location);
		try {
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

	public static void redirect404(HttpServletRequest request,HttpServletResponse response)
	{
		forwardRequestDispatch(request, response, "/htmls/404.html");
	}

	public static void redirect500(HttpServletRequest request, HttpServletResponse response)
	{
		forwardRequestDispatch(request, response, "/htmls/500.html");
	}

	public static void getRecommend(String username, ArrayList<String>movieIds, ArrayList<String>urls)
	{
		try 
		{
			ArrayList<Integer>likeList = DBWrapper.getUserLikeGenres(username);
			System.out.println("like genres:" + likeList.size());
			if(likeList.size() > 0)
			{
				SQLDBMovieQuery sql = new SQLDBMovieQuery(likeList);
				movieIds.addAll(sql.getSimpleMovieId());
				urls.addAll(sql.getSimpleUrl());
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		try 
		{
			ArrayList<String> likeList = DBWrapper.getUserlikeMovies(username);
			System.out.println("like movie list:" + likeList.size());
			for(String movieId: likeList)
			{
				SQLDBMovieQuery sql = new SQLDBMovieQuery(movieId, Const.PAIR_SEARCH); 
				movieIds.addAll(sql.getSimpleMovieId());
				urls.addAll(sql.getSimpleUrl());
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void RedirectToHome(HttpServletRequest request, HttpServletResponse response) 
	{
		String username = getSessionUsername(request);
		if(username == null)
		{
			redirectToLoginPage(request, response);
			return;
		}

		//DBWrapper DBWrapper = initDB();

		NewsListView nlv = DBWrapper.loadAllNews(username);
		request.setAttribute("NewsListView", null); 
		request.setAttribute("NewsListView", nlv); 

		FriendListView flv = DBWrapper.loadFriendList(username); 
		request.setAttribute("FriendListView", null); 
		request.setAttribute("FriendListView", flv); 

		GroupListView glv = DBWrapper.loadGroupList(username);
		request.setAttribute("GroupListView", null); 
		request.setAttribute("GroupListView", glv); 

		//ArrayList<String>movieIds = new ArrayList<String>(); 
		//ArrayList<String>urls = new ArrayList<String>();

		ArrayList<String>movieIds = (ArrayList<String>)request.getAttribute("RecommendMovieIds");
		ArrayList<String>urls = (ArrayList<String>)request.getAttribute("RecommendPosters");
		if(movieIds == null || movieIds.size() == 0) 
		{
			movieIds = new ArrayList<String>();
			urls = new ArrayList<String>();
			getRecommend(username, movieIds, urls);
			request.setAttribute("RecommendMovieIds", movieIds);
			request.setAttribute("RecommendPosters", urls);
		}
		
		System.out.println("movieIds size:" + movieIds.size());
		System.out.println("urls size:" + urls.size()); 
	
		String location = "/jsp/home.jsp";
		forwardRequestDispatch(request, response, location);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @param targetName
	 */
	public static void RedirectToUserPage(HttpServletRequest request, HttpServletResponse response, 
			String username, String targetName) 
	{
		//DBWrapper DBWrapper = initDB();

		NewsListView nlv = DBWrapper.loadMyNews(targetName);
		request.setAttribute("NewsListView", null); 
		request.setAttribute("NewsListView", nlv); 

		FriendListView flv = DBWrapper.loadFriendList(targetName); 
		request.setAttribute("FriendListView", null); 
		request.setAttribute("FriendListView", flv); 

		GroupListView glv = DBWrapper.loadGroupList(targetName);
		request.setAttribute("GroupListView", null); 
		request.setAttribute("GroupListView", glv); 

		boolean isMyPage = username.equals(targetName);
		boolean isMyFriend = DBWrapper.isMyFriend(username, targetName); 
		UserSettingView usv = DBWrapper.loadUserInfoView(targetName, isMyPage, isMyFriend);
		request.setAttribute("UserInfoView", null); 
		request.setAttribute("UserInfoView", usv); 

		//db.close();
		String location = "/jsp/UserPage.jsp";
		forwardRequestDispatch(request, response, location);
	}

	public static void addLoginCookies(String name, HttpServletResponse res) 
	{
		Cookie cookie = new Cookie("usename", name); 
		cookie.setMaxAge(24 * 60 * 60);  // 24 hours. 
		res.addCookie(cookie);
	}

	public static void RedirectToGroupPage(HttpServletRequest request, HttpServletResponse response, 
			String username, Long gid, NewsListView nlv, UserListView ulv, int showTab) 
	{
		//DBWrapper DBWrapper = initDB();

		GroupPageView gpv = DBWrapper.loadGroupPageView(gid, username, showTab); 
		if(gpv == null)
		{
			//db.close();
			ServletCommon.redirect404(request, response);
			return;
		}

		request.setAttribute("NewsListView", null); 
		request.setAttribute("NewsListView", nlv); 

		request.setAttribute("UserListView", null); 
		request.setAttribute("UserListView", ulv); 

		request.setAttribute("GroupPageView", null); 
		request.setAttribute("GroupPageView", gpv); 

		//db.close();

		String location = "/jsp/GroupPage.jsp";
		forwardRequestDispatch(request, response, location);	
	}

	public static void RedirectToMoviePage(HttpServletRequest request, HttpServletResponse response, String username,
			String movie_id) 
	{
		MoviePageView mpv = null;
		if(movie_id != null) 
		{
			mpv = MoviePageViewCache.get(movie_id);	
		}
		if(mpv == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}	


		//DBWrapper DBWrapper = initDB();

		request.setAttribute("MoviePageView", mpv); 
		request.setAttribute("isLiked", new Boolean(DBWrapper.isUserLikeMovie(username, movie_id)));

		//db.close();

		String location = "/jsp/MoviePage.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}

	public static void retrieveGoogleResult(String search, HttpServletRequest request, HttpServletResponse response) 
	{
		String google = "http://www.google.com/search?q=";
		String charset = "UTF-8";
		String userAgent = "cis550"; 
		try 
		{
			GoogleListView glv = new GoogleListView();
			Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select("li.g>h3>a");
			//System.out.println("google links size: " + links.size());
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response) 
	{
		String location = "/jsp/Login.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);
	}	

	public static int getUnReadMailNum(String username)
	{
		//DBWrapper DBWrapper = initDB();

		UserEntity user = DBWrapper.getUserEntity(username);
		if(user != null)
		{
			//db.close();
			return user.getUnReadMailNum();
		}
		//db.close();
		return 0;
	}
}
