/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.playlist;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Playlist;

/**
 * @author rushan
 *
 */
public class PlayPlaylist
{	
	@CanExecute
	public boolean canExecute(Playlist playlist)
	{
		return playlist.getSelectedMedia() != null;
	}
	
	@Execute
	public void play(Playlist playlist, IEventBroker eventBroker)
	{
		Media selectedInPlaylist = playlist.getSelectedMedia();
		if (selectedInPlaylist != null)
		{
			Map<String, Object> params = new HashMap<>();
			params.put(IEventBroker.DATA, selectedInPlaylist);
			params.put(MediaConstants.autoPlay, true);
			eventBroker.send(MediaConstants.mediaEntrySelected, params);	
		}
	}
}
