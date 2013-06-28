/**
 * 
 */
package org.semanticsoft.vaaclipse.app.servlet;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.semanticsoft.vaaclipse.api.VaadinExecutorService;

import com.vaadin.server.ClientConnector;
import com.vaadin.server.LegacyCommunicationManager;
import com.vaadin.server.ServerRpcManager;
import com.vaadin.server.ServerRpcMethodInvocation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VariableOwner;
import com.vaadin.server.LegacyCommunicationManager.InvalidUIDLSecurityKeyException;
import com.vaadin.server.ServerRpcManager.RpcInvocationException;
import com.vaadin.server.communication.ServerRpcHandler;
import com.vaadin.shared.Connector;
import com.vaadin.shared.communication.LegacyChangeVariablesInvocation;
import com.vaadin.shared.communication.MethodInvocation;
import com.vaadin.ui.Component;
import com.vaadin.ui.ConnectorTracker;
import com.vaadin.ui.UI;

/**
 * @author rushan
 *
 */
public class VaaclipseServerRpcHandler extends ServerRpcHandler 
{
	private VaadinExecutorServiceImpl executorService = new VaadinExecutorServiceImpl();
	
	public VaadinExecutorService getExecutorService() {
		return executorService;
	}
	
	@SuppressWarnings("unchecked")
	private List<MethodInvocation> _parseInvocations(ConnectorTracker connectorTracker, final String burst)throws Exception {
		@SuppressWarnings("deprecation")
		Method method = ServerRpcHandler.class.getDeclaredMethod(
				"parseInvocations", ConnectorTracker.class, String.class);
		method.setAccessible(true);
		return (List<MethodInvocation>) method.invoke(this, connectorTracker,
				burst);
	}
	
	public void handleRpc(UI ui, Reader reader, VaadinRequest request)
            throws IOException, InvalidUIDLSecurityKeyException, JSONException {
        ui.getSession().setLastRequestTimestamp(System.currentTimeMillis());

        String changes = getMessage(reader);

        final String[] bursts = changes.split(String
                .valueOf(VAR_BURST_SEPARATOR));

        if (bursts.length > 2) {
            throw new RuntimeException(
                    "Multiple variable bursts not supported in Vaadin 7");
        } else if (bursts.length <= 1) {
            // The client sometimes sends empty messages, this is probably a bug
            return;
        }

        // Security: double cookie submission pattern unless disabled by
        // property
        if (!VaadinService.isCsrfTokenValid(ui.getSession(), bursts[0])) {
            throw new InvalidUIDLSecurityKeyException("");
        }
        handleBurst(ui, unescapeBurst(bursts[1]));
    }
	
	private void handleBurst(UI uI, String burst) {
        // TODO PUSH Refactor so that this is not needed
        LegacyCommunicationManager manager = uI.getSession()
                .getCommunicationManager();

        try {
            Set<Connector> enabledConnectors = new HashSet<Connector>();

            List<MethodInvocation> invocations = _parseInvocations(uI.getConnectorTracker(), burst);
            for (MethodInvocation invocation : invocations) {
                final ClientConnector connector = manager.getConnector(uI,
                        invocation.getConnectorId());

                if (connector != null && connector.isConnectorEnabled()) {
                    enabledConnectors.add(connector);
                }
            }

            for (int i = 0; i < invocations.size(); i++) {
                MethodInvocation invocation = invocations.get(i);

                final ClientConnector connector = manager.getConnector(uI,
                        invocation.getConnectorId());
                if (connector == null) {
                    getLogger()
                            .log(Level.WARNING,
                                    "Received RPC call for unknown connector with id {0} (tried to invoke {1}.{2})",
                                    new Object[] { invocation.getConnectorId(),
                                            invocation.getInterfaceName(),
                                            invocation.getMethodName() });
                    continue;
                }

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
                    getLogger().warning(msg);
                    continue;
                }
                // DragAndDropService has null UI
                if (connector.getUI() != null && connector.getUI().isClosing()) {
                    String msg = "Ignoring RPC call for connector "
                            + connector.getClass().getName();
                    if (connector instanceof Component) {
                        String caption = ((Component) connector).getCaption();
                        if (caption != null) {
                            msg += ", caption=" + caption;
                        }
                    }
                    msg += " in closed UI";
                    getLogger().warning(msg);
                    continue;

                }

                if (invocation instanceof ServerRpcMethodInvocation) {
                    try {
                        ServerRpcManager.applyInvocation(connector,
                                (ServerRpcMethodInvocation) invocation);
                        executorService.exec();
                    } catch (RpcInvocationException e) {
                        manager.handleConnectorRelatedException(connector, e);
                    }
                } else {

                    // All code below is for legacy variable changes
                    LegacyChangeVariablesInvocation legacyInvocation = (LegacyChangeVariablesInvocation) invocation;
                    Map<String, Object> changes = legacyInvocation
                            .getVariableChanges();
                    try {
                        if (connector instanceof VariableOwner) {
                            // The source parameter is never used anywhere
                            changeVariables(null, (VariableOwner) connector,
                                    changes);
                            executorService.exec();
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
                        manager.handleConnectorRelatedException(connector, e);
                    }
                }
            }
        } catch (Exception e) {
            getLogger().warning(
                    "Unable to parse RPC call from the client: "
                            + e.getMessage());
            throw new RuntimeException(e);
        }
    }
	
	private static final Logger getLogger() {
        return Logger.getLogger(ServerRpcHandler.class.getName());
    }
}
