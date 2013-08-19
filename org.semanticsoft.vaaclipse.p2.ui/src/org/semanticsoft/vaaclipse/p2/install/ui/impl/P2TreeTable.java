package org.semanticsoft.vaaclipse.p2.install.ui.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.repository.IRepository;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryExplorer;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TreeTable;

public class P2TreeTable implements IRepositoryExplorer {

	private TreeTable treeTable;
	private List<CheckBox> listChecks = new ArrayList<>();
	private List<IInstallableUnit> selectedRepository = new ArrayList<>();
	private List<IInstallableUnit> loadRepository = new ArrayList<>();
	private HashMap<String, List<CheckBox>> childsLinkedToRoot = new HashMap<>();
	private IInstallNewSoftwareService installService;
	private List<CheckBox> listCheckBoxChilds;
	private String errorMessage;

	public P2TreeTable(IInstallNewSoftwareService installService) {
		super();
		this.installService = installService;
		initUI();
	}

	@Override
	public Object getUIComponent() {

		return treeTable;
	}

	ValueChangeListener checkBoxListener = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
			// TODO Auto-generated method stub

			selectedRepository.clear();
			for (CheckBox checkBox2 : listChecks) {

				Boolean value2 = checkBox2.getValue();
				String caption2 = checkBox2.getCaption();
				for (IInstallableUnit iInstallableUnit2 : loadRepository) {

					if (caption2.equals(iInstallableUnit2.getId())) {

						if (value2) {
							selectedRepository.add(iInstallableUnit2);
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

	@Override
	public void fill(List<IInstallableUnit> iInstallableUnits) {

		loadRepository.addAll(iInstallableUnits);
		for (IInstallableUnit iInstallableUnit : loadRepository) {

			addRootItem(iInstallableUnit);

			List<IInstallableUnit> extractFromCategory = installService
					.extractFromCategory(iInstallableUnit);

			addSubItems(extractFromCategory, iInstallableUnit);

		}

	}

	@Override
	public void initUI() {

		treeTable = new TreeTable("");
		treeTable.addContainerProperty("Name", CheckBox.class, "");
		treeTable.addContainerProperty("Version", String.class, "");
		treeTable.setPageLength(5);
		treeTable.setWidth("40em");
		treeTable.setSelectable(true);
		treeTable.setImmediate(true);
	}

	@Override
	public void addRootItem(IInstallableUnit iInstallableUnit) {
		CheckBox checkBox = new CheckBox(iInstallableUnit.getId());
		treeTable.addItem(new Object[] { checkBox,
				iInstallableUnit.getVersion().toString() },
				iInstallableUnit.getId());
		checkBox.addValueChangeListener(checkBoxListener);
		listChecks.add(checkBox);
	}

	@Override
	public void addRoottems(List<IInstallableUnit> iInstallableUnits) {

		for (IInstallableUnit iInstallableUnit : iInstallableUnits) {
			addRootItem(iInstallableUnit);
		}
	}

	@Override
	public void addSubItems(List<IInstallableUnit> childIInstallableUnits,
			IInstallableUnit root) {
		listCheckBoxChilds = new ArrayList<>();
		for (IInstallableUnit iInstallableUnit : childIInstallableUnits) {

			addSubItem(iInstallableUnit, root);

		}

		childsLinkedToRoot.put(root.getId(), listCheckBoxChilds);
	}

	@Override
	public void addSubItem(IInstallableUnit childIInstallableUnit,
			IInstallableUnit root) {
		CheckBox checkBoxChild = new CheckBox(childIInstallableUnit.getId());
		listChecks.add(checkBoxChild);
		treeTable.addItem(new Object[] { checkBoxChild,
				childIInstallableUnit.getVersion().toString() },
				childIInstallableUnit.getId());

		treeTable.setParent(childIInstallableUnit.getId(), root.getId());

		listCheckBoxChilds.add(checkBoxChild);

	}

	@Override
	public String errorMessage() {
		// TODO Auto-generated method stub
		return errorMessage;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
