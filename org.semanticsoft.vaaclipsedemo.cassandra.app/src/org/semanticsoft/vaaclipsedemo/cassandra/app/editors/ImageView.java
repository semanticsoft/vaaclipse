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

package org.semanticsoft.vaaclipsedemo.cassandra.app.editors;

import com.vaadin.Application;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;

public class ImageView extends FileView
{
	private Embedded embedded;

	@Inject
	public ImageView(VerticalLayout container, MInputPart inputPart, Application app)
	{
		super(inputPart.getInputURI());
		embedded = new Embedded();
		container.addComponent(embedded);
		container.setComponentAlignment(embedded, Alignment.MIDDLE_CENTER);
		
		embedded.setSource(new FileResource(getFile(), app));
	}

}
