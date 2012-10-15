/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.editors;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.io.IOException;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;

/**
 * @author rushan
 *
 */
public class TextEditor extends FileView
{
	protected Label text;
	protected String content;
	
	@Inject
	public TextEditor(VerticalLayout container, MInputPart inputPart)
	{
		super(inputPart.getInputURI());
		
		Panel e = new Panel();
		e.setSizeFull();
		((VerticalLayout)e.getContent()).setMargin(true);
		//e.setMargin(true);
		e.setScrollable(true);
		text = new Label(readContent());
		e.addComponent(text);
		container.addComponent(e);
	}

	protected TextEditor(String inputURI)
	{
		super(inputURI);
	}

	protected String readContent()
	{
		try
		{
			content = FileUtils.readFile(getFile(), "UTF-8");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			content = e.getMessage();
		}
		return content;
	}
}
