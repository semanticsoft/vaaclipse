/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.mediaeditor;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

/**
 * @author rushan
 * 
 */
public class SaveAllHandler
{
	@CanExecute
	boolean canExecute(@Optional MWindow window)
	{
		if (window != null)
		{
			IEclipseContext context = window.getContext();
			if (context != null)
			{
				EPartService partService = context.get(EPartService.class);
				if (partService != null)
				{
					return !partService.getDirtyParts().isEmpty();
				}
			}
		}
		return false;
	}

	@Execute
	void execute(EPartService partService)
	{
		partService.saveAll(false);
	}

}
