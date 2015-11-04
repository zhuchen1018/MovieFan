package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.regex.Pattern;

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
import com.myapp.utils.ServletCommon;


public class DelChannel  extends HttpServlet 
{
	private DBWrapper db; 

	public DelChannel () throws IOException
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		db = ServletCommon.initDB(db);

		if(!ServletCommon.isSessionValid(request))
		{
			ServletCommon.PrintErrorPage("Please login first.",  response);
			return;
		}

		String name = request.getParameter("DelChannelName");
		if(name == null)
		{
			ServletCommon.PrintErrorPage("Please enter a channel name.",  response);
			return;
		}

		/* Check db if the channel name exists*/
		if(!hasChannel(name))
		{
			ServletCommon.PrintErrorPage("Sorry, this channel is not exists.",  response);
			return;
		}
		
		/*check if owner of the channel*/
		String creator = request.getSession().getAttribute("username").toString();
		String real_creator = getChannelCreator(name);
		if(!creator.equals(real_creator))
		{
			ServletCommon.PrintErrorPage("Sorry, you cannot del this channel cause you are not the owner.",  response);
			return;
		}

		db.DelChannel(name);
		
		/*
		 * result page:
		 */
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>Del Channel</TITLE></HEAD><BODY>");
		String res = "delte channel successed!";
		out.println("<P>" + res + "</P>");
		out.println("<P>" + "\n" + "</P>");
		
		ServletCommon.gotoHome(response);
		out.println("</BODY></HTML>");		

		db.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		db = ServletCommon.initDB(db);

		if(!ServletCommon.isSessionValid(request))
		{
			ServletCommon.PrintErrorPage("Please login first.",  response);
			return;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<form action=\"/del_channel\" method=\"post\">");

		out.println("<P>" + "Delete a Channel:\n" + "</P>");
		out.println("<P>" + "Channel Name:" + "</P>");
		out.println("<input type=\"text\" name=\"DelChannelName\" value=\"\" />");
		
		out.println("<input type=\"submit\" />");
		out.println("</form>");
		
		db.close();
	}


	private boolean hasChannel(String name) throws IOException 
	{
		return db.hasChannel(name);
	}

	private String getChannelCreator(String name) throws IOException 
	{
		return db.getChannelCreator(name);
	}
}



