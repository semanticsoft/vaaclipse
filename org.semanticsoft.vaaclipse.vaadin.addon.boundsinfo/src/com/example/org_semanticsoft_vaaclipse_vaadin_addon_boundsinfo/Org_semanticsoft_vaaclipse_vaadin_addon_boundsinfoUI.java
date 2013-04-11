package com.example.org_semanticsoft_vaaclipse_vaadin_addon_boundsinfo;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

public class Org_semanticsoft_vaaclipse_vaadin_addon_boundsinfoUI extends UI {
	@Override
	public void init(VaadinRequest request) {
		Label label = new Label("Hello Vaadin user");
		setContent(label);
	}

}
