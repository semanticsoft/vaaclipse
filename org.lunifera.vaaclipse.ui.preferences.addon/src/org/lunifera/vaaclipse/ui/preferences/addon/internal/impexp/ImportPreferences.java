/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal.impexp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.eclipse.core.internal.preferences.PreferencesService;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IExportedPreferences;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.FileDownloader;
import com.vaadin.ui.Button;
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
		
		return layout;
	}

	@Override
	protected void doAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getActionName() {
		return "Import";
	}

	@SuppressWarnings("restriction")
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		byte[] byteArray = baos.toByteArray();
		IExportedPreferences imported;
		try {
			imported = PreferencesService.getDefault().readPreferences(new ByteArrayInputStream(byteArray));
		} catch (CoreException e) {
			logger.error("Error when importing preferences", e);
			return;
		}
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		
		baos = new ByteArrayOutputStream(200);
		return baos;
		
	}

	@Override
	protected boolean actionAllowed(PreferencesCategory category) {
		return authService == null || authService.importAllowed();
	}

}
