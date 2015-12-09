package com.myapp.storage.accessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.myapp.storage.entity.GroupEntity;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.LockTimeoutException;
import com.sleepycat.je.Transaction;

/**
 * DB Accessor for group Entity 
 * @author Haoyun 
 *
 */
public class GroupAccessor 
{
	private PrimaryIndex<Long, GroupEntity> groupsById;

	private Environment env;

	public GroupAccessor(Environment env, EntityStore store)
	{
		this.env = env;
		groupsById = store.getPrimaryIndex(Long.class, GroupEntity.class);
	}

	public PrimaryIndex<Long, GroupEntity> getPrimaryIndex() 
	{
		return groupsById;
	}

	public List<GroupEntity> getAllEntities()
	{
		List<GroupEntity> gannelList = new ArrayList<GroupEntity>();
		EntityCursor<GroupEntity> gannel_cursor = this.groupsById.entities();
		try
		{
			Iterator<GroupEntity> iter = gannel_cursor.iterator();
			while(iter.hasNext())
			{
				gannelList.add(iter.next());
			}
		}
		catch(DatabaseException dbe) 
		{
			dbe.printStackTrace();
		}
		finally
		{
			gannel_cursor.close();
		}
		return gannelList;
	}

	public GroupEntity getEntity(Long id)
	{
		/*
		Transaction txn = env.beginTransaction(null, null); 
		GroupEntity gobj = groupsById.get(txn, id, LockMode.READ_UNCOMMITTED);
		txn.commit();
		return gobj;
		*/
		return groupsById.get(id);
	}

	public boolean delEntity(Long pKey)
	{
		return groupsById.delete(pKey);
	}

	public void putEntity(GroupEntity entity)
	{
		int cnt = 0;
		while(true && cnt < 3)
		{
			try
			{
				groupsById.put(entity);
				return;
			}
			catch(LockTimeoutException e)
			{
				cnt ++;
				e.printStackTrace();
				try 
				{
					Thread.sleep(500);
				} 
				catch (InterruptedException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
	}

	public boolean containsById(Long id) 
	{
		return groupsById.contains(id);
	}

	/**
	 * TODO
	 * @param name
	 * @return
	 */
	public boolean containsByName(String name) 
	{
		//return groupsByName.contains(name);
		return true;
	}

	public GroupEntity add(long id, String name, String creator) 
	{
		GroupEntity g = new GroupEntity(id, name,  creator);
		g.addMember(creator);
		putEntity(g);
		return g;
	}

	public void addMember(Long id, String username) 
	{
		GroupEntity g = getEntity(id);
		if(g != null)
		{
			g.addMember(username);
			putEntity(g);
		}
	}

	public void removeMember(Long id, String username) 
	{
		GroupEntity g = getEntity(id);
		if(g != null)
		{
			g.removeMember(username);
			putEntity(g);
		}
	}

	public ArrayList<GroupEntity> getSearchGroup(String name) 
	{
		ArrayList<GroupEntity>res = new ArrayList<GroupEntity>();
		for(Long gid: groupsById.keys())
		{
			GroupEntity gobj = groupsById.get(gid);
			String gname = gobj.getName().toLowerCase();
			if(gname.contains(name))
			{
				res.add(gobj);
			}
		}
		return res;
	}
}
