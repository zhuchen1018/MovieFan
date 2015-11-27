package com.myapp.view;
import java.util.*;

public class FriendListView {
	private ArrayList<FriendObjectView> friendList;
	
	public FriendListView(){
		friendList = new ArrayList<FriendObjectView>();
	}
	
	public FriendListView(ArrayList<FriendObjectView> friendList){
		this.friendList=friendList;
	}
	
	public void setFriendList(ArrayList<FriendObjectView> friendList){
		this.friendList=friendList;
	}
	
	public ArrayList<FriendObjectView> getFriendList(){
		return friendList;
	}
	
	public int getFriendCount(){
		return friendList.size();
	}
	
	public void addFriendObject(FriendObjectView friend){
		friendList.add(friend);
	}
	
}
