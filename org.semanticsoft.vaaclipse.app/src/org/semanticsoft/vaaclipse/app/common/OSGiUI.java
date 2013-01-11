/*******************************************************************************
 * Copyright (c) 2012 by committers of lunifera.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Florian Pirchner - initial API and implementation
 *    
 *******************************************************************************/
package org.semanticsoft.vaaclipse.app.common;

import org.osgi.service.component.ComponentInstance;

import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public abstract class OSGiUI extends UI implements SessionDestroyListener {

	private ComponentInstance instance;

	/**
	 * Sets the component instance that can be used to dispose the instance of
	 * that UI
	 * 
	 * @param instance
	 */
	public void setComponentInstance(ComponentInstance instance) {
		if (this.instance != null) {
			throw new IllegalArgumentException(
					"Component instance may only be set onece!");
		}
		this.instance = instance;
	}

	@Override
	public void attach() {
		super.attach();

		getSession().getService().addSessionDestroyListener(this);
	}

	public void sessionDestroy(SessionDestroyEvent event) {
		dispose();
	}

	@Override
	public void detach() {
		getSession().getService().removeSessionDestroyListener(this);

		super.detach();

		dispose();
	}

	/**
	 * Is called to remove the instance as an OSGi service and to cleanup the
	 * OSGi runtime.
	 */
	protected void dispose() {
		if (instance != null) {
			instance.dispose();
			instance = null;
		}
	}

}
