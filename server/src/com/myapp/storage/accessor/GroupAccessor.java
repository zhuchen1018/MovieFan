package com.myapp.storage.accessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;

import com.myapp.storage.entity.GroupEntity;

import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;

/**
 * DB Accessor for group Entity 
 * @author Haoyun 
 *
 */
public class GroupAccessor 
{
	private PrimaryIndex<String, GroupEntity> groups;

	public GroupAccessor(EntityStore store)
	{
		groups = store.getPrimaryIndex(String.class, GroupEntity.class);
	}

	public PrimaryIndex<String, GroupEntity> getPrimaryIndex() 
	{
		return groups;
	}

	public List<GroupEntity> getAllEntities()
	{
		List<GroupEntity> channelList = new ArrayList<GroupEntity>();
		EntityCursor<GroupEntity> channel_cursor = this.groups.entities();
		try
		{
			Iterator<GroupEntity> iter = channel_cursor.iterator();
			while(iter.hasNext())
			{
				channelList.add(iter.next());
			}
		}
		catch(DatabaseException dbe) 
		{
			dbe.printStackTrace();
		}
		finally
		{
			channel_cursor.close();
		}
		return channelList;
	}

	public GroupEntity getEntity(String name)
	{
		return groups.get(name);
	}

	public boolean delEntity(String pKey)
	{
		return groups.delete(pKey);
	}

	public GroupEntity putEntity(GroupEntity entity)
	{
		return groups.put(entity);
	}

	public boolean contains(String name) 
	{
		return groups.contains(name);
	}

	public void add(String name, String creator) 
	{
		GroupEntity ch = new GroupEntity(name,  creator);
		putEntity(ch);
	}
}
