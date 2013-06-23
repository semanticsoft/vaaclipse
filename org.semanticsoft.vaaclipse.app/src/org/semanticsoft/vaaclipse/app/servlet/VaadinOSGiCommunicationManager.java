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

public class VaadinOSGiCommunicationManager extends LegacyCommunicationManager
		implements VaadinExecutorService {
	private Queue<Runnable> runnables = new LinkedList<Runnable>();
	private Set<Object> keys = new HashSet<Object>();
	private Map<Runnable, Object> runnable2Key = new HashMap<Runnable, Object>();
	private Queue<Runnable> runnables2 = new LinkedList<Runnable>();

	public VaadinOSGiCommunicationManager(VaadinSession session) {
		super(session);
	}

	private void updateThreadLocals() {
		for (UI ui : getSession().getUIs()) {
			if (ui instanceof VaadinUI) {
				VaadinUI vaaUI = (VaadinUI) ui;
				ThreadLocals.setRootContext(vaaUI.getRootContext());
				break;
			}
		}
	}

	public synchronized void exec() {
		Runnable runnable;
		while ((runnable = runnables.poll()) != null) {
			try {
				runnable.run();
				Object key = runnable2Key.remove(runnable);
				keys.remove(key);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		for (Runnable runnable2 : runnables2) {
			try {
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

	@SuppressWarnings("unchecked")
	private List<MethodInvocation> _parseInvocations(
			ConnectorTracker connectorTracker, final String burst)
			throws Exception {
		@SuppressWarnings("deprecation")
		Method method = LegacyCommunicationManager.class.getDeclaredMethod(
				"parseInvocations", ConnectorTracker.class, String.class);
		method.setAccessible(true);
		return (List<MethodInvocation>) method.invoke(this, connectorTracker,
				burst);
	}

	public void handleConnectorRelatedException(ClientConnector connector,
			Throwable throwable) {
		ErrorEvent errorEvent = new ConnectorErrorEvent(connector, throwable);
		ErrorHandler handler = ErrorEvent.findErrorHandler(connector);
		handler.error(errorEvent);
	}

	public boolean handleBurst(VaadinRequest source, UI uI, final String burst) {

		updateThreadLocals();

		boolean success = true;
		try {
			Set<Connector> enabledConnectors = new HashSet<Connector>();

			List<MethodInvocation> invocations = _parseInvocations(
					uI.getConnectorTracker(), burst);
			for (MethodInvocation invocation : invocations) {
				final ClientConnector connector = getConnector(uI,
						invocation.getConnectorId());

				if (connector != null && connector.isConnectorEnabled()) {
					enabledConnectors.add(connector);
				}
			}

			for (int i = 0; i < invocations.size(); i++) {
				MethodInvocation invocation = invocations.get(i);

				final ClientConnector connector = getConnector(uI,
						invocation.getConnectorId());

				if (!enabledConnectors.contains(connector)) {

					if (invocation instanceof LegacyChangeVariablesInvocation) {
						LegacyChangeVariablesInvocation legacyInvocation = (LegacyChangeVariablesInvocation) invocation;
						// TODO convert window close to a separate RPC call and
						// handle above - not a variable change

						// Handle special case where window-close is called
						// after the window has been removed from the
						// application or the application has closed
						Map<String, Object> changes = legacyInvocation
								.getVariableChanges();
						if (changes.size() == 1 && changes.containsKey("close")
								&& Boolean.TRUE.equals(changes.get("close"))) {
							// Silently ignore this
							continue;
						}
					}

					// Connector is disabled, log a warning and move to the next
					String msg = "Ignoring RPC call for disabled connector "
							+ connector.getClass().getName();
					if (connector instanceof Component) {
						String caption = ((Component) connector).getCaption();
						if (caption != null) {
							msg += ", caption=" + caption;
						}
					}
					// getLogger().warning(msg);
					continue;
				}

				if (invocation instanceof ServerRpcMethodInvocation) {
					try {
						ServerRpcManager.applyInvocation(connector,
								(ServerRpcMethodInvocation) invocation);
						exec();
					} catch (RpcInvocationException e) {
						handleConnectorRelatedException(connector, e);
					}
				} else {

					// All code below is for legacy variable changes
					LegacyChangeVariablesInvocation legacyInvocation = (LegacyChangeVariablesInvocation) invocation;
					Map<String, Object> changes = legacyInvocation
							.getVariableChanges();
					try {
						if (connector instanceof VariableOwner) {
							// TODO fp
//							changeVariables(source, (VariableOwner) connector,
//									changes);
							exec();
						} else {
							throw new IllegalStateException(
									"Received legacy variable change for "
											+ connector.getClass().getName()
											+ " ("
											+ connector.getConnectorId()
											+ ") which is not a VariableOwner. The client-side connector sent these legacy varaibles: "
											+ changes.keySet());
						}
					} catch (Exception e) {
						handleConnectorRelatedException(connector, e);
					}
				}
			}
		} catch (Exception e) {
			// getLogger().warning(
			// "Unable to parse RPC call from the client: "
			// + e.getMessage());
			// TODO or return success = false?
			throw new RuntimeException(e);
		}

		return success;
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
