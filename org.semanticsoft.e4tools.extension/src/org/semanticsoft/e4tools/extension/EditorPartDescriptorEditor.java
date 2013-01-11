package org.semanticsoft.e4tools.extension;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl;
import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;
import org.eclipse.jface.databinding.swt.IWidgetValueProperty;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import e4modelextension.E4modelextensionPackage;
import e4modelextension.EditorPartDescriptor;

public class EditorPartDescriptorEditor extends AbstractComponentEditor{
	
	private Composite composite;
	private EMFDataBindingContext context;
	
	@Inject
	@Optional
	private IProject project;
	
	@Inject
	public EditorPartDescriptorEditor() {
		super();
	}

	@Override
	public Image getImage(Object element, Display display) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabel(Object element) {
		return "Editor Descriptor";
	}

	@Override
	public String getDetailLabel(Object element) {
		EditorPartDescriptor desc = (EditorPartDescriptor) element;
		return desc.getLabel();
	}

	@Override
	public String getDescription(Object element) {
		return "Editor Descriptor is a template for editor parts";
	}

	@Override
	public Composite doGetEditor(Composite parent, Object object) {
		if( composite == null ) {
			context = new EMFDataBindingContext();
			composite = createForm(parent,context, getMaster());
		}
		getMaster().setValue(object);
		return composite;
	}
	
	protected Composite createForm(Composite parent, EMFDataBindingContext context, IObservableValue master) {
		parent = new Composite(parent,SWT.NONE);
		parent.setLayout(new GridLayout(3, false));

		IWidgetValueProperty textProp = WidgetProperties.text(SWT.Modify);

		// ------------------------------------------------------------
		{
			Label l = new Label(parent, SWT.NONE);
			l.setText("Id");

			Text t = new Text(parent, SWT.BORDER);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan=2;
			t.setLayoutData(gd);
			context.bindValue(textProp.observeDelayed(200,t), EMFEditProperties.value(getEditingDomain(), ApplicationPackageImpl.Literals.APPLICATION_ELEMENT__ELEMENT_ID).observeDetail(master));			
		}

		// ------------------------------------------------------------
		{
			Label l = new Label(parent, SWT.NONE);
			l.setText("Label");

			Text t = new Text(parent, SWT.BORDER);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan=2;
			t.setLayoutData(gd);
			context.bindValue(textProp.observeDelayed(200,t), EMFEditProperties.value(getEditingDomain(), UiPackageImpl.Literals.UI_LABEL__LABEL).observeDetail(master));			
		}
		
		// ------------------------------------------------------------
		{
			Label l = new Label(parent, SWT.NONE);
			l.setText("Tooltip");

			Text t = new Text(parent, SWT.BORDER);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan=2;
			t.setLayoutData(gd);
			context.bindValue(textProp.observeDelayed(200,t), EMFEditProperties.value(getEditingDomain(), UiPackageImpl.Literals.UI_LABEL__TOOLTIP).observeDetail(master));			
		}

		// ------------------------------------------------------------
		{
			Label l = new Label(parent, SWT.NONE);
			l.setText("Icon URI");

			Text t = new Text(parent, SWT.BORDER);
			t.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			context.bindValue(textProp.observeDelayed(200,t), EMFEditProperties.value(getEditingDomain(), UiPackageImpl.Literals.UI_LABEL__ICON_URI).observeDetail(master));

			Button b = new Button(parent, SWT.PUSH|SWT.FLAT);
			b.setImage(createImage(ResourceProvider.IMG_Obj16_zoom));
			b.setText("Find");			
		}
		
		// ------------------------------------------------------------
		{
			Label l = new Label(parent, SWT.NONE);
			l.setText("Content Filter");

			Text t = new Text(parent, SWT.BORDER);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan=2;
			t.setLayoutData(gd);
			context.bindValue(textProp.observeDelayed(200,t), EMFEditProperties.value(getEditingDomain(), E4modelextensionPackage.Literals.EDITOR_PART_DESCRIPTOR__URI_FILTER).observeDetail(master));			
		}
		
		// ------------------------------------------------------------
		{
			Label l = new Label(parent, SWT.NONE);
			l.setText("Class URI");

			Text t = new Text(parent, SWT.BORDER);
			t.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			context.bindValue(textProp.observeDelayed(200,t), EMFEditProperties.value(getEditingDomain(), BasicPackageImpl.Literals.PART_DESCRIPTOR__CONTRIBUTION_URI).observeDetail(master));

			final Button b = new Button(parent, SWT.PUSH|SWT.FLAT);
			b.setImage(createImage(ResourceProvider.IMG_Obj16_zoom));
			b.setText("Find");
			b.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ContributionClassDialog dialog = new ContributionClassDialog(b.getShell(),project,getEditingDomain(),(MPartDescriptor) getMaster().getValue(), BasicPackageImpl.Literals.PART_DESCRIPTOR__CONTRIBUTION_URI, Messages);
					dialog.open();
				}
			});
		}


		// ------------------------------------------------------------
		{
			Label l = new Label(parent, SWT.NONE);
			l.setText("Part Adding Logic");

			Text t = new Text(parent, SWT.BORDER);
			t.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			context.bindValue(textProp.observeDelayed(200,t), EMFEditProperties.value(getEditingDomain(), E4modelextensionPackage.Literals.EDITOR_PART_DESCRIPTOR__PART_ADDING_LOGIC_URI).observeDetail(master));

			final Button b = new Button(parent, SWT.PUSH|SWT.FLAT);
			b.setImage(createImage(ResourceProvider.IMG_Obj16_zoom));
			b.setText("Find");
			b.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ContributionClassDialog dialog = new ContributionClassDialog(b.getShell(), project,getEditingDomain(),(MPartDescriptor) getMaster().getValue(), E4modelextensionPackage.Literals.EDITOR_PART_DESCRIPTOR__PART_ADDING_LOGIC_URI, Messages);
					dialog.open();
				}
			});
		}
		
		return parent;
	}

	@Override
	public IObservableList getChildList(Object element) {
		// TODO Auto-generated method stub
		return null;
	}


}
