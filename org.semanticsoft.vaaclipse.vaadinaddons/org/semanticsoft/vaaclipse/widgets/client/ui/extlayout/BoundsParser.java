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
package org.semanticsoft.vaaclipse.widgets.client.ui.extlayout;

/**
 * @author rushan
 *
 */
public class BoundsParser
{
	public static String toString(int x, int y, int dx, int dy)
	{
		return x + ";" + y + ";" + dx + ";" + dy;
	}
	
	public static int[] fromString(String str)
	{
		String[] strBounds = str.split(";");
		if (strBounds.length != 4)
			return null;
		return new int[] {
			Integer.parseInt(strBounds[0]),
			Integer.parseInt(strBounds[1]),
			Integer.parseInt(strBounds[2]),
			Integer.parseInt(strBounds[3])
		};
	}
}
