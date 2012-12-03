/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.playlist;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Playlist;

/**
 * @author rushan
 *
 */
public class ReorderPlaylist
{
	@CanExecute
	public boolean canExecute(Playlist playlist)
	{
		return !playlist.getMediaList().isEmpty();
	}
	
	@Execute
	public void reorder(Playlist playlist, IEventBroker eventBroker)
	{
		playlist.reverseMediaList();
		eventBroker.send(MediaConstants.reversePlaylist, playlist);
	}
}
