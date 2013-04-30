/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.renderers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.commands.MParameter;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.vaaclipse.api.VaadinExecutorService;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;


@SuppressWarnings("restriction")
public abstract class ItemRenderer extends VaadinRenderer {
	
	@Inject
	EModelService modelService;
	
	@Inject
	protected VaadinExecutorService executorService;
	
	protected Map<MItem, Runnable> enabledUpdaters = new HashMap<MItem, Runnable>();
	
	protected void registerEnablementUpdaters(final MItem item)
	{
		if (!enabledUpdaters.containsKey(item))
		{
			Runnable runnable = new Runnable() {
				
				@Override
				public void run()
				{
					updateItemEnablement(item);
				}
			};
			this.enabledUpdaters.put(item, runnable);
			executorService.invokeLaterAlways(runnable);
		}
	}
	
	protected abstract void updateItemEnablement(MItem item);
	
	protected boolean canExecuteItem(MHandledItem item) {
		final IEclipseContext eclipseContext = getContext(item);
		if (eclipseContext == null) //item is not in hierarchy
			return false;
		EHandlerService service = (EHandlerService) eclipseContext.get(EHandlerService.class.getName());
		if (service == null)
			return false;
		ParameterizedCommand command = item.getWbCommand();
		if (command == null) {
			command = generateParameterizedCommand(item, eclipseContext);
		}
		if (command == null) {
			return false;
		}
		eclipseContext.set(MItem.class, item);
		setupContext(eclipseContext, item);
		return service.canExecute(command, eclipseContext);
	}
	
	@Override
	public void disposeWidget(MUIElement element)
	{
		unregisterEnabledUpdater(element);
	}

	private void unregisterEnabledUpdater(MUIElement element)
	{
		Runnable runnable = enabledUpdaters.remove(element);
		if (runnable != null)
		{
			executorService.removeAlwaysRunnable(runnable);	
		}
	}
	
	protected String prepareText(MMenuItem model)
	{
		String text = model.getLocalizedLabel();
		if (model instanceof MHandledItem) {
			MHandledItem handledItem = (MHandledItem) model;
			generateParameterizedCommand(handledItem, context);
			ParameterizedCommand cmd = handledItem.getWbCommand();
			if (cmd != null && (text == null || text.length() == 0)) {
				try {
					text = cmd.getName();
				} catch (NotDefinedException e) {
					e.printStackTrace();
				}
			}
		}
		if (text == null)
			text = "Blank";
		return text;
	}
	
	protected ParameterizedCommand generateParameterizedCommand(final MHandledItem item,
			final IEclipseContext lclContext) {
		ECommandService cmdService = (ECommandService) lclContext.get(ECommandService.class.getName());
		Map<String, Object> parameters = null;
		List<MParameter> modelParms = item.getParameters();
		if (modelParms != null && !modelParms.isEmpty()) {
			parameters = new HashMap<String, Object>();
			for (MParameter mParm : modelParms) {
				parameters.put(mParm.getName(), mParm.getValue());
			}
		}
		if (item.getCommand() != null) {
			ParameterizedCommand cmd = cmdService.createCommand(item
					.getCommand().getElementId(), parameters);
			item.setWbCommand(cmd);

			return cmd;
		} else
			return null;
	}

	protected Command createParametrizedCommandEventHandler(final MHandledItem item) {
		return new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem)
			{
				processParametrizedAction(item);
			}
		};
	}

	protected Command createEventHandler(final MItem item) {
		return new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem)
			{
				processAction(item);
			}
		};
	}
	
	protected void processParametrizedAction(final MHandledItem item)
	{
		final IEclipseContext eclipseContext = getContext(item);
		EHandlerService service = (EHandlerService) eclipseContext.get(EHandlerService.class.getName());
		ParameterizedCommand command = item.getWbCommand();
		if (command == null) {
			command = generateParameterizedCommand(item, eclipseContext);
		}
		if (command == null) {
			System.err.println("Failed to execute: " + item.getCommand());
			return;
		}
		eclipseContext.set(MItem.class, item);
		setupContext(eclipseContext, item);
		service.executeHandler(command);
		eclipseContext.remove(MItem.class.getName());
	}
	
	protected void processAction(final MItem item)
	{
		final IEclipseContext eclipseContext = getContext(item);
		eclipseContext.set(MItem.class, item);
		setupContext(eclipseContext, item);
		if (item instanceof MDirectToolItem) {
			Object toolItem = ((MDirectToolItem) item).getObject();
			if ((Boolean) ContextInjectionFactory.invoke(toolItem, CanExecute.class, eclipseContext, true))
				ContextInjectionFactory.invoke(toolItem, Execute.class, eclipseContext);
		} else if (item instanceof MDirectMenuItem) {
			Object menuItem = ((MDirectMenuItem) item).getObject();
			if ((Boolean) ContextInjectionFactory.invoke(menuItem, CanExecute.class, eclipseContext, true))
				ContextInjectionFactory.invoke(menuItem, Execute.class, eclipseContext);
		}
		eclipseContext.remove(MItem.class);
	}
	
	protected void setupContext(IEclipseContext context, MItem item)
	{
		MWindow window = modelService.getTopLevelWindowFor(item);
		context.set(MWindow.class, window);
	}
}
