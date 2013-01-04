/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.medialib;

import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;
import org.semanticsoft.vaadin.optiondialog.OptionDialog.ComponentProvider;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

/**
 * @author rushan
 *
 */
public abstract class AddMediaEntryBasic implements OptionDialog.OptionListener
{
	protected NewMediaEntryComponentProvider componentProvider;
	protected MWindow window; //can not be injected, becouse window can change in different executions!
	protected MediaCategory parentCategory;
	
	public static class NewMediaEntryComponentProvider implements ComponentProvider
	{
		private HorizontalLayout layout;
		private Label label;
		private TextField field;
		
		public NewMediaEntryComponentProvider(String labelName)
		{
			label = new Label(labelName);
		}
		
		public TextField getTextField()
		{
			return field;
		}
		
		@Override
		public void setMessage(String message)
		{
			throw new RuntimeException("Not support");
		}
		
		@Override
		public Component getComponent(OptionDialog optionDialog)
		{
			if (this.layout == null)
			{
				this.label.setWidth(null);
				this.layout = new HorizontalLayout();
				this.layout.setWidth("100%");
				this.layout.addComponent(label);
				field = new TextField();
				field.setWidth("100%");
				this.layout.addComponent(field);
				this.layout.setExpandRatio(field, 1.0f);
			}
			return this.layout;
		}

		@Override
		public void optionSelected(OptionDialog dlg, int optionId)
		{
			
		}
	}
	
	protected void init(MediaLibrary mediaLibrary, final MWindow window)
	{
		this.window = window; //can not be injected, becouse window can change in different executions!
		
		if (mediaLibrary.getSelectedMediaEntry() instanceof MediaCategory)
			parentCategory = (MediaCategory) mediaLibrary.getSelectedMediaEntry();
		else if (mediaLibrary.getSelectedMediaEntry() instanceof Media)
		{
			Media selectedMedia = (Media) mediaLibrary.getSelectedMediaEntry();
			parentCategory = selectedMedia.getParent();
		}
		else
			parentCategory = mediaLibrary;
	}
	
	protected void createAndShowDlg(final MWindow window, String caption, String label)
	{
		OptionDialog dlg = new OptionDialog();
		dlg.setModal(true);
		dlg.setCaption(caption);
		componentProvider = new NewMediaEntryComponentProvider(label);
		dlg.setComponentProvider(componentProvider);
		
		dlg.addOption(0, "Create");
		dlg.addOption(1, "Cancel");
		
		final Window vaadinWindow = (Window) window.getWidget();
		vaadinWindow.addWindow(dlg);
		
		dlg.setOptionListener(this);
	}
}
