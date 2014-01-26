/**
 */
package e4modelextension.impl;

import e4modelextension.E4modelextensionPackage;
import e4modelextension.EditorPartDescriptor;
import e4modelextension.VaaclipseApplication;

import java.util.Collection;

import org.eclipse.e4.ui.model.application.impl.ApplicationImpl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vaaclipse Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link e4modelextension.impl.VaaclipseApplicationImpl#getEditorDescriptors <em>Editor Descriptors</em>}</li>
 *   <li>{@link e4modelextension.impl.VaaclipseApplicationImpl#getPreferencesCategories <em>Preferences Categories</em>}</li>
 *   <li>{@link e4modelextension.impl.VaaclipseApplicationImpl#getPreferencesPages <em>Preferences Pages</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VaaclipseApplicationImpl extends ApplicationImpl implements VaaclipseApplication {
	/**
	 * The cached value of the '{@link #getEditorDescriptors() <em>Editor Descriptors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorDescriptors()
	 * @generated
	 * @ordered
	 */
	protected EList<EditorPartDescriptor> editorDescriptors;

	/**
	 * The cached value of the '{@link #getPreferencesCategories() <em>Preferences Categories</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferencesCategories()
	 * @generated
	 * @ordered
	 */
	protected EList<PreferencesCategory> preferencesCategories;

	/**
	 * The cached value of the '{@link #getPreferencesPages() <em>Preferences Pages</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferencesPages()
	 * @generated
	 * @ordered
	 */
	protected EList<PreferencesPage> preferencesPages;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VaaclipseApplicationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return E4modelextensionPackage.Literals.VAACLIPSE_APPLICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EditorPartDescriptor> getEditorDescriptors() {
		if (editorDescriptors == null) {
			editorDescriptors = new EObjectContainmentEList<EditorPartDescriptor>(EditorPartDescriptor.class, this, E4modelextensionPackage.VAACLIPSE_APPLICATION__EDITOR_DESCRIPTORS);
		}
		return editorDescriptors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PreferencesCategory> getPreferencesCategories() {
		if (preferencesCategories == null) {
			preferencesCategories = new EObjectResolvingEList<PreferencesCategory>(PreferencesCategory.class, this, E4modelextensionPackage.VAACLIPSE_APPLICATION__PREFERENCES_CATEGORIES);
		}
		return preferencesCategories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PreferencesPage> getPreferencesPages() {
		if (preferencesPages == null) {
			preferencesPages = new EObjectResolvingEList<PreferencesPage>(PreferencesPage.class, this, E4modelextensionPackage.VAACLIPSE_APPLICATION__PREFERENCES_PAGES);
		}
		return preferencesPages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__EDITOR_DESCRIPTORS:
				return ((InternalEList<?>)getEditorDescriptors()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__EDITOR_DESCRIPTORS:
				return getEditorDescriptors();
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__PREFERENCES_CATEGORIES:
				return getPreferencesCategories();
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__PREFERENCES_PAGES:
				return getPreferencesPages();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__EDITOR_DESCRIPTORS:
				getEditorDescriptors().clear();
				getEditorDescriptors().addAll((Collection<? extends EditorPartDescriptor>)newValue);
				return;
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__PREFERENCES_CATEGORIES:
				getPreferencesCategories().clear();
				getPreferencesCategories().addAll((Collection<? extends PreferencesCategory>)newValue);
				return;
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__PREFERENCES_PAGES:
				getPreferencesPages().clear();
				getPreferencesPages().addAll((Collection<? extends PreferencesPage>)newValue);
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
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__EDITOR_DESCRIPTORS:
				getEditorDescriptors().clear();
				return;
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__PREFERENCES_CATEGORIES:
				getPreferencesCategories().clear();
				return;
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__PREFERENCES_PAGES:
				getPreferencesPages().clear();
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
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__EDITOR_DESCRIPTORS:
				return editorDescriptors != null && !editorDescriptors.isEmpty();
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__PREFERENCES_CATEGORIES:
				return preferencesCategories != null && !preferencesCategories.isEmpty();
			case E4modelextensionPackage.VAACLIPSE_APPLICATION__PREFERENCES_PAGES:
				return preferencesPages != null && !preferencesPages.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //VaaclipseApplicationImpl
