package org.semanticsoft.vaaclipsedemo.mediaplayer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rushan
 *
 */
public class MediaCategory extends MediaEntry
{
	private List<MediaCategory> childCategories = new ArrayList<MediaCategory>();
	private List<Media> mediaList = new ArrayList<Media>();
		
	public List<MediaCategory> getCategories()
	{
		return Collections.unmodifiableList(this.childCategories);
	}
	
	public List<Media> getMediaList()
	{
		return Collections.unmodifiableList(mediaList);
	}
	
	public List<MediaEntry> getMediaEntries()
	{
		List<MediaEntry> entry = new ArrayList<MediaEntry>();
		entry.addAll(this.childCategories);
		entry.addAll(this.mediaList);
		return entry;
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
	
	public void addMediaEntry(MediaEntry mediaEntry)
	{
		if (mediaEntry instanceof MediaCategory)
			this.addCategory((MediaCategory) mediaEntry);
		else if (mediaEntry instanceof Media)
			this.addMedia((Media) mediaEntry);
	}	
	
	public void removeMediaEntry(MediaEntry media)
	{
		this.mediaList.remove(media);
		mediaEntryRemoved(media);
	}
	
	protected void mediaEntryRemoved(MediaEntry mediaEntry)
	{
		if (this.getParent() != null)
			this.getParent().mediaEntryRemoved(mediaEntry);
	}
}
