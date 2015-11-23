package com.myapp.view;
import java.util.*;

public class NewsListView {
	private ArrayList<NewsObjectView> newsList;
	private int newsCount;
	
	public NewsListView(){
		newsList=new ArrayList<NewsObjectView>();
		newsCount=0;
	}
	
	public NewsListView(ArrayList<NewsObjectView> newsList){
		this.newsList=newsList;
		this.newsCount=newsList.size();
	}
	
	public void setNews(ArrayList<NewsObjectView> newsList){
		this.newsList=newsList;
		this.newsCount=newsList.size();
	}
	
	public void addNews(NewsObjectView news){
		newsList.add(news);
		newsCount++;
	}
	
	public ArrayList<NewsObjectView> getNews(){
		return newsList;
	}
	
	public int getNewsNumber(){
		return newsCount;
	}
}
