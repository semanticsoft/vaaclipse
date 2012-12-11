/**
 * 
 */
package org.semanticsoft.vaaclipse.api;

import org.eclipse.e4.ui.model.application.ui.menu.MMenu;

/**
 * @author rushan
 *
 */
public interface MenuContributionService
{
	void addContributions(MMenu menu);
	void removeContributions(MMenu menu);
}
