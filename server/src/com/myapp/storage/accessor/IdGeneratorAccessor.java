package com.myapp.storage.accessor;

import java.util.ArrayList;

import com.myapp.storage.entity.IdGenerator;
import com.myapp.storage.entity.TweetEntity;
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
		return idg.getNextId();
	}
}
