/**
 * 
 */
package org.semanticsoft.e4extension;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.semanticsoft.e4extension.impl.PartServiceExtImpl;
import org.semanticsoft.e4extension.service.EPartServiceExt;

/**
 * @author rushan
 *
 */
public class StartServiceProcessor
{
	@Execute
	void addMinMaxAddon(MApplication application, IEclipseContext context) {
		
		PartServiceExtImpl partServiceExt = ContextInjectionFactory.make(PartServiceExtImpl.class, context);
		context.set(EPartServiceExt.class, partServiceExt);
	}
}
