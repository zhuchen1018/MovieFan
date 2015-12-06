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
import com.myapp.storage.entity.HashTagEntity;
import com.myapp.storage.entity.NewsEntity;
import com.myapp.utils.Const;
import com.myapp.view.GoogleListView;
import com.myapp.view.GoogleObjectView;
import com.myapp.view.GroupListView;
import com.myapp.view.GroupObjectView;
import com.myapp.view.NewsListView;
import com.myapp.view.NewsObjectView;
import com.myapp.utils.Const;

public class HashTagPage extends HttpServlet 
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
		String url = request.getServletPath();
		if(url.equals(Const.HASHTAG_URL))
		{
			handleHashTagGet(request, response);
		}
	}

	private void handleHashTagGet(HttpServletRequest request, HttpServletResponse response) 
	{
		initDB();

		String tag = request.getQueryString(); 
		HashTagEntity hashtags = db.getHashTagEntity(tag);
		//no this tag
		if(hashtags == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}

		initDB();
		
		NewsListView nlv = db.loadSearchHashTag(tag);
		request.setAttribute("NewsListView", null); 
		request.setAttribute("NewsListView", nlv); 

		String location = "/jsp/NewsList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);	
	}	
}

