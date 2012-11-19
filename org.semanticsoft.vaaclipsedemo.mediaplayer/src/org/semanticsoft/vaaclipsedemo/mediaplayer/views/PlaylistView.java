/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public class PlaylistView
{
	private Panel panel;
	
	@Inject
	MediaLibrary mediaLibrary;
	
	@Inject
	EModelService modelService;
	
	@Inject
	MApplication app;
	
	@Inject
	public PlaylistView(VerticalLayout parent, IEclipseContext context)
	{
		panel = new Panel();
		panel.setSizeFull();
		parent.addComponent(panel);
		
		//createList();
	}
}
