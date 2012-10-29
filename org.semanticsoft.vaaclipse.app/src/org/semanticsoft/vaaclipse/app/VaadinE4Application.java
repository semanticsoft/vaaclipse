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
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.ArrayBlockingQueue;

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

@SuppressWarnings("restriction")
public class VaadinE4Application implements IApplication {
	
	private ArrayBlockingQueue<String> queue;
	private Logger logger = new WorkbenchLogger("org.semanticsoft.vaaclipse.app");
	
	private IApplicationContext appContext;
	
	private static VaadinE4Application instance;
	
	public static final String EXIT = "EXIT";
	
	JFrame frame;
	
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
	
//	private Object monitor = new Object();

	@Override
	public Object start(IApplicationContext context) throws Exception {
		instance = this;
		appContext = context;
		
		logger.debug("VaadinE4Application.start()");
		context.applicationRunning();
		
		queue = new ArrayBlockingQueue<>(10);
		
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

	private void showFrame()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(400, 80);
		frame.setResizable(false);
		frame.setTitle("Vaaclipse server");
		final Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		JButton exitButton = new JButton("Shutdown");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				shutdown();
			}
		});
		final JLabel label = new JLabel("Vaaclipse server started at http://localhost:80/vaadinapp");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(label);
		contentPane.add(Box.createVerticalStrut(5));
		contentPane.add(exitButton);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				
				shutdown();
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
	
	private void shutdown()
	{
		int choice = JOptionPane.showOptionDialog(frame, "Are you really want shutdown server?", "Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.CANCEL_OPTION);
		if (choice == JOptionPane.OK_OPTION)
		{
			try
			{
				queue.put(EXIT);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stop() {
		// will never be invoked
	}

}
