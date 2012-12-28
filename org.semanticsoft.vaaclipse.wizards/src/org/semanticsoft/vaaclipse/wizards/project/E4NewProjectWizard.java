/*******************************************************************************
 * Copyright (c) 2006, 2012 Soyatec(http://www.soyatec.com) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Soyatec - initial API and implementation
 *     IBM Corporation - ongoing enhancements
 *     Sopot Cela - ongoing enhancements
 *     Lars Vogel - ongoing enhancements
 *     Wim Jongman - ongoing enhancements
 *     Rushan Gilmullin - adapt to vaaclipse
 *******************************************************************************/
package org.semanticsoft.vaaclipse.wizards.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MBindingContext;
import org.eclipse.e4.ui.model.application.commands.MBindingTable;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MCommandsFactory;
import org.eclipse.e4.ui.model.application.commands.MHandler;
import org.eclipse.e4.ui.model.application.commands.MKeyBinding;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.compiler.ast.InstanceOfExpression;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.pde.core.build.IBuildEntry;
import org.eclipse.pde.core.plugin.IMatchRules;
import org.eclipse.pde.core.plugin.IPluginElement;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.IPluginReference;
import org.eclipse.pde.internal.core.ICoreConstants;
import org.eclipse.pde.internal.core.build.WorkspaceBuildModel;
import org.eclipse.pde.internal.core.bundle.BundlePlugin;
import org.eclipse.pde.internal.core.bundle.BundlePluginBase;
import org.eclipse.pde.internal.core.bundle.WorkspaceBundleModel;
import org.eclipse.pde.internal.core.bundle.WorkspaceBundlePluginModel;
import org.eclipse.pde.internal.core.ibundle.IBundlePluginModelBase;
import org.eclipse.pde.internal.core.ibundle.IManifestHeader;
import org.eclipse.pde.internal.core.plugin.WorkspacePluginModelBase;
import org.eclipse.pde.internal.core.project.PDEProject;
import org.eclipse.pde.internal.core.text.bundle.BundleSymbolicNameHeader;
import org.eclipse.pde.internal.core.util.CoreUtility;
import org.eclipse.pde.internal.ui.PDEPlugin;
import org.eclipse.pde.internal.ui.PDEUIMessages;
import org.eclipse.pde.internal.ui.wizards.IProjectProvider;
import org.eclipse.pde.internal.ui.wizards.plugin.NewPluginProjectWizard;
import org.eclipse.pde.internal.ui.wizards.plugin.NewProjectCreationOperation;
import org.eclipse.pde.internal.ui.wizards.plugin.PluginFieldData;
import org.eclipse.pde.ui.IBundleContentWizard;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.PluginReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

/**
 * @author jin.liu (jin.liu@soyatec.com)
 */
public class E4NewProjectWizard extends NewPluginProjectWizard {

	private static final String PLUGIN_XML = "plugin.xml";
	private static final String MODEL_EDITOR_ID = "org.eclipse.e4.tools.emf.editor3x.e4wbm";
	private static final String APPLICATION_MODEL = "Application.e4xmi";
	private PluginFieldData fPluginData;
	private NewApplicationWizardPage fApplicationPage;
	private IProjectProvider fProjectProvider;
	private PluginContentPage fContentPage;
	private boolean isMinimalist;

	public E4NewProjectWizard() {
		fPluginData = new PluginFieldData();
	}

	public void addPages() {
		fMainPage = new E4NewProjectWizardPage(
				"main", fPluginData, false, getSelection()); //$NON-NLS-1$
		fMainPage.setTitle(PDEUIMessages.NewProjectWizard_MainPage_title);
		fMainPage.setDescription(PDEUIMessages.NewProjectWizard_MainPage_desc);
		String pname = getDefaultValue(DEF_PROJECT_NAME);
		if (pname != null)
			fMainPage.setInitialProjectName(pname);
		addPage(fMainPage);

		fProjectProvider = new IProjectProvider() {
			public String getProjectName() {
				return fMainPage.getProjectName();
			}

			public IProject getProject() {
				return fMainPage.getProjectHandle();
			}

			public IPath getLocationPath() {
				return fMainPage.getLocationPath();
			}
		};

		fContentPage = new PluginContentPage(
				"page2", fProjectProvider, fMainPage, fPluginData); //$NON-NLS-1$

		fApplicationPage = new NewApplicationWizardPage(fProjectProvider,
				fPluginData);

		addPage(fContentPage);
		addPage(fApplicationPage);
	}

	@SuppressWarnings("restriction")
	public boolean performFinish() {
		try {
			fMainPage.updateData();
			fContentPage.updateData();
			IDialogSettings settings = getDialogSettings();
			if (settings != null) {
				fMainPage.saveSettings(settings);
				fContentPage.saveSettings(settings);
			}
			
//			 BundleContext context = ToolsPlugin.getDefault().getBundle().getBundleContext();
//			 ServiceReference ref = context.getServiceReference(IBundleProjectService.class.getName());
//			 if (ref != null)
//			 {
//				 IBundleProjectService projectService = (IBundleProjectService)context.getService(ref);
//				 if (projectService != null)
//				 {
//					 IBundleProjectDescription description = projectService.getDescription(fProjectProvider.getProject());
//					 if (description != null)
//						 description.setSingleton(true); 
//				 }
//				 context.ungetService(ref);
//			 }

			// Create the project
			getContainer().run(
					false,
					true,
					new NewProjectCreationOperation(fPluginData,
							fProjectProvider, new ContentWizard()) {
						private WorkspacePluginModelBase model;

						@Override
						protected void setPluginLibraries(
								WorkspacePluginModelBase model)
								throws CoreException {
							this.model = model;
							super.setPluginLibraries(model);
						}
					});

			// Add Project to working set
			IWorkingSet[] workingSets = fMainPage.getSelectedWorkingSets();
			if (workingSets.length > 0)
				getWorkbench().getWorkingSetManager().addToWorkingSets(
						fProjectProvider.getProject(), workingSets);

			this.createApplicationResources(fProjectProvider.getProject(), new NullProgressMonitor());
			
			updateSingleton(fProjectProvider.getProject(), true);

			// Add the resources to build.properties
			adjustBuildPropertiesFile(fProjectProvider.getProject());

			// Open the model editor
			openEditorForApplicationModel();

			return true;
		} catch (InvocationTargetException e) {
			PDEPlugin.logException(e);
		} catch (InterruptedException e) {
		} catch (CoreException e) {
			PDEPlugin.logException(e);
		}
		return false;
	}

	/**
	 * Opens the model editor after the project was created.
	 * 
	 * @throws PartInitException
	 */
	private void openEditorForApplicationModel() throws PartInitException {
		IFile file = fProjectProvider.getProject().getFile(APPLICATION_MODEL);
		if (file != null) {
			FileEditorInput input = new FileEditorInput(file);
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			page.openEditor(input, MODEL_EDITOR_ID);
		}
	}

	/**
	 * Adds other resources to the build.properties file.
	 * 
	 * @param project
	 * @throws CoreException
	 */
	private void adjustBuildPropertiesFile(IProject project)
			throws CoreException {
		IFile file = PDEProject.getBuildProperties(project);
		if (file.exists()) {
			WorkspaceBuildModel model = new WorkspaceBuildModel(file);
			IBuildEntry e = model.getBuild().getEntry(IBuildEntry.BIN_INCLUDES);

			e.addToken(PLUGIN_XML);
			e.addToken(APPLICATION_MODEL);

			// Event though an icons directory is always created
			// it seems appropriate to only add it if it contains
			// some content
			if (!isMinimalist) {
				e.addToken("icons/");
			}
			
			model.save();
		}
	}
	
	protected void updateSingleton(IProject project, boolean singleton) {
		IFile man = project.getFile(ICoreConstants.BUNDLE_FILENAME_DESCRIPTOR);
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(man.getContents()));
			String line = null;
			StringBuffer manBuf = new StringBuffer();
			
			while ((line = reader.readLine()) != null) {
				manBuf.append(line + System.lineSeparator());
			}
			
			int i = manBuf.indexOf(Constants.BUNDLE_SYMBOLICNAME);
			if (i >= 0)
			{
				int ls = manBuf.indexOf(System.lineSeparator(), i);
				if (ls >= 0)
				{
					manBuf.insert(ls, ";singleton:=true");
				}
			}
			
			man.setContents(new StringBufferInputStream(manBuf.toString()), IResource.FORCE, new NullProgressMonitor());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * create products extension detail
	 * 
	 * @param project
	 */
	@SuppressWarnings("restriction")
	public void createApplicationResources(IProject project,
			IProgressMonitor monitor) {
		Map<String, String> map = fApplicationPage.getData();
		isMinimalist = !map.get(NewApplicationWizardPage.richSample)
				.equalsIgnoreCase("TRUE");
		if (map == null
				|| map.get(NewApplicationWizardPage.PRODUCT_NAME) == null)
			return;

		// If the project has invalid characters, the plug-in name would replace
		// them with underscores, product name does the same
		String pluginName = map.get(NewApplicationWizardPage.PRODUCT_NAME);

		// If there's no Activator created we create default package
		if (!fPluginData.doGenerateClass()) {
			String packageName = fPluginData.getId();
			IPath path = new Path(packageName.replace('.', '/'));
			if (fPluginData.getSourceFolderName().trim().length() > 0)
				path = new Path(fPluginData.getSourceFolderName()).append(path);

			try {
				CoreUtility.createFolder(project.getFolder(path));
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		IJavaProject javaProject = JavaCore.create(project);
		IPackageFragment fragment = null;

		try {
			for (IPackageFragment element : javaProject.getPackageFragments()) {
				if (element.getKind() == IPackageFragmentRoot.K_SOURCE) {
					fragment = element;
				}
			}
		} catch (JavaModelException e1) {
			e1.printStackTrace();
		}

		IFile file = project.getFile("css/main.css");

		try {
			prepareFolder(file.getParent(), monitor);

			URL corePath = ResourceLocator
					.getProjectTemplateFiles("css/main.css");
			file.create(corePath.openStream(), true, monitor);
		} catch (Exception e) {
			PDEPlugin.logException(e);
		}

		String template_id = "common";
		Set<String> binaryExtentions = new HashSet<String>();
		binaryExtentions.add(".gif");
		binaryExtentions.add(".png");

		Map<String, String> keys = new HashMap<String, String>();
		keys.put("projectName", pluginName);
		String elementName = fragment.getElementName();
		keys.put("handlersPackageName", (elementName.equals("") ? "" : elementName
				+ ".")
				+ "handlers");
		keys.put("loginPackageName", (elementName.equals("") ? "" : elementName
				+ ".")
				+ "login");
		keys.put("viewsPackageName", (elementName.equals("") ? "" : elementName
				+ ".")
				+ "views");
		keys.put(
				"programArgs",
				"true".equalsIgnoreCase(map.get(NewApplicationWizardPage.CLEAR_PERSISTED_STATE)) ? "-clearPersistedState" : "");
		keys.put(
				"contextPath", 
				!map.get(NewApplicationWizardPage.CONTEXT_PATH).trim().isEmpty() ? map.get(NewApplicationWizardPage.CONTEXT_PATH) 
						: "vaaclipse");
		
		keys.put(
				"port", 
				!map.get(NewApplicationWizardPage.PORT).trim().isEmpty() ? map.get(NewApplicationWizardPage.PORT) 
						: "8080");
		
		try {
			URL corePath = ResourceLocator.getProjectTemplateFiles(template_id);
			IRunnableWithProgress op = new TemplateOperation(corePath, project,
					keys, binaryExtentions, isMinimalist);
			getContainer().run(false, true, op);
		} catch (Exception e) {
			PDEPlugin.logException(e);
		}
		if (!isMinimalist) {
			try {
				URL corePath = ResourceLocator.getProjectTemplateFiles("src");
				IRunnableWithProgress op = new TemplateOperation(corePath,
						(IContainer) fragment.getResource(), keys,
						binaryExtentions, isMinimalist);
				getContainer().run(false, true, op);
			} catch (Exception e) {
				PDEPlugin.logException(e);
			}
		}
	}
	
	private MCommand createCommand(String commandId, String name,
			String className, String keyBinding, String projectName,
			IPackageFragment fragment, MApplication application) {
		MCommand command = MCommandsFactory.INSTANCE.createCommand();
		command.setCommandName(name);
		command.setElementId(commandId);
		application.getCommands().add(command);
		{
			// Create Quit handler for command
			MHandler quitHandler = MCommandsFactory.INSTANCE.createHandler();
			quitHandler.setCommand(command);
			String elementName = fragment.getElementName();
			quitHandler.setContributionURI("bundleclass://" + projectName + "/"
					+ (elementName.equals("") ? "" : elementName + ".")
					+ "handlers." + className);
			application.getHandlers().add(quitHandler);

			MKeyBinding binding = MCommandsFactory.INSTANCE.createKeyBinding();
			binding.setKeySequence(keyBinding);
			binding.setCommand(command);
			List<MBindingTable> tables = application.getBindingTables();
			if (tables.size() == 0) {
				MBindingContext rootContext = null;
				if (application.getRootContext().size() > 0) {
					rootContext = application.getRootContext().get(0);
				} else {
					rootContext = MCommandsFactory.INSTANCE
							.createBindingContext();
					rootContext
							.setElementId("org.eclipse.ui.contexts.dialogAndWindow");
					rootContext.setName("In Dialog and Windows");
					application.getRootContext().add(rootContext);
				}
				MBindingTable table = MCommandsFactory.INSTANCE
						.createBindingTable();
				table.setBindingContext(rootContext);
				tables.add(table);
			}
			tables.get(0).getBindings().add(binding);
		}
		return command;
	}

	private void prepareFolder(IContainer container, IProgressMonitor monitor)
			throws CoreException {
		IContainer parent = container.getParent();
		if (parent instanceof IFolder) {
			prepareFolder((IFolder) parent, monitor);
		}
		if (!container.exists() && container instanceof IFolder) {
			IFolder folder = (IFolder) container;
			folder.create(true, true, monitor);
		}
	}

	public String getPluginId() {
		return fPluginData.getId();
	}

	public String getPluginVersion() {
		return fPluginData.getVersion();
	}

	private class ContentWizard extends Wizard implements IBundleContentWizard {
		String[] dependencies = new String[] {
			 "javax.inject",
			 "org.eclipse.e4.core.di",
			 "org.eclipse.e4.core.contexts",
			 "com.vaadin",
			 "org.eclipse.e4.ui.model.workbench",
			 "org.eclipse.e4.ui.workbench",
			 "org.semanticsoft.e4modelextension",
			 "org.semanticsoft.vaaclipse.publicapi",
			 "org.eclipse.e4.core.services",
			 "org.semanticsoft.vaaclipse.theme",
			 "org.eclipse.osgi.services",
			 "org.eclipse.e4.core.di.extensions"
		 };
		
		public void init(IFieldData data) {
		}

		public IPluginReference[] getDependencies(String schemaVersion) {
			ArrayList<IPluginReference> result = new ArrayList<IPluginReference>(
					dependencies.length);
			for (String dependency : dependencies) {
				Bundle bundle = Platform.getBundle(dependency);
				String versionString = "0.0.0";
				if (bundle != null) {
					Version version = bundle.getVersion();
					versionString = version.getMajor() + "."
							+ version.getMinor() + "." + version.getMicro();
				}
				result.add(new PluginReference(dependency, versionString,
						IMatchRules.GREATER_OR_EQUAL));
			}
			return result.toArray(new IPluginReference[0]);
		}

		public String[] getNewFiles() {
			return new String[0];
		}

		public boolean performFinish(IProject project, IPluginModelBase model,
				IProgressMonitor monitor) {
			return true;
		}

		public String[] getImportPackages() {
			return new String[] { "javax.annotation;version=\"1.0.0\"" };
		}

		@Override
		public boolean performFinish() {
			return true;
		}

	}
}