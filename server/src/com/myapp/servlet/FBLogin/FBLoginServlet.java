package com.myapp.servlet.FBLogin;


import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.servlet.ServletCommon;
import com.myapp.storage.DBWrapper;
import com.myapp.utils.MD5Encryptor;

public class FBLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String code="";

	public void service(HttpServletRequest req, HttpServletResponse res)
	{		
		code = req.getParameter("code");
		if (code == null || code.equals("")) 
		{
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback.");
		}
		FBConnection fbConnection = new FBConnection();
		String accessToken = fbConnection.getAccessToken(code);

		FBGraph fbGraph = new FBGraph(accessToken);
		String graph = fbGraph.getFBGraph();
		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);

		String name = fbProfileData.get("name");
		String[] names = name.split(" ");
		String fbid = fbProfileData.get("id");

		//use first name
		name = names[0];
		
		System.out.println("LoginServlet service :" + fbid + " " + name); 
		
		handleUser(req, res, fbid, name);
	}

	public DBWrapper initDB()
	{
		return new DBWrapper();
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param fbid
	 * @param name
	 */
	public void handleUser(HttpServletRequest request, HttpServletResponse response, String fbid, String name)
	{
		DBWrapper db = initDB();

		//first login
		if(!db.hasFBUser(fbid))
		{
			//name duplicated, should sign in
			if(db.hasUser(name))
			{
				ServletCommon.PrintErrorPage("You username has been registered, please sign up a new one", request, response); 
				return;
			}
			else
			{
				db.createFBUser(name, MD5Encryptor.crypt(fbid+name), fbid);			
				db.sync();
			}
		}
		ServletCommon.addLoginSession(request, response, name);
		ServletCommon.RedirectToUserPage(request, response, name, name);
	}
}
