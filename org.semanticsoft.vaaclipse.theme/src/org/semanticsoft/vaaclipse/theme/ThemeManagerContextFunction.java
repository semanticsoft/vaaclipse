/**
 * 
 */
package org.semanticsoft.vaaclipse.theme;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeEngine;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeManager;

/**
 * @author rushan
 *
 */
public class ThemeManagerContextFunction extends ContextFunction
{
	private ThemeEngine themeEngine;
	
	public void activate()
	{
		System.out.println("Theme manager factory is started");
	}
	
	@Override
	public Object compute(IEclipseContext context) {
		ThemeManager manager = context.getLocal(ThemeManager.class);
		if (manager == null) {
			if (themeEngine != null)
				context.set(ThemeEngine.class, themeEngine);
            manager = ContextInjectionFactory.make(ThemeManagerImpl.class, context);
            context.set(ThemeManager.class, manager);
		}
		return manager;
	}
	
	public void bindThemeEngine(ThemeEngine themeEngine)
	{
		this.themeEngine = themeEngine;
	}
}
