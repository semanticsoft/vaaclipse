/*******************************************************************************
 * Copyright (c) 2011, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Rushan R. Gilmullin - adoption to vaadin
 ******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.renderers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.model.application.ui.MCoreExpression;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarSeparator;
import org.eclipse.e4.ui.workbench.modeling.ExpressionContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class ToolBarContributionRecord {
	public static final String FACTORY = "ToolBarContributionFactory"; //$NON-NLS-1$
	static final String STATIC_CONTEXT = "ToolBarContributionFactoryContext"; //$NON-NLS-1$

	MToolBar toolbarModel;
	MToolBarContribution toolbarContribution;
	ArrayList<MToolBarElement> generatedElements = new ArrayList<MToolBarElement>();
	ToolBarRenderer renderer;
	boolean isVisible = true;
	private IEclipseContext infoContext;

	public ToolBarContributionRecord(MToolBar model,
			MToolBarContribution contribution, ToolBarRenderer renderer) {
		this.toolbarModel = model;
		this.toolbarContribution = contribution;
		this.renderer = renderer;
	}

	/**
	 * @param context
	 */
	public void updateVisibility(IEclipseContext context) {
		ExpressionContext exprContext = new ExpressionContext(context);
		updateIsVisible(exprContext);
		HashSet<ToolBarContributionRecord> recentlyUpdated = new HashSet<ToolBarContributionRecord>();
		recentlyUpdated.add(this);
		for (MToolBarElement item : generatedElements) {
			boolean currentVisibility = computeVisibility(recentlyUpdated,
					item, exprContext);
			if (item.isVisible() != currentVisibility) {
				item.setVisible(currentVisibility);
			}
		}
	}

	public void updateIsVisible(ExpressionContext exprContext) {
		isVisible = ContributionsAnalyzer.isVisible(toolbarContribution,
				exprContext);
	}

	public boolean computeVisibility(
			HashSet<ToolBarContributionRecord> recentlyUpdated,
			MToolBarElement item, ExpressionContext exprContext) {
		boolean currentVisibility = isVisible;
		
		if (currentVisibility
				&& item.getVisibleWhen() instanceof MCoreExpression) {
			boolean val = ContributionsAnalyzer.isVisible(
					(MCoreExpression) item.getVisibleWhen(), exprContext);
			currentVisibility = val;
		}
		return currentVisibility;
	}

	public boolean anyVisibleWhen() {
		if (toolbarContribution.getVisibleWhen() != null) {
			return true;
		}
		for (MToolBarElement child : toolbarContribution.getChildren()) {
			if (child.getVisibleWhen() != null) {
				return true;
			}
		}
		return false;
	}

	public boolean mergeIntoModel() {
		int idx = getIndex(toolbarModel,
				toolbarContribution.getPositionInParent());
		if (idx == -1) {
			return false;
		}

		final List<MToolBarElement> copyElements = new ArrayList<MToolBarElement>();
		for (MToolBarElement item : toolbarContribution.getChildren()) {
			MToolBarElement copy = (MToolBarElement) EcoreUtil
					.copy((EObject) item);
			copyElements.add(copy);
		}
		
		for (MToolBarElement copy : copyElements) {
			// if a visibleWhen clause is defined, the item should not be
			// visible until the clause has been evaluated and returned 'true'
			copy.setVisible(!anyVisibleWhen());
			generatedElements.add(copy);
			toolbarModel.getChildren().add(idx++, copy);
		}
		return true;
	}

	private IEclipseContext getStaticContext() {
		if (infoContext == null) {
			IEclipseContext parentContext = renderer.getContext(toolbarModel);
			if (parentContext != null) {
				infoContext = parentContext.createChild(STATIC_CONTEXT);
			} else {
				infoContext = EclipseContextFactory.create(STATIC_CONTEXT);
			}
			ContributionsAnalyzer.populateModelInterfaces(toolbarModel,
					infoContext, toolbarModel.getClass().getInterfaces());
			infoContext.set(ToolBarRenderer.class, renderer);
		}
		return infoContext;
	}

	private static int getIndex(MElementContainer<?> model,
			String positionInParent) {
		String id = null;
		String modifier = null;
		if (positionInParent != null && positionInParent.length() > 0) {
			String[] array = positionInParent.split("="); //$NON-NLS-1$
			modifier = array[0];
			id = array[1];
		}
		if (id == null) {
			return model.getChildren().size();
		}
		
		int idx = 0;
		int size = model.getChildren().size();
		while (idx < size) {
			if (id.equals(model.getChildren().get(idx).getElementId())) {
				if ("after".equals(modifier)) { //$NON-NLS-1$
					idx++;
				} else if ("endof".equals(modifier)) { //$NON-NLS-1$
					// Skip current menu item
					idx++;

					// Skip all menu items until next MenuSeparator is found
					while (idx < size
							&& !(model.getChildren().get(idx) instanceof MToolBarSeparator && model
									.getChildren().get(idx).getElementId() != null)) {
						idx++;
					}
				}
				return idx;
			}
			idx++;
		}
		return id.equals("additions") ? model.getChildren().size() : -1; //$NON-NLS-1$
	}
}