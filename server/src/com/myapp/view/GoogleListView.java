package com.myapp.view;
import java.util.*;

public class GoogleListView
{
	private ArrayList<GoogleObjectView> results;
	
	public GoogleListView(){
		results=new ArrayList<GoogleObjectView>();
	}
	
	public GoogleListView(ArrayList<GoogleObjectView> results){
		this.results=results;
	}
	
	public void setResults(ArrayList<GoogleObjectView> results){
		this.results=results;
	}
	
	public void addResult(GoogleObjectView res){
		results.add(res);
	}
	
	public ArrayList<GoogleObjectView> getResults(){
		return results;
	}
	
	public GoogleObjectView getItem(int idx)
	{
		return results.get(idx);
	}
	
	public int getCount(){
		return results.size();
	}
}
