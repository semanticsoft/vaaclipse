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

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;



/**
 * @author rushan
 *
 */
class HierarchyUtils 
{
	private static class TabSheetMatchPredicate implements Predicate
	{
		MUIElement tabSheet;
		public TabSheetMatchPredicate(MUIElement tabSheet) 
		{
			this.tabSheet = tabSheet;
		}
		
		public boolean predicate(MUIElement component) {
			return component == tabSheet;
		}
	}
	
	private static class HorizontalSplitPaneMatchPredicate implements Predicate
	{
		MUIElement tabSheet;
		public HorizontalSplitPaneMatchPredicate(MUIElement tabSheet) {
			this.tabSheet = tabSheet;
		}
		
		public boolean predicate(MUIElement component) {
			return component == tabSheet || (component instanceof MPartSashContainer && ((MPartSashContainer)component).isHorizontal());
		}
	}
	
	private static class VerticalSplitPaneMatchPredicate implements Predicate
	{
		MUIElement tabSheet;
		public VerticalSplitPaneMatchPredicate(MUIElement tabSheet) {
			this.tabSheet = tabSheet;
		}
		
		public boolean predicate(MUIElement component) {
			return component == tabSheet || (component instanceof MPartSashContainer && !((MPartSashContainer)component).isHorizontal());
		}
	}
	
	public static SideValue findNearestSide(MWindow pageComponent, MUIElement component)
	{
		if (pageComponent.getChildren().isEmpty() || !(pageComponent.getChildren().get(0) instanceof MElementContainer<?>))
			return SideValue.LEFT;
		
		MElementContainer<?> rootComponent = (MElementContainer<?>) pageComponent.getChildren().get(0);
		if (rootComponent instanceof MPartSashContainer && ((MPartSashContainer) rootComponent).isHorizontal())
		{
			MPartSashContainer hsp = (MPartSashContainer) rootComponent;
			if (findInTree(hsp.getChildren().get(0), new TabSheetMatchPredicate(component)) != null)
				return SideValue.LEFT;
			else
			{
				MUIElement comp = findInTree(hsp.getChildren().get(1), new VerticalSplitPaneMatchPredicate(component));
				if (comp instanceof MPartStack)
					return SideValue.RIGHT;
				else if (comp instanceof MPartSashContainer && !((MPartSashContainer)comp).isHorizontal())
				{
					if (findInTree(((MPartSashContainer) comp).getChildren().get(0), new TabSheetMatchPredicate(component)) != null)
						return SideValue.RIGHT;
					else if (findInTree(((MPartSashContainer) comp).getChildren().get(1), new TabSheetMatchPredicate(component)) != null)
						return SideValue.BOTTOM;
				}
			}
		}
		else if (rootComponent instanceof MPartSashContainer && !((MPartSashContainer)rootComponent).isHorizontal())
		{
			MPartSashContainer vsp = (MPartSashContainer) rootComponent;
			if (findInTree(vsp.getChildren().get(1), new TabSheetMatchPredicate(component)) != null)
			{
				return SideValue.BOTTOM;
			}
			else
			{
				MUIElement comp = findInTree(vsp.getChildren().get(0), new HorizontalSplitPaneMatchPredicate(component));
				if (comp instanceof MPartStack)
					return SideValue.RIGHT;
				else if (comp instanceof MPartSashContainer && ((MPartSashContainer)comp).isHorizontal())
				{
					if (findInTree(((MPartSashContainer) comp).getChildren().get(0), new TabSheetMatchPredicate(component)) != null)
						return SideValue.LEFT;
					else if (findInTree(((MPartSashContainer) comp).getChildren().get(1), new TabSheetMatchPredicate(component)) != null)
						return SideValue.RIGHT;
				}
			}
		}
		else if (rootComponent == component)
			return SideValue.RIGHT;
		
		return null;
	}
	
	private static MUIElement findInTree(MUIElement parent, Predicate predicate)
	{
		if (parent instanceof MPartSashContainer)
		{
			if (predicate.predicate(parent))
				return parent;
			else
			{
				MPartSashContainer splitPanel = (MPartSashContainer) parent;
				MUIElement findedComponent = findInTree((MElementContainer<MUIElement>) splitPanel.getChildren().get(0), predicate);
				if (findedComponent != null)
					return findedComponent;
				else
					return findInTree((MElementContainer<MUIElement>) splitPanel.getChildren().get(1), predicate);				
			}
		}
		else if (parent instanceof MElementContainer<?>)
		{
			if (predicate.predicate(parent))
				return parent;
			else
				return null;
		}
		
		return null;
	}
	
	private static interface Predicate
	{
		boolean predicate(MUIElement component);
	}
}
