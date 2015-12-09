package com.myapp.servlet.FBLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FBConnection 
{
	public static final String FB_APP_ID = "1574620219446066";
	public static final String FB_APP_SECRET = "f931763466c1354b0b490132c0a87829";
	//public static final String REDIRECT_URI = "http://localhost:8080/fb_login";
	public static final String REDIRECT_URI = "http://moviefans.elasticbeanstalk.com/fb_login";

	/*
	String imdb_sample = "https://www.facebook.com/dialog/oauth?client_id=" + "127059960673829"
			+ "&redirect_uri=https%3A%2F%2Fsecure.imdb.com%2Foauth%2Ffacebook"
			+ "&scope=email%2C"
			+ "user_about_me%2C"
			+ "user_birthday%2C"
			+ "publish_actions&state=4030";
	*/

	static String accessToken = "";

	public String getFBAuthUrl() 
	{
		String fbLoginUrl = "";
		try 
		{
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
					+ FBConnection.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(FBConnection.REDIRECT_URI, "UTF-8")
					+ "&scope=email%2C";
		}
					
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) 
	{
		String fbGraphUrl = "";
		try 
		{
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + FBConnection.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(FBConnection.REDIRECT_URI, "UTF-8")
					+ "&client_secret=" + FB_APP_SECRET + "&code=" + code;
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		return fbGraphUrl;
	}

	public String getAccessToken(String code) 
	{
		if ("".equals(accessToken)) 
		{
			URL fbGraphURL;
			try 
			{
				fbGraphURL = new URL(getFBGraphUrl(code));
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook " + e);
			}

			accessToken = b.toString();
			if (accessToken.startsWith("{")) {
				throw new RuntimeException("ERROR: Access Token Invalid: "
						+ accessToken);
			}
		}
		return accessToken;
	}
}
