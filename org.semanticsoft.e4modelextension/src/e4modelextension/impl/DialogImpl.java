/**
 */
package e4modelextension.impl;

import e4modelextension.Dialog;
import e4modelextension.E4modelextensionPackage;

import java.util.Collection;
import java.util.List;

import java.util.Map;
import org.eclipse.core.commands.ParameterizedCommand;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MParameter;

import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.e4.ui.model.application.impl.StringToObjectMapImpl;
import org.eclipse.e4.ui.model.application.impl.StringToStringMapImpl;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MExpression;
import org.eclipse.e4.ui.model.application.ui.MGenericStack;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.MUILabel;

import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindowElement;
import org.eclipse.e4.ui.model.application.ui.basic.impl.PartStackImpl;

import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;

import org.eclipse.e4.ui.model.application.ui.menu.ItemType;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;

import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuPackageImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dialog</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link e4modelextension.impl.DialogImpl#isDirty <em>Dirty</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getElementId <em>Element Id</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getPersistedState <em>Persisted State</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getTags <em>Tags</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getContributorURI <em>Contributor URI</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getTransientData <em>Transient Data</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getWidget <em>Widget</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getRenderer <em>Renderer</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#isToBeRendered <em>To Be Rendered</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#isOnTop <em>On Top</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#isVisible <em>Visible</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getContainerData <em>Container Data</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getCurSharedRef <em>Cur Shared Ref</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getVisibleWhen <em>Visible When</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getAccessibilityPhrase <em>Accessibility Phrase</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getIconURI <em>Icon URI</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getTooltip <em>Tooltip</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#isSelected <em>Selected</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getType <em>Type</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getCommand <em>Command</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getWbCommand <em>Wb Command</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getSelectedElement <em>Selected Element</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getContributionURI <em>Contribution URI</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getObject <em>Object</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getContext <em>Context</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link e4modelextension.impl.DialogImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DialogImpl extends EObjectImpl implements Dialog {
	/**
	 * The default value of the '{@link #isDirty() <em>Dirty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirty()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DIRTY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDirty() <em>Dirty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirty()
	 * @generated
	 * @ordered
	 */
	protected boolean dirty = DIRTY_EDEFAULT;

	/**
	 * The default value of the '{@link #getElementId() <em>Element Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementId()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEMENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElementId() <em>Element Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementId()
	 * @generated
	 * @ordered
	 */
	protected String elementId = ELEMENT_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPersistedState() <em>Persisted State</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistedState()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> persistedState;

	/**
	 * The cached value of the '{@link #getTags() <em>Tags</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTags()
	 * @generated
	 * @ordered
	 */
	protected EList<String> tags;

	/**
	 * The default value of the '{@link #getContributorURI() <em>Contributor URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributorURI()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTRIBUTOR_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContributorURI() <em>Contributor URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributorURI()
	 * @generated
	 * @ordered
	 */
	protected String contributorURI = CONTRIBUTOR_URI_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTransientData() <em>Transient Data</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransientData()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Object> transientData;

	/**
	 * The default value of the '{@link #getWidget() <em>Widget</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidget()
	 * @generated
	 * @ordered
	 */
	protected static final Object WIDGET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWidget() <em>Widget</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidget()
	 * @generated
	 * @ordered
	 */
	protected Object widget = WIDGET_EDEFAULT;

	/**
	 * The default value of the '{@link #getRenderer() <em>Renderer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRenderer()
	 * @generated
	 * @ordered
	 */
	protected static final Object RENDERER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRenderer() <em>Renderer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRenderer()
	 * @generated
	 * @ordered
	 */
	protected Object renderer = RENDERER_EDEFAULT;

	/**
	 * The default value of the '{@link #isToBeRendered() <em>To Be Rendered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isToBeRendered()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TO_BE_RENDERED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isToBeRendered() <em>To Be Rendered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isToBeRendered()
	 * @generated
	 * @ordered
	 */
	protected boolean toBeRendered = TO_BE_RENDERED_EDEFAULT;

	/**
	 * The default value of the '{@link #isOnTop() <em>On Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOnTop()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ON_TOP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOnTop() <em>On Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOnTop()
	 * @generated
	 * @ordered
	 */
	protected boolean onTop = ON_TOP_EDEFAULT;

	/**
	 * The default value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VISIBLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected boolean visible = VISIBLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getContainerData() <em>Container Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerData()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTAINER_DATA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContainerData() <em>Container Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerData()
	 * @generated
	 * @ordered
	 */
	protected String containerData = CONTAINER_DATA_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCurSharedRef() <em>Cur Shared Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurSharedRef()
	 * @generated
	 * @ordered
	 */
	protected MPlaceholder curSharedRef;

	/**
	 * The cached value of the '{@link #getVisibleWhen() <em>Visible When</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisibleWhen()
	 * @generated
	 * @ordered
	 */
	protected MExpression visibleWhen;

	/**
	 * The default value of the '{@link #getAccessibilityPhrase() <em>Accessibility Phrase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessibilityPhrase()
	 * @generated
	 * @ordered
	 */
	protected static final String ACCESSIBILITY_PHRASE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAccessibilityPhrase() <em>Accessibility Phrase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessibilityPhrase()
	 * @generated
	 * @ordered
	 */
	protected String accessibilityPhrase = ACCESSIBILITY_PHRASE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getIconURI() <em>Icon URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIconURI()
	 * @generated
	 * @ordered
	 */
	protected static final String ICON_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIconURI() <em>Icon URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIconURI()
	 * @generated
	 * @ordered
	 */
	protected String iconURI = ICON_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getTooltip() <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTooltip()
	 * @generated
	 * @ordered
	 */
	protected static final String TOOLTIP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTooltip() <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTooltip()
	 * @generated
	 * @ordered
	 */
	protected String tooltip = TOOLTIP_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean enabled = ENABLED_EDEFAULT;

	/**
	 * The default value of the '{@link #isSelected() <em>Selected</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSelected()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SELECTED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSelected() <em>Selected</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSelected()
	 * @generated
	 * @ordered
	 */
	protected boolean selected = SELECTED_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final ItemType TYPE_EDEFAULT = ItemType.PUSH;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ItemType type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCommand() <em>Command</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommand()
	 * @generated
	 * @ordered
	 */
	protected MCommand command;

	/**
	 * The default value of the '{@link #getWbCommand() <em>Wb Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWbCommand()
	 * @generated
	 * @ordered
	 */
	protected static final ParameterizedCommand WB_COMMAND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWbCommand() <em>Wb Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWbCommand()
	 * @generated
	 * @ordered
	 */
	protected ParameterizedCommand wbCommand = WB_COMMAND_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<MParameter> parameters;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<MStackElement> children;

	/**
	 * The cached value of the '{@link #getSelectedElement() <em>Selected Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectedElement()
	 * @generated
	 * @ordered
	 */
	protected MStackElement selectedElement;

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
	 * The default value of the '{@link #getContext() <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected static final IEclipseContext CONTEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected IEclipseContext context = CONTEXT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<String> variables;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> properties;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DialogImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return E4modelextensionPackage.Literals.DIALOG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirty(boolean newDirty) {
		boolean oldDirty = dirty;
		dirty = newDirty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__DIRTY, oldDirty, dirty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getElementId() {
		return elementId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElementId(String newElementId) {
		String oldElementId = elementId;
		elementId = newElementId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__ELEMENT_ID, oldElementId, elementId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, String> getPersistedState() {
		if (persistedState == null) {
			persistedState = new EcoreEMap<String,String>(ApplicationPackageImpl.Literals.STRING_TO_STRING_MAP, StringToStringMapImpl.class, this, E4modelextensionPackage.DIALOG__PERSISTED_STATE);
		}
		return persistedState.map();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getTags() {
		if (tags == null) {
			tags = new EDataTypeUniqueEList<String>(String.class, this, E4modelextensionPackage.DIALOG__TAGS);
		}
		return tags;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContributorURI() {
		return contributorURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContributorURI(String newContributorURI) {
		String oldContributorURI = contributorURI;
		contributorURI = newContributorURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__CONTRIBUTOR_URI, oldContributorURI, contributorURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, Object> getTransientData() {
		if (transientData == null) {
			transientData = new EcoreEMap<String,Object>(ApplicationPackageImpl.Literals.STRING_TO_OBJECT_MAP, StringToObjectMapImpl.class, this, E4modelextensionPackage.DIALOG__TRANSIENT_DATA);
		}
		return transientData.map();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getWidget() {
		return widget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidget(Object newWidget) {
		Object oldWidget = widget;
		widget = newWidget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__WIDGET, oldWidget, widget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getRenderer() {
		return renderer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRenderer(Object newRenderer) {
		Object oldRenderer = renderer;
		renderer = newRenderer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__RENDERER, oldRenderer, renderer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isToBeRendered() {
		return toBeRendered;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToBeRendered(boolean newToBeRendered) {
		boolean oldToBeRendered = toBeRendered;
		toBeRendered = newToBeRendered;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__TO_BE_RENDERED, oldToBeRendered, toBeRendered));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOnTop() {
		return onTop;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnTop(boolean newOnTop) {
		boolean oldOnTop = onTop;
		onTop = newOnTop;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__ON_TOP, oldOnTop, onTop));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisible(boolean newVisible) {
		boolean oldVisible = visible;
		visible = newVisible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__VISIBLE, oldVisible, visible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public MElementContainer<MUIElement> getParent() {
		if (eContainerFeatureID() != E4modelextensionPackage.DIALOG__PARENT) return null;
		return (MElementContainer<MUIElement>)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(MElementContainer<MUIElement> newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, E4modelextensionPackage.DIALOG__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(MElementContainer<MUIElement> newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != E4modelextensionPackage.DIALOG__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, (EObject)newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, UiPackageImpl.ELEMENT_CONTAINER__CHILDREN, MElementContainer.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContainerData() {
		return containerData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainerData(String newContainerData) {
		String oldContainerData = containerData;
		containerData = newContainerData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__CONTAINER_DATA, oldContainerData, containerData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MPlaceholder getCurSharedRef() {
		if (curSharedRef != null && ((EObject)curSharedRef).eIsProxy()) {
			InternalEObject oldCurSharedRef = (InternalEObject)curSharedRef;
			curSharedRef = (MPlaceholder)eResolveProxy(oldCurSharedRef);
			if (curSharedRef != oldCurSharedRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, E4modelextensionPackage.DIALOG__CUR_SHARED_REF, oldCurSharedRef, curSharedRef));
			}
		}
		return curSharedRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MPlaceholder basicGetCurSharedRef() {
		return curSharedRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurSharedRef(MPlaceholder newCurSharedRef) {
		MPlaceholder oldCurSharedRef = curSharedRef;
		curSharedRef = newCurSharedRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__CUR_SHARED_REF, oldCurSharedRef, curSharedRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MExpression getVisibleWhen() {
		return visibleWhen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVisibleWhen(MExpression newVisibleWhen, NotificationChain msgs) {
		MExpression oldVisibleWhen = visibleWhen;
		visibleWhen = newVisibleWhen;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__VISIBLE_WHEN, oldVisibleWhen, newVisibleWhen);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisibleWhen(MExpression newVisibleWhen) {
		if (newVisibleWhen != visibleWhen) {
			NotificationChain msgs = null;
			if (visibleWhen != null)
				msgs = ((InternalEObject)visibleWhen).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - E4modelextensionPackage.DIALOG__VISIBLE_WHEN, null, msgs);
			if (newVisibleWhen != null)
				msgs = ((InternalEObject)newVisibleWhen).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - E4modelextensionPackage.DIALOG__VISIBLE_WHEN, null, msgs);
			msgs = basicSetVisibleWhen(newVisibleWhen, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__VISIBLE_WHEN, newVisibleWhen, newVisibleWhen));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAccessibilityPhrase() {
		return accessibilityPhrase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccessibilityPhrase(String newAccessibilityPhrase) {
		String oldAccessibilityPhrase = accessibilityPhrase;
		accessibilityPhrase = newAccessibilityPhrase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__ACCESSIBILITY_PHRASE, oldAccessibilityPhrase, accessibilityPhrase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIconURI() {
		return iconURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIconURI(String newIconURI) {
		String oldIconURI = iconURI;
		iconURI = newIconURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__ICON_URI, oldIconURI, iconURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTooltip(String newTooltip) {
		String oldTooltip = tooltip;
		tooltip = newTooltip;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__TOOLTIP, oldTooltip, tooltip));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnabled(boolean newEnabled) {
		boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__ENABLED, oldEnabled, enabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSelected(boolean newSelected) {
		boolean oldSelected = selected;
		selected = newSelected;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__SELECTED, oldSelected, selected));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ItemType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ItemType newType) {
		ItemType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MCommand getCommand() {
		if (command != null && ((EObject)command).eIsProxy()) {
			InternalEObject oldCommand = (InternalEObject)command;
			command = (MCommand)eResolveProxy(oldCommand);
			if (command != oldCommand) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, E4modelextensionPackage.DIALOG__COMMAND, oldCommand, command));
			}
		}
		return command;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MCommand basicGetCommand() {
		return command;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommand(MCommand newCommand) {
		MCommand oldCommand = command;
		command = newCommand;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__COMMAND, oldCommand, command));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterizedCommand getWbCommand() {
		return wbCommand;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWbCommand(ParameterizedCommand newWbCommand) {
		ParameterizedCommand oldWbCommand = wbCommand;
		wbCommand = newWbCommand;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__WB_COMMAND, oldWbCommand, wbCommand));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<MParameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<MParameter>(MParameter.class, this, E4modelextensionPackage.DIALOG__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<MStackElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<MStackElement>(MUIElement.class, this, E4modelextensionPackage.DIALOG__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStackElement getSelectedElement() {
		if (selectedElement != null && ((EObject)selectedElement).eIsProxy()) {
			InternalEObject oldSelectedElement = (InternalEObject)selectedElement;
			selectedElement = (MStackElement)eResolveProxy(oldSelectedElement);
			if (selectedElement != oldSelectedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, E4modelextensionPackage.DIALOG__SELECTED_ELEMENT, oldSelectedElement, selectedElement));
			}
		}
		return selectedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStackElement basicGetSelectedElement() {
		return selectedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSelectedElement(MStackElement newSelectedElement) {
		MStackElement oldSelectedElement = selectedElement;
		selectedElement = newSelectedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__SELECTED_ELEMENT, oldSelectedElement, selectedElement));
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
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__CONTRIBUTION_URI, oldContributionURI, contributionURI));
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
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__OBJECT, oldObject, object));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IEclipseContext getContext() {
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContext(IEclipseContext newContext) {
		IEclipseContext oldContext = context;
		context = newContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.DIALOG__CONTEXT, oldContext, context));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getVariables() {
		if (variables == null) {
			variables = new EDataTypeUniqueEList<String>(String.class, this, E4modelextensionPackage.DIALOG__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, String> getProperties() {
		if (properties == null) {
			properties = new EcoreEMap<String,String>(ApplicationPackageImpl.Literals.STRING_TO_STRING_MAP, StringToStringMapImpl.class, this, E4modelextensionPackage.DIALOG__PROPERTIES);
		}
		return properties.map();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocalizedLabel() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocalizedTooltip() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocalizedAccessibilityPhrase() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case E4modelextensionPackage.DIALOG__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((MElementContainer<MUIElement>)otherEnd, msgs);
			case E4modelextensionPackage.DIALOG__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
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
			case E4modelextensionPackage.DIALOG__PERSISTED_STATE:
				return ((InternalEList<?>)((EMap.InternalMapView<String, String>)getPersistedState()).eMap()).basicRemove(otherEnd, msgs);
			case E4modelextensionPackage.DIALOG__TRANSIENT_DATA:
				return ((InternalEList<?>)((EMap.InternalMapView<String, Object>)getTransientData()).eMap()).basicRemove(otherEnd, msgs);
			case E4modelextensionPackage.DIALOG__PARENT:
				return basicSetParent(null, msgs);
			case E4modelextensionPackage.DIALOG__VISIBLE_WHEN:
				return basicSetVisibleWhen(null, msgs);
			case E4modelextensionPackage.DIALOG__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case E4modelextensionPackage.DIALOG__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case E4modelextensionPackage.DIALOG__PROPERTIES:
				return ((InternalEList<?>)((EMap.InternalMapView<String, String>)getProperties()).eMap()).basicRemove(otherEnd, msgs);
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
			case E4modelextensionPackage.DIALOG__PARENT:
				return eInternalContainer().eInverseRemove(this, UiPackageImpl.ELEMENT_CONTAINER__CHILDREN, MElementContainer.class, msgs);
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
			case E4modelextensionPackage.DIALOG__DIRTY:
				return isDirty();
			case E4modelextensionPackage.DIALOG__ELEMENT_ID:
				return getElementId();
			case E4modelextensionPackage.DIALOG__PERSISTED_STATE:
				if (coreType) return ((EMap.InternalMapView<String, String>)getPersistedState()).eMap();
				else return getPersistedState();
			case E4modelextensionPackage.DIALOG__TAGS:
				return getTags();
			case E4modelextensionPackage.DIALOG__CONTRIBUTOR_URI:
				return getContributorURI();
			case E4modelextensionPackage.DIALOG__TRANSIENT_DATA:
				if (coreType) return ((EMap.InternalMapView<String, Object>)getTransientData()).eMap();
				else return getTransientData();
			case E4modelextensionPackage.DIALOG__WIDGET:
				return getWidget();
			case E4modelextensionPackage.DIALOG__RENDERER:
				return getRenderer();
			case E4modelextensionPackage.DIALOG__TO_BE_RENDERED:
				return isToBeRendered();
			case E4modelextensionPackage.DIALOG__ON_TOP:
				return isOnTop();
			case E4modelextensionPackage.DIALOG__VISIBLE:
				return isVisible();
			case E4modelextensionPackage.DIALOG__PARENT:
				return getParent();
			case E4modelextensionPackage.DIALOG__CONTAINER_DATA:
				return getContainerData();
			case E4modelextensionPackage.DIALOG__CUR_SHARED_REF:
				if (resolve) return getCurSharedRef();
				return basicGetCurSharedRef();
			case E4modelextensionPackage.DIALOG__VISIBLE_WHEN:
				return getVisibleWhen();
			case E4modelextensionPackage.DIALOG__ACCESSIBILITY_PHRASE:
				return getAccessibilityPhrase();
			case E4modelextensionPackage.DIALOG__LABEL:
				return getLabel();
			case E4modelextensionPackage.DIALOG__ICON_URI:
				return getIconURI();
			case E4modelextensionPackage.DIALOG__TOOLTIP:
				return getTooltip();
			case E4modelextensionPackage.DIALOG__ENABLED:
				return isEnabled();
			case E4modelextensionPackage.DIALOG__SELECTED:
				return isSelected();
			case E4modelextensionPackage.DIALOG__TYPE:
				return getType();
			case E4modelextensionPackage.DIALOG__COMMAND:
				if (resolve) return getCommand();
				return basicGetCommand();
			case E4modelextensionPackage.DIALOG__WB_COMMAND:
				return getWbCommand();
			case E4modelextensionPackage.DIALOG__PARAMETERS:
				return getParameters();
			case E4modelextensionPackage.DIALOG__CHILDREN:
				return getChildren();
			case E4modelextensionPackage.DIALOG__SELECTED_ELEMENT:
				if (resolve) return getSelectedElement();
				return basicGetSelectedElement();
			case E4modelextensionPackage.DIALOG__CONTRIBUTION_URI:
				return getContributionURI();
			case E4modelextensionPackage.DIALOG__OBJECT:
				return getObject();
			case E4modelextensionPackage.DIALOG__CONTEXT:
				return getContext();
			case E4modelextensionPackage.DIALOG__VARIABLES:
				return getVariables();
			case E4modelextensionPackage.DIALOG__PROPERTIES:
				if (coreType) return ((EMap.InternalMapView<String, String>)getProperties()).eMap();
				else return getProperties();
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
			case E4modelextensionPackage.DIALOG__DIRTY:
				setDirty((Boolean)newValue);
				return;
			case E4modelextensionPackage.DIALOG__ELEMENT_ID:
				setElementId((String)newValue);
				return;
			case E4modelextensionPackage.DIALOG__PERSISTED_STATE:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getPersistedState()).eMap()).set(newValue);
				return;
			case E4modelextensionPackage.DIALOG__TAGS:
				getTags().clear();
				getTags().addAll((Collection<? extends String>)newValue);
				return;
			case E4modelextensionPackage.DIALOG__CONTRIBUTOR_URI:
				setContributorURI((String)newValue);
				return;
			case E4modelextensionPackage.DIALOG__TRANSIENT_DATA:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, Object>)getTransientData()).eMap()).set(newValue);
				return;
			case E4modelextensionPackage.DIALOG__WIDGET:
				setWidget(newValue);
				return;
			case E4modelextensionPackage.DIALOG__RENDERER:
				setRenderer(newValue);
				return;
			case E4modelextensionPackage.DIALOG__TO_BE_RENDERED:
				setToBeRendered((Boolean)newValue);
				return;
			case E4modelextensionPackage.DIALOG__ON_TOP:
				setOnTop((Boolean)newValue);
				return;
			case E4modelextensionPackage.DIALOG__VISIBLE:
				setVisible((Boolean)newValue);
				return;
			case E4modelextensionPackage.DIALOG__PARENT:
				setParent((MElementContainer<MUIElement>)newValue);
				return;
			case E4modelextensionPackage.DIALOG__CONTAINER_DATA:
				setContainerData((String)newValue);
				return;
			case E4modelextensionPackage.DIALOG__CUR_SHARED_REF:
				setCurSharedRef((MPlaceholder)newValue);
				return;
			case E4modelextensionPackage.DIALOG__VISIBLE_WHEN:
				setVisibleWhen((MExpression)newValue);
				return;
			case E4modelextensionPackage.DIALOG__ACCESSIBILITY_PHRASE:
				setAccessibilityPhrase((String)newValue);
				return;
			case E4modelextensionPackage.DIALOG__LABEL:
				setLabel((String)newValue);
				return;
			case E4modelextensionPackage.DIALOG__ICON_URI:
				setIconURI((String)newValue);
				return;
			case E4modelextensionPackage.DIALOG__TOOLTIP:
				setTooltip((String)newValue);
				return;
			case E4modelextensionPackage.DIALOG__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case E4modelextensionPackage.DIALOG__SELECTED:
				setSelected((Boolean)newValue);
				return;
			case E4modelextensionPackage.DIALOG__TYPE:
				setType((ItemType)newValue);
				return;
			case E4modelextensionPackage.DIALOG__COMMAND:
				setCommand((MCommand)newValue);
				return;
			case E4modelextensionPackage.DIALOG__WB_COMMAND:
				setWbCommand((ParameterizedCommand)newValue);
				return;
			case E4modelextensionPackage.DIALOG__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends MParameter>)newValue);
				return;
			case E4modelextensionPackage.DIALOG__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends MStackElement>)newValue);
				return;
			case E4modelextensionPackage.DIALOG__SELECTED_ELEMENT:
				setSelectedElement((MStackElement)newValue);
				return;
			case E4modelextensionPackage.DIALOG__CONTRIBUTION_URI:
				setContributionURI((String)newValue);
				return;
			case E4modelextensionPackage.DIALOG__OBJECT:
				setObject(newValue);
				return;
			case E4modelextensionPackage.DIALOG__CONTEXT:
				setContext((IEclipseContext)newValue);
				return;
			case E4modelextensionPackage.DIALOG__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends String>)newValue);
				return;
			case E4modelextensionPackage.DIALOG__PROPERTIES:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getProperties()).eMap()).set(newValue);
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
			case E4modelextensionPackage.DIALOG__DIRTY:
				setDirty(DIRTY_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__ELEMENT_ID:
				setElementId(ELEMENT_ID_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__PERSISTED_STATE:
				getPersistedState().clear();
				return;
			case E4modelextensionPackage.DIALOG__TAGS:
				getTags().clear();
				return;
			case E4modelextensionPackage.DIALOG__CONTRIBUTOR_URI:
				setContributorURI(CONTRIBUTOR_URI_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__TRANSIENT_DATA:
				getTransientData().clear();
				return;
			case E4modelextensionPackage.DIALOG__WIDGET:
				setWidget(WIDGET_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__RENDERER:
				setRenderer(RENDERER_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__TO_BE_RENDERED:
				setToBeRendered(TO_BE_RENDERED_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__ON_TOP:
				setOnTop(ON_TOP_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__VISIBLE:
				setVisible(VISIBLE_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__PARENT:
				setParent((MElementContainer<MUIElement>)null);
				return;
			case E4modelextensionPackage.DIALOG__CONTAINER_DATA:
				setContainerData(CONTAINER_DATA_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__CUR_SHARED_REF:
				setCurSharedRef((MPlaceholder)null);
				return;
			case E4modelextensionPackage.DIALOG__VISIBLE_WHEN:
				setVisibleWhen((MExpression)null);
				return;
			case E4modelextensionPackage.DIALOG__ACCESSIBILITY_PHRASE:
				setAccessibilityPhrase(ACCESSIBILITY_PHRASE_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__ICON_URI:
				setIconURI(ICON_URI_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__TOOLTIP:
				setTooltip(TOOLTIP_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__SELECTED:
				setSelected(SELECTED_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__COMMAND:
				setCommand((MCommand)null);
				return;
			case E4modelextensionPackage.DIALOG__WB_COMMAND:
				setWbCommand(WB_COMMAND_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__PARAMETERS:
				getParameters().clear();
				return;
			case E4modelextensionPackage.DIALOG__CHILDREN:
				getChildren().clear();
				return;
			case E4modelextensionPackage.DIALOG__SELECTED_ELEMENT:
				setSelectedElement((MStackElement)null);
				return;
			case E4modelextensionPackage.DIALOG__CONTRIBUTION_URI:
				setContributionURI(CONTRIBUTION_URI_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__OBJECT:
				setObject(OBJECT_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__CONTEXT:
				setContext(CONTEXT_EDEFAULT);
				return;
			case E4modelextensionPackage.DIALOG__VARIABLES:
				getVariables().clear();
				return;
			case E4modelextensionPackage.DIALOG__PROPERTIES:
				getProperties().clear();
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
			case E4modelextensionPackage.DIALOG__DIRTY:
				return dirty != DIRTY_EDEFAULT;
			case E4modelextensionPackage.DIALOG__ELEMENT_ID:
				return ELEMENT_ID_EDEFAULT == null ? elementId != null : !ELEMENT_ID_EDEFAULT.equals(elementId);
			case E4modelextensionPackage.DIALOG__PERSISTED_STATE:
				return persistedState != null && !persistedState.isEmpty();
			case E4modelextensionPackage.DIALOG__TAGS:
				return tags != null && !tags.isEmpty();
			case E4modelextensionPackage.DIALOG__CONTRIBUTOR_URI:
				return CONTRIBUTOR_URI_EDEFAULT == null ? contributorURI != null : !CONTRIBUTOR_URI_EDEFAULT.equals(contributorURI);
			case E4modelextensionPackage.DIALOG__TRANSIENT_DATA:
				return transientData != null && !transientData.isEmpty();
			case E4modelextensionPackage.DIALOG__WIDGET:
				return WIDGET_EDEFAULT == null ? widget != null : !WIDGET_EDEFAULT.equals(widget);
			case E4modelextensionPackage.DIALOG__RENDERER:
				return RENDERER_EDEFAULT == null ? renderer != null : !RENDERER_EDEFAULT.equals(renderer);
			case E4modelextensionPackage.DIALOG__TO_BE_RENDERED:
				return toBeRendered != TO_BE_RENDERED_EDEFAULT;
			case E4modelextensionPackage.DIALOG__ON_TOP:
				return onTop != ON_TOP_EDEFAULT;
			case E4modelextensionPackage.DIALOG__VISIBLE:
				return visible != VISIBLE_EDEFAULT;
			case E4modelextensionPackage.DIALOG__PARENT:
				return getParent() != null;
			case E4modelextensionPackage.DIALOG__CONTAINER_DATA:
				return CONTAINER_DATA_EDEFAULT == null ? containerData != null : !CONTAINER_DATA_EDEFAULT.equals(containerData);
			case E4modelextensionPackage.DIALOG__CUR_SHARED_REF:
				return curSharedRef != null;
			case E4modelextensionPackage.DIALOG__VISIBLE_WHEN:
				return visibleWhen != null;
			case E4modelextensionPackage.DIALOG__ACCESSIBILITY_PHRASE:
				return ACCESSIBILITY_PHRASE_EDEFAULT == null ? accessibilityPhrase != null : !ACCESSIBILITY_PHRASE_EDEFAULT.equals(accessibilityPhrase);
			case E4modelextensionPackage.DIALOG__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case E4modelextensionPackage.DIALOG__ICON_URI:
				return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
			case E4modelextensionPackage.DIALOG__TOOLTIP:
				return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
			case E4modelextensionPackage.DIALOG__ENABLED:
				return enabled != ENABLED_EDEFAULT;
			case E4modelextensionPackage.DIALOG__SELECTED:
				return selected != SELECTED_EDEFAULT;
			case E4modelextensionPackage.DIALOG__TYPE:
				return type != TYPE_EDEFAULT;
			case E4modelextensionPackage.DIALOG__COMMAND:
				return command != null;
			case E4modelextensionPackage.DIALOG__WB_COMMAND:
				return WB_COMMAND_EDEFAULT == null ? wbCommand != null : !WB_COMMAND_EDEFAULT.equals(wbCommand);
			case E4modelextensionPackage.DIALOG__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case E4modelextensionPackage.DIALOG__CHILDREN:
				return children != null && !children.isEmpty();
			case E4modelextensionPackage.DIALOG__SELECTED_ELEMENT:
				return selectedElement != null;
			case E4modelextensionPackage.DIALOG__CONTRIBUTION_URI:
				return CONTRIBUTION_URI_EDEFAULT == null ? contributionURI != null : !CONTRIBUTION_URI_EDEFAULT.equals(contributionURI);
			case E4modelextensionPackage.DIALOG__OBJECT:
				return OBJECT_EDEFAULT == null ? object != null : !OBJECT_EDEFAULT.equals(object);
			case E4modelextensionPackage.DIALOG__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case E4modelextensionPackage.DIALOG__VARIABLES:
				return variables != null && !variables.isEmpty();
			case E4modelextensionPackage.DIALOG__PROPERTIES:
				return properties != null && !properties.isEmpty();
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
		if (baseClass == MApplicationElement.class) {
			switch (derivedFeatureID) {
				case E4modelextensionPackage.DIALOG__ELEMENT_ID: return ApplicationPackageImpl.APPLICATION_ELEMENT__ELEMENT_ID;
				case E4modelextensionPackage.DIALOG__PERSISTED_STATE: return ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTED_STATE;
				case E4modelextensionPackage.DIALOG__TAGS: return ApplicationPackageImpl.APPLICATION_ELEMENT__TAGS;
				case E4modelextensionPackage.DIALOG__CONTRIBUTOR_URI: return ApplicationPackageImpl.APPLICATION_ELEMENT__CONTRIBUTOR_URI;
				case E4modelextensionPackage.DIALOG__TRANSIENT_DATA: return ApplicationPackageImpl.APPLICATION_ELEMENT__TRANSIENT_DATA;
				default: return -1;
			}
		}
		if (baseClass == MUIElement.class) {
			switch (derivedFeatureID) {
				case E4modelextensionPackage.DIALOG__WIDGET: return UiPackageImpl.UI_ELEMENT__WIDGET;
				case E4modelextensionPackage.DIALOG__RENDERER: return UiPackageImpl.UI_ELEMENT__RENDERER;
				case E4modelextensionPackage.DIALOG__TO_BE_RENDERED: return UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED;
				case E4modelextensionPackage.DIALOG__ON_TOP: return UiPackageImpl.UI_ELEMENT__ON_TOP;
				case E4modelextensionPackage.DIALOG__VISIBLE: return UiPackageImpl.UI_ELEMENT__VISIBLE;
				case E4modelextensionPackage.DIALOG__PARENT: return UiPackageImpl.UI_ELEMENT__PARENT;
				case E4modelextensionPackage.DIALOG__CONTAINER_DATA: return UiPackageImpl.UI_ELEMENT__CONTAINER_DATA;
				case E4modelextensionPackage.DIALOG__CUR_SHARED_REF: return UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF;
				case E4modelextensionPackage.DIALOG__VISIBLE_WHEN: return UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN;
				case E4modelextensionPackage.DIALOG__ACCESSIBILITY_PHRASE: return UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE;
				default: return -1;
			}
		}
		if (baseClass == MUILabel.class) {
			switch (derivedFeatureID) {
				case E4modelextensionPackage.DIALOG__LABEL: return UiPackageImpl.UI_LABEL__LABEL;
				case E4modelextensionPackage.DIALOG__ICON_URI: return UiPackageImpl.UI_LABEL__ICON_URI;
				case E4modelextensionPackage.DIALOG__TOOLTIP: return UiPackageImpl.UI_LABEL__TOOLTIP;
				default: return -1;
			}
		}
		if (baseClass == MItem.class) {
			switch (derivedFeatureID) {
				case E4modelextensionPackage.DIALOG__ENABLED: return MenuPackageImpl.ITEM__ENABLED;
				case E4modelextensionPackage.DIALOG__SELECTED: return MenuPackageImpl.ITEM__SELECTED;
				case E4modelextensionPackage.DIALOG__TYPE: return MenuPackageImpl.ITEM__TYPE;
				default: return -1;
			}
		}
		if (baseClass == MHandledItem.class) {
			switch (derivedFeatureID) {
				case E4modelextensionPackage.DIALOG__COMMAND: return MenuPackageImpl.HANDLED_ITEM__COMMAND;
				case E4modelextensionPackage.DIALOG__WB_COMMAND: return MenuPackageImpl.HANDLED_ITEM__WB_COMMAND;
				case E4modelextensionPackage.DIALOG__PARAMETERS: return MenuPackageImpl.HANDLED_ITEM__PARAMETERS;
				default: return -1;
			}
		}
		if (baseClass == MElementContainer.class) {
			switch (derivedFeatureID) {
				case E4modelextensionPackage.DIALOG__CHILDREN: return UiPackageImpl.ELEMENT_CONTAINER__CHILDREN;
				case E4modelextensionPackage.DIALOG__SELECTED_ELEMENT: return UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == MGenericStack.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MPartSashContainerElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MWindowElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MPartStack.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MContribution.class) {
			switch (derivedFeatureID) {
				case E4modelextensionPackage.DIALOG__CONTRIBUTION_URI: return ApplicationPackageImpl.CONTRIBUTION__CONTRIBUTION_URI;
				case E4modelextensionPackage.DIALOG__OBJECT: return ApplicationPackageImpl.CONTRIBUTION__OBJECT;
				default: return -1;
			}
		}
		if (baseClass == MContext.class) {
			switch (derivedFeatureID) {
				case E4modelextensionPackage.DIALOG__CONTEXT: return UiPackageImpl.CONTEXT__CONTEXT;
				case E4modelextensionPackage.DIALOG__VARIABLES: return UiPackageImpl.CONTEXT__VARIABLES;
				case E4modelextensionPackage.DIALOG__PROPERTIES: return UiPackageImpl.CONTEXT__PROPERTIES;
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
		if (baseClass == MApplicationElement.class) {
			switch (baseFeatureID) {
				case ApplicationPackageImpl.APPLICATION_ELEMENT__ELEMENT_ID: return E4modelextensionPackage.DIALOG__ELEMENT_ID;
				case ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTED_STATE: return E4modelextensionPackage.DIALOG__PERSISTED_STATE;
				case ApplicationPackageImpl.APPLICATION_ELEMENT__TAGS: return E4modelextensionPackage.DIALOG__TAGS;
				case ApplicationPackageImpl.APPLICATION_ELEMENT__CONTRIBUTOR_URI: return E4modelextensionPackage.DIALOG__CONTRIBUTOR_URI;
				case ApplicationPackageImpl.APPLICATION_ELEMENT__TRANSIENT_DATA: return E4modelextensionPackage.DIALOG__TRANSIENT_DATA;
				default: return -1;
			}
		}
		if (baseClass == MUIElement.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.UI_ELEMENT__WIDGET: return E4modelextensionPackage.DIALOG__WIDGET;
				case UiPackageImpl.UI_ELEMENT__RENDERER: return E4modelextensionPackage.DIALOG__RENDERER;
				case UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED: return E4modelextensionPackage.DIALOG__TO_BE_RENDERED;
				case UiPackageImpl.UI_ELEMENT__ON_TOP: return E4modelextensionPackage.DIALOG__ON_TOP;
				case UiPackageImpl.UI_ELEMENT__VISIBLE: return E4modelextensionPackage.DIALOG__VISIBLE;
				case UiPackageImpl.UI_ELEMENT__PARENT: return E4modelextensionPackage.DIALOG__PARENT;
				case UiPackageImpl.UI_ELEMENT__CONTAINER_DATA: return E4modelextensionPackage.DIALOG__CONTAINER_DATA;
				case UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF: return E4modelextensionPackage.DIALOG__CUR_SHARED_REF;
				case UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN: return E4modelextensionPackage.DIALOG__VISIBLE_WHEN;
				case UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE: return E4modelextensionPackage.DIALOG__ACCESSIBILITY_PHRASE;
				default: return -1;
			}
		}
		if (baseClass == MUILabel.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.UI_LABEL__LABEL: return E4modelextensionPackage.DIALOG__LABEL;
				case UiPackageImpl.UI_LABEL__ICON_URI: return E4modelextensionPackage.DIALOG__ICON_URI;
				case UiPackageImpl.UI_LABEL__TOOLTIP: return E4modelextensionPackage.DIALOG__TOOLTIP;
				default: return -1;
			}
		}
		if (baseClass == MItem.class) {
			switch (baseFeatureID) {
				case MenuPackageImpl.ITEM__ENABLED: return E4modelextensionPackage.DIALOG__ENABLED;
				case MenuPackageImpl.ITEM__SELECTED: return E4modelextensionPackage.DIALOG__SELECTED;
				case MenuPackageImpl.ITEM__TYPE: return E4modelextensionPackage.DIALOG__TYPE;
				default: return -1;
			}
		}
		if (baseClass == MHandledItem.class) {
			switch (baseFeatureID) {
				case MenuPackageImpl.HANDLED_ITEM__COMMAND: return E4modelextensionPackage.DIALOG__COMMAND;
				case MenuPackageImpl.HANDLED_ITEM__WB_COMMAND: return E4modelextensionPackage.DIALOG__WB_COMMAND;
				case MenuPackageImpl.HANDLED_ITEM__PARAMETERS: return E4modelextensionPackage.DIALOG__PARAMETERS;
				default: return -1;
			}
		}
		if (baseClass == MElementContainer.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.ELEMENT_CONTAINER__CHILDREN: return E4modelextensionPackage.DIALOG__CHILDREN;
				case UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT: return E4modelextensionPackage.DIALOG__SELECTED_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == MGenericStack.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MPartSashContainerElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MWindowElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MPartStack.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MContribution.class) {
			switch (baseFeatureID) {
				case ApplicationPackageImpl.CONTRIBUTION__CONTRIBUTION_URI: return E4modelextensionPackage.DIALOG__CONTRIBUTION_URI;
				case ApplicationPackageImpl.CONTRIBUTION__OBJECT: return E4modelextensionPackage.DIALOG__OBJECT;
				default: return -1;
			}
		}
		if (baseClass == MContext.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.CONTEXT__CONTEXT: return E4modelextensionPackage.DIALOG__CONTEXT;
				case UiPackageImpl.CONTEXT__VARIABLES: return E4modelextensionPackage.DIALOG__VARIABLES;
				case UiPackageImpl.CONTEXT__PROPERTIES: return E4modelextensionPackage.DIALOG__PROPERTIES;
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
		result.append(" (dirty: ");
		result.append(dirty);
		result.append(", elementId: ");
		result.append(elementId);
		result.append(", tags: ");
		result.append(tags);
		result.append(", contributorURI: ");
		result.append(contributorURI);
		result.append(", widget: ");
		result.append(widget);
		result.append(", renderer: ");
		result.append(renderer);
		result.append(", toBeRendered: ");
		result.append(toBeRendered);
		result.append(", onTop: ");
		result.append(onTop);
		result.append(", visible: ");
		result.append(visible);
		result.append(", containerData: ");
		result.append(containerData);
		result.append(", accessibilityPhrase: ");
		result.append(accessibilityPhrase);
		result.append(", label: ");
		result.append(label);
		result.append(", iconURI: ");
		result.append(iconURI);
		result.append(", tooltip: ");
		result.append(tooltip);
		result.append(", enabled: ");
		result.append(enabled);
		result.append(", selected: ");
		result.append(selected);
		result.append(", type: ");
		result.append(type);
		result.append(", wbCommand: ");
		result.append(wbCommand);
		result.append(", contributionURI: ");
		result.append(contributionURI);
		result.append(", object: ");
		result.append(object);
		result.append(", context: ");
		result.append(context);
		result.append(", variables: ");
		result.append(variables);
		result.append(')');
		return result.toString();
	}

} //DialogImpl
