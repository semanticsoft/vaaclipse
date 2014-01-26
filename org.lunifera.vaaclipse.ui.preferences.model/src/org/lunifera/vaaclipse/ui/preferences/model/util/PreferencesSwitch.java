/**
 */
package org.lunifera.vaaclipse.ui.preferences.model.util;

import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.MContribution;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.lunifera.vaaclipse.ui.preferences.model.*;

import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage
 * @generated
 */
public class PreferencesSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PreferencesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesSwitch() {
		if (modelPackage == null) {
			modelPackage = PreferencesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case PreferencesPackage.PREFERENCES_CATEGORY: {
				PreferencesCategory preferencesCategory = (PreferencesCategory)theEObject;
				T1 result = casePreferencesCategory(preferencesCategory);
				if (result == null) result = caseApplicationElement(preferencesCategory);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.PREFERENCES_PAGE: {
				PreferencesPage preferencesPage = (PreferencesPage)theEObject;
				T1 result = casePreferencesPage(preferencesPage);
				if (result == null) result = caseElementContainer(preferencesPage);
				if (result == null) result = caseUIElement(preferencesPage);
				if (result == null) result = caseApplicationElement(preferencesPage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.FIELD_EDITOR: {
				FieldEditor<?> fieldEditor = (FieldEditor<?>)theEObject;
				T1 result = caseFieldEditor(fieldEditor);
				if (result == null) result = caseUIElement(fieldEditor);
				if (result == null) result = caseApplicationElement(fieldEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.BOOLEAN_FIELD_EDITOR: {
				BooleanFieldEditor booleanFieldEditor = (BooleanFieldEditor)theEObject;
				T1 result = caseBooleanFieldEditor(booleanFieldEditor);
				if (result == null) result = caseFieldEditor(booleanFieldEditor);
				if (result == null) result = caseUIElement(booleanFieldEditor);
				if (result == null) result = caseApplicationElement(booleanFieldEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.LIST_FIELD_EDITOR: {
				ListFieldEditor listFieldEditor = (ListFieldEditor)theEObject;
				T1 result = caseListFieldEditor(listFieldEditor);
				if (result == null) result = caseFieldEditor(listFieldEditor);
				if (result == null) result = caseContribution(listFieldEditor);
				if (result == null) result = caseUIElement(listFieldEditor);
				if (result == null) result = caseApplicationElement(listFieldEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.ENTRY: {
				Entry entry = (Entry)theEObject;
				T1 result = caseEntry(entry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.COMBO_FIELD_EDITOR: {
				ComboFieldEditor comboFieldEditor = (ComboFieldEditor)theEObject;
				T1 result = caseComboFieldEditor(comboFieldEditor);
				if (result == null) result = caseListFieldEditor(comboFieldEditor);
				if (result == null) result = caseFieldEditor(comboFieldEditor);
				if (result == null) result = caseContribution(comboFieldEditor);
				if (result == null) result = caseUIElement(comboFieldEditor);
				if (result == null) result = caseApplicationElement(comboFieldEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.LIST_EDITOR: {
				ListEditor listEditor = (ListEditor)theEObject;
				T1 result = caseListEditor(listEditor);
				if (result == null) result = caseListFieldEditor(listEditor);
				if (result == null) result = caseFieldEditor(listEditor);
				if (result == null) result = caseContribution(listEditor);
				if (result == null) result = caseUIElement(listEditor);
				if (result == null) result = caseApplicationElement(listEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.RADIO_GROUP_FIELD_EDITOR: {
				RadioGroupFieldEditor radioGroupFieldEditor = (RadioGroupFieldEditor)theEObject;
				T1 result = caseRadioGroupFieldEditor(radioGroupFieldEditor);
				if (result == null) result = caseListFieldEditor(radioGroupFieldEditor);
				if (result == null) result = caseFieldEditor(radioGroupFieldEditor);
				if (result == null) result = caseContribution(radioGroupFieldEditor);
				if (result == null) result = caseUIElement(radioGroupFieldEditor);
				if (result == null) result = caseApplicationElement(radioGroupFieldEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.SCALE_FIELD_EDITOR: {
				ScaleFieldEditor scaleFieldEditor = (ScaleFieldEditor)theEObject;
				T1 result = caseScaleFieldEditor(scaleFieldEditor);
				if (result == null) result = caseFieldEditor(scaleFieldEditor);
				if (result == null) result = caseUIElement(scaleFieldEditor);
				if (result == null) result = caseApplicationElement(scaleFieldEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.STRING_FIELD_EDITOR: {
				StringFieldEditor stringFieldEditor = (StringFieldEditor)theEObject;
				T1 result = caseStringFieldEditor(stringFieldEditor);
				if (result == null) result = caseFieldEditor(stringFieldEditor);
				if (result == null) result = caseUIElement(stringFieldEditor);
				if (result == null) result = caseApplicationElement(stringFieldEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.INTEGER_FIELD_EDITOR: {
				IntegerFieldEditor integerFieldEditor = (IntegerFieldEditor)theEObject;
				T1 result = caseIntegerFieldEditor(integerFieldEditor);
				if (result == null) result = caseFieldEditor(integerFieldEditor);
				if (result == null) result = caseUIElement(integerFieldEditor);
				if (result == null) result = caseApplicationElement(integerFieldEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.FILE_FIELD_EDITOR: {
				FileFieldEditor fileFieldEditor = (FileFieldEditor)theEObject;
				T1 result = caseFileFieldEditor(fileFieldEditor);
				if (result == null) result = caseFieldEditor(fileFieldEditor);
				if (result == null) result = caseContribution(fileFieldEditor);
				if (result == null) result = caseUIElement(fileFieldEditor);
				if (result == null) result = caseApplicationElement(fileFieldEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.DIRECTORY_FIELD_EDITOR: {
				DirectoryFieldEditor directoryFieldEditor = (DirectoryFieldEditor)theEObject;
				T1 result = caseDirectoryFieldEditor(directoryFieldEditor);
				if (result == null) result = caseFieldEditor(directoryFieldEditor);
				if (result == null) result = caseContribution(directoryFieldEditor);
				if (result == null) result = caseUIElement(directoryFieldEditor);
				if (result == null) result = caseApplicationElement(directoryFieldEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Category</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Category</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 casePreferencesCategory(PreferencesCategory object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Page</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Page</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 casePreferencesPage(PreferencesPage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Field Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T> T1 caseFieldEditor(FieldEditor<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Field Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseBooleanFieldEditor(BooleanFieldEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>List Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>List Field Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseListFieldEditor(ListFieldEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Combo Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Combo Field Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseComboFieldEditor(ComboFieldEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>List Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>List Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseListEditor(ListEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Radio Group Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Radio Group Field Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseRadioGroupFieldEditor(RadioGroupFieldEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Scale Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Scale Field Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseScaleFieldEditor(ScaleFieldEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Field Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStringFieldEditor(StringFieldEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Field Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseIntegerFieldEditor(IntegerFieldEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Field Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFileFieldEditor(FileFieldEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Directory Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Directory Field Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDirectoryFieldEditor(DirectoryFieldEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEntry(Entry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseApplicationElement(MApplicationElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UI Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UI Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseUIElement(MUIElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends MUIElement> T1 caseElementContainer(MElementContainer<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Contribution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Contribution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseContribution(MContribution object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T1 defaultCase(EObject object) {
		return null;
	}

} //PreferencesSwitch
