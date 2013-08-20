package org.semanticsoft.vaaclipse.p2.install.ui.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.metadata.ILicense;
import org.semanticsoft.vaaclipse.p2.install.ui.ILicenseView;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class LicenseView implements ILicenseView {

	HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
	VerticalLayout leftLayout = new VerticalLayout();
	VerticalLayout mainLayout = new VerticalLayout();
	CssLayout rightLayout = new CssLayout();
	CssLayout errorLayout = new CssLayout();
	Tree treeRepo = new Tree();
	List<IInstallableUnit> listReos = new ArrayList<>();
	private CheckBox checkBox;

	public LicenseView() {
		// TODO Auto-generated constructor stub
		initUI();
	}

	@Override
	public Object getUIComponent() {
		// TODO Auto-generated method stub
		return mainLayout;
	}

	@Override
	public void initUI() {
		// TODO Auto-generated method stub
		errorLayout.setHeight("30px");

		mainLayout.addComponent(errorLayout);
		horizontalSplitPanel.setWidth("600px");
		horizontalSplitPanel.setHeight("380px");
		leftLayout.addComponent(treeRepo);
		horizontalSplitPanel.setFirstComponent(leftLayout);
		horizontalSplitPanel.setSecondComponent(rightLayout);
		leftLayout.setSizeFull();
		rightLayout.setSizeFull();
		checkBox = new CheckBox("I agree");
		checkBox.setValue(false);
		mainLayout.addComponent(horizontalSplitPanel);
		mainLayout.addComponent(checkBox);

		treeRepo.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				// TODO Auto-generated method stub

				Object itemId = event.getItemId();

				rightLayout.removeAllComponents();
				for (IInstallableUnit inst : listReos) {

					if (inst.getId().equals(itemId)) {

						Label c = new Label();
						Collection<ILicense> licenses = inst.getLicenses();
						c.setValue("");

						for (ILicense iLicense : licenses) {

							c.setValue(c.getValue() + iLicense.getBody());
						}

						rightLayout.addComponent(c);
					}
				}
			}
		});
	}

	@Override
	public String errorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validate() {
		errorLayout.removeAllComponents();
		// TODO Auto-generated method stub
		Boolean value = checkBox.getValue();
		if(!value){
			
			Label c = new Label();
			c.setValue("You must agree with license");
			errorLayout.addComponent(c);
		}
		return value;
	}

	@Override
	public void addRepositories(List<IInstallableUnit> list) {
		// TODO Auto-generated method stub

		clear();
		listReos.addAll(list);
		for (IInstallableUnit iInstallableUnit : listReos) {

			treeRepo.addItem(iInstallableUnit.getId());

		}

	}

	private void clear() {
		treeRepo.removeAllItems();
		listReos.clear();
		rightLayout.removeAllComponents();
	}

	@Override
	public List<IInstallableUnit> getRepos() {
		// TODO Auto-generated method stub
		return listReos;
	}

}
