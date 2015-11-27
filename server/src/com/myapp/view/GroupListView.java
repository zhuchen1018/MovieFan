package com.myapp.view;
import java.util.*;

public class GroupListView {
	private ArrayList<GroupObjectView> groupList;
	
	public GroupListView(){
		groupList = new ArrayList<GroupObjectView>();
	}
	
	public GroupListView(ArrayList<GroupObjectView> groupList){
		this.groupList=groupList;
	}
	
	public void setGroupList(ArrayList<GroupObjectView> groupList){
		this.groupList=groupList;
	}
	
	public ArrayList<GroupObjectView> getGroupList(){
		return groupList;
	}
	
	public int getGroupCount(){
		return groupList.size();
	}
	
	public void addGroupObject(GroupObjectView group){
		groupList.add(group);
	}
	
}
