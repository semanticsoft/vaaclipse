/**
 * 
 */
package org.semanticsoft.vaaclipse.widgets;

import com.vaadin.ui.AbstractSplitPanel;

/**
 * @author rushan
 *
 */
public interface SplitPositionChangedListener 
{
	void processEvent(AbstractSplitPanel splitPanel, float newSplitPos);
}
