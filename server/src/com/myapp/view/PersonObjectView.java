package com.myapp.view;
import java.util.*;

public class PersonObjectView {
	private String profile;
	private String name;
	private String personId;
	private String dayOfBirth;
	private String dayOfDeath;
	private String biography;
	
	public PersonObjectView(){
		
	}
	
	public void setProfile(String profile){
		this.profile=profile;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setPersonId(String personId){
		this.personId=personId;
	}
	
	public void setDayOfBirth(String dayOfBirth){
		this.dayOfBirth=dayOfBirth;
	}
	
	public void setDayOfDeath(String dayOfDeath){
		this.dayOfDeath=dayOfDeath;
	}
	
	public void setBiography(String biography){
		this.biography=biography;
	}
	
	public String getProfile(){
		return profile;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPersonId(){
		return personId;
	}
	
	public String getDayOfBirth(){
		return dayOfBirth;
	}
	
	public String getDayOfDeath(){
		return dayOfDeath;
	}
	
	public String getBiography(){
		return biography;
	}
}
