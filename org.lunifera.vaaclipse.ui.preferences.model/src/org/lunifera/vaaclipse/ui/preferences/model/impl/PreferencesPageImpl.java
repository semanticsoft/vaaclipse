/**
 */
package org.lunifera.vaaclipse.ui.preferences.model.impl;

import java.util.List;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

import org.eclipse.e4.ui.model.application.ui.impl.ElementContainerImpl;
import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;

import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPageImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPageImpl#getPreferencesScope <em>Preferences Scope</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PreferencesPageImpl extends ElementContainerImpl<FieldEditor<?>> implements PreferencesPage {
	/**
	 * The default value of the '{@link #getPreferencesScope() <em>Preferences Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferencesScope()
	 * @generated
	 * @ordered
	 */
	protected static final String PREFERENCES_SCOPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPreferencesScope() <em>Preferences Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferencesScope()
	 * @generated
	 * @ordered
	 */
	protected String preferencesScope = PREFERENCES_SCOPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PreferencesPageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.PREFERENCES_PAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public List<FieldEditor<?>> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<FieldEditor<?>>(FieldEditor.class, this, PreferencesPackage.PREFERENCES_PAGE__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return MUIElement.class; } };
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setSelectedElement(FieldEditor<?> newSelectedElement) {
		super.setSelectedElement(newSelectedElement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesCategory getCategory() {
		if (eContainerFeatureID() != PreferencesPackage.PREFERENCES_PAGE__CATEGORY) return null;
		return (PreferencesCategory)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCategory(PreferencesCategory newCategory, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newCategory, PreferencesPackage.PREFERENCES_PAGE__CATEGORY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategory(PreferencesCategory newCategory) {
		if (newCategory != eInternalContainer() || (eContainerFeatureID() != PreferencesPackage.PREFERENCES_PAGE__CATEGORY && newCategory != null)) {
			if (EcoreUtil.isAncestor(this, newCategory))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newCategory != null)
				msgs = ((InternalEObject)newCategory).eInverseAdd(this, PreferencesPackage.PREFERENCES_CATEGORY__PAGE, PreferencesCategory.class, msgs);
			msgs = basicSetCategory(newCategory, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.PREFERENCES_PAGE__CATEGORY, newCategory, newCategory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPreferencesScope() {
		return preferencesScope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreferencesScope(String newPreferencesScope) {
		String oldPreferencesScope = preferencesScope;
		preferencesScope = newPreferencesScope;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.PREFERENCES_PAGE__PREFERENCES_SCOPE, oldPreferencesScope, preferencesScope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetCategory((PreferencesCategory)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				return basicSetCategory(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				return eInternalContainer().eInverseRemove(this, PreferencesPackage.PREFERENCES_CATEGORY__PAGE, PreferencesCategory.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				return getCategory();
			case PreferencesPackage.PREFERENCES_PAGE__PREFERENCES_SCOPE:
				return getPreferencesScope();
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
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				setCategory((PreferencesCategory)newValue);
				return;
			case PreferencesPackage.PREFERENCES_PAGE__PREFERENCES_SCOPE:
				setPreferencesScope((String)newValue);
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
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				setCategory((PreferencesCategory)null);
				return;
			case PreferencesPackage.PREFERENCES_PAGE__PREFERENCES_SCOPE:
				setPreferencesScope(PREFERENCES_SCOPE_EDEFAULT);
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
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				return getCategory() != null;
			case PreferencesPackage.PREFERENCES_PAGE__PREFERENCES_SCOPE:
				return PREFERENCES_SCOPE_EDEFAULT == null ? preferencesScope != null : !PREFERENCES_SCOPE_EDEFAULT.equals(preferencesScope);
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
		result.append(" (preferencesScope: ");
		result.append(preferencesScope);
		result.append(')');
		return result.toString();
	}

} //PreferencesPageImpl
