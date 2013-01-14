/**
 * 
 */
package org.semanticsoft.vaaclipse.widgets;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.AbstractSplitPanel;

/**
 * @author rushan
 *
 */
public class SashWidgetExtension 
{
	AbstractSplitPanel splitPanel;
	List<SplitPositionChangedListener> listeners = new ArrayList<SplitPositionChangedListener>();
	
	public SashWidgetExtension(AbstractSplitPanel splitPanel) 
	{
		this.splitPanel = splitPanel;
	}
	
	public void addListener(SplitPositionChangedListener listener) 
	{
		this.listeners.add(listener);
	}
	
	public void fireEvent(float newPos)
	{
		for (SplitPositionChangedListener l : this.listeners)
		{
			l.processEvent(splitPanel, newPos);
		}
	}
}
