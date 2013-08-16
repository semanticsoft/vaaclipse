package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
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
		textField.setValue("http://localhost/MyUpdateSite");
		textField.setImmediate(true);

		CssLayout cssLayout = new CssLayout();
		cssLayout.addComponent(label);
		cssLayout.addComponent(textField);

		final TreeTable treeTable = new TreeTable("");
		treeTable.addContainerProperty("Name", CheckBox.class, "");
		treeTable.addContainerProperty("Version", String.class, "");
		treeTable.setPageLength(5);
		treeTable.setWidth("40em");
		treeTable.setSelectable(true);
		treeTable.setImmediate(true);
		mainLayout.addComponent(cssLayout);
		mainLayout.addComponent(treeTable);

		final TextArea textArea = new TextArea("Error report");

		mainLayout.addComponent(textArea);
		textArea.setImmediate(true);

		textArea.setEnabled(true);

		textArea.setWidth("40em");
		textArea.setHeight("7em");
		Button button = new Button("Install");
		button.setWidth("300px");
		mainLayout.addComponent(button);

		final List<IInstallableUnit> loadRepository = new ArrayList<>();
		final List<IInstallableUnit> selectedRepository = new ArrayList<>();
		textField.addShortcutListener(new ShortcutListener("Enter URI",
				ShortcutAction.KeyCode.ENTER, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				// TODO Auto-generated method stub

				final List<CheckBox> listChecks = new ArrayList<>();
				final HashMap<String, List<CheckBox>> childsLinkedToRoot = new HashMap<>();
				String value = textField.getValue();

				if (value != null && !value.trim().isEmpty()) {
					loadRepository.addAll(installService.loadRepository(value,
							provisioningAgent));

					for (IInstallableUnit iInstallableUnit : loadRepository) {

						CheckBox checkBox = new CheckBox(iInstallableUnit
								.getId());
						ValueChangeListener checkBoxListener = new ValueChangeListener() {

							@Override
							public void valueChange(ValueChangeEvent event) {
								// TODO Auto-generated method stub

								selectedRepository.clear();
								for (CheckBox checkBox2 : listChecks) {

									Boolean value2 = checkBox2.getValue();
									String caption2 = checkBox2.getCaption();
									for (IInstallableUnit iInstallableUnit2 : loadRepository) {

										if (caption2.equals(iInstallableUnit2
												.getId())) {

											if (value2) {
												selectedRepository
														.add(iInstallableUnit2);
											}
											boolean category = installService
													.isCategory(iInstallableUnit2);
											if (category) {
												// check all childs
												List<CheckBox> list = childsLinkedToRoot
														.get(caption2);

												for (CheckBox checkBox3 : list) {

													checkBox3.setValue(value2);
												}

											}
										}
									}

								}
							}
						};
						checkBox.addValueChangeListener(checkBoxListener);

						listChecks.add(checkBox);
						treeTable.addItem(new Object[] { checkBox,
								iInstallableUnit.getVersion().toString() },
								iInstallableUnit.getId());

						List<IInstallableUnit> extractFromCategory = installService
								.extractFromCategory(iInstallableUnit);

						List<CheckBox> listCheckBoxChilds = new ArrayList<>();

						for (IInstallableUnit iInstallableUnitChild : extractFromCategory) {

							CheckBox checkBoxChild = new CheckBox(
									iInstallableUnitChild.getId());

							listChecks.add(checkBoxChild);
							treeTable.addItem(new Object[] {
									checkBoxChild,
									iInstallableUnitChild.getVersion()
											.toString() },
									iInstallableUnitChild.getId());

							treeTable.setParent(iInstallableUnitChild.getId(),
									iInstallableUnit.getId());

							listCheckBoxChilds.add(checkBoxChild);
						}
						childsLinkedToRoot.put(iInstallableUnit.getId(),
								listCheckBoxChilds);

					}
				}
			}
		});

		button.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				textArea.setValue("");

				String installNewSoftware = "OK";
				try {
					installNewSoftware = installService
							.installNewSoftware(selectedRepository);

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