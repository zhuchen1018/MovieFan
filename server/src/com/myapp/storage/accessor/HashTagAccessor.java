package com.myapp.storage.accessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.myapp.storage.entity.HashTagEntity;
import com.myapp.storage.entity.HashTagEntity;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

public class HashTagAccessor 
{
	private PrimaryIndex<String, HashTagEntity> tags;
	
	public HashTagAccessor(EntityStore store)
	{
		tags = store.getPrimaryIndex(String.class, HashTagEntity.class);
	}

	public void addHashTag(HashTagEntity obj)
	{
		tags.put(obj);
	}

	public HashTagEntity getHashTags(String key) 
	{
		return tags.get(key);
	}

	public void addHashTag(String tag, long id) 
	{
		HashTagEntity entity = tags.get(tag);
		if(entity == null)
		{
			entity = new HashTagEntity(tag);
		}
		entity.addNews(id);
		tags.put(entity);
	}
	
	public List<HashTagEntity> getAllEntities()
	{
		List<HashTagEntity> tagList = new ArrayList<HashTagEntity>();
		EntityCursor<HashTagEntity> cursors = tags.entities();
		try
		{
			Iterator<HashTagEntity> iter = cursors.iterator();
			while(iter.hasNext())
			{
				tagList.add(iter.next());
			}
		}
		catch(DatabaseException dbe) 
		{
			dbe.printStackTrace();
		}
		finally
		{
			cursors.close();
		}
		return tagList;
	}
	
	public ArrayList<HashTagEntity> searchHashTag(String tag) 
	{
		if(tag.startsWith("#"))
		{
			tag = tag.substring(1);  
		}
		ArrayList<HashTagEntity>result = new ArrayList<HashTagEntity>();
		for(HashTagEntity tmp: getAllEntities()) 
		{
			String key = tmp.getKey();
			if(key.contains(tag))
			{
				result.add(tags.get(key));
			}
		}
		return result;
	}

	public void put(HashTagEntity entity) 
	{
		tags.put(entity);
	}
}
