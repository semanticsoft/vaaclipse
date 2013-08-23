package org.semanticsoft.vaaclipse.p2.install.ui.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.JOptionPane;

import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.metadata.ILicense;
import org.semanticsoft.vaaclipse.p2.install.ui.IBasicUI;
import org.semanticsoft.vaaclipse.p2.install.ui.ILicenseView;
import org.semanticsoft.vaaclipse.p2.install.ui.ILoadExplorRepoistory;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryExplorer;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryLoader;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class LoadExplorRepositoryView implements ILoadExplorRepoistory {

	private IRepositoryLoader iRepositoryLoader;

	private IRepositoryExplorer iRepositoryExplorer;

	private ILicenseView licenseView;
	String errorMessage;
	TextArea textArea;

	VerticalLayout mainLayout = new VerticalLayout();
	IInstallNewSoftwareService installService;

	@Override
	public Object getUIComponent() {
		// TODO Auto-generated method stub
		return mainLayout;
	}



	@Inject
	public LoadExplorRepositoryView(IRepositoryLoader iRepositoryLoader,
			IRepositoryExplorer iRepositoryExplorer,
			IInstallNewSoftwareService installNewSoftwareService,
			ILicenseView licenseView) {
		super();
		this.licenseView = licenseView;
		setiRepositoryExplorer(iRepositoryExplorer);
		setiRepositoryLoader(iRepositoryLoader);
		this.installService = installNewSoftwareService;
		initUI();
	}

	@Override
	public void initUI() {
		// TODO Auto-generated method stub

		mainLayout.setWidth("600px");
		mainLayout.setHeight("450px");
		textArea = new TextArea("Error Message");
		getiRepositoryLoader().setiRepositoryExplorer(getiRepositoryExplorer());

		mainLayout.addComponent((Component) getiRepositoryLoader()
				.getUIComponent());
		mainLayout.addComponent((Component) getiRepositoryExplorer()
				.getUIComponent());
		textArea.setSizeFull();
		mainLayout.addComponent(textArea);

	}

	public boolean validate() {

		textArea.setValue("");

		String installNewSoftware = "OK";
		try {
			List<IInstallableUnit> selectedRepository = getiRepositoryExplorer()
					.getSelectedRepository();
			if (selectedRepository == null || selectedRepository.isEmpty()) {

				textArea.setValue("You must select at last one");
				return false;
			}
			installNewSoftware = installService.validate(selectedRepository);

		} catch (Exception exception) {

			errorMessage = exception.getMessage();
			exception.printStackTrace();
			if (exception.getMessage().contains(
					"Profile id _SELF_ is not registered"))

				textArea.setValue("You must export via .product file first");
			else
				textArea.setValue(exception.getMessage()
						+ "Something bat happended");

			return false;

		}

		licenseView.addRepositories(getiRepositoryExplorer()
				.getSelectedRepository());

		textArea.setValue(installNewSoftware);

		if (installNewSoftware == null)
			return true;
		else
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

		this.iRepositoryLoader = iRepositoryLoader;
	}

	public IRepositoryExplorer getiRepositoryExplorer() {
		return iRepositoryExplorer;
	}

	public void setiRepositoryExplorer(IRepositoryExplorer iRepositoryExplorer) {
		this.iRepositoryExplorer = iRepositoryExplorer;
	}

}
