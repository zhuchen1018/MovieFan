package com.myapp.storage.accessor;

import com.myapp.utils.Const;
import com.myapp.storage.entity.IdGenerator;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

public class IdGeneratorAccessor
{
	private PrimaryIndex<String, IdGenerator> id_generarors;

	public IdGeneratorAccessor(EntityStore store)
	{
		id_generarors = store.getPrimaryIndex(String.class, IdGenerator.class);
	}

	public long getNextId(String idType) 
	{
		IdGenerator idg = id_generarors.get(idType);
		if(idg == null)
		{
			idg = new IdGenerator(idType);
		}
		long nextid = idg.getNextId();
		id_generarors.put(idg);
		return nextid;
	}

	public long getNextCommentId() 
	{
		return getNextId(Const.COMMENT_ID_TYPE); 
	}
	
	public long getNextNewsId() 
	{
		return getNextId(Const.NEWS_ID_TYPE); 
	}
	
	public long getNextGroupId() 
	{
		return getNextId(Const.GROUP_ID_TYPE); 
	}
	
	public long getNextEventId() 
	{
		return getNextId(Const.EVENT_ID_TYPE); 
	}
}
