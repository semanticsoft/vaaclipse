/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.medialib;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.semanticsoft.e4extension.service.EPartServiceExt;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;

/**
 * @author rushan
 *
 */
public class EditSelectedMedia
{	
	@CanExecute
	public boolean canExecute(MediaLibrary medialib)
	{
		return medialib.getSelectedMediaEntry() instanceof Media;
	}
	
	@Execute
	public void play(MediaLibrary medialib, EPartServiceExt partServiceExt)
	{
		if (medialib.getSelectedMediaEntry() instanceof Media)
		{
			Media selectedInPlaylist = (Media) medialib.getSelectedMediaEntry();
			MInputPart part = partServiceExt.openUri(selectedInPlaylist.getUri());
			part.setLabel(selectedInPlaylist.getName());
		}
		
	}
}
