package org.semanticsoft.vaaclipsedemo.cassandra.widgetset;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class WidgetsetUI extends UI {
	@Override
	public void init(VaadinRequest request) {
		Label label = new Label("Hello Vaadin user");
		setContent(label);
	}

}
