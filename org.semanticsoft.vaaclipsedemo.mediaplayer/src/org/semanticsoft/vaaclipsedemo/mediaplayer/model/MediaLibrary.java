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
	private Media selectedMedia;
	
	public Media getSelectedMedia()
	{
		return selectedMedia;
	}
	
	public void setSelectedMedia(Media selectedMedia)
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
