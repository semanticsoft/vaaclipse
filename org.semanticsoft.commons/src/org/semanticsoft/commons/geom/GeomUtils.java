/**
 * 
 */
package org.semanticsoft.commons.geom;


/**
 * @author rushan
 *
 */
public class GeomUtils {
	public static enum Side
	{
		LEFT, RIGHT, TOP, BOTTOM, CENTER
	}
	
	public static Side findDockSide(int x0, int y0, int dx, int dy, double docX, double docY, Vector mousePos) 
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
