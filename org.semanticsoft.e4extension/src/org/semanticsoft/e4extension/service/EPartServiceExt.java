/**
 * 
 */
package org.semanticsoft.e4extension.service;

import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import e4modelextension.EditorPartDescriptor;

/**
 * @author rushan
 *
 */
public interface EPartServiceExt
{
	public static interface PartAddLogic {
		void addMPart(MPart mpart);
	}
	
	/**
	 * Creates a new input part of the given id.
	 * 
	 * @param id
	 *            the identifier of the part, must not be <code>null</code>
	 * @return a new part of the given id, or <code>null</code> if no part descriptors can be found
	 *         that match the specified id
	 */
	MInputPart createInputPart(String id);
	
	MPartDescriptor findDescriptor(String id);
	
	EditorPartDescriptor findEditorPartDescriptor(String inputUri);
	
	MInputPart openUri(MElementContainer<?> area, String inputUri);
}
