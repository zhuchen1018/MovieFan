package com.myapp.storage.accessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.myapp.storage.entity.MoviePageEntity;
import com.sleepycat.je.DatabaseException;

/**
 * DB Accessor for MoviePageEntity 
 * @author Haoyun 
 *
 */
public class MoviePageAccessor 
{
	private PrimaryIndex<String, MoviePageEntity> moviePageById;

	public MoviePageAccessor(EntityStore store)
	{
		moviePageById = store.getPrimaryIndex(String.class, MoviePageEntity.class);
	}

	public PrimaryIndex<String, MoviePageEntity> getPrimaryIndex() 
	{
		return moviePageById;
	}

	public List<MoviePageEntity> getAllEntities()
	{
		List<MoviePageEntity> gannelList = new ArrayList<MoviePageEntity>();
		EntityCursor<MoviePageEntity> gannel_cursor = this.moviePageById.entities();
		try
		{
			Iterator<MoviePageEntity> iter = gannel_cursor.iterator();
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

	public MoviePageEntity getEntity(String id)
	{
		return moviePageById.get(id);
	}

	public boolean delEntity(String pKey)
	{
		return moviePageById.delete(pKey);
	}

	public MoviePageEntity putEntity(MoviePageEntity entity)
	{
		return moviePageById.put(entity);
	}

	public boolean containsById(String id) 
	{
		return moviePageById.contains(id);
	}

	public MoviePageEntity add(String id) 
	{
		MoviePageEntity movie = new MoviePageEntity(id); 
		putEntity(movie);
		return movie;
	}

	public void addLike(String id)
	{
		MoviePageEntity movie = getEntity(id);
		if(movie != null)
		{
			movie.addLike();
		}
		putEntity(movie);
	}
	
	public void removeLike(String id)
	{
		MoviePageEntity movie = getEntity(id);
		if(movie != null)
		{
			movie.removeLike();
		}
		putEntity(movie);
	}

	public MoviePageEntity createEntity(String movieId) 
	{
		MoviePageEntity movie = new MoviePageEntity(movieId);
		putEntity(movie);
		return movie;
	}
}
