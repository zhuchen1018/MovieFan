package com.myapp.storage.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class IdGenerator
{
	@PrimaryKey
	private String name;
	private long nextId; 

	public IdGenerator()
	{
		
	}

	public IdGenerator(String name) 
	{
		this.name = name;
		this.nextId = 1;
	}

	/*get funcs*/
	public long getNextId()
	{
		nextId ++;
		return nextId;
	}
	
	private void print(String s)
	{
		System.out.println(s);
	}
	
}
