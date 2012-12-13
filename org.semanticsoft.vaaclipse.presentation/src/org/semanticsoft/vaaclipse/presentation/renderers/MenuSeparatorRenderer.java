/**
 * 
 */
package org.semanticsoft.vaaclipse.presentation.renderers;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuSeparator;

import com.vaadin.ui.MenuBar.MenuItem;

/**
 * @author rushan
 *
 */
public class MenuSeparatorRenderer extends VaadinRenderer
{
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		if (!(element instanceof MMenuSeparator))
			return;
		
		MUIElement nextRenderableAndVisible = findNextRendarableAndVisible(element, parent);
		
		MenuItem separator = null;
		if (nextRenderableAndVisible == null)
			separator = ((MenuItem)parent.getWidget()).addSeparator();
		else
			separator = ((MenuItem)parent.getWidget()).addSeparatorBefore((MenuItem)nextRenderableAndVisible.getWidget());
		element.setWidget(separator);
	}
	
	@Override
	public void setVisible(MUIElement changedElement, boolean visible)
	{
		((MenuItem)changedElement.getWidget()).setVisible(visible);
	}
}
