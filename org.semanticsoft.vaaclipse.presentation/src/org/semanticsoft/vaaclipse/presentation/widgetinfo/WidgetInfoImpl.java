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

package org.semanticsoft.vaaclipse.presentation.widgetinfo;

import org.semanticsoft.commons.geom.Bounds;
import org.semanticsoft.vaaclipse.api.WidgetInfo;
import org.semanticsoft.vaaclipse.presentation.renderers.StackRenderer;
import org.semanticsoft.vaaclipse.widgets.StackWidget;
import org.semanticsoft.vaadinaddons.boundsinfo.BoundsinfoVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

/**
 * @author rushan
 *
 */
public class WidgetInfoImpl implements WidgetInfo
{
	public void activate()
	{
		System.out.println("widgetinfo started");
	}

	@Override
	public Bounds getBounds(Object widget)
	{
		if (widget instanceof Window)
		{
			Window w = (Window) widget;
			return new Bounds((int)w.getPositionX(), (int)w.getPositionY(), (int)w.getWidth(), (int)w.getHeight());
		}
		else if (widget instanceof BoundsinfoVerticalLayout)
		{
			return ((BoundsinfoVerticalLayout) widget).getBounds();
		}
		else if (widget instanceof StackWidget)
		{
			return ((StackWidget) widget).getBounds();
		}
		else
			return null;
	}

	@Override
	public Object getParent(Object widget)
	{
		if (widget instanceof Component)
			return ((Component) widget).getParent();
		else
			return null;
	}

}
