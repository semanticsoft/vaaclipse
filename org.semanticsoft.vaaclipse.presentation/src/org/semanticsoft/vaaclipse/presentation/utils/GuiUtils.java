/**
 * 
 */
package org.semanticsoft.vaaclipse.presentation.utils;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

/**
 * @author rushan
 *
 */
public class GuiUtils
{
	public static Component createSeparator(MTrimElement trimElement)
	{
		if ((MElementContainer<?>)trimElement.getParent() instanceof MTrimBar)
		{
			Panel separator = new Panel();
			separator.setSizeUndefined();
			
			MTrimBar parentTrimBar = (MTrimBar)(MElementContainer<?>)trimElement.getParent();
			int orientation = parentTrimBar.getSide().getValue();
			
			if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
			{
				separator.addStyleName("horizontalseparator");
			}
			else
			{
				separator.addStyleName("verticalseparator");
			}
			return separator;
		}
		else
			return null;
		
	}
}
