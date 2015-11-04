package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

import com.myapp.storage.DBConst;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.MD5Encryptor;
import com.myapp.storage.accessor.UserAccessor;
import com.myapp.storage.entity.UserEntity;


public class Logoff extends HttpServlet 
{
	private DBWrapper db; 

	public Logoff() throws IOException
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		db = ServletCommon.initDB(db);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(!ServletCommon.isSessionValid(request))
		{
			ServletCommon.PrintErrorPage("You are not logined now", response);
			return;
		}
		
		ServletCommon.delLoginCookie(request, response);

		out.println("<HTML><HEAD><TITLE>Log off</TITLE></HEAD><BODY>");
		String res = "Logoff successed!"; 
		out.println("<P>" + res + "</P>");
		out.println("<P>" + "\n" + "</P>");
		
		ServletCommon.gotoHome(response);
		out.println("</BODY></HTML>");		
		
		db.close();
	}

	private boolean userLogin(String name) throws IOException 
	{
		return db.userLogin(name);
	}
	
	private boolean hasLogin(String name) throws IOException 
	{
		return db.isLogin(name);
	}
}


