package com.myapp.view;
import java.util.*;

public class UserListView {
	private ArrayList<UserObjectView> userList;
	
	public UserListView(){
		userList = new ArrayList<UserObjectView>();
	}
	
	public UserListView(ArrayList<UserObjectView> userList){
		this.userList=userList;
	}
	
	public void setUserList(ArrayList<UserObjectView> userList){
		this.userList=userList;
	}
	
	public ArrayList<UserObjectView> getUserList(){
		return userList;
	}
	
	public int size(){
		return userList.size();
	}
	
	public void add(UserObjectView user)
	{
		userList.add(user);
	}
	
}
