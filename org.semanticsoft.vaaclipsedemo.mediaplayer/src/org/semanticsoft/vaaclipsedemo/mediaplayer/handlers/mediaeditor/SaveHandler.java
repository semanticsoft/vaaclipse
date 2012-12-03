/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.mediaeditor;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

/**
 * @author rushan
 * 
 */
public class SaveHandler
{
	@CanExecute
	boolean canExecute(@Named(IServiceConstants.ACTIVE_PART) MDirtyable dirtyable)
	{
		return dirtyable == null ? false : dirtyable.isDirty();
	}

	@Execute
	void execute(EPartService partService, @Named(IServiceConstants.ACTIVE_PART) MPart part)
	{
		partService.savePart(part, false);
	}
}
