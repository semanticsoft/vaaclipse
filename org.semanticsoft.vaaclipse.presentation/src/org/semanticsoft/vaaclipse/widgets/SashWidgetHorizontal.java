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

package org.semanticsoft.vaaclipse.widgets;

import com.vaadin.ui.HorizontalSplitPanel;

/**
 * @author rushan
 * 
 */
public class SashWidgetHorizontal extends HorizontalSplitPanel implements SashWidget
{
	private SashWidgetExtension extension = new SashWidgetExtension(this);
	
	public void addListener(SplitPositionChangedListener listener)
	{
		this.extension.addListener(listener);
	}
	
	public SashWidgetHorizontal()
	{
		this.setImmediate(true);
	}
	
//	@Override
//	public void changeVariables(Object source, Map<String, Object> variables)
//	{
//		super.changeVariables(source, variables);
//		
//		extension.fireEvent(this.getSplitPosition());
//
//	}
}
