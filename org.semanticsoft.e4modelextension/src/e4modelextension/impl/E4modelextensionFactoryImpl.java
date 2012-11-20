/**
 */
package e4modelextension.impl;

import e4modelextension.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class E4modelextensionFactoryImpl extends EFactoryImpl implements E4modelextensionFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static E4modelextensionFactory init()
	{
		try {
			E4modelextensionFactory theE4modelextensionFactory = (E4modelextensionFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.semanticsoft.org/ExtensionUI/e4modelextension"); 
			if (theE4modelextensionFactory != null) {
				return theE4modelextensionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new E4modelextensionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public E4modelextensionFactoryImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID()) {
			case E4modelextensionPackage.EDITOR_PART_DESCRIPTOR: return createEditorPartDescriptor();
			case E4modelextensionPackage.VAACLIPSE_APPLICATION: return createVaaclipseApplication();
			case E4modelextensionPackage.DIALOG: return createDialog();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditorPartDescriptor createEditorPartDescriptor()
	{
		EditorPartDescriptorImpl editorPartDescriptor = new EditorPartDescriptorImpl();
		return editorPartDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VaaclipseApplication createVaaclipseApplication()
	{
		VaaclipseApplicationImpl vaaclipseApplication = new VaaclipseApplicationImpl();
		return vaaclipseApplication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dialog createDialog() {
		DialogImpl dialog = new DialogImpl();
		return dialog;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public E4modelextensionPackage getE4modelextensionPackage()
	{
		return (E4modelextensionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static E4modelextensionPackage getPackage()
	{
		return E4modelextensionPackage.eINSTANCE;
	}

} //E4modelextensionFactoryImpl
