/*******************************************************************************
 * Copyright (c) 2011 Rushan R. Gilmullin and others.
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

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.commands.MParameter;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;


@SuppressWarnings("restriction")
public abstract class ItemRenderer extends GenericRenderer {
	
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
		ParameterizedCommand cmd = cmdService.createCommand(item.getCommand().getElementId(), parameters);
		item.setWbCommand(cmd);
		return cmd;
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
		eclipseContext.set(MItem.class.getName(), item);
		service.executeHandler(command);
		eclipseContext.remove(MItem.class.getName());
	}
	
	protected void processAction(final MItem item)
	{
		final IEclipseContext eclipseContext = getContext(item);
		eclipseContext.set(MItem.class, item);
		if (item instanceof MDirectToolItem) {
			ContextInjectionFactory.invoke(((MDirectToolItem) item).getObject(), Execute.class, eclipseContext);
		} else if (item instanceof MDirectMenuItem) {
			ContextInjectionFactory.invoke(((MDirectMenuItem) item).getObject(), Execute.class, eclipseContext);
		}
		eclipseContext.remove(MItem.class);
	}
}
