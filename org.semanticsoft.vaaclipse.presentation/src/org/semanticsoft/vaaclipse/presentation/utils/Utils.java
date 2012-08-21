/*******************************************************************************
 * Copyright (c) 2011 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.utils;

import org.eclipse.emf.common.util.URI;

public class Utils {
	public static String convertPath(String uriString) {
		URI uri = URI.createURI(uriString);
		
		if (uri.segmentCount() < 5 || !"VAADIN".equals(uri.segment(2)) || !"themes".equals(uri.segment(3)))
			throw new IllegalArgumentException("Illegal path " + uriString + ". Path must start with VAADIN/themes/<theme_name>");
		
		StringBuilder bundlePath = new StringBuilder();
		for (int i = 5; i < uri.segmentCount(); i++) {
			if (bundlePath.length() != 0) {
				bundlePath.append("/");
			}
			bundlePath.append(uri.segment(i));
		}
		
		return "../" + uri.segment(4) + "/" + bundlePath.toString();
	}
}
