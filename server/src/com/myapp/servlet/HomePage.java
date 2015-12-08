package com.myapp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.myapp.storage.DBWrapper;
import com.myapp.utils.Const;


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

	public HomePage () 
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	{

	}

	public DBWrapper initDB()
	{
		return new DBWrapper();
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


