/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.editors;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;

/**
 * @author rushan
 *
 */
public class HtmlEditor extends TextEditor
{
	@Inject
	public HtmlEditor(VerticalLayout container, MInputPart inputPart)
	{
		super(inputPart.getInputURI());
		
		Panel e = new Panel();
		e.setSizeFull();
		((VerticalLayout)e.getContent()).setMargin(true);
		e.setScrollable(true);
		text = new Label(readContent(), Label.CONTENT_XHTML);
		e.addComponent(text);
		container.addComponent(e);
	}
}
