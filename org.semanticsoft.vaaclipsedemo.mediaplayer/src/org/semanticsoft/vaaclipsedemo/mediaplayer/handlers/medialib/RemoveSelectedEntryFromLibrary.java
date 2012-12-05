/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.medialib;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaEntry;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;

/**
 * @author rushan
 *
 */
public class RemoveSelectedEntryFromLibrary
{
	@CanExecute
	public boolean canExecute(MediaLibrary medialib)
	{
		return medialib.getSelectedMediaEntry() != null;
	}
	
	@Execute
	public void play(MediaLibrary medialib, IEventBroker eventBroker)
	{
		MediaEntry selectedMediaEntry = medialib.getSelectedMediaEntry();
		if (selectedMediaEntry != null)
		{
			MediaCategory parent = selectedMediaEntry.getParent();
			parent.removeMediaEntry(selectedMediaEntry);
			
			eventBroker.send(MediaConstants.mediaEntryRemoved, selectedMediaEntry);
		}
		
	}
}
