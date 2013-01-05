/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.views;

import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;

import java.util.Scanner;

import java.io.IOException;

import java.io.InputStream;

import java.net.URL;

import java.net.URI;

import javax.annotation.PostConstruct;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;

/**
 * @author rushan
 *
 */
public class Help
{
	private Panel panel;
	private Label content;
	
	@Inject
	public void Help(VerticalLayout parent, IEclipseContext context)
	{
		panel = new Panel();
		panel.setSizeFull();
		panel.setScrollable(true);
		parent.addComponent(panel);
	}
	
	@PostConstruct
	public void loadHelpText()
	{
		String helpPath = "platform:/plugin/org.semanticsoft.vaaclipsedemo.cassandra.app/data/Help.html";
		try {
			URL cssUrl = new URL(helpPath);
			InputStream is = cssUrl.openStream();
			
			try {
				String contentString = IOUtils.toString(is);
				content = new Label(contentString, Label.CONTENT_XHTML);
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			content = new Label("Can not open file " + helpPath);
		}
		
		panel.addComponent(content);
	}
}
