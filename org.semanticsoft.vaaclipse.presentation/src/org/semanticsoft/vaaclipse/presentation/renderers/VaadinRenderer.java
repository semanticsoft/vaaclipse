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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.commons.general.Condition;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

@SuppressWarnings("restriction")
public class VaadinRenderer implements GenericRenderer {

	//public static final String OWNING_ME = "modelElement";
	
	protected IEclipseContext context;
	protected EModelService modelService;
	
	@Override
	public boolean isLazy()
	{
		return false;
	}

	@PostConstruct
	public void postConstruct(IEclipseContext context) {
		this.context = context;
		this.modelService = (EModelService) context.get(EModelService.class.getName());
	}

	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		
	}

	public void disposeWidget(MUIElement element) {
		
	}

	public void processContents(MElementContainer<MUIElement> element) {
		
	}
	
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		throw new RuntimeException("addChildGui not implemented for renderer " + this);
	}
	
	public void removeChildGui(MUIElement element, MElementContainer<MUIElement> parent) {
		if (parent.getWidget() instanceof ComponentContainer && element.getWidget() != null)
		{
			((ComponentContainer)parent.getWidget()).removeComponent((Component) element.getWidget());
		}
	}
	
	protected List<? extends MUIElement> filterRenderableAndVisibleElements(MElementContainer<?> sash)
	{
		List<MUIElement> renderableAndVisible = new ArrayList<MUIElement>();
		for (MUIElement e : sash.getChildren())
		{
			if (e.isToBeRendered() && e.isVisible() 
					&& e.getWidget() != null /*element can be renderable but has no widget when removeGui called for element*/ )
				renderableAndVisible.add(e);
		}
		return renderableAndVisible;
	}
	
	protected MUIElement findFirstRenderableAndVisibleElement(MElementContainer<?> container)
	{
		for (MUIElement p : container.getChildren())
		{
			if (p.isToBeRendered() && p.isVisible())
			{
				return p;
			}
		}
		return null;
	}
	
	protected MUIElement findNextRendarableAndVisible(MUIElement element, MElementContainer<MUIElement> parent)
	{
		MUIElement nextRenderableAndVisible = null;
		for (int i = parent.getChildren().indexOf(element) + 1; i < parent.getChildren().size(); i++)
		{
			MUIElement child = parent.getChildren().get(i);
			if (child.isToBeRendered() && child.isVisible() && child.getWidget() != null)
			{
				nextRenderableAndVisible = child;
				break;
			}
		}
		return nextRenderableAndVisible;
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

	public void hookControllerLogic(MUIElement element) {
		
	}

//	public IEclipseContext getContext(MUIElement element) {
//		if (element instanceof MContext) {
//			return ((MContext) element).getContext();
//		}
//		IEclipseContext parentContext = getContextForParent(element);
//		if (parentContext == null)
//			return null; //element is not attached
//		return parentContext.createChild();
//	}
	
	/**
	 * Return a context for this part.
	 * 
	 * @param part
	 *            the part to start searching from
	 * @return the closest context, or global context if none in the hierarchy
	 */
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

	public void unbindWidget(MUIElement element) {
		if (element.getWidget() != null && element.getWidget() instanceof AbstractComponent)
		{
			AbstractComponent component = (AbstractComponent) element.getWidget();
			component.setData(null);
		}
	}

	@Override
	public void bindWidget(MUIElement element) {
		if (element.getWidget() != null && element.getWidget() instanceof AbstractComponent)
		{
			AbstractComponent component = (AbstractComponent) element.getWidget();
			component.setData(element);
		}
	}
}
