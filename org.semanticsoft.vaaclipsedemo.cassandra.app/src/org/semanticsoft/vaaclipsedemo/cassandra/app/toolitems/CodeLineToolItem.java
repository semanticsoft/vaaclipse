/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.toolitems;

import com.vaadin.ui.Label;

import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;

/**
 * @author rushan
 *
 */
public class CodeLineToolItem
{
	@Inject
	public void CodeLabelToolItem(VerticalLayout parent, IEclipseContext context)
	{
		parent.addComponent(new Label("170 : 20"));
	}
}
