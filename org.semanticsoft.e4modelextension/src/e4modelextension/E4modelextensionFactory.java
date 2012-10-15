/**
 */
package e4modelextension;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see e4modelextension.E4modelextensionPackage
 * @generated
 */
public interface E4modelextensionFactory extends EFactory
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	E4modelextensionFactory eINSTANCE = e4modelextension.impl.E4modelextensionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Editor Part Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Editor Part Descriptor</em>'.
	 * @generated
	 */
	EditorPartDescriptor createEditorPartDescriptor();

	/**
	 * Returns a new object of class '<em>Vaaclipse Application</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Vaaclipse Application</em>'.
	 * @generated
	 */
	VaaclipseApplication createVaaclipseApplication();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	E4modelextensionPackage getE4modelextensionPackage();

} //E4modelextensionFactory
