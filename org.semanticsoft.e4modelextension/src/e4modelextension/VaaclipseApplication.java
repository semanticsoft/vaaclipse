/**
 */
package e4modelextension;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vaaclipse Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link e4modelextension.VaaclipseApplication#getEditorDescriptors <em>Editor Descriptors</em>}</li>
 * </ul>
 * </p>
 *
 * @see e4modelextension.E4modelextensionPackage#getVaaclipseApplication()
 * @model
 * @generated
 */
public interface VaaclipseApplication extends EObject, MApplication
{
	/**
	 * Returns the value of the '<em><b>Editor Descriptors</b></em>' containment reference list.
	 * The list contents are of type {@link e4modelextension.EditorPartDescriptor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editor Descriptors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editor Descriptors</em>' containment reference list.
	 * @see e4modelextension.E4modelextensionPackage#getVaaclipseApplication_EditorDescriptors()
	 * @model containment="true"
	 * @generated
	 */
	EList<EditorPartDescriptor> getEditorDescriptors();

} // VaaclipseApplication
