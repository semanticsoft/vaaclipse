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

package org.semanticsoft.vaaclipse.app.servlet;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.semanticsoft.vaaclipse.api.VaadinExecutorService;
import org.semanticsoft.vaaclipse.app.webapp.VaadinUI;
import org.semanticsoft.vaaclipse.publicapi.app.ThreadLocals;

import com.vaadin.server.ClientConnector;
import com.vaadin.server.ClientConnector.ConnectorErrorEvent;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.LegacyCommunicationManager;
import com.vaadin.server.ServerRpcManager;
import com.vaadin.server.ServerRpcManager.RpcInvocationException;
import com.vaadin.server.ServerRpcMethodInvocation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.VariableOwner;
import com.vaadin.shared.Connector;
import com.vaadin.shared.communication.LegacyChangeVariablesInvocation;
import com.vaadin.shared.communication.MethodInvocation;
import com.vaadin.ui.Component;
import com.vaadin.ui.ConnectorTracker;
import com.vaadin.ui.UI;

public class VaadinExecutorServiceImpl implements VaadinExecutorService {
	private Queue<Runnable> runnables = new LinkedList<Runnable>();
	private Set<Object> keys = new HashSet<Object>();
	private Map<Runnable, Object> runnable2Key = new HashMap<Runnable, Object>();
	private Queue<Runnable> runnables2 = new LinkedList<Runnable>();

//	void updateThreadLocals() {
//		for (UI ui : getSession().getUIs()) {
//			if (ui instanceof VaadinUI) {
//				VaadinUI vaaUI = (VaadinUI) ui;
//				ThreadLocals.setRootContext(vaaUI.getRootContext());
//				break;
//			}
//		}
//	}

	public synchronized void exec() {
		//System.out.println("exec called!");
		Runnable runnable;
		while ((runnable = runnables.poll()) != null) {
			try {
				//System.out.println("Runnable 1");
				runnable.run();
				Object key = runnable2Key.remove(runnable);
				keys.remove(key);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		for (Runnable runnable2 : runnables2) {
			try {
				//System.out.println("Runnable 2");
				runnable2.run();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		// clean runnables that may added during runnables2 execution
		runnables.clear();
		runnable2Key.clear();
		keys.clear();
	}

	public synchronized void invokeLater(Runnable runnable) {
		this.runnables.add(runnable);
	}

	@Override
	public void invokeLater(Object key, Runnable runnable) {
		if (!this.keys.contains(key)) {
			this.keys.add(key);
			this.runnable2Key.put(runnable, key);
			this.runnables.add(runnable);
		}
	}

	@Override
	public boolean containsKey(Object key) {
		return this.keys.contains(key);
	}

	public synchronized void invokeLaterAlways(Runnable runnable) {
		this.runnables2.add(runnable);
	}

	@Override
	public synchronized void removeAlwaysRunnable(Runnable runnable) {
		this.runnables2.remove(runnable);
	}

	@Override
	public void removeAllAlwaysRunnables() {
		runnables2.clear();
	}

	@Override
	public void removeAllInvokeLater() {
		this.runnables.clear();
		this.keys.clear();
		this.runnable2Key.clear();
	}
}
