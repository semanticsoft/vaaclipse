/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal.exception;

/**
 * @author rushan
 *
 */
public class ValidationFailedException extends Exception {
	public ValidationFailedException(String label, String msg) {
		super(String.format("Field '%s' is not valid. %s", label, msg));
	}
}
