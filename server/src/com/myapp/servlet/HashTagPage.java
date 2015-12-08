package com.myapp.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.HashTagEntity;
import com.myapp.utils.Const;
import com.myapp.view.NewsListView;

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

	/**
	 * 
	 * @param request
	 * @param response
	 */
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

