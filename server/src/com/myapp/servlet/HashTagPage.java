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


	public DBWrapper initDB()
	{
		return new DBWrapper();
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
		DBWrapper db = initDB();

		String tag = request.getQueryString(); 
		HashTagEntity hashtags = db.getHashTagEntity(tag);
		//no this tag
		if(hashtags == null)
		{
			ServletCommon.redirect404(request, response);
			return;
		}
	
		NewsListView nlv = db.loadSearchHashTag(tag);
		request.setAttribute("NewsListView", null); 
		request.setAttribute("NewsListView", nlv); 
		
		db.sync();

		String location = "/jsp/NewsList.jsp";
		ServletCommon.forwardRequestDispatch(request, response, location);	
	}	
}

