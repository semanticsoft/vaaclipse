package org.semanticsoft.e4tools.extension;

import org.eclipse.e4.tools.emf.ui.common.IEditorDescriptor;
import org.eclipse.emf.ecore.EClass;

import e4modelextension.E4modelextensionPackage;

public class EditorPartDescriptorEditorDesc implements IEditorDescriptor {

	public EClass getEClass() {
		return E4modelextensionPackage.Literals.EDITOR_PART_DESCRIPTOR;
	}

	public Class<?> getEditorClass() {
		return EditorPartDescriptorEditor.class;
	}

}
