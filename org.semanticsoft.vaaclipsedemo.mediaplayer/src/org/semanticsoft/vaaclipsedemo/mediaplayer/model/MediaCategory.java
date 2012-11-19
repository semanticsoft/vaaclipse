package org.semanticsoft.vaaclipsedemo.mediaplayer.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author rushan
 *
 */
public class MediaCategory extends MediaEntry
{
	private List<MediaCategory> childCategories = new ArrayList<>();
	private List<Media> mediaList = new ArrayList<>();
		
	public List<MediaCategory> getCategories()
	{
		return Collections.unmodifiableList(this.childCategories);
	}
	
	public List<Media> getMediaList()
	{
		return Collections.unmodifiableList(mediaList);
	}
	
	public void addCategory(MediaCategory child)
	{
		child.setParent(this);
		this.childCategories.add(child);
	}
	
	public void addMedia(Media media)
	{
		media.setParent(this);
		this.mediaList.add(media);
	}
}
