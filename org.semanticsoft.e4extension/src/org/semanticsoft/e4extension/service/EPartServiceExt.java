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

package org.semanticsoft.e4extension.service;

import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

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
	
	/**
	 * Open the give uri in area with id = org.eclipse.ui.editorss (eclipse conditions)
	 * @param inputUri given uri
	 * @return part that was opened or finded with this uri
	 */
	MInputPart openUri(String inputUri);
	
	/**
	 * Open given uri in given are
	 * @param area
	 * @param inputUri
	 * @return
	 */
	MInputPart openUri(MElementContainer<?> area, String inputUri);
	
	void closeUri(String inputUri, boolean saveBeforeClose);
	
	/**
	 * Fixed method EPartService.showPart. Current method search any opened shared part and if allowMultiples == false (default) it
	 * create placeholder on this part rather creating new. The differences from original showPart is that it search shared part in any perspective,
	 * the original showPart search shared part in active perspective. I can not say is this bug or general contract of this method...
	 * @param id
	 * @param partState
	 * @return
	 */
	MPart showPart(String id, PartState partState);
}
