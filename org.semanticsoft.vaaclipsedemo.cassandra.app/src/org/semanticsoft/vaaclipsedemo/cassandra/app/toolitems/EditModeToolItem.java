/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.toolitems;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;

/**
 * @author rushan
 *
 */
public class EditModeToolItem
{
	@Inject
	public void EditModeToolItem(ComponentContainer parent, IEclipseContext context)
	{
		//the parent - is toolcontrol widget, we must add style for layouting in trimbar to parent widget, not to user control
		parent.addStyleName("editmode");
		
		final Label label = new Label("Readonly");
		parent.addComponent(label);
	}
}
