/*******************************************************************************
 * Copyright (c) 2011 Kai Toedter, Rushan R. Gilmullin and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Toedter - initial API and implementation
 *     Rushan R. Gilmullin - adoption to vaaclipse and other changes
 ******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.renderers;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.commons.general.Condition;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

/**
 * This renderer was adopted from Kai Toedter's generic renderer project. I place it in vaaclipse packages temproraly -
 * until the generic renderer will be the part of eclipse project.
 * @author Kai Toedter
 */
@SuppressWarnings("restriction")
public class GenericRenderer {

	//public static final String OWNING_ME = "modelElement";
	
	protected IEclipseContext context;
	protected EModelService modelService;

	@PostConstruct
	public void postConstruct(IEclipseContext context) {
		this.context = context;
		this.modelService = (EModelService) context.get(EModelService.class.getName());
	}

	public Object getParentWidget(MUIElement element) {
		if (element.getParent() != null) {
			return element.getParent().getWidget();
		}
		return null;
	}

	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		
	}

	public void removeWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (parent.getWidget() instanceof ComponentContainer && element.getWidget() != null)
		{
			((ComponentContainer)parent.getWidget()).removeComponent((Component) element.getWidget());
		}
	}

	public void processContents(MElementContainer<MUIElement> element) {
		System.out.println("GenericRenderer.processContents(): " + element);
	}
	
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		
	}
	
	public void removeChildGui(MUIElement element, MElementContainer<MUIElement> parent) {
		if (parent.getWidget() instanceof ComponentContainer && element.getWidget() != null)
		{
			((ComponentContainer)parent.getWidget()).removeComponent((Component) element.getWidget());
		}
	}
	
	protected int indexOf(MUIElement element, MElementContainer<MUIElement> parent)
	{
		return indexOf(element, parent, null);
	}
	
	protected int indexOf(MUIElement element, MElementContainer<MUIElement> parent, Condition<MUIElement> condition)
	{
		int i = 0;
		for (MUIElement child : parent.getChildren())
		{
			boolean additionalConditionRes = condition != null ? condition.check(child) : true;
			if (child.isToBeRendered() && additionalConditionRes)
			{
				if (child.equals(element))
					return i;
				i++;
			}
		}
		return -1;
	}
	
	/**
	 * This method will be removed as soon as possible
	 * @param element
	 */
	@Deprecated
	public void refreshPlatformElement(MElementContainer<?> element)
	{
		
	}


	public void hookControllerLogic(MUIElement element) {
		// System.out.println("GenericRenderer.hookControllerLogic(): " +
		// element);
	}

	public IEclipseContext getContext(MUIElement part) {
		if (part instanceof MContext) {
			return ((MContext) part).getContext();
		}
		return getContextForParent(part);
	}

	protected IEclipseContext getContextForParent(MUIElement element) {
		return modelService.getContainingContext(element);
	}

	public void setVisible(MUIElement changedElement, boolean visible) {
		((Component)changedElement.getWidget()).setVisible(visible);
	}
}
