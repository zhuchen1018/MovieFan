package com.myapp.view;
import java.util.*;

public class PersonListView {
	private ArrayList<PersonObjectView> persons;
	private int personCount;
	
	public PersonListView(){
		persons=new ArrayList<PersonObjectView>();
		personCount=0;
	}
	
	public PersonListView(ArrayList<PersonObjectView> persons){
		this.persons=persons;
		this.personCount=persons.size();
	}
	
	public void setPersons(ArrayList<PersonObjectView> persons){
		this.persons=persons;
		this.personCount=persons.size();
	}
	
	public void addPerson(PersonObjectView person){
		persons.add(person);
		personCount++;
	}
	
	public ArrayList<PersonObjectView> getPersons(){
		return persons;
	}
	
	public int getPersonNumber(){
		return personCount;
	}
}
