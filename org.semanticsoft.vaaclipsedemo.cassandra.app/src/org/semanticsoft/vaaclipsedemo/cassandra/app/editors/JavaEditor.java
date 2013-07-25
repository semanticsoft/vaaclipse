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

import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;

/**
 * @author rushan
 *
 */
public class JavaEditor extends JHighlightEditor
{
//	@Inject
//	public JavaEditor(VerticalLayout container, MInputPart inputPart)
//	{
//		super(inputPart.getInputURI());
//		Panel e = new Panel();
//		e.setSizeFull();
//		//text = new CodeLabel(readContent());
//		container.addComponent(e);
//		
//		CodeLabel text = new CodeLabel(readContent());
//		e.setContent(text);
//	}

	@Inject
	public JavaEditor(VerticalLayout container, MInputPart inputPart)
	{
		super(container, inputPart);
	}

	@Override
	String getJHighlighTypeName()
	{
		return "java";
	}
}
