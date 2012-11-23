package org.semanticsoft.e4tools.extension;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.e4.tools.emf.ui.internal.common.VirtualEntry;
import org.eclipse.e4.tools.emf.ui.internal.common.component.ApplicationEditor;
import org.eclipse.emf.databinding.EMFProperties;

import e4modelextension.E4modelextensionPackage;

public class VaaclipseApplicationEditor extends ApplicationEditor {
	
	private static final String EDITOR_DESCRIPTORS_ID = VaaclipseApplicationEditor.class.getName() + ".EDITOR_DESCRIPTORS"; 
	private IListProperty EDITOR_DESCRIPTORS = EMFProperties.list(E4modelextensionPackage.Literals.VAACLIPSE_APPLICATION__EDITOR_DESCRIPTORS);
	
	
	@Inject
	public VaaclipseApplicationEditor() {
		super();
	}
	
	@Override
	public String getLabel(Object element) {
		return "Vaaclipse Application";
	}
	
	@Override
	public IObservableList getChildList(Object element) {
		IObservableList list = super.getChildList(element);
		VirtualEntry<Object> v = new VirtualEntry<Object>(EDITOR_DESCRIPTORS_ID,EDITOR_DESCRIPTORS,element,"Editor Part Descriptors") {

			@Override
			protected boolean accepted(Object o) {
				return true;
			}
		};
		list.add(v);
		return list;
	}

}
