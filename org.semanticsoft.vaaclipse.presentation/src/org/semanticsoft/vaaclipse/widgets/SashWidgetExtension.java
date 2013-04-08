/**
 * 
 */
package org.semanticsoft.vaaclipse.widgets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.splitpanel.AbstractSplitPanelRpc;
import com.vaadin.ui.AbstractSplitPanel;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickEvent;

/**
 * @author rushan
 *
 */
public class SashWidgetExtension 
{
	AbstractSplitPanel splitPanel;
	SashWidget sash;
	List<SplitPositionChangedListener> listeners = new ArrayList<SplitPositionChangedListener>();
	
	public SashWidgetExtension(AbstractSplitPanel splitPanel) 
	{
		this.splitPanel = splitPanel;
		this.sash = (SashWidget) splitPanel;
		installRpc();
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
	
	private void installRpc()
	{
		sash.registerRpc(new AbstractSplitPanelRpc() {

				@Override
		        public void splitterClick(MouseEventDetails mouseDetails) {
		            sash.fireEvent(new SplitterClickEvent(splitPanel, mouseDetails));
		        }

		        @Override
		        public void setSplitterPosition(float position) {
		            sash.getState().splitterState.position = position;
		            System.out.println("split position changed!");
		            fireEvent(splitPanel.getSplitPosition());
		        }
				
			}, AbstractSplitPanelRpc.class);
	}
}
