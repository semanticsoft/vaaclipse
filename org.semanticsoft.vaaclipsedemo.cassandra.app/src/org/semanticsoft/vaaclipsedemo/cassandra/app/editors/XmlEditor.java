/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.editors;

import java.io.IOException;

import java.io.UnsupportedEncodingException;

import java.io.ByteArrayOutputStream;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;

import javax.annotation.PostConstruct;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;

/**
 * @author rushan
 *
 */
public class XmlEditor extends TextEditor
{
	Panel e = new Panel();
	
	@Inject
	public XmlEditor(VerticalLayout container, MInputPart inputPart)
	{
		super(inputPart.getInputURI());
		
		e.setSizeFull();
		((VerticalLayout)e.getContent()).setMargin(true);
		e.setScrollable(true);
		container.addComponent(e);
	}
	
	@PostConstruct
	private void pc()
	{
		String content = readContent();
		String html = "";
		if (content != null)
		{
			InputStream in = IOUtils.toInputStream(content);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			try
			{
				try {
					XhtmlRendererFactory.getRenderer("xml").highlight("Заголовок",
							   in,
							   baos,
							   "UTF-8",
							   true);
					
					html = new String(baos.toByteArray(), "UTF-8");
				}
				finally {
					in.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if (html.isEmpty()) //check the exception is not in finally
					html = e.getMessage();
			}
		}
		
		text = new Label(html, Label.CONTENT_XHTML);
		e.addComponent(text);
	}
}
