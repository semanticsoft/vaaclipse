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

import javax.annotation.PreDestroy;

import com.vaadin.ui.Panel;

import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.vaadin.codelabel.CodeLabel;

/**
 * @author rushan
 *
 */
public class JavaEditor extends TextEditor
{
	@Inject
	public JavaEditor(VerticalLayout container, MInputPart inputPart)
	{
		super(inputPart.getInputURI());
		
		Panel e = new Panel();
		e.setSizeFull();
		((VerticalLayout)e.getContent()).setMargin(true);
		e.setScrollable(true);
		text = new CodeLabel(readContent());
		e.addComponent(text);
		container.addComponent(e);
	}
	
	@PreDestroy
	public void preDestory()
	{
		System.out.println("predestroy");
	}
}
