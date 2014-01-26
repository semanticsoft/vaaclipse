/**
 */
package preferences.util;

import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.MContribution;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import preferences.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see preferences.PreferencesPackage
 * @generated
 */
public class PreferencesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PreferencesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PreferencesPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PreferencesSwitch<Adapter> modelSwitch =
		new PreferencesSwitch<Adapter>() {
			@Override
			public Adapter casePreferencesCategory(PreferencesCategory object) {
				return createPreferencesCategoryAdapter();
			}
			@Override
			public Adapter casePreferencesPage(PreferencesPage object) {
				return createPreferencesPageAdapter();
			}
			@Override
			public <T> Adapter caseFieldEditor(FieldEditor<T> object) {
				return createFieldEditorAdapter();
			}
			@Override
			public Adapter caseBooleanFieldEditor(BooleanFieldEditor object) {
				return createBooleanFieldEditorAdapter();
			}
			@Override
			public Adapter caseListFieldEditor(ListFieldEditor object) {
				return createListFieldEditorAdapter();
			}
			@Override
			public Adapter caseEntry(Entry object) {
				return createEntryAdapter();
			}
			@Override
			public Adapter caseComboFieldEditor(ComboFieldEditor object) {
				return createComboFieldEditorAdapter();
			}
			@Override
			public Adapter caseListEditor(ListEditor object) {
				return createListEditorAdapter();
			}
			@Override
			public Adapter caseRadioGroupFieldEditor(RadioGroupFieldEditor object) {
				return createRadioGroupFieldEditorAdapter();
			}
			@Override
			public Adapter caseScaleFieldEditor(ScaleFieldEditor object) {
				return createScaleFieldEditorAdapter();
			}
			@Override
			public Adapter caseStringFieldEditor(StringFieldEditor object) {
				return createStringFieldEditorAdapter();
			}
			@Override
			public Adapter caseIntegerFieldEditor(IntegerFieldEditor object) {
				return createIntegerFieldEditorAdapter();
			}
			@Override
			public Adapter caseFileFieldEditor(FileFieldEditor object) {
				return createFileFieldEditorAdapter();
			}
			@Override
			public Adapter caseDirectoryFieldEditor(DirectoryFieldEditor object) {
				return createDirectoryFieldEditorAdapter();
			}
			@Override
			public Adapter caseApplicationElement(MApplicationElement object) {
				return createApplicationElementAdapter();
			}
			@Override
			public Adapter caseUIElement(MUIElement object) {
				return createUIElementAdapter();
			}
			@Override
			public <T extends MUIElement> Adapter caseElementContainer(MElementContainer<T> object) {
				return createElementContainerAdapter();
			}
			@Override
			public Adapter caseContribution(MContribution object) {
				return createContributionAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link preferences.PreferencesCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.PreferencesCategory
	 * @generated
	 */
	public Adapter createPreferencesCategoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.PreferencesPage <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.PreferencesPage
	 * @generated
	 */
	public Adapter createPreferencesPageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.FieldEditor <em>Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.FieldEditor
	 * @generated
	 */
	public Adapter createFieldEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.BooleanFieldEditor <em>Boolean Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.BooleanFieldEditor
	 * @generated
	 */
	public Adapter createBooleanFieldEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.ListFieldEditor <em>List Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.ListFieldEditor
	 * @generated
	 */
	public Adapter createListFieldEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.Entry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.Entry
	 * @generated
	 */
	public Adapter createEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.ComboFieldEditor <em>Combo Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.ComboFieldEditor
	 * @generated
	 */
	public Adapter createComboFieldEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.ListEditor <em>List Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.ListEditor
	 * @generated
	 */
	public Adapter createListEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.RadioGroupFieldEditor <em>Radio Group Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.RadioGroupFieldEditor
	 * @generated
	 */
	public Adapter createRadioGroupFieldEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.ScaleFieldEditor <em>Scale Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.ScaleFieldEditor
	 * @generated
	 */
	public Adapter createScaleFieldEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.StringFieldEditor <em>String Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.StringFieldEditor
	 * @generated
	 */
	public Adapter createStringFieldEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.IntegerFieldEditor <em>Integer Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.IntegerFieldEditor
	 * @generated
	 */
	public Adapter createIntegerFieldEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.FileFieldEditor <em>File Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.FileFieldEditor
	 * @generated
	 */
	public Adapter createFileFieldEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link preferences.DirectoryFieldEditor <em>Directory Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see preferences.DirectoryFieldEditor
	 * @generated
	 */
	public Adapter createDirectoryFieldEditorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.e4.ui.model.application.MApplicationElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.e4.ui.model.application.MApplicationElement
	 * @generated
	 */
	public Adapter createApplicationElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.e4.ui.model.application.ui.MUIElement <em>UI Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.e4.ui.model.application.ui.MUIElement
	 * @generated
	 */
	public Adapter createUIElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.e4.ui.model.application.ui.MElementContainer <em>Element Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.e4.ui.model.application.ui.MElementContainer
	 * @generated
	 */
	public Adapter createElementContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.e4.ui.model.application.MContribution <em>Contribution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.e4.ui.model.application.MContribution
	 * @generated
	 */
	public Adapter createContributionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //PreferencesAdapterFactory
