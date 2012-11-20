/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rushan
 *
 */
public class MediaLibraryService
{
	public static String getId(MediaEntry entry)
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

	private static List<MediaCategory> getPathToParent(MediaEntry entry)
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

	public static List<Media> getAllInnerMedia(MediaCategory mediaCat)
	{
		List<Media> medialist = new ArrayList<>();
		for (MediaCategory childCat : mediaCat.getCategories())
		{
			medialist.addAll(getAllInnerMedia(childCat));
		}
		medialist.addAll(mediaCat.getMediaList());
		return medialist;
	}
	
	public static Media findMediaById(MediaCategory mediaCat, String id, String path)
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
