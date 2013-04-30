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
import org.semanticsoft.vaaclipse.api.WidgetInfo;

import com.vaadin.ui.Component;

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
	public Object getParent(Object widget)
	{
		if (widget instanceof Component)
			return ((Component) widget).getParent();
		else
			return null;
	}

}
