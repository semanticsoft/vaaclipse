/**
 * 
 */
package org.semanticsoft.vaaclipse.additions.view;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;

import com.vaadin.ui.Window;

/**
 * @author rushan
 *
 */
public class ShowViewHandler
{
	public static final String VIEWS_SHOW_VIEW_PARM_ID = "org.eclipse.ui.views.showView.viewId"; //$NON-NLS-1$

	@Execute
	public void execute(MWindow window,
			MApplication application, EPartService partService,
			IEclipseContext context,
			@Optional @Named(VIEWS_SHOW_VIEW_PARM_ID) String viewId) {
		if (viewId != null) {
			partService.showPart(viewId, PartState.ACTIVATE);
			return;
		}
		
		OptionDialog dlg = new OptionDialog();
		dlg.setWidth("350px");
		dlg.setHeight("500px");
		dlg.setModal(true);
		dlg.setCaption("Show View");
		ShowViewDialogContent componentProvider = ContextInjectionFactory.make(ShowViewDialogContent.class, context);
		dlg.setComponentProvider(componentProvider);
		
		dlg.addOption(0, "OK");
		dlg.addOption(1, "Cancel");
		
		final Window vaadinWindow = (Window) window.getWidget();
		vaadinWindow.addWindow(dlg);
	}
}
