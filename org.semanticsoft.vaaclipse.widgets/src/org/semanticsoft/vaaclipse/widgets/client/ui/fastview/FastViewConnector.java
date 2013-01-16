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

package org.semanticsoft.vaaclipse.widgets.client.ui.fastview;

import java.util.Set;

import org.semanticsoft.commons.geom.Side;
import org.semanticsoft.vaaclipse.widgets.Constants;
import org.semanticsoft.vaaclipse.widgets.FastView;
import org.semanticsoft.vaaclipse.widgets.client.ui.extlayout.VExtendedVerticalLayout;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.VConsole;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.window.WindowConnector;
import com.vaadin.shared.ui.Connect;

/**
 * @author rushan
 *
 */
@Connect(FastView.class)
public class FastViewConnector extends WindowConnector
{
	@Override
	public VFastView getWidget() 
	{
		return (VFastView) super.getWidget();
	}
	
	@Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) 
	{
        super.onStateChanged(stateChangeEvent);
        
        Set<String> changedProperties = stateChangeEvent.getChangedProperties();
        if (changedProperties.contains("side")) 
        {
            getWidget().side = getState().side;
            
            VConsole.log("FastView: side = " + getState().side.toString());
            setPaneLocation();
        }
    }
	
	@Override
	public FastViewState getState() 
	{
		return (FastViewState) super.getState();
	}
	
	public void setPaneLocation()
	{
		VFastView fastView = getWidget();
		
		if (fastView.side == null)
			return;
		
		VExtendedVerticalLayout trimmedContent = findTrimmedWindowContent(RootPanel.get());
		
		int x, y;
		int w = 600, h = 400;
		if (Side.LEFT.equals(fastView.side))
		{
			x = trimmedContent.getAbsoluteLeft();
			y = trimmedContent.getAbsoluteTop();
		}
		else if (Side.RIGHT.equals(fastView.side))
		{
			x = trimmedContent.getAbsoluteLeft() + trimmedContent.getOffsetWidth() - w;
			y = trimmedContent.getAbsoluteTop();
		}
		else
			return;
		
		fastView.setPopupPosition(x, y);
		fastView.setWidth(w + "px");
		fastView.setHeight(h + "px");
	}

	private VExtendedVerticalLayout findTrimmedWindowContent(Widget widget)
	{
		if (widget instanceof VExtendedVerticalLayout)
		{
			VExtendedVerticalLayout layout = (VExtendedVerticalLayout) widget;
			
			final String type = layout.getVariableValue(Constants.E4_ELEMENT_TYPE);
			if (type != null && Constants.TRIMMED_WINDOW_CLIENT_AREA.equals(type))
				return layout;
		}
		
		if (widget instanceof SimplePanel)
		{
			SimplePanel spanel = (SimplePanel) widget;
			return findTrimmedWindowContent(spanel.getWidget());
		}
		else if (widget instanceof ComplexPanel)
		{
			ComplexPanel cpanel = (ComplexPanel) widget;
			for (int i = 0; i < cpanel.getWidgetCount(); i++)
			{
				Widget child = cpanel.getWidget(i);
				VExtendedVerticalLayout result = findTrimmedWindowContent(child);
				if (result != null)
					return result;
			}
		}
		return null;
	}
}
