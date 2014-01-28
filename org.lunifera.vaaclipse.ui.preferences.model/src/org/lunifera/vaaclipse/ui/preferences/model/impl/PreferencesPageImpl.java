/**
 */
package org.lunifera.vaaclipse.ui.preferences.model.impl;

import java.util.List;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
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
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPageImpl#getContributionURI <em>Contribution URI</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPageImpl#getObject <em>Object</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPageImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPageImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PreferencesPageImpl extends ElementContainerImpl<FieldEditor<?>> implements PreferencesPage {
	/**
	 * The default value of the '{@link #getContributionURI() <em>Contribution URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributionURI()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTRIBUTION_URI_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getContributionURI() <em>Contribution URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributionURI()
	 * @generated
	 * @ordered
	 */
	protected String contributionURI = CONTRIBUTION_URI_EDEFAULT;
	/**
	 * The default value of the '{@link #getObject() <em>Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObject()
	 * @generated
	 * @ordered
	 */
	protected static final Object OBJECT_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getObject() <em>Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObject()
	 * @generated
	 * @ordered
	 */
	protected Object object = OBJECT_EDEFAULT;
	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

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
	public String getContributionURI() {
		return contributionURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContributionURI(String newContributionURI) {
		String oldContributionURI = contributionURI;
		contributionURI = newContributionURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.PREFERENCES_PAGE__CONTRIBUTION_URI, oldContributionURI, contributionURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObject(Object newObject) {
		Object oldObject = object;
		object = newObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.PREFERENCES_PAGE__OBJECT, oldObject, object));
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
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.PREFERENCES_PAGE__DESCRIPTION, oldDescription, description));
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
			case PreferencesPackage.PREFERENCES_PAGE__CONTRIBUTION_URI:
				return getContributionURI();
			case PreferencesPackage.PREFERENCES_PAGE__OBJECT:
				return getObject();
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				return getCategory();
			case PreferencesPackage.PREFERENCES_PAGE__DESCRIPTION:
				return getDescription();
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
			case PreferencesPackage.PREFERENCES_PAGE__CONTRIBUTION_URI:
				setContributionURI((String)newValue);
				return;
			case PreferencesPackage.PREFERENCES_PAGE__OBJECT:
				setObject(newValue);
				return;
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				setCategory((PreferencesCategory)newValue);
				return;
			case PreferencesPackage.PREFERENCES_PAGE__DESCRIPTION:
				setDescription((String)newValue);
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
			case PreferencesPackage.PREFERENCES_PAGE__CONTRIBUTION_URI:
				setContributionURI(CONTRIBUTION_URI_EDEFAULT);
				return;
			case PreferencesPackage.PREFERENCES_PAGE__OBJECT:
				setObject(OBJECT_EDEFAULT);
				return;
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				setCategory((PreferencesCategory)null);
				return;
			case PreferencesPackage.PREFERENCES_PAGE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
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
			case PreferencesPackage.PREFERENCES_PAGE__CONTRIBUTION_URI:
				return CONTRIBUTION_URI_EDEFAULT == null ? contributionURI != null : !CONTRIBUTION_URI_EDEFAULT.equals(contributionURI);
			case PreferencesPackage.PREFERENCES_PAGE__OBJECT:
				return OBJECT_EDEFAULT == null ? object != null : !OBJECT_EDEFAULT.equals(object);
			case PreferencesPackage.PREFERENCES_PAGE__CATEGORY:
				return getCategory() != null;
			case PreferencesPackage.PREFERENCES_PAGE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MContribution.class) {
			switch (derivedFeatureID) {
				case PreferencesPackage.PREFERENCES_PAGE__CONTRIBUTION_URI: return ApplicationPackageImpl.CONTRIBUTION__CONTRIBUTION_URI;
				case PreferencesPackage.PREFERENCES_PAGE__OBJECT: return ApplicationPackageImpl.CONTRIBUTION__OBJECT;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MContribution.class) {
			switch (baseFeatureID) {
				case ApplicationPackageImpl.CONTRIBUTION__CONTRIBUTION_URI: return PreferencesPackage.PREFERENCES_PAGE__CONTRIBUTION_URI;
				case ApplicationPackageImpl.CONTRIBUTION__OBJECT: return PreferencesPackage.PREFERENCES_PAGE__OBJECT;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (contributionURI: ");
		result.append(contributionURI);
		result.append(", object: ");
		result.append(object);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //PreferencesPageImpl
