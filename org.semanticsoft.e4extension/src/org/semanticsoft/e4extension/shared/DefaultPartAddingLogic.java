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

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class DefaultPartAddingLogic
{
	@Inject
	MApplication application;
	
	@Inject
	EModelService modelService;
	
	@Execute
	public void execute(MWindow window, @Optional MElementContainer<?> area, MPart part, EPartService partService, EModelService modelService, IPresentationEngine engine)
	{
		if (area != null)
		{
			addLogic1(area, part, modelService);
		}
		else
		{//add using part logic copied from eclipse e4 service epartservice implementation - partservicesimpl
			addLogic2(window, part, modelService);
		}
	}

	private void addLogic1(MElementContainer<?> area, MPart part, EModelService modelService)
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
//					engine.createGui(stack);
//					((GenericRenderer)marea.getRenderer()).refreshPlatformElement(marea);
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
	
	/**
	 * Add using part logic copied from eclipse e4 service epartservice implementation - partservicesimpl
	 */
	private void addLogic2(MWindow window, MPart part, EModelService modelService)
	{
		//------------from epartserviceimpl---
		MElementContainer<MUIElement> container = getContainer(window);
		MElementContainer<?> area = (MElementContainer<?>) modelService.find("org.eclipse.ui.editorss", container); //$NON-NLS-1$

		MPartStack activeStack = null;
		if (area instanceof MPlaceholder
				&& ((MPlaceholder) area).getRef() instanceof MArea) {
			// Find the currently 'active' stack in the area
			MArea a = (MArea) ((MPlaceholder) area).getRef();
			MUIElement curActive = a.getSelectedElement();
			while (curActive instanceof MElementContainer<?>) {
				if (curActive instanceof MPartStack) {
					activeStack = (MPartStack) curActive;
					break;
				}
				MElementContainer<?> curContainer = (MElementContainer<?>) curActive;
				curActive = curContainer.getSelectedElement();
			}
		}

		if (activeStack != null) {
			activeStack.getChildren().add(part);
		} else {
			// Find the first visible stack in the area
			List<MPartStack> sharedStacks = modelService.findElements(area, null,
					MPartStack.class, null);
			if (sharedStacks.size() > 0) {
				for (MPartStack stack : sharedStacks) {
					if (stack.isToBeRendered()) {
						stack.getChildren().add(part);
						break;
					}
				}
			} else {
				addLogic1(area, part, modelService);
			}
		}
	}
	
	/**
	 * "Container" here is: 1) a selected MPerspective, or, if none available 2) the MWindow for
	 * which this part service is created, or, if not available, 3) the MApplication.
	 */
	private MElementContainer<MUIElement> getContainer(MWindow workbenchWindow) {
		MElementContainer<? extends MUIElement> outerContainer = (workbenchWindow != null) ? workbenchWindow
				: application;

		// see if we can narrow it down to the active perspective
		for (MElementContainer<?> container = outerContainer; container != null;) {
			if (container instanceof MPerspective)
				return (MElementContainer<MUIElement>) container;
			Object child = container.getSelectedElement();
			if (child == null)
				break;
			if (child instanceof MElementContainer<?>)
				container = (MElementContainer<?>) child;
			else
				break;
		}
		return (MElementContainer<MUIElement>) outerContainer;
	}
}
