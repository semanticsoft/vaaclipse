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
public class Playlist
{
	private List<Media> mediaList = new ArrayList<>();
	private Media selectedMedia;
	
	public List<Media> getMediaList()
	{
		return Collections.unmodifiableList(this.mediaList);
	}
	
	public void addMedia(Media media)
	{
		this.mediaList.add(media);
	}
	
	public void addMediaAfter(Media media)
	{
		int index = this.mediaList.indexOf(media);
		if (index >= 0)
			this.mediaList.add(index, media);
	}
	
	public void removeMedia(Media media)
	{
		if (this.selectedMedia == media)
			this.selectedMedia = null;
		this.mediaList.remove(media);
	}
	
	public void reverseMediaList()
	{
		Collections.reverse(this.mediaList);
	}
	
	public int getMediaIndex(Media media)
	{
		return this.mediaList.indexOf(media);
	}
	
	public Media getSelectedMedia()
	{
		return selectedMedia;
	}
	
	public void setSelectedMedia(Media media)
	{
		if (this.mediaList.contains(media))
			this.selectedMedia = media;
	}
}
