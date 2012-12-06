/**
 * 
 */
package org.semanticsoft.vaaclipse.presentation.utils;

/**
 * @author rushan
 *
 */
public class Commons
{
	public static String trim(String value)
	{
		if (value != null)
		{
			value = value.trim();
			if (value.isEmpty())
				value = null;
		}
		return value;
	}
}
