/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.editors;

import javax.inject.Inject;

import com.vaadin.ui.VerticalLayout;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;

/**
 * @author rushan
 *
 */
public class XmlEditor extends JHighlightEditor
{
	@Inject
	public XmlEditor(VerticalLayout container, MInputPart inputPart)
	{
		super(container, inputPart);
	}

	@Override
	String getJHighlighTypeName()
	{
		return "xml";
	}
}
