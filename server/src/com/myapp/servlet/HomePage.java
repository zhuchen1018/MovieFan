package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.myapp.storage.DBWrapper;
import com.myapp.utils.Const;
import com.myapp.view.FriendListView;
import com.myapp.view.GroupListView;
import com.myapp.view.NewsListView;


/**
 * 
 * @author Haoyun 
 *
 */
public class HomePage  extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8696988485281625971L;
	private DBWrapper db; 

	public HomePage () 
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
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
		String url = request.getServletPath();
		if(url.equals(Const.HOME_URL))
		{
			handleHomeGet(request, response);
		}	
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void handleHomeGet(HttpServletRequest request, HttpServletResponse response) 
	{
		boolean isLogin = ServletCommon.hasLoginSession(request);
		if(!isLogin)
		{
			String location = "/jsp/Login.jsp";
			ServletCommon.forwardRequestDispatch(request, response, location);
		}
		else
		{
			ServletCommon.RedirectToHome(request, response);
		}
	}

	
	private static void print(String a)
	{
		System.out.println(a);
	}
}


