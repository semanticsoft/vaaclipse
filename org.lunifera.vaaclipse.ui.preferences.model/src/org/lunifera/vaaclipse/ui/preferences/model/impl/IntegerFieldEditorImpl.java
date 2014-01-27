/**
 */
package org.lunifera.vaaclipse.ui.preferences.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor;

import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Integer Field Editor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.IntegerFieldEditorImpl#getMinValidValue <em>Min Valid Value</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.IntegerFieldEditorImpl#getMaxValidValue <em>Max Valid Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IntegerFieldEditorImpl extends FieldEditorImpl<Integer> implements IntegerFieldEditor {
	/**
	 * The default value of the '{@link #getMinValidValue() <em>Min Valid Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinValidValue()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MIN_VALID_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMinValidValue() <em>Min Valid Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinValidValue()
	 * @generated
	 * @ordered
	 */
	protected Integer minValidValue = MIN_VALID_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxValidValue() <em>Max Valid Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxValidValue()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MAX_VALID_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMaxValidValue() <em>Max Valid Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxValidValue()
	 * @generated
	 * @ordered
	 */
	protected Integer maxValidValue = MAX_VALID_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IntegerFieldEditorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.INTEGER_FIELD_EDITOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setDefaultValueTyped(Integer newDefaultValueTyped) {
		super.setDefaultValueTyped(newDefaultValueTyped);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMinValidValue() {
		return minValidValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinValidValue(Integer newMinValidValue) {
		Integer oldMinValidValue = minValidValue;
		minValidValue = newMinValidValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.INTEGER_FIELD_EDITOR__MIN_VALID_VALUE, oldMinValidValue, minValidValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMaxValidValue() {
		return maxValidValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxValidValue(Integer newMaxValidValue) {
		Integer oldMaxValidValue = maxValidValue;
		maxValidValue = newMaxValidValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.INTEGER_FIELD_EDITOR__MAX_VALID_VALUE, oldMaxValidValue, maxValidValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PreferencesPackage.INTEGER_FIELD_EDITOR__MIN_VALID_VALUE:
				return getMinValidValue();
			case PreferencesPackage.INTEGER_FIELD_EDITOR__MAX_VALID_VALUE:
				return getMaxValidValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PreferencesPackage.INTEGER_FIELD_EDITOR__MIN_VALID_VALUE:
				setMinValidValue((Integer)newValue);
				return;
			case PreferencesPackage.INTEGER_FIELD_EDITOR__MAX_VALID_VALUE:
				setMaxValidValue((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PreferencesPackage.INTEGER_FIELD_EDITOR__MIN_VALID_VALUE:
				setMinValidValue(MIN_VALID_VALUE_EDEFAULT);
				return;
			case PreferencesPackage.INTEGER_FIELD_EDITOR__MAX_VALID_VALUE:
				setMaxValidValue(MAX_VALID_VALUE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PreferencesPackage.INTEGER_FIELD_EDITOR__MIN_VALID_VALUE:
				return MIN_VALID_VALUE_EDEFAULT == null ? minValidValue != null : !MIN_VALID_VALUE_EDEFAULT.equals(minValidValue);
			case PreferencesPackage.INTEGER_FIELD_EDITOR__MAX_VALID_VALUE:
				return MAX_VALID_VALUE_EDEFAULT == null ? maxValidValue != null : !MAX_VALID_VALUE_EDEFAULT.equals(maxValidValue);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (minValidValue: ");
		result.append(minValidValue);
		result.append(", maxValidValue: ");
		result.append(maxValidValue);
		result.append(')');
		return result.toString();
	}

} //IntegerFieldEditorImpl
