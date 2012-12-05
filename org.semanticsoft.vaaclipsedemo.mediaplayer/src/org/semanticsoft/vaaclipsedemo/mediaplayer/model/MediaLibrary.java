/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.model;

/**
 * @author rushan
 *
 */
public class MediaLibrary extends MediaCategory 
{
	private MediaEntry selectedMedia;
	
	public MediaEntry getSelectedMediaEntry()
	{
		return selectedMedia;
	}
	
	public void setSelectedMediaEntry(MediaEntry selectedMedia)
	{
		this.selectedMedia = selectedMedia;
	}
	
	@Override
	protected void mediaRemoved(Media media)
	{
		if (this.selectedMedia == media)
			this.selectedMedia = null;
	}
}
