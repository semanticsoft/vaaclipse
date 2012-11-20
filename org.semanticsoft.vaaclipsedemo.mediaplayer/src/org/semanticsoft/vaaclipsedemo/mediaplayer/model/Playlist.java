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
	
	public List<Media> getMediaList()
	{
		return Collections.unmodifiableList(this.mediaList);
	}
	
	public void addMedia(Media media)
	{
		this.mediaList.add(media);
	}
}
