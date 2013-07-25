/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.editors;

import com.vaadin.shared.ui.label.ContentMode;
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
		super(container, inputPart);
	}
	
	@Override
	protected void setupText() 
	{
		super.setupText();
		text.setContentMode(ContentMode.HTML);
		text.removeStyleName("texteditor");
		text.addStyleName("textview");
	}
}
