package com.myapp.view;
import java.util.*;

import com.myapp.SQL.SQLDBMovieQuery;
import com.myapp.utils.Const;

/**
 * An LRU cache for MoviePageView 
 * @author Haoyun 
 *
 */
public class MoviePageViewCache
{
	public static Hashtable<String, MoviePageView>cache = new Hashtable<String, MoviePageView>();

		
	public MoviePageViewCache()
	{
	}
	
	synchronized public static MoviePageView get(String key)
	{
		if(key == null) return null;

		MoviePageView mpv = cache.get(key);
		if(mpv == null)
		{
			try
			{
				//connect sql
				SQLDBMovieQuery sql = new SQLDBMovieQuery(key,Const.ID_SEARCH);
				mpv = sql.getMovieHomepage();
				//cache 
				if(mpv != null)
				{
					put(key, mpv);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		return mpv;
	}

	public synchronized static void put(String key, MoviePageView mpv)
	{
		cache.put(key, mpv);
	}

	//TODO
	public static void update()
	{
		
	}
}