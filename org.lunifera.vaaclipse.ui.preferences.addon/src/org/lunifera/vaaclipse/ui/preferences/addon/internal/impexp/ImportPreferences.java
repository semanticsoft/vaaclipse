/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal.impexp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

import org.eclipse.core.internal.preferences.PreferencesService;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IExportedPreferences;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.util.PrefHelper;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesFactory;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

/**
 * @author rushan
 *
 */
public class ImportPreferences extends BasicImpExp implements SucceededListener, Receiver {

	private ByteArrayOutputStream baos;
	Logger logger = LoggerFactory.getLogger(ImportPreferences.class);
	private byte[] byteArray;
	
	@Inject
	PreferencesFactory factory;
	private IExportedPreferences toImport;
	
	@Override
	public Component getComponent(OptionDialog optionDialog) {
		
		CssLayout layout = new CssLayout();
		layout.addStyleName("import");
		
		Upload upload = new Upload("Select file with preferences for upload", this);
		upload.setWidth("100%");
		upload.setButtonCaption("Upload");
		upload.addSucceededListener(this);
		layout.addComponent(upload);
		
		layout.addComponent(new Label("Select preferences to import"));
		createPreferencesTable(layout, new ArrayList<PreferencesPage>());
		
		createStatusLabel(layout, "Choose file with preferences");
		
		return layout;
	}

	@Override
	protected void doAction() {
		
		if (toImport == null) {
			setStatusText("No uploaded preferences for import. Upload preferences file.");
			return;
		}
		
		List<PreferencesPage> selectedPages = getSelectedPages();
		if (selectedPages.isEmpty()) {
			setStatusText("Nothing selected to import");
			return;
		}
		
		try {
			PreferencesService.getDefault().applyPreferences(toImport, new IPreferenceFilter[] {createFilter(selectedPages)});
		} catch (CoreException e) {
			logger.error("Error when import preferences", e);
			setStatusText("Import preferences failed");
			return;
		}
		
		setStatusText("Preferences imported: " + toTextWithCatName(selectedPages));
	}

	@Override
	protected String getActionName() {
		return "Import";
	}

	@SuppressWarnings("restriction")
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		byteArray = baos.toByteArray();
		try {
			toImport = PreferencesService.getDefault().readPreferences(new ByteArrayInputStream(byteArray));
		} catch (CoreException e) {
			logger.error("Error when importing preferences", e);
			return;
		}
		
		//Search pages that preferences contains in this preferences for import
		List<PreferencesPage> pages = findPagesInPreferencesForImport(toImport);
		refreshPreferences(pages);
		setStatusText("Preferences file loaded. Please select preferences to import and press Import");
	}
	
	List<PreferencesPage> findPagesInPreferencesForImport(IExportedPreferences toImport) {
		Set<PreferencesPage> pages = new LinkedHashSet<>();
		for (PreferencesPage page : app.getPreferencesPages()) {
			if (page.getCategory() != null) {
				for (FieldEditor<?> editor : page.getChildren()) {
					try {
						boolean nodeExists = toImport.nodeExists(editor.getEquinoxPath());
						if (nodeExists) {
							pages.add(page);
						}
					} catch (BackingStoreException e) {
						logger.error("Error read from preferences for import", e);
					}
				}	
			}
		}
		return new ArrayList<>(pages);
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		
		baos = new ByteArrayOutputStream(200);
		return baos;
		
	}

}
