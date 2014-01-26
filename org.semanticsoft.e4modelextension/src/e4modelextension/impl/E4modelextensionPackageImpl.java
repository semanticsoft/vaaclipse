/**
 */
package e4modelextension.impl;

import org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl;
import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import preferences.PreferencesPackage;
import preferences.impl.PreferencesPackageImpl;
import e4modelextension.E4modelextensionFactory;
import e4modelextension.E4modelextensionPackage;
import e4modelextension.EditorPartDescriptor;
import e4modelextension.VaaclipseApplication;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class E4modelextensionPackageImpl extends EPackageImpl implements E4modelextensionPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass editorPartDescriptorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vaaclipseApplicationEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see e4modelextension.E4modelextensionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private E4modelextensionPackageImpl()
	{
		super(eNS_URI, E4modelextensionFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link E4modelextensionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static E4modelextensionPackage init()
	{
		if (isInited) return (E4modelextensionPackage)EPackage.Registry.INSTANCE.getEPackage(E4modelextensionPackage.eNS_URI);

		// Obtain or create and register package
		E4modelextensionPackageImpl theE4modelextensionPackage = (E4modelextensionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof E4modelextensionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new E4modelextensionPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ApplicationPackageImpl.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		PreferencesPackageImpl thePreferencesPackage = (PreferencesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PreferencesPackage.eNS_URI) instanceof PreferencesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PreferencesPackage.eNS_URI) : PreferencesPackage.eINSTANCE);

		// Create package meta-data objects
		theE4modelextensionPackage.createPackageContents();
		thePreferencesPackage.createPackageContents();

		// Initialize created meta-data
		theE4modelextensionPackage.initializePackageContents();
		thePreferencesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theE4modelextensionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(E4modelextensionPackage.eNS_URI, theE4modelextensionPackage);
		return theE4modelextensionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEditorPartDescriptor()
	{
		return editorPartDescriptorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditorPartDescriptor_UriFilter()
	{
		return (EAttribute)editorPartDescriptorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditorPartDescriptor_PartAddingLogicUri()
	{
		return (EAttribute)editorPartDescriptorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVaaclipseApplication()
	{
		return vaaclipseApplicationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVaaclipseApplication_EditorDescriptors()
	{
		return (EReference)vaaclipseApplicationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVaaclipseApplication_PreferencesCategories() {
		return (EReference)vaaclipseApplicationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVaaclipseApplication_PreferencesPages() {
		return (EReference)vaaclipseApplicationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public E4modelextensionFactory getE4modelextensionFactory()
	{
		return (E4modelextensionFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents()
	{
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		editorPartDescriptorEClass = createEClass(EDITOR_PART_DESCRIPTOR);
		createEAttribute(editorPartDescriptorEClass, EDITOR_PART_DESCRIPTOR__URI_FILTER);
		createEAttribute(editorPartDescriptorEClass, EDITOR_PART_DESCRIPTOR__PART_ADDING_LOGIC_URI);

		vaaclipseApplicationEClass = createEClass(VAACLIPSE_APPLICATION);
		createEReference(vaaclipseApplicationEClass, VAACLIPSE_APPLICATION__EDITOR_DESCRIPTORS);
		createEReference(vaaclipseApplicationEClass, VAACLIPSE_APPLICATION__PREFERENCES_CATEGORIES);
		createEReference(vaaclipseApplicationEClass, VAACLIPSE_APPLICATION__PREFERENCES_PAGES);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents()
	{
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		BasicPackageImpl theBasicPackage = (BasicPackageImpl)EPackage.Registry.INSTANCE.getEPackage(BasicPackageImpl.eNS_URI);
		ApplicationPackageImpl theApplicationPackage = (ApplicationPackageImpl)EPackage.Registry.INSTANCE.getEPackage(ApplicationPackageImpl.eNS_URI);
		PreferencesPackage thePreferencesPackage = (PreferencesPackage)EPackage.Registry.INSTANCE.getEPackage(PreferencesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		editorPartDescriptorEClass.getESuperTypes().add(theBasicPackage.getPartDescriptor());
		vaaclipseApplicationEClass.getESuperTypes().add(theApplicationPackage.getApplication());

		// Initialize classes and features; add operations and parameters
		initEClass(editorPartDescriptorEClass, EditorPartDescriptor.class, "EditorPartDescriptor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEditorPartDescriptor_UriFilter(), ecorePackage.getEString(), "uriFilter", null, 0, 1, EditorPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEditorPartDescriptor_PartAddingLogicUri(), ecorePackage.getEString(), "partAddingLogicUri", "bundleclass://org.semanticsoft.e4extension/org.semanticsoft.e4extension.shared.DefaultPartAddingLogic", 0, 1, EditorPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vaaclipseApplicationEClass, VaaclipseApplication.class, "VaaclipseApplication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVaaclipseApplication_EditorDescriptors(), this.getEditorPartDescriptor(), null, "editorDescriptors", null, 0, -1, VaaclipseApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVaaclipseApplication_PreferencesCategories(), thePreferencesPackage.getPreferencesCategory(), null, "preferencesCategories", null, 0, -1, VaaclipseApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVaaclipseApplication_PreferencesPages(), thePreferencesPackage.getPreferencesPage(), null, "preferencesPages", null, 0, -1, VaaclipseApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //E4modelextensionPackageImpl
