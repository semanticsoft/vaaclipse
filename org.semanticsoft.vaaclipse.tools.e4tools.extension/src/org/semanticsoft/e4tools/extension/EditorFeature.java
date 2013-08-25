package org.semanticsoft.e4tools.extension;

import java.util.Collections;
import java.util.List;

import org.eclipse.e4.tools.emf.ui.common.IEditorFeature;
import org.eclipse.e4.ui.model.fragment.impl.FragmentPackageImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import e4modelextension.E4modelextensionPackage;

public class EditorFeature implements IEditorFeature {

	@Override
	public List<FeatureClass> getFeatureClasses(EClass eClass,
			EStructuralFeature feature) {
		if( eClass == FragmentPackageImpl.Literals.MODEL_FRAGMENT ) {
			if( feature == FragmentPackageImpl.Literals.MODEL_FRAGMENT__ELEMENTS ) {
				return Collections.singletonList(new FeatureClass(E4modelextensionPackage.Literals.EDITOR_PART_DESCRIPTOR.getName(), E4modelextensionPackage.Literals.EDITOR_PART_DESCRIPTOR));
			}
		}
		return Collections.emptyList();
	}

}
