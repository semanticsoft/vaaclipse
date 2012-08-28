/**
 * 
 */
package org.semanticsoft.vaadinaddons.boundsinfo.client.ui;

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
