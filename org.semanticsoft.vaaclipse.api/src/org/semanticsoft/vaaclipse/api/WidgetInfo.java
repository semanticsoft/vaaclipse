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

package org.semanticsoft.vaaclipse.api;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.semanticsoft.commons.geom.Bounds;

/**
 * @author rushan
 * 
 */
public interface WidgetInfo {
	Bounds getBounds(MWindow window);

	Bounds getBounds(MWindow window, MUIElement element);

	void invalidateBounds(MWindow window);

	Object getParent(Object widget);
}
