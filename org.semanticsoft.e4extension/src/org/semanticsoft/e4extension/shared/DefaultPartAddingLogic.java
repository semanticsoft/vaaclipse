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

package org.semanticsoft.e4extension.shared;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.semanticsoft.vaaclipse.presentation.renderers.GenericRenderer;

public class DefaultPartAddingLogic
{
	@Execute
	public void execute(MWindow window, MElementContainer<?> area, MPart part, EPartService partService, EModelService modelService, IPresentationEngine engine)
	{
		MPartStack stack = null;
		List<MPartStack> partStacks = modelService.findElements(area, null, MPartStack.class, null);
		if (partStacks.isEmpty())
		{
			if (area instanceof MArea)
			{
				MArea marea = (MArea) area;
				stack = MBasicFactory.INSTANCE.createPartStack();
				marea.getChildren().add(stack);
				engine.createGui(stack);
				((GenericRenderer)marea.getRenderer()).refreshPlatformElement(marea);
			}
			else
				throw new IllegalStateException(String.format("Can not add the part %s to area %s", part, area));
		}
		else
		{
			stack = partStacks.get(0);
		}
		
		if (stack != null)
			stack.getChildren().add(part);
	}	
}
