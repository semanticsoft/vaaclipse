package org.semanticsoft.vaaclipse.theme;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
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
