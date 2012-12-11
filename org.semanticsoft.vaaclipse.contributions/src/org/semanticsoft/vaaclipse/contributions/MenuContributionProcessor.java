/**
 * 
 */
package org.semanticsoft.vaaclipse.contributions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuSeparator;
import org.eclipse.e4.ui.model.application.ui.menu.MPopupMenu;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.vaaclipse.api.MenuContributionService;

/**
 * @author rushan
 *
 */
public class MenuContributionProcessor implements MenuContributionService
{
	@Inject
	MApplication application;
	@Inject
	EModelService modelService;
	
	private Map<MMenuElement, ArrayList<ContributionRecord>> sharedElementToRecord = new HashMap<MMenuElement, ArrayList<ContributionRecord>>();
	
	@Execute
	public void start(IEclipseContext context)
	{
		context.set(MenuContributionService.class, this);
	}
	
	@Override
	public void addContributions(MMenu menu)
	{
		processContributions(menu, menu instanceof MPopupMenu);
	}
	
	/**
	 * @param menuModel
	 * @param isMenuBar
	 * @param isPopup
	 */
	private void processContributions(MMenu menuModel, boolean isPopup) {
		if (menuModel.getElementId() == null) {
			return;
		}
		final ArrayList<MMenuContribution> toContribute = new ArrayList<MMenuContribution>();
		ContributionsAnalyzer.XXXgatherMenuContributions(menuModel,
				application.getMenuContributions(), menuModel.getElementId(),
				toContribute, null, isPopup);
		generateContributions(menuModel, toContribute);
//		for (MMenuElement element : menuModel.getChildren()) {
//			if (element instanceof MMenu) {
//				processContributions((MMenu) element, isPopup);
//			}
//		}
	}

	/**
	 * @param menuModel
	 * @param toContribute
	 */
	private void generateContributions(MMenu menuModel, ArrayList<MMenuContribution> toContribute) {
		HashSet<String> existingMenuIds = new HashSet<String>();
		HashSet<String> existingSeparatorNames = new HashSet<String>();
		for (MMenuElement child : menuModel.getChildren()) {
			String elementId = child.getElementId();
			if (child instanceof MMenu && elementId != null) {
				existingMenuIds.add(elementId);
			} else if (child instanceof MMenuSeparator && elementId != null) {
				existingSeparatorNames.add(elementId);
			}
		}

		boolean done = toContribute.size() == 0;
		while (!done) {
			ArrayList<MMenuContribution> curList = new ArrayList<MMenuContribution>(
					toContribute);
			int retryCount = toContribute.size();
			toContribute.clear();

			for (MMenuContribution menuContribution : curList) {
				if (!processAddition(menuModel, menuContribution,
						existingMenuIds, existingSeparatorNames)) {
					toContribute.add(menuContribution);
				}
			}

			// We're done if the retryList is now empty (everything done) or
			// if the list hasn't changed at all (no hope)
			done = (toContribute.size() == 0)
					|| (toContribute.size() == retryCount);
		}
	}

	/**
	 * @param menuModel
	 * @param manager
	 * @param menuContribution
	 * @return true if the menuContribution was processed
	 */
	private boolean processAddition(MMenu menuModel,
			MMenuContribution menuContribution,
			final HashSet<String> existingMenuIds,
			HashSet<String> existingSeparatorNames) {
		final ContributionRecord record = new ContributionRecord(menuModel,
				menuContribution, this);
		if (!record.mergeIntoModel()) {
			return false;
		}
		
		final IEclipseContext parentContext = modelService
				.getContainingContext(menuModel);
		parentContext.runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(IEclipseContext context) {
				record.updateVisibility(parentContext.getActiveLeaf());
				return true;
			}
		});
		
		return true;
	}
	
	ArrayList<ContributionRecord> getList(MMenuElement item) {
		ArrayList<ContributionRecord> tmp = sharedElementToRecord.get(item);
		if (tmp == null) {
			tmp = new ArrayList<ContributionRecord>();
			sharedElementToRecord.put(item, tmp);
		}
		return tmp;
	}
	
	public void removeContributions(MMenu menu)
	{
		
	}

	IEclipseContext getContext(MUIElement element) {
		if (element instanceof MContext) {
			return ((MContext) element).getContext();
		}
		return modelService.getContainingContext(element);
	}
}
