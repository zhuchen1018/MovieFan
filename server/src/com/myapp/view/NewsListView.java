package com.myapp.view;
import java.util.*;

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
	
	public int getNewsNumber(){
		return newsList.size();
	}

	public NewsObjectView getNextItem()
	{
		return newsList.poll();
	}
}
