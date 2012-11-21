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

package org.semanticsoft.vaaclipsedemo.cassandra.app.handlers;

import org.semanticsoft.vaaclipse.app.VaadinE4Application;

import com.vaadin.Application;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

public class SaveHandler {

	@CanExecute
	public boolean canExecute() {
		return true;
	}
	@Execute
	public void execute(IEclipseContext context, Application vaadinapp)
			throws InvocationTargetException, InterruptedException {
		vaadinapp.getMainWindow().showNotification("Save");

		if (VaadinE4Application.getInstance().getUserVaadinTheme()
				.equals("them1")
				|| VaadinE4Application.getInstance().equals(
						"vaaclipse_user_theme")){

			VaadinE4Application.getInstance().setUserVaadinTheme("them2");
			System.out.println("Setting them2 ......");
		}
		else {
			VaadinE4Application.getInstance().setUserVaadinTheme("them1");
			System.out.println("Setting them1 .......");

		}
	}

}
