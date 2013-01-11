package org.semanticsoft.e4tools.extension;

import org.eclipse.emf.ecore.EClass;

import e4modelextension.E4modelextensionPackage;

public class VaaclipseApplicationEditorDesc implements IEditorDescriptor {

	public EClass getEClass() {
		return E4modelextensionPackage.Literals.VAACLIPSE_APPLICATION;
	}

	public Class<?> getEditorClass() {
		return VaaclipseApplicationEditor.class;
	}

}
