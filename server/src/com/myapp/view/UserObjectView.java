package com.myapp.view;
import java.util.*;

public class UserObjectView {
	private String imageUrl;
	private String name;
	
	public UserObjectView(){
		
	}
	
	public UserObjectView(String imageUrl,String name){
		this.imageUrl=imageUrl;
		this.name=name;
	}
	
	public void setImageUrl(String imageUrl){
		this.imageUrl=imageUrl;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getImageUrl(){
		return imageUrl;
	}
	
	public String getName(){
		return name;
	}
	
}
