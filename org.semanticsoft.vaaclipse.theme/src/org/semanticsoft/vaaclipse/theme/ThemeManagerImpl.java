package org.semanticsoft.vaaclipse.theme;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.vaaclipse.publicapi.theme.Theme;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeConstants;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeEngine;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeManager;

/**
 * @author rushan
 *
 */
public class ThemeManagerImpl implements ThemeManager
{
	@Inject
	IEventBroker eventBroker;
	
	@Inject
	private ThemeEngine themeEngine;
	
	private String themeId;

	@Override
	public String getThemeId()
	{
		return this.themeId;
	}

	@Override
	public ThemeEngine getThemeEngine()
	{
		return this.themeEngine;
	}

	@Override
	public Theme getTheme()
	{
		return themeEngine.getTheme(themeId);
	}
	
	@Override
	public void setTheme(String themeId)
	{
		if (this.themeId != null && this.themeId.equals(themeId))
			return;
		
		Theme theme = themeEngine.getTheme(themeId);
		if (theme == null)
			throw new IllegalArgumentException(String.format("Theme with id=%s is not exists", themeId));
		this.themeId = themeId;
		eventBroker.send(ThemeConstants.Events.setThemeEvent, theme);
	}	
}
