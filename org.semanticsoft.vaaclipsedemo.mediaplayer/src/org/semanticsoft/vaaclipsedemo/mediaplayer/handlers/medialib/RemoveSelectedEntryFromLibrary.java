/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.medialib;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.e4extension.service.EPartServiceExt;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaEntry;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;
import org.semanticsoft.vaaclipsedemo.mediaplayer.service.MediaService;

/**
 * @author rushan
 *
 */
public class RemoveSelectedEntryFromLibrary
{
	@Inject
	EPartServiceExt partServiceExt;
	
	@CanExecute
	public boolean canExecute(MediaLibrary medialib)
	{
		return medialib.getSelectedMediaEntry() != null;
	}
	
	@Execute
	public void remove(MediaLibrary medialib, MediaService mediaService, IEventBroker eventBroker)
	{
		MediaEntry selectedMediaEntry = medialib.getSelectedMediaEntry();
		if (selectedMediaEntry != null)
		{
			if (selectedMediaEntry instanceof Media)
			{//if media close editor in editor area if opened
				partServiceExt.closeUri(((Media) selectedMediaEntry).getUri(), false);
			}
			else
			{//if category close editor in editor area for child media
				for (Media m : mediaService.getAllInnerMedia((MediaCategory) selectedMediaEntry))
				{
					partServiceExt.closeUri(m.getUri(), false);	
				}
			}
			
			MediaCategory parent = selectedMediaEntry.getParent();
			parent.removeMediaEntry(selectedMediaEntry);
			
			eventBroker.send(MediaConstants.mediaEntryRemoved, selectedMediaEntry);
		}
		
	}
}
