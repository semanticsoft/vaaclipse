/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.widgets.client.ui.stackwidget;

import java.util.logging.Logger;

import org.semanticsoft.vaaclipse.widgets.StackWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.UIDL;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.client.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.tabsheet.DDTabsheetConnector;

/**
 * @author rushan
 * 
 */
@Connect(StackWidget.class)
public class StackWidgetConnector extends DDTabsheetConnector
{
	Logger logger = Logger.getLogger(StackWidgetConnector.class.getName());
	
	@Override
	protected Widget createWidget()
	{
		return GWT.create(VStackWidget.class);
	}

	@Override
	public VStackWidget getWidget()
	{
		return (VStackWidget) super.getWidget();
	}


	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client)
	{
		VStackWidget stackWidget = getWidget();

		stackWidget.id = uidl.getId();
		stackWidget.client = client;
		
		if (uidl.getIntAttribute("svoi") == 5)
		{
			int state = uidl.getIntAttribute("vaadock_tabsheet_state");
			stackWidget.setState(state);
			stackWidget.maximizeEnabled = uidl.getBooleanAttribute("maximize_enabled");
			stackWidget.minimizeEnabled = uidl.getBooleanAttribute("minimize_enabled");

			if (!stackWidget.maximizeEnabled)
				stackWidget.maximizeButton.setAttribute("style", "display: none;");
			if (!stackWidget.minimizeEnabled)
				stackWidget.minimizeButton.setAttribute("style", "display: none;");
		}

		if (isRealUpdate(uidl) && !uidl.hasAttribute("hidden"))
		{
			UIDL acceptCrit = uidl.getChildByTagName("-ac");
			if (acceptCrit == null)
			{
				getWidget().setDropHandler(null);
			}
			else
			{
				if (getWidget().getDropHandler() == null)
				{
					getWidget().setDropHandler(new VStackWidgetDropHandler(getWidget(), client));
					logger.info("updateFromUIDL: VStackWidgetDropHandler installed");
				}
				getWidget().getDropHandler().updateAcceptRules(acceptCrit);
			}
		}

		super.updateFromUIDL(uidl, client);
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent)
	{
		super.onStateChanged(stateChangeEvent);
		getWidget().setDragFilter(new VSWDragFilter(getState()));
	}

	private class VSWDragFilter extends VDragFilter
	{

		public VSWDragFilter(DDLayoutState state)
		{
			super(state);
		}

		@Override
		public boolean isDraggable(Widget widget)
		{
			return true;
		}
	}
}
