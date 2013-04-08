/**
 * 
 */
package org.semanticsoft.vaaclipse.widgets;

import java.util.EventObject;

import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.ui.splitpanel.AbstractSplitPanelState;

/**
 * @author rushan
 *
 */
public interface SashWidget
{
	void addListener(SplitPositionChangedListener listener);
	void fireEvent(EventObject event);
	AbstractSplitPanelState getState();
	<T extends ServerRpc> void registerRpc(T implementation, Class<T> rpcInterfaceType);
}
