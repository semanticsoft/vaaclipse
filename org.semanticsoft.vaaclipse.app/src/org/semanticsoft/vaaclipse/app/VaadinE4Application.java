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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;

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
import org.semanticsoft.vaaclipse.api.ResourceInfoProvider;

@SuppressWarnings("restriction")
public class VaadinE4Application implements IApplication, ResourceInfoProvider {
	
	private ArrayBlockingQueue<String> queue;
	private Logger logger = new WorkbenchLogger("org.semanticsoft.vaaclipse.app");
	
	private IApplicationContext appContext;
	
	private static VaadinE4Application instance;
	
	public static final String EXIT = "EXIT";
	
	JFrame frame;
	private String contextPath = "/";
	private String port = "80";
	private String productionMode;
	private String appCss;
	private String appWidgetset;
	private String appWidgetsetName;
	private String appHeaderIcon;
	private boolean debugMode = false;
	
	private static final String VAACLIPSE_USER_THEME = "vaaclipse_user_theme";
	
	public static VaadinE4Application getInstance()
	{
		return instance;
	}
	
	@Override
	public String getApplicationCSS()
	{
		return appCss;
	}
	
	@Override
	public String getApplicationtWidgetset()
	{
		return appWidgetset;
	}
	
	@Override
	public String getApplicationtWidgetsetName()
	{
		return this.appWidgetsetName;
	}
	
	@Override
	public String getApplicationHeaderIcon() 
	{
		return this.appHeaderIcon;
	}
	
	@Override
	public String getUserVaadinTheme()
	{
		return VAACLIPSE_USER_THEME;
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

	@Override
	public Object start(IApplicationContext context) throws Exception {
		instance = this;
		appContext = context;
		
		registerServices();
		
		logger.debug("VaadinE4Application.start()");
		context.applicationRunning();
		
		queue = new ArrayBlockingQueue<>(10);
		
		startHttpService();
		if (debugMode)
		showFrame();//it was getting really annoying :)
		
		String msg;
		while (!(msg = queue.take()).equals(EXIT)) 
		{
	        System.out.println(msg);
		}
		
		frame.setVisible(false);
		frame.dispose();
		
		return EXIT_OK;
	}

	private void registerServices()
	{
		Activator.getDefault().getBundle().getBundleContext().registerService(ResourceInfoProvider.class.getName(), this, null);
	}

	private void startHttpService() throws Exception
	{
		port = System.getProperty("org.osgi.service.http.port");
		
		if (port == null)
			port = "80";
		
		contextPath = System.getProperty("org.eclipse.equinox.http.jetty.context.path");
		
		if (contextPath == null)
			contextPath = "/";
		
		appCss = appContext.getBrandingProperty("applicationCSS");
		
		if (appCss == null)
			appCss = "platform:/plugin/org.semanticsoft.vaaclipse.app/css/default_user_css.css";
		
		appWidgetset = appContext.getBrandingProperty("applicationWidgetset");
		if (appWidgetset == null || appWidgetset.trim().isEmpty())
			appWidgetset = "platform:/plugin/com.vaadin/VAADIN/widgetsets/vaaclipse_widgetset.widgetset.Vaaclipse_widgetsetWidgetset";
		else
			appWidgetset = appWidgetset.trim();
		
		int index = appWidgetset.lastIndexOf("/");
		if (index < 0)
			throw new IllegalStateException("applicationWidgetset property has wrong value");
		
		if (index == appWidgetset.length() - 1)
		{
			appWidgetset = appWidgetset.substring(0, appWidgetset.length() - 1);
			index = appWidgetset.lastIndexOf("/");
			if (index < 0)
				throw new IllegalStateException("applicationWidgetset property has wrong value");
		}
		
		appWidgetsetName = appWidgetset.substring(index + 1);
		
		appHeaderIcon = appContext.getBrandingProperty("applicationHeaderIcon");
		
		if (appHeaderIcon == null || appHeaderIcon.trim().isEmpty())
			appHeaderIcon = "platform:/plugin/com.vaadin/VAADIN/themes/reindeer/favicon.ico";
		
		productionMode = appContext.getBrandingProperty("org.semanticsoft.vaaclipse.app.vaadin.production_mode");
		
		final BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> httpServiceRef = bundleContext.getServiceReference(HttpService.class.getName());
		if (httpServiceRef == null)
		{
			JOptionPane.showMessageDialog(null, "HttpService is not accessible");
			throw new Exception();
		}
		
		HttpService httpService = (HttpService) bundleContext.getService(httpServiceRef);
		Dictionary<String, String> initParams = new Hashtable<String, String>();
		//initParams.put("widgetset", "vaaclipse_widgetset.widgetset.Vaaclipse_widgetsetWidgetset");
		//initParams.put("widgetset", "mediaplayer.MediaplayerApplicationWidgetset");
		initParams.put("widgetset", appWidgetsetName);
		
		if (productionMode != null)
			initParams.put("productionMode", productionMode);
		
		final HttpServlet servlet = new VaadinOSGiServlet();

		httpService.registerServlet("/", servlet, initParams, null);
	}

	private void showFrame()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(500, 90);
		frame.setResizable(false);
		frame.setTitle("Vaaclipse server");
		final Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		String host;
		try
		{
			InetAddress thisIp = InetAddress.getLocalHost();
			host = thisIp.getHostAddress().toString();
		}
		catch (UnknownHostException e1)
		{
			host = "localhost";
		}
	       
		final JLabel label = new JLabel(String.format("Vaaclipse server started at http://%s:%s%s", host, port, contextPath));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(label);
		
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
				return;
			}
		});
		contentPane.add(exitButton);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				
				shutdown(true);
				return;
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
	
	private boolean shutdown(boolean confirm)
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
		return exit;
	}

	@Override
	public void stop() {
		// will never be invoked
	}

}
