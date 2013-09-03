package org.semanticsoft.vaaclipse.p2.sites.ui;
/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Klevis Ramo - initial API and implementation
 *******************************************************************************/
public interface ISitesView {

	
	Object getUIComponent();

	void initUI();

	String errorMessage();

	boolean validate();	
}
