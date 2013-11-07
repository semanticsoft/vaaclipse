package org.semanticsoft.vaaclipse.contributions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.internal.expressions.OrExpression;
import org.eclipse.e4.core.commands.ExpressionContext;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.model.application.ui.MCoreExpression;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MExpression;
import org.eclipse.e4.ui.model.application.ui.impl.UiFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuSeparator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class ContributionRecord {
	public static final String FACTORY = "ContributionFactory"; //$NON-NLS-1$
	static final String STATIC_CONTEXT = "ContributionFactoryContext"; //$NON-NLS-1$

	MMenu menuModel;
	MMenuContribution menuContribution;
	ArrayList<MMenuElement> generatedElements = new ArrayList<MMenuElement>();
	HashSet<MMenuElement> sharedElements = new HashSet<MMenuElement>();
	MenuContributionProcessor renderer;
	boolean isVisible = true;
	private IEclipseContext infoContext;
	private Runnable factoryDispose;

	public ContributionRecord(MMenu menuModel, MMenuContribution contribution,
			MenuContributionProcessor renderer) {
		this.menuModel = menuModel;
		this.menuContribution = contribution;
		this.renderer = renderer;
	}

	public MMenuContribution getMenuContribution() {
		return menuContribution;
	}

	/**
	 * Access to analyze for tests. For Looking, not touching!
	 * 
	 * @return the shared elements collection
	 */
	public Collection<MMenuElement> getSharedElements() {
		return sharedElements;
	}

	/**
	 * Access to analyze for tests. For Looking, not touching!
	 * 
	 * @return the generated elements collection
	 */
	public Collection<MMenuElement> getGeneratedElements() {
		return generatedElements;
	}

	/**
	 * @param context
	 */
	public void updateVisibility(IEclipseContext context) {
		ExpressionContext exprContext = new ExpressionContext(context);
		updateIsVisible(exprContext);
		HashSet<ContributionRecord> recentlyUpdated = new HashSet<ContributionRecord>();
		recentlyUpdated.add(this);
		for (MMenuElement item : generatedElements) {
			boolean currentVisibility = computeVisibility(recentlyUpdated,
					item, exprContext);
			if (item.isVisible() != currentVisibility) {
				item.setVisible(currentVisibility);
			}
		}
		for (MMenuElement item : sharedElements) {
			boolean currentVisibility = computeVisibility(recentlyUpdated,
					item, exprContext);
			if (item.isVisible() != currentVisibility) {
				item.setVisible(currentVisibility);
			}
		}
	}

	public void collectInfo(ExpressionInfo info) {
		ContributionsAnalyzer.collectInfo(info,
				menuContribution.getVisibleWhen());
		for (MMenuElement item : generatedElements) {
			ContributionsAnalyzer.collectInfo(info, item.getVisibleWhen());
		}
		for (MMenuElement item : sharedElements) {
			ContributionsAnalyzer.collectInfo(info, item.getVisibleWhen());
		}
	}

	public void updateIsVisible(ExpressionContext exprContext) {
		isVisible = ContributionsAnalyzer.isVisible(menuContribution,
				exprContext);
	}

	public boolean computeVisibility(
			HashSet<ContributionRecord> recentlyUpdated, MMenuElement item,
			ExpressionContext exprContext) {
		boolean currentVisibility = isVisible;
		if (item instanceof MMenu || item instanceof MMenuSeparator) {
			ArrayList<ContributionRecord> list = renderer.getList(item);
			if (list != null) {
				Iterator<ContributionRecord> cr = list.iterator();
				while (!currentVisibility && cr.hasNext()) {
					ContributionRecord rec = cr.next();
					if (!recentlyUpdated.contains(rec)) {
						rec.updateIsVisible(exprContext);
						recentlyUpdated.add(rec);
					}
					currentVisibility |= rec.isVisible;
				}
			}
		}
		if (currentVisibility
				&& item.getVisibleWhen() instanceof MCoreExpression) {
			boolean val = ContributionsAnalyzer.isVisible(
					(MCoreExpression) item.getVisibleWhen(), exprContext);
			currentVisibility = val;
		}
		return currentVisibility;
	}

	private Expression getExpression(MExpression expression) {
		if (expression instanceof MCoreExpression) {
			Object coreExpression = ((MCoreExpression) expression)
					.getCoreExpression();
			return coreExpression instanceof Expression ? (Expression) coreExpression
					: null;
		}
		return null;
	}

	private MExpression merge(MExpression expressionA, MExpression expressionB) {
		Expression coreExpressionA = getExpression(expressionA);
		Expression coreExpressionB = getExpression(expressionB);
		if (coreExpressionA == null || coreExpressionB == null) {
			// implied to always be visible
			return null;
		}

		// combine the two expressions
		OrExpression expression = new OrExpression();
		expression.add(coreExpressionA);
		expression.add(coreExpressionB);

		MCoreExpression exp = UiFactoryImpl.eINSTANCE.createCoreExpression();
		exp.setCoreExpressionId("programmatic.value"); //$NON-NLS-1$
		exp.setCoreExpression(expression);
		return exp;
	}

	public boolean mergeIntoModel() {
		int idx = getIndex(menuModel, menuContribution.getPositionInParent());
		if (idx == -1) {
			return false;
		}

		final List<MMenuElement> copyElements;
		if (menuContribution.getTransientData().get(FACTORY) != null) {
			copyElements = mergeFactoryIntoModel();
		} else {
			copyElements = new ArrayList<MMenuElement>();
			for (MMenuElement item : menuContribution.getChildren()) {
				MMenuElement copy = (MMenuElement) EcoreUtil
						.copy((EObject) item);
				copyElements.add(copy);
			}
		}

		for (MMenuElement copy : copyElements) {
			if (copy instanceof MMenu) {
				MMenu shared = findExistingMenu(copy.getElementId());
				if (shared == null) {
					shared = (MMenu) copy;
					menuModel.getChildren().add(idx++, copy);
				} else {
					shared.setVisibleWhen(merge(
							menuContribution.getVisibleWhen(),
							shared.getVisibleWhen()));
					copy = shared;
				}
				sharedElements.add(shared);
			} else if (copy instanceof MMenuSeparator) {
				MMenuSeparator shared = findExistingSeparator(copy
						.getElementId());
				if (shared == null) {
					shared = (MMenuSeparator) copy;
					menuModel.getChildren().add(idx++, copy);
				} else {
					copy = shared;
				}
				sharedElements.add(shared);
			} else {
				generatedElements.add(copy);
				menuModel.getChildren().add(idx++, copy);
			}
			if (copy instanceof MMenu || copy instanceof MMenuSeparator) {
				ArrayList<ContributionRecord> array = renderer.getList(copy);
				array.add(this);
			}
		}
		return true;
	}

	/**
	 * @return
	 */
	private List<MMenuElement> mergeFactoryIntoModel() {
		Object obj = menuContribution.getTransientData().get(FACTORY);
		if (!(obj instanceof IContextFunction)) {
			return Collections.EMPTY_LIST;
		}
		IEclipseContext staticContext = getStaticContext();
		staticContext.remove(List.class);
		factoryDispose = (Runnable) ((IContextFunction) obj)
				.compute(staticContext, null);
		return staticContext.get(List.class);
	}

	private IEclipseContext getStaticContext() {
		if (infoContext == null) {
			IEclipseContext parentContext = renderer.getContext(menuModel);
			if (parentContext != null) {
				infoContext = parentContext.createChild(STATIC_CONTEXT);
			} else {
				infoContext = EclipseContextFactory.create(STATIC_CONTEXT);
			}
			ContributionsAnalyzer.populateModelInterfaces(menuModel,
					infoContext, menuModel.getClass().getInterfaces());
			infoContext.set(MenuContributionProcessor.class, renderer);
		}
		return infoContext;
	}

	MMenu findExistingMenu(String id) {
		if (id == null) {
			return null;
		}
		for (MMenuElement item : menuModel.getChildren()) {
			if (item instanceof MMenu && id.equals(item.getElementId())) {
				return (MMenu) item;
			}
		}
		return null;
	}

	MMenuSeparator findExistingSeparator(String id) {
		if (id == null) {
			return null;
		}
		for (MMenuElement item : menuModel.getChildren()) {
			if (item instanceof MMenuSeparator
					&& id.equals(item.getElementId())) {
				return (MMenuSeparator) item;
			}
		}
		return null;
	}

	public void dispose() {
		for (MMenuElement copy : generatedElements) {
			menuModel.getChildren().remove(copy);
		}
		for (MMenuElement shared : sharedElements) {
			ArrayList<ContributionRecord> array = renderer.getList(shared);
			array.remove(this);
			if (array.isEmpty()) {
				menuModel.getChildren().remove(shared);
			}
		}
		if (factoryDispose != null) {
			factoryDispose.run();
			factoryDispose = null;
		}
	}

	private static int getIndex(MElementContainer<?> menuModel,
			String positionInParent) {
		String id = null;
		String modifier = null;
		if (positionInParent != null && positionInParent.length() > 0) {
			String[] array = positionInParent.split("="); //$NON-NLS-1$
			modifier = array[0];
			id = array[1];
		}
		if (id == null) {
			return menuModel.getChildren().size();
		}

		int idx = 0;
		int size = menuModel.getChildren().size();
		while (idx < size) {
			if (id.equals(menuModel.getChildren().get(idx).getElementId())) {
				if ("after".equals(modifier)) { //$NON-NLS-1$
					idx++;
				} else if ("endof".equals(modifier)) { //$NON-NLS-1$
					// Skip current menu item
					idx++;

					// Skip all menu items until next MenuSeparator is found
					while (idx < size
							&& !(menuModel.getChildren().get(idx) instanceof MMenuSeparator && menuModel
									.getChildren().get(idx).getElementId() != null)) {
						idx++;
					}
				}
				return idx;
			}
			idx++;
		}
		return id.equals("additions") ? menuModel.getChildren().size() : -1; //$NON-NLS-1$
	}
}