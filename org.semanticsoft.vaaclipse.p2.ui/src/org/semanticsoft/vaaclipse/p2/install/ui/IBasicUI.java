package org.semanticsoft.vaaclipse.p2.install.ui;

public interface IBasicUI {
	Object getUIComponent();

	void initUI();

	String errorMessage();

	boolean validate();
}
