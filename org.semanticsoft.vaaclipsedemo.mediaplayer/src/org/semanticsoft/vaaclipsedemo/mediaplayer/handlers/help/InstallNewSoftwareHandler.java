package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.help;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class InstallNewSoftwareHandler {
	@Inject
	IProvisioningAgent provisioningAgent;

	@Execute
	public void execute(UI ui, final IInstallNewSoftwareService installService) {

		Window window = new Window("Install new Software");

		VerticalLayout mainLayout = new VerticalLayout();
		window.setContent(mainLayout);
		window.setModal(true);
		window.setPositionX(500);
		window.setPositionY(100);
		mainLayout.setWidth("600px");
		mainLayout.setHeight("450px");

		Label label = new Label("Enter URI here");

		final TextField textField = new TextField();

		CssLayout cssLayout = new CssLayout();
		cssLayout.addComponent(label);
		cssLayout.addComponent(textField);
		final Tree tree = new Tree("List of categories");
		tree.setMultiSelect(true);

		mainLayout.addComponent(cssLayout);
		mainLayout.addComponent(tree);
		tree.setImmediate(true);
		textField.setImmediate(true);
		Button button = new Button("Install");
		mainLayout.addComponent(button);

		final TextArea textArea = new TextArea("Error report");
		mainLayout.addComponent(textArea);
		textArea.setImmediate(true);
		textArea.setSizeFull();

		final List<IInstallableUnit> loadRepository = new ArrayList<IInstallableUnit>();
		textField.addBlurListener(new FieldEvents.BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				// TODO Auto-generated method stub

				String value = textField.getValue();
				System.out.println("Value " + value);
				tree.removeAllItems();
				if (value != null && !value.trim().isEmpty()) {
					loadRepository.addAll(installService.loadRepository(value,
							provisioningAgent));

					for (IInstallableUnit iInstallableUnit : loadRepository) {

						tree.addItem(iInstallableUnit.getId() + " "
								+ iInstallableUnit.getVersion());
					}
				}
			}
		});

		button.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				tree.removeAllItems();

				/*
				 * List<IInstallableUnit> listFinal = new ArrayList<>(); Set
				 * values = (Set) tree.getValue(); for (Object string : values)
				 * { System.out.println(string); for (IInstallableUnit
				 * iInstallableUnit : loadRepository) {
				 * 
				 * if (string.equals(iInstallableUnit.getId() + " " +
				 * iInstallableUnit.getVersion())) {
				 * System.out.println("Ok found it");
				 * listFinal.add(iInstallableUnit); } }
				 * 
				 * }
				 */

				textArea.setValue("");

				String installNewSoftware = "OK";
				try {
					installNewSoftware = installService
							.installNewSoftware(loadRepository);

				} catch (Exception exception) {

					exception.printStackTrace();
					if (exception.getMessage().contains(
							"Profile id _SELF_ is not registered"))

						textArea.setValue("You must export via .product file first");
					else
						textArea.setValue(exception.getMessage()
								+ "Something bat happended");

					return;

				}

				textArea.setValue(installNewSoftware);

			}
		});

		ui.addWindow(window);

	}

}