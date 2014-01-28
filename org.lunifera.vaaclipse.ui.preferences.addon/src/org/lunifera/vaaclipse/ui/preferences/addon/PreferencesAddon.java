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
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MContribution;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.PrefHelper;
import org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesFactory;
import org.lunifera.vaaclipse.ui.preferences.model.util.PreferencesSwitch;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.prefs.Preferences;

import e4modelextension.VaaclipseApplication;

/**
 * @author rushan
 *
 */
public class PreferencesAddon {

	@Inject
	MApplication app;
	
	@Inject
	IEclipseContext context;
	
	@PostConstruct
	void init() {
		VaaclipseApplication vaaApp = (VaaclipseApplication) app;
		context.set(VaaclipseApplication.class, vaaApp);
		
		BundleContext bundleContext = FrameworkUtil.getBundle(PreferencesAddon.class).getBundleContext();
		
		obtainPreferencesAuthService(bundleContext);
		
		Map<String, Bundle> bundlesByName = new HashMap<>();
		for (Bundle b : bundleContext.getBundles()) {
			bundlesByName.put(b.getSymbolicName(), b);
		}
		
		for (PreferencesPage page : vaaApp.getPreferencesPages()) {
			
			setTypedDefaultValues(page);
			
			initContributions(page);
			
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

	private void obtainPreferencesAuthService(BundleContext bundleContext) {
		
		ServiceReference<PreferencesAuthorization> ref = bundleContext.getServiceReference(PreferencesAuthorization.class);
		if (ref != null) {
			PreferencesAuthorization authService = bundleContext.getService(ref);
			context.set(PreferencesAuthorization.class, authService);
		}
		
		ServiceReference<PreferencesFactory> prefFactoryRef = bundleContext.getServiceReference(PreferencesFactory.class);
		if (prefFactoryRef != null) {
			PreferencesFactory service = bundleContext.getService(prefFactoryRef);
			context.set(PreferencesFactory.class, service);
		}
	}

	private void setTypedDefaultValues(PreferencesPage page) {
		for (FieldEditor<?> ed : page.getChildren()) {
			FieldEditor<Object> editor = (FieldEditor<Object>) ed;
			
			PreferencesSwitch<?> sw = new PreferencesSwitch() {
				@Override
				public Object caseBooleanFieldEditor(BooleanFieldEditor object) {
					if (object.getDefaultValue() == null)
						return false;
					return Boolean.valueOf(object.getDefaultValue());
				}
				
				@Override
				public Object caseScaleFieldEditor(ScaleFieldEditor object) {
					if (object.getDefaultValue() == null)
						return 0;
					return Integer.valueOf(object.getDefaultValue());
				}
				
				@Override
				public Object caseIntegerFieldEditor(IntegerFieldEditor object) {
					if (object.getDefaultValue() == null)
						return 0;
					return Integer.valueOf(object.getDefaultValue());
				}
			};
			Object converted = sw.doSwitch(editor);
			if (converted == null) {
				converted = editor.getDefaultValue();
				if (converted == null)
					converted = "";
			}
			editor.setDefaultValueTyped(converted);
			editor.setDefaultValue(converted.toString());
		}
	}
	
	@SuppressWarnings("restriction")
	private void initContributions(PreferencesPage page) {
		for (FieldEditor<?> ed : page.getChildren()) {
			if (ed instanceof MContribution) {
				MContribution editorWithContribution = (MContribution) ed;
				String contributorURI = editorWithContribution.getContributionURI();
				if (contributorURI != null) {
					IEclipseContext childContext = context.createChild();
					PrefHelper.populateInterfaces((FieldEditor<?>) editorWithContribution, childContext, editorWithContribution.getClass().getInterfaces());
					IContributionFactory contributionFactory = (IContributionFactory) childContext.get(IContributionFactory.class.getName());
					Object editorContribution = contributionFactory.create(contributorURI, childContext);
					editorWithContribution.setObject(editorContribution);
				}
			}
		}
	}
}
