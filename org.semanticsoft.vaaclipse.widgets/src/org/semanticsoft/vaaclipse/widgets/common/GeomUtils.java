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
package org.semanticsoft.vaaclipse.widgets.common;


/**
 * @author rushan
 *
 */
public class GeomUtils {
	
	public static Integer findDockSide(int x0, int y0, int dx, int dy, double docX, double docY, Vector mousePos) 
	{
		if (GeometryHelper.containsInRectangle(Vector.valueOf(x0 + docX, y0 + docY), Vector.valueOf(dx - 2*docX, dy - 2*docY), mousePos))
			return Side.CENTER;
		else if (
			GeometryHelper.containsInRectangle(Vector.valueOf(x0 + docX, y0 + dy - docY), Vector.valueOf(dx - 2*docX, docY),mousePos) ||
			GeometryHelper.containsInRightTriangle(Vector.valueOf(x0 + docX, y0 + dy), -docX, -docY, mousePos) ||
			GeometryHelper.containsInRightTriangle(Vector.valueOf(x0 + dx - docX, y0 + dy), docX, -docY, mousePos)
		)
		{
			return Side.BOTTOM;
		}
		else if (
			GeometryHelper.containsInRectangle(Vector.valueOf(x0, y0 + docY), new Vector(docX, dy - 2*docY), mousePos) ||
			GeometryHelper.containsInRightTriangle(Vector.valueOf(x0, y0 + docY), docX, -docY, mousePos) ||
			GeometryHelper.containsInRightTriangle(Vector.valueOf(x0, y0 + dy - docY), docX, docY, mousePos)
		)
		{
			return Side.LEFT;
		}
		else if (
			GeometryHelper.containsInRectangle(Vector.valueOf(x0 + dx - docX, y0 + docY), new Vector(docX, dy - 2*docY), mousePos) ||
			GeometryHelper.containsInRightTriangle(Vector.valueOf(x0 + dx, y0 + docY), -docX, -docY, mousePos) ||
			GeometryHelper.containsInRightTriangle(Vector.valueOf(x0 + dx, y0 + dy - docY), -docX, docY, mousePos)
		)
		{
			return Side.RIGHT;
		}
		else if (
			GeometryHelper.containsInRectangle(Vector.valueOf(x0 + docX, y0), new Vector(dy - 2*docY, docY), mousePos) ||
			GeometryHelper.containsInRightTriangle(Vector.valueOf(x0 + docX, y0), -docX, docY, mousePos) ||
			GeometryHelper.containsInRightTriangle(Vector.valueOf(x0 + dx - docX, y0), docX, docY, mousePos)
		)
		{
			return Side.TOP;
		}
		else
			return null;
	}
	
}
