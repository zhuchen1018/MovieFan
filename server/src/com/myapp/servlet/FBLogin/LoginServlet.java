package com.myapp.servlet.FBLogin;


import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myapp.servlet.ServletCommon;
import com.myapp.storage.DBWrapper;
import com.myapp.utils.MD5Encryptor;

public class LoginServlet extends HttpServlet {

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

		System.out.println("Login FacebookId:" + fbProfileData.get("id"));
		String name = fbProfileData.get("name");
		String[] names = name.split(" ");
		//use first name
		name = names[0];
		ServletCommon.RedirectToUserPage(req, res, name, name);
	}

	public DBWrapper initDB()
	{
		return new DBWrapper();
	}

	/**
	 * 
	 * To be able to log in a user by his/her Facebook account, you need to know which user in the datastore 
	 * we are talking about. 
	 * If it's a new user, create a new user object (with a field called "facebookId"), persist it in the datastore and log the user in.
	 * If the user exist, you need to have the field with the facebookId. When the user is redirected from Facebook, you can grab the facebookId, 
	 * and look in the datastore to find the user you want to log in.
	 *If you already have users, you will need to let them log in the way you usually do, 
	 *so you know who they are, then send them to Facebook, get the facebookId back and update their user object. 
	 *This way, they can log in using Facebook the next time.
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
				db.createFBUser(name, MD5Encryptor.crypt(name), fbid);			
			}
		}
		db.sync();

		ServletCommon.addLoginSession(request, response, name);
	}
}
