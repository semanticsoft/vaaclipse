/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.playlist;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.IMediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Playlist;

/**
 * @author rushan
 *
 */
public class ReorderPlaylist
{
	@Inject
	IEventBroker eventBroker;
	
	@Execute
	public void reorder(Playlist playlist)
	{
		playlist.reverseMediaList();
		eventBroker.send(IMediaConstants.reversePlaylist, playlist);
	}
}
