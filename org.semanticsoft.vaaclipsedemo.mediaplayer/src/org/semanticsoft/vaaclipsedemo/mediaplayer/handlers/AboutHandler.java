/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationFactory;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MCommandsFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import com.vaadin.Application;

import e4modelextension.Dialog;
import e4modelextension.E4modelextensionFactory;

public class AboutHandler {
	@Execute
	public void execute(Application vaadinapp, MApplication app, MWindow win, EPartService partService) {
		
		Dialog dialog = E4modelextensionFactory.eINSTANCE.createDialog();
		dialog.setDirty(true);
		dialog.setLabel("This is a dialog");
		MPart part = partService.createPart("dialog.pd");
		dialog.getChildren().add(part);
		dialog.setSelectedElement(part);
		dialog.setContributionURI("bundleclass://org.semanticsoft.vaaclipsedemo.mediaplayer/org.semanticsoft.vaaclipsedemo.mediaplayer.views.FooterLayout");
		MCommand command = MCommandsFactory.INSTANCE.createCommand();
		command.setCategory(null);
		command.setElementId("C1");
		command.setDescription("This is a thing");
		dialog.setCommand(command );
		win.getChildren().add(dialog);
		
	}
}
