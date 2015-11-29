package com.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.storage.DBWrapper;
import com.myapp.storage.entity.GroupEntity;
import com.myapp.utils.Const;
import com.myapp.utils.ServletCommon;

public class Forbid extends HttpServlet 
{
	public void service(HttpServletRequest req, HttpServletResponse res)
	{
		ServletCommon.redirect404(req, res);
	}
}


