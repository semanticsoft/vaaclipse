package org.semanticsoft.vaaclipse.p2.sites.ui;

public interface ISitesView {

	
	Object getUIComponent();

	void initUI();

	String errorMessage();

	boolean validate();	
}
