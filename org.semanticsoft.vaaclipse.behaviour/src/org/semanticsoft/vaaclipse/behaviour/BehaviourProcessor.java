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

package org.semanticsoft.vaaclipse.behaviour;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.semanticsoft.vaaclipse.api.Behaviour;

public class BehaviourProcessor implements Behaviour {
	@Execute
	void execute(IEclipseContext context) {
		
		context.set(Behaviour.class, this);
	}

	@Override
	public MTrimBar getTrimBarForMinimizedElement(MUIElement minimizedElement)
	{
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public MToolBar getToolBarForMinimizedElement(MUIElement minimizedElement)
	{
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public MUIElement getMinimizedParentForPart(MPart part)
	{
		throw new RuntimeException("Not implemented yet");
	}
}
