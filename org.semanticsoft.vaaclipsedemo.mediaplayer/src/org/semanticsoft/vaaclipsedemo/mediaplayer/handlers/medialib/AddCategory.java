/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.medialib;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.semanticsoft.e4extension.service.EPartServiceExt;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;
import org.semanticsoft.vaaclipsedemo.mediaplayer.service.MediaService;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;

import com.vaadin.ui.Window;

/**
 * @author rushan
 *
 */
public class AddCategory extends AddMediaEntryBasic
{
	@Inject
	IEventBroker eventBroker;
	
	@Inject
	EPartServiceExt partServiceExt;
	
	@Inject
	MediaService mediaService;
	
	@CanExecute
	public boolean canExecute(MediaLibrary medialib)
	{
		return medialib.getSelectedMediaEntry() != null;
	}
	
	@Execute
	public void addCategory(@Optional final String mediaUri, MediaLibrary mediaLibrary, final MWindow window)
	{
		init(mediaLibrary, window);
		createAndShowDlg(window, "New category", "Category name:");
	}
	
	@Override
	public void optionSelected(OptionDialog dlg, int optionId)
	{
		if (optionId == 0)
		{
			String newCategoryName = componentProvider.getTextField().getValue().toString();
			addMediaCategoryToLibrary(window, parentCategory, newCategoryName);
		}
		dlg.close();
	}
	
	private void addMediaCategoryToLibrary(MWindow window, MediaCategory category, String newCategoryName)
	{
		MediaCategory mediaCategory = mediaService.findChildCategory(category, newCategoryName);
		if (mediaCategory != null)
		{
			((Window)window.getWidget()).showNotification(String.format("Category with name \"%s\" exists in media library", newCategoryName), Notification.TYPE_WARNING_MESSAGE);
			return;
		}
		else
		{
			mediaCategory = new MediaCategory();
			mediaCategory.setName(newCategoryName);
			category.addCategory(mediaCategory);
			
			eventBroker.send(MediaConstants.mediaEntryAdded, mediaCategory);
		}
	}

}
