package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.myapp.storage.DBConst;
import com.myapp.storage.DBWrapper;
import com.myapp.storage.MD5Encryptor;
import com.myapp.storage.accessor.UserAccessor;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.storage.entity.UserEntity;
import com.myapp.utils.ServletCommon;


/*
 * The main page of the Channel
 * ==Show all channels created
 * ==Show create channel entry 
 * ==Show delete channel entry 
 */
public class HomePage  extends HttpServlet 
{
	private DBWrapper db; 

	public HomePage () throws IOException
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
			
		out.println("<P>" + "\n" + "</P>");
		out.println("<a href=\"/register\" class=\"button\">Register</a>");

		/*not logined*/
		if(!ServletCommon.isSessionValid(request))
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
		}
		
		//out.println("<P>" + "\n" + "</P>");
		//out.println("<a href=\"/del_channel\" class=\"button\">Delete a Channel</a>");
		
		//out.println("<P>" + "\n" + "</P>");
		//out.println("<P>" + "All Channels:\n" + "</P>");
		
		db.close();
	}
}


