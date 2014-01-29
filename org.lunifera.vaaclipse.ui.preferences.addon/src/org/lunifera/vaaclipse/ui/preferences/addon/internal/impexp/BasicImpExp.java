/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal.impexp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;
import org.semanticsoft.vaadin.optiondialog.OptionDialog.ComponentProvider;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.UI;

import e4modelextension.VaaclipseApplication;

/**
 * @author rushan
 *
 */
public abstract class BasicImpExp implements ComponentProvider {
	
	public static final int IMP_EXP = 0;
	public static final int CANCEL = 1;
	public static final int MAX_DESCRIPTION_LENGTH = 75;

	@Inject
	VaaclipseApplication app;
	
	OptionDialog dlg;
	Table table;
	
	List<CheckBox> checkBoxes = new ArrayList<>();
	HashMap<String, Bundle> bundlesByName;
	
	@PostConstruct
	public void init(UI ui) {
		
		BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		bundlesByName = new HashMap<>();
		for (Bundle b : bundleContext.getBundles()) {
			bundlesByName.put(b.getSymbolicName(), b);
		}
		
		dlg = new OptionDialog();
		dlg.addStyleName("preferences-export-import-dialog");
		
		dlg.addOption(IMP_EXP, getActionName());
		dlg.addOption(CANCEL, "Close");
		
		dlg.setCaption(getActionName() + " Preferences");
		dlg.setWidth("600px");
		dlg.setHeight("450px");
		dlg.setComponentProvider(this);
		ui.addWindow(dlg);
	}
	
	@Override
	public void setMessage(String message) {}
	
	@Override
	public void optionSelected(OptionDialog optionDialog, int optionId) {
		if (optionId == IMP_EXP) {
			doAction();
		} else {
			dlg.close();
		}
	}
	
	protected abstract void doAction();
	protected abstract String getActionName();
	
	protected void createPreferencesTable(CssLayout layout) {
		createPreferencesTable(layout, app.getPreferencesPages());
	}
	
	protected void createPreferencesTable(CssLayout layout, List<PreferencesPage> pageList) {
		
		BeanItemContainer<PreferencesPage> container = new BeanItemContainer<>(PreferencesPage.class);
		container.addNestedContainerProperty("category.name");
		
		for (PreferencesPage page : pageList) {
			container.addBean(page);
		}
		
		table = new Table();
		table.setSizeFull();
		table.setContainerDataSource(container);
		
		table.addGeneratedColumn("description", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				PreferencesPage page = (PreferencesPage) itemId;
				String d = page.getDescription();
				if (d.length() > MAX_DESCRIPTION_LENGTH) {
					d = d.substring(0, MAX_DESCRIPTION_LENGTH).trim() + "...";
				}
				return d;
			}
		});
		
		table.addGeneratedColumn("include", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				CheckBox cb = new CheckBox();
				cb.setData(itemId);
				checkBoxes.add(cb);
				return cb;
			}
		});
		
		table.setColumnHeader("include", " ");
		table.setColumnHeader("category.name", "Name");
		table.setColumnHeader("description", "Description");
		table.setVisibleColumns("include", "category.name", "description");
		
		Panel panel = new Panel(table);
		panel.setWidth("100%");
		panel.setHeight("200px");
		layout.addComponent(panel);
	}
	
	protected List<PreferencesPage> getSelectedPages() {
		List<PreferencesPage> list = new ArrayList<>();
		for (CheckBox cb : checkBoxes) {
			if (cb.getValue()) {
				PreferencesPage page = (PreferencesPage) cb.getData();
				list.add(page);	
			}
		}
		return list;
	}
	
}
