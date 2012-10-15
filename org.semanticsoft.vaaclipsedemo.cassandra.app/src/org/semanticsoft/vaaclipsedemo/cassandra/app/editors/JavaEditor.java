/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.editors;

import com.vaadin.ui.Panel;

import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.vaadin.codelabel.CodeLabel;

/**
 * @author rushan
 *
 */
public class JavaEditor extends TextEditor
{
	@Inject
	public JavaEditor(VerticalLayout container, MInputPart inputPart)
	{
		super(inputPart.getInputURI());
		
		Panel e = new Panel();
		e.setSizeFull();
		((VerticalLayout)e.getContent()).setMargin(true);
		e.setScrollable(true);
		text = new CodeLabel(readContent());
		e.addComponent(text);
		container.addComponent(e);
	}
}
