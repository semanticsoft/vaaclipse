/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.playlist;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.IMediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Playlist;

/**
 * @author rushan
 *
 */
public class RemoveFromPlaylist
{
	@Inject
	IEventBroker eventBroker;
	
	@Execute
	public void remove(Playlist playlist)
	{
		Media selectedMedia = playlist.getSelectedMedia();
		if (selectedMedia != null)
		{
			playlist.removeMedia(selectedMedia);
			eventBroker.send(IMediaConstants.deleteMediaFromPlaylist, selectedMedia);
		}
	}
}
