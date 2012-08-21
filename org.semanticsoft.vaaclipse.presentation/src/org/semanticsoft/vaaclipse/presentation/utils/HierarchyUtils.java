/**
 * 
 */
package org.semanticsoft.vaaclipse.presentation.utils;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;

/**
 * @author rushan
 *
 */
public class HierarchyUtils
{
	/**
	 * Recursively search in container the top right stack (the stack locatied
	 * in top right corner), i.e. the stack with top right corner is mathced
	 * with top right corner of container (area).
	 * 
	 * @param container
	 *            - input container (area)
	 * @return the top right part stack
	 */
	public static MPartStack findTopLeftFolder(MUIElement container)
	{
		if (container instanceof MPartStack)
			return (MPartStack) container;
		else if (container instanceof MPartSashContainer)
		{
			MPartSashContainer sash = (MPartSashContainer) container;
			if (sash.isHorizontal() && sash.getChildren().get(1) instanceof MElementContainer<?>)
			{
				return findTopLeftFolder((MElementContainer) sash.getChildren().get(1));
			}
			else if (!sash.isHorizontal() && sash.getChildren().get(0) instanceof MElementContainer<?>)
			{
				return findTopLeftFolder((MElementContainer) sash.getChildren().get(0));
			}
		}

		return null;
	}
}
