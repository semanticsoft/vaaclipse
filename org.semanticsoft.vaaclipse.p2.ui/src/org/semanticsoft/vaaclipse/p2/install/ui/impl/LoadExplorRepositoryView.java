package org.semanticsoft.vaaclipse.p2.install.ui.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.semanticsoft.vaaclipse.p2.install.ui.IBasicUI;
import org.semanticsoft.vaaclipse.p2.install.ui.ILoadExplorRepoistory;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryExplorer;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryLoader;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class LoadExplorRepositoryView implements ILoadExplorRepoistory {

	private IRepositoryLoader iRepositoryLoader;

	private IRepositoryExplorer iRepositoryExplorer;
	String errorMessage;

	VerticalLayout mainLayout = new VerticalLayout();

	@Override
	public Object getUIComponent() {
		// TODO Auto-generated method stub
		return mainLayout;
	}

	@Inject
	public LoadExplorRepositoryView() {
		// TODO Auto-generated constructor stub

	}

	public LoadExplorRepositoryView(IRepositoryLoader iRepositoryLoader,
			IRepositoryExplorer iRepositoryExplorer) {
		super();
		this.iRepositoryLoader = iRepositoryLoader;
		this.iRepositoryExplorer = iRepositoryExplorer;
	}

	@Override
	public void initUI() {
		// TODO Auto-generated method stub

		CssLayout errorLayout = new CssLayout();

		mainLayout.setWidth("600px");
		mainLayout.setHeight("450px");
		mainLayout.addComponent(errorLayout);

		getiRepositoryLoader().setiRepositoryExplorer(getiRepositoryExplorer());

		System.out.println("Vlera eshte " + getiRepositoryExplorer() + "  ");
		mainLayout.addComponent((Component) getiRepositoryLoader()
				.getUIComponent());
		mainLayout.addComponent((Component) getiRepositoryExplorer()
				.getUIComponent());

		Label c = new Label(getiRepositoryLoader().errorMessage());
		c.setImmediate(true);

		errorLayout.addComponent(c);

	}

	public boolean validate() {

		return false;

	}

	@Override
	public String errorMessage() {
		// TODO Auto-generated method stub
		return errorMessage;
	}

	public IRepositoryLoader getiRepositoryLoader() {
		return iRepositoryLoader;
	}

	public void setiRepositoryLoader(IRepositoryLoader iRepositoryLoader) {
		System.out.println("Settttttttttttttttttttting");
		this.iRepositoryLoader = iRepositoryLoader;
	}

	public IRepositoryExplorer getiRepositoryExplorer() {
		return iRepositoryExplorer;
	}

	public void setiRepositoryExplorer(IRepositoryExplorer iRepositoryExplorer) {
		this.iRepositoryExplorer = iRepositoryExplorer;
	}

}
