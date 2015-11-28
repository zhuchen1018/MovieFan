package com.myapp.view;
import java.util.*;

public class GoogleObjectView 
{
	private String title;
	private String url;

	public GoogleObjectView()
	{
		
	}
	
	public GoogleObjectView(String title,String url)
	{
		this.title = title;
		this.url = url;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getUrl()
	{
		return url;
	}	
}
