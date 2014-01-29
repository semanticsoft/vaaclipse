/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal.impexp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.preferences.PreferencesService;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.util.PrefHelper;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.osgi.framework.Bundle;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

/**
 * @author rushan
 *
 */
public class ExportPreferences extends BasicImpExp {

	Logger logger = LoggerFactory.getLogger(ExportPreferences.class);
	private CssLayout layout;
	private Button downloadButton;
	private byte[] preferencesBytes;
	private Label statusLabel;
	
	@Override
	public Component getComponent(OptionDialog optionDialog) {
		
		layout = new CssLayout();
		layout.addStyleName("export");
		layout.addComponent(new Label("Select preferences to export"));
		createPreferencesTable(layout);
		
		statusLabel = new Label("Press Export to export preferences");
		statusLabel.addStyleName("status-label");
		layout.addComponent(statusLabel);
		
		return layout;
	}

	private StreamResource createResource() {
		
        return new StreamResource(new StreamSource() {
			@Override
            public InputStream getStream() {
            	if (preferencesBytes != null) {
            		ByteArrayInputStream is = new ByteArrayInputStream(preferencesBytes);
                	return is;	
            	}
            	else
            		return null;
            }
        }, "preferences.epf");
    }

	protected IPreferenceFilter createFilter(List<PreferencesPage> selectedPages) {
		final List<String> list = new ArrayList<>();
		for (PreferencesPage p : selectedPages) {
			for (FieldEditor<?> e : p.getChildren()) {
				Bundle bundle = bundlesByName.get(e.getBundle());
				String eqPath = PrefHelper.toEquinoxPath(bundle, p.getCategory());
				list.add(eqPath);
			}
		}
		
		return new IPreferenceFilter() {
			
			@Override
			public String[] getScopes() {
				return (String[]) list.toArray(new String[list.size()]);
			}
			
			@Override
			public Map<?, ?> getMapping(String scope) {return null;}
		};
	}

	@SuppressWarnings("restriction")
	@Override
	protected void doAction() {
		//dlg.close();
		
		List<PreferencesPage> selectedPages = getSelectedPages();
    	if (selectedPages.isEmpty()) {
    		setStatusText("Preferences doesn't selected. Please select preferences above.");
    		return;
    	}
    	
    	IEclipsePreferences root = PreferencesService.getDefault().getRootNode();
    	
    	ByteArrayOutputStream baos = new ByteArrayOutputStream(200);
    	
    	try {
			PreferencesService.getDefault().exportPreferences(root, new IPreferenceFilter[] {createFilter(selectedPages)}, baos);
		} catch (CoreException e) {
			logger.error("Exception when export preferences", e);
			return;
		}
		
		preferencesBytes = baos.toByteArray();
		
		StringBuffer exportedPrefNames = new StringBuffer();
		for (PreferencesPage page: selectedPages) {
			String name = page.getCategory().getName();
			if (name == null)
				name = "NoName";
			exportedPrefNames.append(name + ", ");
		}
		
		exportedPrefNames.delete(exportedPrefNames.length()-2, exportedPrefNames.length()-1);
		
		setStatusText("Preferences was exported: " + exportedPrefNames.toString());
		
		if (downloadButton == null) {
			downloadButton = new Button(BaseTheme.BUTTON_LINK);
			downloadButton.addStyleName("download-button");
			downloadButton.setCaption("Download preferences.epf");
			layout.addComponent(downloadButton);
			
			FileDownloader fileDownloader = new FileDownloader(createResource());
			fileDownloader.extend(downloadButton);
		}
	}

	private void setStatusText(String statusText) {
//		layout.removeComponent(statusLabel);
//		statusLabel = new Label(statusText);
//		statusLabel.addStyleName("status-label");
//		layout.addComponent(statusLabel);
		
		statusLabel.setValue(statusText);
	}

	@Override
	protected String getActionName() {
		return "Export";
	}	
}
