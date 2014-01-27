/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.internal.preferences.PreferencesService;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.e4.ui.model.application.MApplication;
import org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.util.PreferencesSwitch;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.prefs.Preferences;

import e4modelextension.VaaclipseApplication;

/**
 * @author rushan
 *
 */
public class PreferencesAddon {

	@Inject
	MApplication app;
	
	@PostConstruct
	void init() {
		VaaclipseApplication vaaApp = (VaaclipseApplication) app;
		
		BundleContext bundleContext = FrameworkUtil.getBundle(PreferencesAddon.class).getBundleContext();
		
		Map<String, Bundle> bundlesByName = new HashMap<>();
		for (Bundle b : bundleContext.getBundles()) {
			bundlesByName.put(b.getSymbolicName(), b);
		}
		
		for (PreferencesPage page : vaaApp.getPreferencesPages()) {
			
			setTypedDefaultValues(page);
			
			String scope = page.getPreferencesScope();
			
			int searchFrom = 0;
			if (scope.startsWith("/"))
				searchFrom = 1;
			int endOfBundleName = scope.indexOf("/", searchFrom);
			String absolutePreferencePath = scope;
			if (endOfBundleName > 0 && endOfBundleName < scope.length() - 1) {
				String bundleName = scope.substring(searchFrom, endOfBundleName);
				Bundle bundle = bundlesByName.get(bundleName);
				if (bundle != null) {
					String bundleRelativePath = scope.substring(endOfBundleName + 1);
					absolutePreferencePath = "/configuration/org.eclipse.core.runtime.preferences.OSGiPreferences." + bundle.getBundleId()
							+ "/" + bundleRelativePath;	
				}
			}
			IPreferencesService equinoxPrefService = PreferencesService.getDefault();
			IEclipsePreferences root = equinoxPrefService.getRootNode();
			Preferences node = root.node(absolutePreferencePath);
			if (node != null) {
				page.setPreferences(node);
			}
		}
	}

	private void setTypedDefaultValues(PreferencesPage page) {
		for (FieldEditor<?> ed : page.getChildren()) {
			FieldEditor<Object> editor = (FieldEditor<Object>) ed;
			
			PreferencesSwitch<?> sw = new PreferencesSwitch() {
				@Override
				public Object caseBooleanFieldEditor(BooleanFieldEditor object) {
					return Boolean.valueOf(object.getDefaultValue());
				}
				
				@Override
				public Object caseScaleFieldEditor(ScaleFieldEditor object) {
					return Integer.valueOf(object.getDefaultValue());
				}
				
				
				
			};
			Object converted = sw.doSwitch(editor);
			if (converted == null) {
				converted = editor.getDefaultValue();
			}
			editor.setDefaultValueTyped(converted);
		}
	}
}
