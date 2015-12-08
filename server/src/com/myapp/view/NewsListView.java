package com.myapp.view;
import java.util.*;

/**
 * From lastest news to oldest
 * @author Haoyun 
 *
 */
public class NewsListView {
	private PriorityQueue<NewsObjectView> newsList;
		
	Comparator<NewsObjectView> newsCmp= new Comparator<NewsObjectView>()
	{
		public int compare(NewsObjectView x, NewsObjectView y)
		{
			return (int) (y.getReleaseTimeLong() - x.getReleaseTimeLong()); 
		}
	};
		
	public NewsListView(){
		newsList = new PriorityQueue<NewsObjectView>(100, newsCmp);
	}
		
	public void addNews(NewsObjectView news){
		newsList.add(news);
	}
	
	public PriorityQueue<NewsObjectView> getNews(){
		return newsList;
	}
	
	public int size(){
		return newsList.size();
	}

	public NewsObjectView pop()
	{
		return newsList.poll();
	}
	
	public boolean isEmpty()
	{
		return newsList.isEmpty();
	}
}
