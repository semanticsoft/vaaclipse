/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaEntry;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;

/**
 * @author rushan
 *
 */
public class MediaService
{
//	@Inject
//	IEventBroker eventBroker;
//	
//	@PostConstruct
//	public void postConstruct()
//	{
//		
//	}
	
	@Inject
	MediaLibrary mediaLib;
	
	public Media findMedia(String uri)
	{
		for (Media m : getAllInnerMedia(mediaLib))
		{
			if (uri.equals(m.getUri()))
				return m;
		}
		return null;
	}
	
	public MediaCategory findChildCategory(MediaCategory parentCategory, String categoryName)
	{
		for (MediaCategory child : parentCategory.getCategories())
		{
			if (categoryName.equals(child.getName()))
				return child;
		}
		return null;
	}
	
	public String getId(MediaEntry entry)
	{
		if (entry instanceof MediaLibrary)
			return "library";
		List<MediaCategory> pathToParent = getPathToParent(entry);
		Collections.reverse(pathToParent);
		String id = "/";
		for (MediaCategory c : pathToParent)
		{
			id += c.getName() + "/";
		}
		
		if (entry instanceof Media)
			id += ((Media) entry).getUri();
		else
			id = id.substring(0, id.length()-1);
		
		return id;
	}

	private List<MediaCategory> getPathToParent(MediaEntry entry)
	{
		List<MediaCategory> path = new ArrayList<>();
		
		MediaEntry curEntry = entry;
		while (!(curEntry instanceof MediaLibrary))
		{
			if (curEntry instanceof MediaCategory)
				path.add((MediaCategory) curEntry);
			curEntry = curEntry.getParent();
		}
		
		return path;
	}

	public List<Media> getAllInnerMedia(MediaCategory mediaCat)
	{
		List<Media> medialist = new ArrayList<>();
		for (MediaCategory childCat : mediaCat.getCategories())
		{
			medialist.addAll(getAllInnerMedia(childCat));
		}
		medialist.addAll(mediaCat.getMediaList());
		return medialist;
	}
	
	public List<MediaCategory> getAllInnerCategories(MediaCategory mediaCat)
	{
		List<MediaCategory> medialist = new ArrayList<>();
		for (MediaCategory childCat : mediaCat.getCategories())
		{
			medialist.addAll(getAllInnerCategories(childCat));
		}
		medialist.addAll(mediaCat.getCategories());
		return medialist;
	}
	
	public Media findMediaById(MediaCategory mediaCat, String id, String path)
	{
		for (Media m : mediaCat.getMediaList())
		{
			if (id.equals(path + m.getUri()))
				return m;
		}
		
		for (MediaCategory childCat : mediaCat.getCategories())
		{
			Media m = findMediaById(childCat, id, path + childCat.getName() + "/");
			if (m != null)
				return m;
		}
		
		return null;
	}
}
