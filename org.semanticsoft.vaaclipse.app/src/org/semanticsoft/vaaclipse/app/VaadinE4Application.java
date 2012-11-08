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

package org.semanticsoft.vaaclipse.app;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.internal.workbench.WorkbenchLogger;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

@SuppressWarnings("restriction")
public class VaadinE4Application implements IApplication {
	
	private ArrayBlockingQueue<String> queue;
	private Logger logger = new WorkbenchLogger("org.semanticsoft.vaaclipse.app");
	
	private IApplicationContext appContext;
	
	private static VaadinE4Application instance;
	
	public static final String EXIT = "EXIT";
	
	JFrame frame;
	private String contextPath;
	private String themeName;
	private String productionMode;
	private String widgetset;
	
	public static VaadinE4Application getInstance()
	{
		return instance;
	}
	
	public Location getInstanceLocation()
	{
		 return Activator.getDefault().getInstanceLocation();
	}
	
	public IApplicationContext getAppContext()
	{
		return appContext;
	}
	
	public Logger getLogger()
	{
		return logger;
	}
	
	String getProp(IApplicationContext context, String propName, boolean exitOnNull)
	{
		String result = null;
		String val = context.getBrandingProperty(propName);
		if (val != null)
		{
			val = val.trim();
			if (!val.isEmpty())
				result = val;
		}
		
		if (exitOnNull && result == null)
		{
			JOptionPane.showMessageDialog(null, "Application start failed. Property " + propName + " is not specified.");
			shutdown(false);
		}
		
		return val;
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		instance = this;
		appContext = context;
		
		logger.debug("VaadinE4Application.start()");
		context.applicationRunning();
		
		queue = new ArrayBlockingQueue<>(10);
		
		startHttpService();
		showFrame();
		
		String msg;
		while (!(msg = queue.take()).equals(EXIT)) 
		{
	        System.out.println(msg);
		}
		
		frame.setVisible(false);
		frame.dispose();
		
		return EXIT_OK;
	}

	private void startHttpService()
	{
		contextPath = getProp(appContext, "contextPath", true);
		widgetset = getProp(appContext, "vaadinWidgetset", true);
		themeName = getProp(appContext, "vaadinTheme", true);
		productionMode = getProp(appContext, "vaadinProductionMode", false);
		
		final BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> httpServiceRef = bundleContext.getServiceReference("org.osgi.service.http.HttpService");
		if (httpServiceRef == null)
		{
			JOptionPane.showMessageDialog(null, "HttpService is not accessible");
			shutdown(false);
		}
		
		HttpService httpService = (HttpService) bundleContext.getService(httpServiceRef);
		
		Dictionary<String, String> initParams;
		initParams = new Hashtable<String, String>();
		initParams.put("widgetset", widgetset);
		if (productionMode != null)
			initParams.put("productionMode", productionMode);
		
		System.out.println("New Vaadin context : " + contextPath);
		
		final HttpServlet servlet = new VaadinOSGiServlet();

		try
		{
			httpService.registerServlet("/" + contextPath, servlet, initParams, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void showFrame()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(500, 150);
		frame.setResizable(false);
		frame.setTitle("Vaaclipse server");
		final Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		final JLabel label = new JLabel("Vaaclipse server started at http://localhost:80/" + contextPath);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(label);
		
		contentPane.add(Box.createVerticalStrut(5));
		
		final JLabel themeLabel = new JLabel("Theme: " + themeName);
		themeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(themeLabel);
		
		contentPane.add(Box.createVerticalStrut(5));
		
		final JLabel widgetsetLabel = new JLabel("Widgetset: " + widgetset);
		widgetsetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(widgetsetLabel);
		
		contentPane.add(Box.createVerticalStrut(5));
		
		final JLabel productionModeLabel = new JLabel("ProductionMode: " + productionMode != null ? productionMode : "false");
		productionModeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(productionModeLabel);
		
		contentPane.add(Box.createVerticalStrut(20));
		
		JButton exitButton = new JButton("Shutdown");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				shutdown(true);
			}
		});
		contentPane.add(exitButton);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				
				shutdown(true);
			}
		});
		
		//centering frame
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenDimension.width - frame.getSize().width)/2;
		int y = (screenDimension.height - frame.getSize().height)/2;
		 
		// Move the window
		frame.setLocation(x, y);
		
		frame.setVisible(true);
	}
	
	private void shutdown(boolean confirm)
	{
		boolean exit = true;
		if (confirm)
		{
			exit = JOptionPane.OK_OPTION == JOptionPane.showOptionDialog(frame, 
						"Are you really want shutdown server?", "Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.CANCEL_OPTION);
		}
		
		if (exit)
		{
			try
			{
				queue.put(EXIT);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stop() {
		// will never be invoked
	}

}
