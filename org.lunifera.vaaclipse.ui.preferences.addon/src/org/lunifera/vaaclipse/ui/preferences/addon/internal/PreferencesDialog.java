/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.common.util.EList;
import org.lunifera.vaaclipse.ui.preferences.addon.PreferencesAuthorization;
import org.lunifera.vaaclipse.ui.preferences.addon.PreferencesEvents;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.semanticsoft.vaaclipse.publicapi.resources.BundleResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import e4modelextension.VaaclipseApplication;

/**
 * @author rushan
 *
 */
public class PreferencesDialog {
	
	static final String PAGE_HEADER_ERROR_TEXT = "page-header-error-text";
	
	@Inject
	IEventBroker eventBroker;
	
	@Inject
	VaaclipseApplication app;
	
	@Inject
	IEclipseContext context;
	
	@Inject
	@Optional
	PreferencesCategory selectedCategory;
	
	@Inject
	@Optional
	PreferencesAuthorization authService;
	
	Logger logger = LoggerFactory.getLogger(PreferencesDialog.class);
	
	@Inject
	UI ui;
	
	@Inject
	@Named(value = "username")
	@Optional
	String username;
	
	TextField filterField = new TextField();

	private Tree tree;

	private Window window;
	
	HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

	private Panel treePanel;

	private VerticalLayout rightSide;

	private CssLayout leftSide;
	
	private Button clearFilterButton = new Button();

	private CssLayout pageHeader;

	private CssLayout pageContent;

	private CssLayout pageBottom;

	private Button restoreDefaults;

	private Button apply;

	private VerticalLayout root;

	private CssLayout dlgButtonPanel;

	private Button cancelButton;

	private Button okButton;

	private Label pageHeaderText;

	private BundleContext bundleContext;

	private PreferencesFactory preferencesFactory;

	private String errorMessage;
	
	List<PreferencesPage> visitedPages = new ArrayList<>();
	
	public Window getWindow() {
		return window;
	}
	
	public PreferencesCategory getSelectedCategory() {
		return selectedCategory;
	}
	
	public PreferencesPage getCurrentPage() {
		return selectedCategory.getPage();
	}
	
	@PostConstruct
	public void init() {
		
		bundleContext = FrameworkUtil.getBundle(PreferencesDialog.class).getBundleContext();
		ServiceReference<PreferencesFactory> ref = bundleContext.getServiceReference(PreferencesFactory.class);
		if (ref != null) {
			preferencesFactory = bundleContext.getService(ref);
		}
		
		if (selectedCategory == null && !app.getPreferencesCategories().isEmpty()) {
			selectedCategory = app.getPreferencesCategories().get(0);
		}
		
		createWindow();
		createSearchField();
		createTree();
		
		addListeners();
	}

	public void createWindow() {
		window = new Window();
		window.addStyleName("preferences-dialog");
		window.setWidth("800px");
		window.setHeight("600px");
		
		splitPanel.setSizeFull();		
		
		dlgButtonPanel = new CssLayout();
		dlgButtonPanel.setWidth("100%");
		dlgButtonPanel.addStyleName("dlg-button-panel");
		
		//dlgButtonPanel.addComponent(new Label("<hr/>", ContentMode.HTML));
		
		cancelButton = new Button("Cancel");
		cancelButton.addStyleName("cancel-button");
		dlgButtonPanel.addComponent(cancelButton);
		okButton = new Button("OK");
		okButton.addStyleName("ok-button");
		dlgButtonPanel.addComponent(okButton);
		
		root = new VerticalLayout();
		root.setSizeFull();
		window.setContent(root);
		root.addComponent(splitPanel);
		root.addComponent(dlgButtonPanel);
		root.setExpandRatio(splitPanel, 10);
		root.setExpandRatio(dlgButtonPanel, 0);
		
		treePanel = new Panel();
		treePanel.addStyleName("categories-panel");
		treePanel.addStyleName("borderless");
		treePanel.setSizeFull();
		
		leftSide = new CssLayout();
		leftSide.addStyleName("categories");
		leftSide.setSizeFull();
		filterField.setWidth("80%");
		filterField.addStyleName("categories-filter");
		leftSide.addComponent(filterField);
		
		clearFilterButton.setIcon(BundleResource.valueOf("platform:/plugin/org.lunifera.vaaclipse.ui.preferences.addon/img/clear.png"));
		clearFilterButton.addStyleName("vaaclipsebutton");
		clearFilterButton.addStyleName("icon-only");
		clearFilterButton.addStyleName("clear-filter-button");
		leftSide.addComponent(clearFilterButton);
		leftSide.addComponent(treePanel);
		
		rightSide = new VerticalLayout();
		rightSide.setSizeFull();
		//rightSide.setMargin(new MarginInfo(true, true, false, true));
		
		pageHeader = new CssLayout();
		pageHeader.addStyleName("page-header-panel");
		pageHeader.setWidth("100%");
		pageHeaderText = new Label("Page");
		pageHeaderText.addStyleName("page-header-text");
		pageHeader.addComponent(pageHeaderText);
		rightSide.addComponent(pageHeader);
		
		pageContent = new CssLayout();
		pageContent.setSizeFull();
		rightSide.addComponent(pageContent);
		
		pageBottom = new CssLayout();
		pageBottom.addStyleName("page-bottom-panel");
		pageBottom.setWidth("100%");
		rightSide.addComponent(pageBottom);
		
		apply = new Button("Apply");
		apply.addStyleName("apply");
		pageBottom.addComponent(apply);
		
		restoreDefaults = new Button("RestoreDefaults");
		restoreDefaults.addStyleName("restore-defaults");
		pageBottom.addComponent(restoreDefaults);
		
		splitPanel.addComponent(leftSide);
		splitPanel.addComponent(rightSide);
		splitPanel.setSplitPosition(30, Unit.PERCENTAGE);
		
		rightSide.setExpandRatio(pageHeader, 0);
		rightSide.setExpandRatio(pageContent, 1);
		rightSide.setExpandRatio(pageBottom, 0);
		
		clearFilterButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				filterField.setValue("");
				refreshTree();
			}
		});
	}
	
	public void createSearchField() {
		filterField.addTextChangeListener(new TextChangeListener() {
			
			@Override
			public void textChange(TextChangeEvent event) {
				refreshTree();
			}
		});
	}
	
	private void createTree() {
		tree = new Tree();
		tree.setSizeFull();
		tree.setImmediate(true);
		treePanel.setContent(tree);
		
		tree.addContainerProperty("name", String.class, "NoName");
		tree.setItemCaptionPropertyId("name");
		
		refreshTree();
		
		tree.addItemClickListener(new ItemClickEvent.ItemClickListener() {

			public void itemClick(final ItemClickEvent event)
			{
				if (event.getButton() == MouseButton.LEFT)
				{
					PreferencesCategory selectedCat = (PreferencesCategory) event.getItemId();
					if (selectedCat != null) {
						openPreferencePageForCategory(selectedCat);
					}
				}
			}
		});
		
		if (selectedCategory != null) {
			tree.select(selectedCategory);
			openPreferencePageForCategory(selectedCategory);
		}
	}
	
	private void refreshTree()
	{
		tree.removeAllItems();
		
		EList<PreferencesCategory> catList = app.getPreferencesCategories();
		
		fillCategories(catList, null);
		
		for (Object id : tree.rootItemIds())
		{
			tree.expandItem(id);
		}
	}

	@SuppressWarnings("unchecked")
	private void fillCategories(List<PreferencesCategory> list, PreferencesCategory parentCat) {
		String search = filterField.getValue().trim();
		for (PreferencesCategory c : list) {
			if (c.getName() == null)
				c.setName("No Name");
			if (search.isEmpty() || c.getName().contains(search)) {
				
				Item catItem = tree.addItem(c);
				catItem.getItemProperty("name").setValue(c.getName());
				if (c.getChildCategories().isEmpty())
					tree.setChildrenAllowed(c, false);
				
				if (parentCat != null)
					tree.setParent(c, parentCat);
				
				fillCategories(c.getChildCategories(), c);
			}
		}
	}
	
	private void openPreferencePageForCategory(PreferencesCategory selectedCat) {
		selectedCategory = selectedCat;
		refreshHeaderMessage();
		
		pageContent.removeAllComponents();
		
		if (selectedCat.getPage() != null) {
			
			if (authService != null && username != null && !authService.isAllowed(selectedCat.getPage(), username)) {
				pageContent.addComponent(new Label("Access to this page restricted."));
			}
			else {
				IEclipseContext pageContext = context.createChild();
				pageContext.set(CssLayout.class, pageContent);
				pageContext.set(PreferencesPage.class, selectedCat.getPage());
				PreferencesPageRenderer pageRenderer = ContextInjectionFactory.make(PreferencesPageRenderer.class, pageContext);
				pageRenderer.render();
				visitedPages.add(selectedCat.getPage());	
			}
		} else if (!selectedCat.getChildCategories().isEmpty()) {
			pageContent.addComponent(new Label("Expand the tree to edit a preferences for a specific feature"));
		}
	}
	
	private void addListeners() {
		
		restoreDefaults.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				restoreDefaults();
			}
		});
		
		apply.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				applyChangesOnCurrentPage();
			}
		});
		
		okButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				closeWithSave();
			}
		});
		
		cancelButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				closeWithoutSave();
			}
		});
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		refreshHeaderMessage();
	}
	
	public void clearErrorMessage() {
		this.errorMessage = null;
		refreshHeaderMessage();
	}
	
	public void refreshHeaderMessage() {
		if (errorMessage != null) {
			pageHeaderText.addStyleName(PAGE_HEADER_ERROR_TEXT);	
			pageHeaderText.setValue(errorMessage);
		}
		else {
			pageHeaderText.removeStyleName(PAGE_HEADER_ERROR_TEXT);
			pageHeaderText.setValue(selectedCategory.getName());
		}
	}
	
	private void restoreDefaults() {
		PreferencesPage currentPage = getCurrentPage();
		if (currentPage != null) {
			try {
				restoreDefaultsOnPage(currentPage);
			} catch (Exception e) {
				Notification.show("Error restoring defaults", "Restoring defaults on this page failed", Notification.Type.ERROR_MESSAGE);
				return;
			}
			fireEvent(PreferencesEvents.PREFERENCES_TO_DEFAULTS, currentPage);
		}
	}
	
	private void closeWithSave() {
		
		applyChangesOnAllVisitedPages();
		
		ui.removeWindow(window);
	}
	
	private void closeWithoutSave() {
		ui.removeWindow(window);
	}
	
	private void applyChangesOnCurrentPage() {
		PreferencesPage currentPage = getCurrentPage();
		if (currentPage != null) {
			try {
				applyChangesOnPage(currentPage);
			} catch (Exception e) {
				Notification.show("Error apply changes", "Applying changes on this page failed", Notification.Type.ERROR_MESSAGE);
				return;
			}
			fireEvent(PreferencesEvents.PREFERENCES_APPLIED, currentPage);
		}
	}
	
	private void applyChangesOnAllVisitedPages() {
		Exception exception = null;
		for (PreferencesPage page : visitedPages) {
			try {
				applyChangesOnPage(page);
			} catch (Exception e) {
				exception = e;
			}
		}
		if (exception != null)
			Notification.show("Error apply changes", "Saving changes was failed on some pages", Notification.Type.ERROR_MESSAGE);
		else {
			fireEvent(PreferencesEvents.PREFERENCES_APPLIED, (PreferencesPage[]) visitedPages.toArray(new PreferencesPage[visitedPages.size()]));
		}
	}
	
	private void applyChangesOnPage(PreferencesPage page) throws BackingStoreException {
		PreferencesPageRenderer pageRenderer = (PreferencesPageRenderer) page.getRenderer();
		try {
			pageRenderer.save();	
		}
		catch (Exception e) {
			logger.error("Error copy changes from UI to preferences for category {}", selectedCategory.getName(), e);
			throw e;
		}
		
		Preferences preferences = (Preferences) page.getPreferences();
		try {
			preferences.flush();
		} catch (BackingStoreException e) {
			logger.error("Error flushing changes for preference {} with category {}", preferences, page.getCategory().getName(), e);
			throw e;
		}
	}
	
	private void restoreDefaultsOnPage(PreferencesPage page) throws BackingStoreException {
		Preferences preferences = (Preferences) page.getPreferences();
		
		try {
			for (FieldEditor<?> fieldEditor : page.getChildren()) {
				String defaultValue = fieldEditor.getDefaultValue();
				if (defaultValue != null)
					preferences.put(fieldEditor.getPreferenceName(), defaultValue);
			}
		}
		catch (Exception e) {
			logger.error("Error copy default values from model to preferences for category {}", page.getCategory().getName(), e);
			throw e;
		}
		
		try {
			preferences.flush();
		} catch (BackingStoreException e) {
			logger.error("Error flushing changes for preference {} with category {}", preferences, page.getCategory().getName(), e);
			throw e;
		}		
	}
	
	private void fireEvent(String topic, PreferencesPage... pages) {
		eventBroker.send(topic, pages);
	}
}
