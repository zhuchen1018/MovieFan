package com.myapp.view;
import java.util.*;

public class GroupObjectView {
	private Long id;
	private String imageUrl;
	private String name;
	
	public GroupObjectView(){
		
	}
	
	public GroupObjectView(Long id, String imageUrl,String name){
		this.id = id;
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
	
	public Long getId(){
		return id;
	}
	
}

