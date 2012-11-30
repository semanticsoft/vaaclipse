/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.playlist;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.IMediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Playlist;

/**
 * @author rushan
 *
 */
public class PlayPlaylist
{
	@Inject
	IEventBroker eventBroker;
	
	@Inject
	Playlist playlist;
	
	@CanExecute
	public boolean canExecute()
	{
		return playlist.getSelectedMedia() != null;
	}
	
	@Execute
	public void play()
	{
		Media selectedInPlaylist = playlist.getSelectedMedia();
		if (selectedInPlaylist != null)
		{
			Map<String, Object> params = new HashMap<>();
			params.put(IEventBroker.DATA, selectedInPlaylist);
			params.put(IMediaConstants.autoPlay, true);
			eventBroker.send(IMediaConstants.mediaSelected, params);	
		}
	}
}
