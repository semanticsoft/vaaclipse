/**
 */
package e4modelextension;

import org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl;
import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see e4modelextension.E4modelextensionFactory
 * @model kind="package"
 * @generated
 */
public interface E4modelextensionPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "e4modelextension";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.semanticsoft.org/ExtensionUI/e4modelextension";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "e4modelextension";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	E4modelextensionPackage eINSTANCE = e4modelextension.impl.E4modelextensionPackageImpl.init();

	/**
	 * The meta object id for the '{@link e4modelextension.impl.EditorPartDescriptorImpl <em>Editor Part Descriptor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see e4modelextension.impl.EditorPartDescriptorImpl
	 * @see e4modelextension.impl.E4modelextensionPackageImpl#getEditorPartDescriptor()
	 * @generated
	 */
	int EDITOR_PART_DESCRIPTOR = 0;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__ELEMENT_ID = BasicPackageImpl.PART_DESCRIPTOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__PERSISTED_STATE = BasicPackageImpl.PART_DESCRIPTOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__TAGS = BasicPackageImpl.PART_DESCRIPTOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__CONTRIBUTOR_URI = BasicPackageImpl.PART_DESCRIPTOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__TRANSIENT_DATA = BasicPackageImpl.PART_DESCRIPTOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__LABEL = BasicPackageImpl.PART_DESCRIPTOR__LABEL;

	/**
	 * The feature id for the '<em><b>Icon URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__ICON_URI = BasicPackageImpl.PART_DESCRIPTOR__ICON_URI;

	/**
	 * The feature id for the '<em><b>Tooltip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__TOOLTIP = BasicPackageImpl.PART_DESCRIPTOR__TOOLTIP;

	/**
	 * The feature id for the '<em><b>Handlers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__HANDLERS = BasicPackageImpl.PART_DESCRIPTOR__HANDLERS;

	/**
	 * The feature id for the '<em><b>Binding Contexts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__BINDING_CONTEXTS = BasicPackageImpl.PART_DESCRIPTOR__BINDING_CONTEXTS;

	/**
	 * The feature id for the '<em><b>Allow Multiple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__ALLOW_MULTIPLE = BasicPackageImpl.PART_DESCRIPTOR__ALLOW_MULTIPLE;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__CATEGORY = BasicPackageImpl.PART_DESCRIPTOR__CATEGORY;

	/**
	 * The feature id for the '<em><b>Menus</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__MENUS = BasicPackageImpl.PART_DESCRIPTOR__MENUS;

	/**
	 * The feature id for the '<em><b>Toolbar</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__TOOLBAR = BasicPackageImpl.PART_DESCRIPTOR__TOOLBAR;

	/**
	 * The feature id for the '<em><b>Closeable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__CLOSEABLE = BasicPackageImpl.PART_DESCRIPTOR__CLOSEABLE;

	/**
	 * The feature id for the '<em><b>Dirtyable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__DIRTYABLE = BasicPackageImpl.PART_DESCRIPTOR__DIRTYABLE;

	/**
	 * The feature id for the '<em><b>Contribution URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__CONTRIBUTION_URI = BasicPackageImpl.PART_DESCRIPTOR__CONTRIBUTION_URI;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__DESCRIPTION = BasicPackageImpl.PART_DESCRIPTOR__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Uri Filter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__URI_FILTER = BasicPackageImpl.PART_DESCRIPTOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Part Adding Logic Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR__PART_ADDING_LOGIC_URI = BasicPackageImpl.PART_DESCRIPTOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Editor Part Descriptor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_PART_DESCRIPTOR_FEATURE_COUNT = BasicPackageImpl.PART_DESCRIPTOR_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link e4modelextension.impl.VaaclipseApplicationImpl <em>Vaaclipse Application</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see e4modelextension.impl.VaaclipseApplicationImpl
	 * @see e4modelextension.impl.E4modelextensionPackageImpl#getVaaclipseApplication()
	 * @generated
	 */
	int VAACLIPSE_APPLICATION = 1;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__ELEMENT_ID = ApplicationPackageImpl.APPLICATION__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__PERSISTED_STATE = ApplicationPackageImpl.APPLICATION__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__TAGS = ApplicationPackageImpl.APPLICATION__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__CONTRIBUTOR_URI = ApplicationPackageImpl.APPLICATION__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__TRANSIENT_DATA = ApplicationPackageImpl.APPLICATION__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__WIDGET = ApplicationPackageImpl.APPLICATION__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__RENDERER = ApplicationPackageImpl.APPLICATION__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__TO_BE_RENDERED = ApplicationPackageImpl.APPLICATION__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__ON_TOP = ApplicationPackageImpl.APPLICATION__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__VISIBLE = ApplicationPackageImpl.APPLICATION__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__PARENT = ApplicationPackageImpl.APPLICATION__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__CONTAINER_DATA = ApplicationPackageImpl.APPLICATION__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__CUR_SHARED_REF = ApplicationPackageImpl.APPLICATION__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__VISIBLE_WHEN = ApplicationPackageImpl.APPLICATION__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__ACCESSIBILITY_PHRASE = ApplicationPackageImpl.APPLICATION__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__CHILDREN = ApplicationPackageImpl.APPLICATION__CHILDREN;

	/**
	 * The feature id for the '<em><b>Selected Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__SELECTED_ELEMENT = ApplicationPackageImpl.APPLICATION__SELECTED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__CONTEXT = ApplicationPackageImpl.APPLICATION__CONTEXT;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__VARIABLES = ApplicationPackageImpl.APPLICATION__VARIABLES;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__PROPERTIES = ApplicationPackageImpl.APPLICATION__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Handlers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__HANDLERS = ApplicationPackageImpl.APPLICATION__HANDLERS;

	/**
	 * The feature id for the '<em><b>Binding Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__BINDING_TABLES = ApplicationPackageImpl.APPLICATION__BINDING_TABLES;

	/**
	 * The feature id for the '<em><b>Root Context</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__ROOT_CONTEXT = ApplicationPackageImpl.APPLICATION__ROOT_CONTEXT;

	/**
	 * The feature id for the '<em><b>Descriptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__DESCRIPTORS = ApplicationPackageImpl.APPLICATION__DESCRIPTORS;

	/**
	 * The feature id for the '<em><b>Binding Contexts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__BINDING_CONTEXTS = ApplicationPackageImpl.APPLICATION__BINDING_CONTEXTS;

	/**
	 * The feature id for the '<em><b>Menu Contributions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__MENU_CONTRIBUTIONS = ApplicationPackageImpl.APPLICATION__MENU_CONTRIBUTIONS;

	/**
	 * The feature id for the '<em><b>Tool Bar Contributions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__TOOL_BAR_CONTRIBUTIONS = ApplicationPackageImpl.APPLICATION__TOOL_BAR_CONTRIBUTIONS;

	/**
	 * The feature id for the '<em><b>Trim Contributions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__TRIM_CONTRIBUTIONS = ApplicationPackageImpl.APPLICATION__TRIM_CONTRIBUTIONS;

	/**
	 * The feature id for the '<em><b>Snippets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__SNIPPETS = ApplicationPackageImpl.APPLICATION__SNIPPETS;

	/**
	 * The feature id for the '<em><b>Commands</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__COMMANDS = ApplicationPackageImpl.APPLICATION__COMMANDS;

	/**
	 * The feature id for the '<em><b>Addons</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__ADDONS = ApplicationPackageImpl.APPLICATION__ADDONS;

	/**
	 * The feature id for the '<em><b>Categories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__CATEGORIES = ApplicationPackageImpl.APPLICATION__CATEGORIES;

	/**
	 * The feature id for the '<em><b>Editor Descriptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__EDITOR_DESCRIPTORS = ApplicationPackageImpl.APPLICATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Preferences Categories</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__PREFERENCES_CATEGORIES = ApplicationPackageImpl.APPLICATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Preferences Pages</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION__PREFERENCES_PAGES = ApplicationPackageImpl.APPLICATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Vaaclipse Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAACLIPSE_APPLICATION_FEATURE_COUNT = ApplicationPackageImpl.APPLICATION_FEATURE_COUNT + 3;


	/**
	 * Returns the meta object for class '{@link e4modelextension.EditorPartDescriptor <em>Editor Part Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Editor Part Descriptor</em>'.
	 * @see e4modelextension.EditorPartDescriptor
	 * @generated
	 */
	EClass getEditorPartDescriptor();

	/**
	 * Returns the meta object for the attribute '{@link e4modelextension.EditorPartDescriptor#getUriFilter <em>Uri Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri Filter</em>'.
	 * @see e4modelextension.EditorPartDescriptor#getUriFilter()
	 * @see #getEditorPartDescriptor()
	 * @generated
	 */
	EAttribute getEditorPartDescriptor_UriFilter();

	/**
	 * Returns the meta object for the attribute '{@link e4modelextension.EditorPartDescriptor#getPartAddingLogicUri <em>Part Adding Logic Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Part Adding Logic Uri</em>'.
	 * @see e4modelextension.EditorPartDescriptor#getPartAddingLogicUri()
	 * @see #getEditorPartDescriptor()
	 * @generated
	 */
	EAttribute getEditorPartDescriptor_PartAddingLogicUri();

	/**
	 * Returns the meta object for class '{@link e4modelextension.VaaclipseApplication <em>Vaaclipse Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vaaclipse Application</em>'.
	 * @see e4modelextension.VaaclipseApplication
	 * @generated
	 */
	EClass getVaaclipseApplication();

	/**
	 * Returns the meta object for the containment reference list '{@link e4modelextension.VaaclipseApplication#getEditorDescriptors <em>Editor Descriptors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Editor Descriptors</em>'.
	 * @see e4modelextension.VaaclipseApplication#getEditorDescriptors()
	 * @see #getVaaclipseApplication()
	 * @generated
	 */
	EReference getVaaclipseApplication_EditorDescriptors();

	/**
	 * Returns the meta object for the reference list '{@link e4modelextension.VaaclipseApplication#getPreferencesCategories <em>Preferences Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Preferences Categories</em>'.
	 * @see e4modelextension.VaaclipseApplication#getPreferencesCategories()
	 * @see #getVaaclipseApplication()
	 * @generated
	 */
	EReference getVaaclipseApplication_PreferencesCategories();

	/**
	 * Returns the meta object for the reference list '{@link e4modelextension.VaaclipseApplication#getPreferencesPages <em>Preferences Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Preferences Pages</em>'.
	 * @see e4modelextension.VaaclipseApplication#getPreferencesPages()
	 * @see #getVaaclipseApplication()
	 * @generated
	 */
	EReference getVaaclipseApplication_PreferencesPages();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	E4modelextensionFactory getE4modelextensionFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals
	{
		/**
		 * The meta object literal for the '{@link e4modelextension.impl.EditorPartDescriptorImpl <em>Editor Part Descriptor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see e4modelextension.impl.EditorPartDescriptorImpl
		 * @see e4modelextension.impl.E4modelextensionPackageImpl#getEditorPartDescriptor()
		 * @generated
		 */
		EClass EDITOR_PART_DESCRIPTOR = eINSTANCE.getEditorPartDescriptor();

		/**
		 * The meta object literal for the '<em><b>Uri Filter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDITOR_PART_DESCRIPTOR__URI_FILTER = eINSTANCE.getEditorPartDescriptor_UriFilter();

		/**
		 * The meta object literal for the '<em><b>Part Adding Logic Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDITOR_PART_DESCRIPTOR__PART_ADDING_LOGIC_URI = eINSTANCE.getEditorPartDescriptor_PartAddingLogicUri();

		/**
		 * The meta object literal for the '{@link e4modelextension.impl.VaaclipseApplicationImpl <em>Vaaclipse Application</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see e4modelextension.impl.VaaclipseApplicationImpl
		 * @see e4modelextension.impl.E4modelextensionPackageImpl#getVaaclipseApplication()
		 * @generated
		 */
		EClass VAACLIPSE_APPLICATION = eINSTANCE.getVaaclipseApplication();

		/**
		 * The meta object literal for the '<em><b>Editor Descriptors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAACLIPSE_APPLICATION__EDITOR_DESCRIPTORS = eINSTANCE.getVaaclipseApplication_EditorDescriptors();

		/**
		 * The meta object literal for the '<em><b>Preferences Categories</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAACLIPSE_APPLICATION__PREFERENCES_CATEGORIES = eINSTANCE.getVaaclipseApplication_PreferencesCategories();

		/**
		 * The meta object literal for the '<em><b>Preferences Pages</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAACLIPSE_APPLICATION__PREFERENCES_PAGES = eINSTANCE.getVaaclipseApplication_PreferencesPages();

	}

} //E4modelextensionPackage
