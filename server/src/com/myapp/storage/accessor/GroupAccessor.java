package com.myapp.storage.accessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;

import com.myapp.storage.entity.ChannelEntity;

import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;

/**
 * DB Accessor for Channel Entity 
 * @author Haoyun 
 *
 */
public class GroupAccessor 
{
	private PrimaryIndex<String, ChannelEntity> channels;

	public GroupAccessor(EntityStore store)
	{
		channels = store.getPrimaryIndex(String.class, ChannelEntity.class);
	}

	public PrimaryIndex<String, ChannelEntity> getPrimaryIndex() 
	{
		return channels;
	}

	public List<ChannelEntity> getAllEntities()
	{
		List<ChannelEntity> channelList = new ArrayList<ChannelEntity>();
		EntityCursor<ChannelEntity> channel_cursor = this.channels.entities();
		try
		{
			Iterator<ChannelEntity> iter = channel_cursor.iterator();
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

	public ChannelEntity getEntity(String name)
	{
		return channels.get(name);
	}

	public boolean delEntity(String pKey)
	{
		return channels.delete(pKey);
	}

	public ChannelEntity putEntity(ChannelEntity entity)
	{
		return channels.put(entity);
	}

	public boolean contains(String name) 
	{
		return channels.contains(name);
	}

	public void add(String name, String[] xpaths, String creator) 
	{
		ChannelEntity ch = new ChannelEntity(name, xpaths, creator);
		putEntity(ch);
	}
}
