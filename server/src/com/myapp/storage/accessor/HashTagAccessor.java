package com.myapp.storage.accessor;

import java.util.ArrayList;

import com.myapp.storage.entity.HashTagEntity;
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

	public void put(HashTagEntity entity) 
	{
		tags.put(entity);
	}
}