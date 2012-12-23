/**
 * 
 */
package org.semanticsoft.vaaclipse.theme;

import org.semanticsoft.vaaclipse.publicapi.theme.ThemeContribution;

/**
 * @author rushan
 *
 */
public class ThemeContributionImpl extends ThemeEntryImpl implements ThemeContribution
{
	private String insertPosition = "after=MAIN_CSS";
	
	public ThemeContributionImpl(String id)
	{
		super(id);
	}
	
	@Override
	public String getInsertPosition()
	{
		return insertPosition;
	}
	
	public void setInsertPosition(String insertPosition)
	{
		this.insertPosition = insertPosition;
	}
}
