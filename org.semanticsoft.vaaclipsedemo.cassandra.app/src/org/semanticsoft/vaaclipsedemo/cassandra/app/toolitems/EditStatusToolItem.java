package org.semanticsoft.vaaclipsedemo.cassandra.app.toolitems;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;

public class EditStatusToolItem
{
	@Inject
	public void EditModeToolItem(ComponentContainer parent, IEclipseContext context)
	{
		//the parent - is toolcontrol widget, we must add style for layouting in trimbar to parent widget, not to user control
		parent.addStyleName("editstatus");
		
		final Label label = new Label("Smart Insert");
		parent.addComponent(label);
	}
}
