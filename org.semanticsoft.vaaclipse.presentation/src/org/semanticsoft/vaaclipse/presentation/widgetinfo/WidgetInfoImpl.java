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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.semanticsoft.commons.geom.Bounds;
import org.semanticsoft.vaaclipse.api.WidgetInfo;
import org.semanticsoft.vaaclipse.presentation.widgets.TrimmedWindowContent;
import org.semanticsoft.vaaclipse.widgets.ExtendedVerticalLayout;
import org.semanticsoft.vaaclipse.widgets.StackWidget;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

/**
 * @author rushan
 *
 */
public class WidgetInfoImpl implements WidgetInfo
{
	@Execute
	public void activate(IEclipseContext context)
	{
		context.set(WidgetInfo.class, this);
		System.out.println("widgetinfo started");
	}
	
	@Override
	public void invalidateBounds(MWindow window)
	{
		Panel vWindow = (Panel) window.getWidget();
		TrimmedWindowContent windowWidget = (TrimmedWindowContent) vWindow.getContent();
		windowWidget.invalidateBounds();
	}
	
	@Override
	public Bounds getBounds(MWindow window)
	{
		if (!(window.getWidget() instanceof Window))
			return null;
		
		Window w = (Window) window.getWidget();
		return new Bounds((int)w.getPositionX(), (int)w.getPositionY(), (int)w.getWidth(), (int)w.getHeight());
	}

	@Override
	public Bounds getBounds(MWindow window, MUIElement element)
	{

		Panel vWindow = (Panel) window.getWidget();
		TrimmedWindowContent windowWidget = (TrimmedWindowContent) vWindow.getContent();
		if (!windowWidget.isBoundsValid())
			windowWidget.updateWindowContentBounds();
		
		Object widget = element.getWidget();
		if (widget instanceof Window)
		{
			Window w = (Window) widget;
			return new Bounds((int)w.getPositionX(), (int)w.getPositionY(), (int)w.getWidth(), (int)w.getHeight());
		}
		else if (widget instanceof ExtendedVerticalLayout)
		{
			return ((ExtendedVerticalLayout) widget).getBounds();
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
