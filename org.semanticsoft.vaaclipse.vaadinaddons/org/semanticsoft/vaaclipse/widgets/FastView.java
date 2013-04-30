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

import org.semanticsoft.commons.geom.GeomUtils.Side;
import org.semanticsoft.vaaclipse.widgets.client.ui.fastview.FastViewState;

import com.vaadin.ui.Window;

/**
 * @author rushan
 *
 */
public class FastView extends Window
{
	@Override
	protected FastViewState getState()
	{
		return (FastViewState) super.getState();
	}
	
	public void setSide(Side side)
	{
		this.getState().side = side;
	}
}
