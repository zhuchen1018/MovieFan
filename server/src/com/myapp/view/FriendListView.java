package com.myapp.view;
import java.util.*;

public class FriendListView {
	private ArrayList<String> name;
	private ArrayList<String> pageUrl;
	
	public FriendListView(){
		
	}
	
	public FriendListView(ArrayList<String> name,ArrayList<String> pageUrl){
		this.name=name;
		this.pageUrl=pageUrl;
	}
	
	public void setName(ArrayList<String> name){
		this.name=name;
	}
	
	public void setPageUrl(ArrayList<String> pageUrl){
		this.pageUrl=pageUrl;
	}
	
	public ArrayList getName(){
		return name;
	}
	
	public ArrayList getPageUrl(){
		return pageUrl;
	}
}
