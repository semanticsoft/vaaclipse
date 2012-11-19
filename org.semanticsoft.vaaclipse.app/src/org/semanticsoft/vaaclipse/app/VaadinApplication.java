/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.app;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.core.services.translation.TranslationProviderFactory;
import org.eclipse.e4.core.services.translation.TranslationService;
import org.eclipse.e4.ui.internal.workbench.ActiveChildLookupFunction;
import org.eclipse.e4.ui.internal.workbench.ActivePartLookupFunction;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.ExceptionHandler;
import org.eclipse.e4.ui.internal.workbench.ModelServiceImpl;
import org.eclipse.e4.ui.internal.workbench.ReflectionContributionFactory;
import org.eclipse.e4.ui.internal.workbench.ResourceHandler;
import org.eclipse.e4.ui.internal.workbench.WorkbenchLogger;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IExceptionHandler;
import org.eclipse.e4.ui.workbench.IModelResourceHandler;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;

import com.vaadin.Application;
import com.vaadin.ui.Window;

public class VaadinApplication extends Application
{
	private static final long serialVersionUID = 1L;
	
	public static final String THEME_ID = "cssTheme";
	
	protected static String presentationEngineURI = "bundleclass://org.semanticsoft.vaaclipse.presentation/"
			+ "org.semanticsoft.vaaclipse.presentation.engine.VaadinPresentationEngine";

	protected Logger logger;

	private String[] args;
	private IModelResourceHandler modelResourceHandler;

	private E4Workbench e4Workbench;

	private Location instanceLocation;

	private IApplicationContext context;

	private VaadinOSGiServlet servlet;
	
	private Map<String, MUIElement> id2element = new HashMap<String, MUIElement>();
	
	public VaadinApplication(VaadinOSGiServlet servlet)
	{
		this.servlet = servlet;
	}
	
	@Override
	public void init()
	{
		context = VaadinE4Application.getInstance().getAppContext();
		
		String themeName = VaadinE4Application.getInstance().getUserVaadinTheme();
		setTheme(themeName);
		
		logger = VaadinE4Application.getInstance().getLogger();
		Window mainWindow = new Window("Vaaclipse");
		setMainWindow(mainWindow);
		
		//--user agent detection
//		if (this.getContext() instanceof WebApplicationContext) {
//		   String userAgent = ((WebApplicationContext)this.getContext()).getBrowser().getBrowserApplication();
//		   if (userAgent.contains("MSIE"))
//		   {
//			   String str = "<html><br/>Vaaclipse currently does not support Internet Explorer.<br/><br/>" +
//			   		"Please use one of the browser from list:<br/><ul><li>Mozilla Firefox (recomended)</li> <li>Google Chrome or Chromium</li> <li>Opera</li> <li>Safari</li> <li>Rekonq</li> <li>Any other not listed browser based on Webkit</li></ul></html>";
//			   Label errorLabel = new Label(str, Label.CONTENT_XHTML);
//			   mainWindow.getContent().addComponent(errorLabel);
//			   return;
//		   }
//		}
		//--
		
		//-------------------------------------
		e4Workbench = createE4Workbench(context);
		e4Workbench.createAndRunUI(e4Workbench.getApplication());

//		try {
//			if (e4Workbench != null && e4Workbench.getContext() != null) {
//				modelResourceHandler.save();
//				e4Workbench.close();
//				logger.debug("workbench model saved");
//			}
//		} catch (IOException e) {
//			System.out.println("Warning: cannot save workbench model.");
//		} finally {
//			if (instanceLocation != null) {
//				instanceLocation.release();
//			}
//		}
	}
	
	public E4Workbench createE4Workbench(IApplicationContext applicationContext) {
		logger.debug("VaadinE4Application.createE4Workbench()");

		args = (String[]) applicationContext.getArguments().get(IApplicationContext.APPLICATION_ARGS);

		IEclipseContext appContext = createDefaultContext(applicationContext);
		appContext.set("e4ApplicationInstanceId","Sopot");
		appContext.set("vaadinapp", this);
		appContext.set(Application.class, this);
		appContext.set(VaadinOSGiCommunicationManager.class, servlet.getCommunicationManager());

		// Create the app model and its context
		MApplication appModel = loadApplicationModel(applicationContext, appContext);
		fixNullElementIds(appModel);
		appModel.setContext(appContext);
		appContext.set(MApplication.class.getName(), appModel);

		// Create the addons
		IContributionFactory factory = (IContributionFactory) appContext.get(IContributionFactory.class.getName());
		for (MContribution addon : appModel.getAddons()) {
			Object obj = factory.create(addon.getContributionURI(), appContext);
			addon.setObject(obj);
		}
		
		E4Workbench e4Workbench = new E4Workbench(appModel, appContext);
		return e4Workbench;
	}
	
	private void fixNullElementIds(MUIElement element)
	{
		if (!(element instanceof MApplication))
		{
			if (element.getElementId() == null || element.getElementId().trim().isEmpty())
			{
				element.setElementId(UUID.randomUUID().toString());
			}
			else
			{
				//check that there are not element in model with this id
				//MUIElement someElement = modelService.find(element.getElementId(), app); //this search recursive - very long, so use map
				MUIElement someElement = id2element.get(element.getElementId());
				if (someElement != null && someElement != element)
				{
					final String randomUUID = UUID.randomUUID().toString();
					element.setElementId(element.getElementId() + "_" + randomUUID);
				}
			}
			
			id2element.put(element.getElementId(), element);
		}
		
		if (element instanceof MElementContainer<?>)
		{
			for (MUIElement child : ((MElementContainer<?>) element).getChildren())
			{
				fixNullElementIds(child);
			}
		}
	}

	private MApplication loadApplicationModel(IApplicationContext appContext, IEclipseContext eclipseContext) {
		logger.debug("VaadinE4Application.loadApplicationModel()");
		MApplication theApp = null;

		instanceLocation = VaadinE4Application.getInstance().getInstanceLocation();

		String appModelPath = getArgValue(E4Workbench.XMI_URI_ARG, appContext, false);
		Assert.isNotNull(appModelPath, E4Workbench.XMI_URI_ARG + " argument missing"); //$NON-NLS-1$
		final URI initialWorkbenchDefinitionInstance = URI.createPlatformPluginURI(appModelPath, true);

		eclipseContext.set(E4Workbench.INITIAL_WORKBENCH_MODEL_URI, initialWorkbenchDefinitionInstance);
		eclipseContext.set(E4Workbench.INSTANCE_LOCATION, instanceLocation);

		// Save and restore
		boolean saveAndRestore;
		String value = getArgValue(E4Workbench.PERSIST_STATE, appContext, false);

		saveAndRestore = value == null || Boolean.parseBoolean(value);

		eclipseContext.set(E4Workbench.PERSIST_STATE, false);

		// Persisted state
		boolean clearPersistedState;
		value = getArgValue(E4Workbench.CLEAR_PERSISTED_STATE, appContext, true);
		clearPersistedState = value != null && Boolean.parseBoolean(value);
		eclipseContext.set(E4Workbench.CLEAR_PERSISTED_STATE, false);

		// Delta save and restore
		boolean deltaRestore;
		value = getArgValue(E4Workbench.DELTA_RESTORE, appContext, false);
		deltaRestore = value == null || Boolean.parseBoolean(value);
		eclipseContext.set(E4Workbench.DELTA_RESTORE, Boolean.valueOf(deltaRestore));

		String resourceHandler = getArgValue(E4Workbench.MODEL_RESOURCE_HANDLER, appContext, false);

		if (resourceHandler == null) {
			resourceHandler = "bundleclass://org.eclipse.e4.ui.workbench/" + ResourceHandler.class.getName();
		}

		IContributionFactory factory = eclipseContext.get(IContributionFactory.class);

		modelResourceHandler = (IModelResourceHandler) factory.create(resourceHandler, eclipseContext);

		Resource resource = modelResourceHandler.loadMostRecentModel();
		theApp = (MApplication) resource.getContents().get(0);

		return theApp;
	}

	private String getArgValue(String argName, IApplicationContext appContext, boolean singledCmdArgValue) {
		// Is it in the arg list ?
		if (argName == null || argName.length() == 0) {
			return null;
		}

		if (singledCmdArgValue) {
			for (int i = 0; i < args.length; i++) {
				if (("-" + argName).equals(args[i])) {
					return "true";
				}
			}
		} else {
			for (int i = 0; i < args.length; i++) {
				if (("-" + argName).equals(args[i]) && i + 1 < args.length) {
					return args[i + 1];
				}
			}
		}

		final String brandingProperty = appContext.getBrandingProperty(argName);
		return brandingProperty == null ? System.getProperty(argName) : brandingProperty;
	}

	public IEclipseContext createDefaultContext(IApplicationContext applicationContext) {
		IEclipseContext serviceContext = E4Workbench.getServiceContext();
		final IEclipseContext eclipseContext = serviceContext.createChild("WorkbenchContext"); //$NON-NLS-1$

		IExtensionRegistry registry = RegistryFactory.getRegistry();
		ReflectionContributionFactory contributionFactory = new ReflectionContributionFactory(registry);
		eclipseContext.set(IContributionFactory.class.getName(), contributionFactory);

		eclipseContext.set(Logger.class.getName(), ContextInjectionFactory.make(WorkbenchLogger.class, eclipseContext));

		String presentationURI = getArgValue(E4Workbench.PRESENTATION_URI_ARG, applicationContext, false);
		if (presentationURI == null) {
			presentationURI = presentationEngineURI;
		}
		eclipseContext.set(E4Workbench.PRESENTATION_URI_ARG, presentationURI);

		// eclipseContext.set(EModelService.class, new
		// ModelServiceImpl(eclipseContext));

		String themeId = getArgValue(THEME_ID, applicationContext, false);
		eclipseContext.set(THEME_ID, themeId);

		String cssURI = getArgValue(E4Workbench.CSS_URI_ARG, applicationContext, false);
		if (cssURI != null) {
			eclipseContext.set(E4Workbench.CSS_URI_ARG, cssURI);
		}

		// Temporary to support old property as well
		if (cssURI != null && !cssURI.startsWith("platform:")) {
			logger.warn("Warning " + cssURI + " changed its meaning it is used now to run without theme support");
			eclipseContext.set(THEME_ID, cssURI);
		}

		String cssResourcesURI = getArgValue(E4Workbench.CSS_RESOURCE_URI_ARG, applicationContext, false);
		eclipseContext.set(E4Workbench.CSS_RESOURCE_URI_ARG, cssResourcesURI);

		eclipseContext.set(EModelService.class, new ModelServiceImpl(eclipseContext));

		// translation
		String locale = Locale.getDefault().toString();
		serviceContext.set(TranslationService.LOCALE, locale);
		TranslationService bundleTranslationProvider = TranslationProviderFactory
				.bundleTranslationService(serviceContext);
		serviceContext.set(TranslationService.class, bundleTranslationProvider);

		ExceptionHandler exceptionHandler = new ExceptionHandler();
		eclipseContext.set(IExceptionHandler.class.getName(), exceptionHandler);
		eclipseContext.set(IExtensionRegistry.class.getName(), registry);

		// setup for commands and handlers
		eclipseContext.set(IServiceConstants.ACTIVE_PART, new ActivePartLookupFunction());

		eclipseContext.set(IServiceConstants.ACTIVE_SHELL, new ActiveChildLookupFunction(
				IServiceConstants.ACTIVE_SHELL, E4Workbench.LOCAL_ACTIVE_SHELL));

		return eclipseContext;
	}
}
