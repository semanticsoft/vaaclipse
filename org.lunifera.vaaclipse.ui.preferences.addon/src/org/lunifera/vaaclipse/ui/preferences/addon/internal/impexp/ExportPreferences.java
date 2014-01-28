/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal.impexp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.PrefHelper;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.Preferences;
import org.osgi.service.prefs.PreferencesService;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public class ExportPreferences extends BasicImpExp {

	Logger logger = LoggerFactory.getLogger(ExportPreferences.class);
	
	@Override
	public Component getComponent(OptionDialog optionDialog) {
		CssLayout layout = new CssLayout();
		layout.addStyleName("export");
		layout.addComponent(new Label("Select preferences to export"));
		createPreferencesTable(layout);
		
		Button importButton = dlg.getOptionButton(IMP_EXP);
		FileDownloader fileDownloader = new FileDownloader(createResource());
		fileDownloader.extend(importButton);
		
		return layout;
	}

	private StreamResource createResource() {
		
        return new StreamResource(new StreamSource() {
            @SuppressWarnings("restriction")
			@Override
            public InputStream getStream() {
            	
            	List<PreferencesPage> selectedPages = getSelectedPages();
            	if (selectedPages.isEmpty())
            		return null;
            	
            	IEclipsePreferences root = org.eclipse.core.internal.preferences.PreferencesService.getDefault().getRootNode();
            	
            	ByteArrayOutputStream baos = new ByteArrayOutputStream(200);
            	
            	try {
					org.eclipse.core.internal.preferences.PreferencesService.getDefault().exportPreferences(root, new IPreferenceFilter[] {createFilter(selectedPages)}, baos);
				} catch (CoreException e) {
					logger.error("Exception when export preferences", e);
					return null;
				}
        		
        		byte[] preferencesBytes = baos.toByteArray();
            	
        		ByteArrayInputStream is = new ByteArrayInputStream(preferencesBytes);
            	return is;
                
            }
        }, "preferences");
    }

	protected IPreferenceFilter createFilter(List<PreferencesPage> selectedPages) {
		final List<String> list = new ArrayList<>();
		for (PreferencesPage p : selectedPages) {
			String eqPath = PrefHelper.toEquinoxPreferencePath(bundlesByName, p.getPreferencesScope());
			list.add(eqPath);
		}
		
		return new IPreferenceFilter() {
			
			@Override
			public String[] getScopes() {
				return (String[]) list.toArray(new String[list.size()]);
			}
			
			@Override
			public Map getMapping(String scope) {return null;}
		};
	}

	@Override
	protected void doAction() {
		
	}

	@Override
	protected String getActionName() {
		return "Export";
	}

	
	
}
