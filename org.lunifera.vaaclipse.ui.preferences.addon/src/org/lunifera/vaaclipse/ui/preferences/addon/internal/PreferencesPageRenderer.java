/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ComboFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ListEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.lunifera.vaaclipse.ui.preferences.model.RadioGroupFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.StringFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.util.PreferencesSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

import e4modelextension.VaaclipseApplication;

/**
 * @author rushan
 *
 */
public class PreferencesPageRenderer {

	@Inject
	VaaclipseApplication app;
	
	@Inject
	CssLayout pageLayout;
	
	@Inject
	PreferencesPage page;
	
	@Inject
	IEclipseContext context;
	
	Logger logger = LoggerFactory.getLogger(PreferencesPageRenderer.class);
	
	@PostConstruct
	public void init() {
		page.setRenderer(this);
	}

	@SuppressWarnings({ "unchecked", "restriction" })
	public void render() {
		pageLayout.addStyleName("preferences-page");
		
		for (FieldEditor<?> editor : page.getChildren()) {
			Class<? extends FieldEditorRenderer<?>> rendererClass = getRendererClass(editor);
			if (rendererClass != null) {
				IEclipseContext rendererContext = context.createChild();
				
				Class<?>[] interfaces = editor.getClass().getInterfaces();	
				populateInterfaces(editor, rendererContext, interfaces);
				FieldEditorRenderer<?> fieldRenderer = ContextInjectionFactory.make(rendererClass, rendererContext);
				fieldRenderer.render();
				Component fieldComponent = fieldRenderer.getComponent();
				editor.setWidget(fieldComponent);
				editor.setRenderer(fieldRenderer);
			}
			else {
				logger.warn("{} editor has no renderer. It is not rendered.", editor);
			}
		}
		
		//If there are contribution let it to layout this page
		if (page.getContributionURI() != null) {
			IContributionFactory contributionFactory = (IContributionFactory) context.get(IContributionFactory.class.getName());
			Object pageContribution = contributionFactory.create(page.getContributionURI(), context);
			page.setObject(pageContribution);
		}
		else {
			//If there are no contribution class for this page, adding with default layout and style 'field-editor'
			Label pageDesc = new Label(page.getDescription());
			pageDesc.addStyleName("field-editor");
			pageLayout.addComponent(pageDesc);
			for (FieldEditor<?> editor : page.getChildren()) {
				Component fieldComponent = (Component) editor.getWidget();
				fieldComponent.addStyleName("field-editor");
				pageLayout.addComponent(fieldComponent);	
			}
		}
	}

	private void populateInterfaces(FieldEditor<?> editor, IEclipseContext rendererContext, Class<?>[] interfaces) {
		for (Class<?> i : interfaces) {
			if (FieldEditor.class.isAssignableFrom(i)) {
				Class<FieldEditor<?>> editorInterface = (Class<FieldEditor<?>>) i;
				rendererContext.set(editorInterface, editor);
				populateInterfaces(editor, rendererContext, i.getInterfaces());
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Class<? extends FieldEditorRenderer<?>> getRendererClass(FieldEditor<?> editor) {
		
		PreferencesSwitch<?> switcher = new PreferencesSwitch() {
			@Override
			public Object caseBooleanFieldEditor(BooleanFieldEditor object) {
				return BooleanFieldEditorRenderer.class;
			}
			
			@Override
			public Object caseComboFieldEditor(ComboFieldEditor object) {
				return ComboFieldEditorRenderer.class;
			}
			
			@Override
			public Object caseListEditor(ListEditor object) {
				return ListEditorRenderer.class;
			}
			
			@Override
			public Object caseRadioGroupFieldEditor(RadioGroupFieldEditor object) {
				return RadioGroupFieldEditorRenderer.class;
			}
			
			@Override
			public Object caseStringFieldEditor(StringFieldEditor object) {
				return StringFieldEditorRenderer.class;
			}
		};
		return (Class<? extends FieldEditorRenderer<?>>) switcher.doSwitch(editor);
	}
	
	public void save() {
		for (FieldEditor<?> editor : page.getChildren()) {
			FieldEditorRenderer<?> renderer = (FieldEditorRenderer<?>) editor.getRenderer();
			renderer.save();
		}
	}
}
