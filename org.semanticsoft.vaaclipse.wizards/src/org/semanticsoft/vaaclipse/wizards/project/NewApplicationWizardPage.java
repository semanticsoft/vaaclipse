/*******************************************************************************
 * Copyright (c) 2006, 2012 Soyatec (http://www.soyatec.com) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Soyatec - initial API and implementation
 *     IBM Corporation - ongoing enhancements
 *     Lars Vogel, vogella GmbH - ongoing enhancements
 *     Sopot Cela - ongoing enhancements
 *     Rushan Gilmullin - adapt to vaaclipse
 *******************************************************************************/
package org.semanticsoft.vaaclipse.wizards.project;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.branding.IProductConstants;

/**
 * @author jin.liu (jin.liu@soyatec.com)
 */
public class NewApplicationWizardPage extends WizardPage {
	public static final String THEME_CONTRIBUTION_RESOURCE_PATH = "mainThemeContributionResourcePath";
	public static final String PRODUCT_NAME = "productName";
	public static final String CLEAR_PERSISTED_STATE = "clearPersistedState";
	public static final String CONTEXT_PATH = "contextPath";
	public static final String PORT = "port";
	public static final String EOL = System.getProperty("line.separator");
	public static final String richSample = "RICH_SAMPLE";
	private final Map<String, String> data;

	private IProject project;
	private IProjectProvider projectProvider;
//	private Text proNameText;
	private Text contextPathText;
	private Text portText;
	private Group propertyGroup;
	private AbstractFieldData pluginData;

	private PropertyData[] PROPERTIES;

	protected NewApplicationWizardPage(IProjectProvider projectProvider,
			AbstractFieldData pluginData) {
		super("New Eclipse 4 Vaadin Application Wizard Page");
		this.projectProvider = projectProvider;
		this.pluginData = pluginData;
		data = new HashMap<String, String>();
		data.put(richSample, "TRUE");// minimalist by default
		setTitle("Eclipse 4 Vaadin Application");
		setMessage("Configure application with special values.");
	}

	public IProject getProject() {
		if (project == null && projectProvider != null) {
			project = projectProvider.getProject();
		}
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	public void createControl(Composite parent) {
		Composite control = new Composite(parent, SWT.NONE);
		control.setLayout(new GridLayout());

//		Group productGroup = createProductGroup(control);
//		productGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Group webAppGroup = createWebAppGroup(control);
		webAppGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		propertyGroup = createPropertyGroup(control);
		propertyGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

//		Group templateGroup = createTemplateGroup(control);
//		templateGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		setControl(control);
	}

//	private Group createTemplateGroup(Composite parent) {
//		Group group = new Group(parent, SWT.NONE);
//		group.setLayout(new GridLayout(1, false));
//		group.setText("Template option");
//		
//		richSampleCheckbox = new Button(group, SWT.CHECK);
//		
//		richSampleCheckbox.setSelection(false);
//		richSampleCheckbox.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				data.put(richSample, richSampleCheckbox.getSelection() ? "TRUE"
//						: "FALSE");
//			}
//		});
//		richSampleCheckbox.setText("Create sample content (parts, menu etc.)");
//		return group;
//	}
	
	static class PropertyData {
		private String name;
		private String label;
		private String extraTooltipInfo;

		private String value;
		private Class<?> type;
		private boolean editable;

		public PropertyData(String name, String label, String value,
				Class<?> type, boolean editable) {
			this.name = name;
			this.value = value;
			this.label = label;
			this.type = type;
			this.editable = editable;
		}

		public PropertyData(String name, String label, String value,
				Class<?> type, boolean editable, String extraTooltipInfo) {
			this.name = name;
			this.value = value;
			this.label = label;
			this.type = type;
			this.editable = editable;
			this.extraTooltipInfo = extraTooltipInfo;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public Class<?> getType() {
			return type;
		}

		public boolean isEditable() {
			return editable;
		}

		public String getLabel() {
			return label;
		}

		public String getExtraTooltipInfo() {
			return extraTooltipInfo;
		}

	}

	private Group createPropertyGroup(Composite control) {
		Group group = new Group(control, SWT.NONE);
		group.setText("Properties");

		group.setLayout(new GridLayout(3, false));

		return group;
	}

	private void createPropertyItem(final Composite parent,
			final PropertyData property) {
		

		if (property.getType() == Boolean.class) {
			final Button button = new Button(parent, SWT.CHECK);
			button.setSelection("true".equalsIgnoreCase(property.getValue()));
			button.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					handleCheckBoxEvent(property.getName(),
							button.getSelection());
				}
			});
			button.setText(property.getLabel());
			new Label(parent, SWT.NONE);
		} else {
			createLabelForField(parent, property);
			final Text valueText = new Text(parent, SWT.BORDER);
			valueText.setText(property.getValue());
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			valueText.setLayoutData(gridData);
			if (!property.isEditable()) {
				valueText.setEditable(false);
			}
			valueText.addListener(SWT.Modify, new Listener() {
				public void handleEvent(Event event) {
					handleTextEvent(property.getName(), valueText);
				}
			});

			if (property.getType() == Color.class
					|| property.getType() == Rectangle.class) {
				Button button = new Button(parent, SWT.PUSH);
				button.setText("...");
				button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						handleLinkEvent(property, valueText, parent.getShell());
					}
				});
			} else {
				new Label(parent, SWT.NONE);
			}
		}
		data.put(property.getName(), property.getValue());
	}

	private void createLabelForField(final Composite parent,
			final PropertyData property) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(property.getLabel());
		label.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_BLUE));
		label.setToolTipText("Property \"" + property.getName() + "\"");
		if (property.getExtraTooltipInfo() != null) {
			label.setToolTipText(label.getToolTipText() + EOL
					+ property.getExtraTooltipInfo());
		}
	}

	private void handleLinkEvent(PropertyData property, Text valueText,
			Shell shell) {
		if (property == null || valueText == null || valueText.isDisposed()) {
			return;
		}
		if (property.getType() == Color.class) {
			ColorDialog colorDialog = new ColorDialog(shell);
			RGB selectRGB = colorDialog.open();
			if (selectRGB != null) {
				valueText.setText((this.hexColorConvert(Integer
						.toHexString(selectRGB.blue))
						+ this.hexColorConvert(Integer
								.toHexString(selectRGB.green)) + this
						.hexColorConvert(Integer.toHexString(selectRGB.red)))
						.toUpperCase());
			}
		} else if (property.getType() == Rectangle.class) {
			this.createRectDialog(shell, valueText).open();
		}
	}

	/**
	 * exchange the color pattern of hex numeric
	 * 
	 * @param number
	 * @return
	 */
	public String hexColorConvert(String color) {
		if (color.length() == 1) {
			return "0" + color;
		}
		return color;
	}

	/**
	 * create Rect Set dialog
	 * 
	 * @param parent
	 * @param valueText
	 * @return
	 */
	public Dialog createRectDialog(final Composite parent, final Text valueText) {
		return new Dialog(parent.getShell()) {
			Text xPointText, yPointText, widthText, heightText;

			@Override
			protected Button createButton(Composite parent, int id,
					String label, boolean defaultButton) {
				return super.createButton(parent, id, label, defaultButton);
			}

			@Override
			protected Control createDialogArea(final Composite parent) {
				Composite composite = (Composite) super
						.createDialogArea(parent);
				composite.getShell().setText("Set Rect");
				Group group = new Group(composite, SWT.NONE);
				group.setText("Rect");
				GridLayout gridLayout = new GridLayout();
				gridLayout.numColumns = 4;
				group.setLayout(gridLayout);

				Label xPointLabel = new Label(group, SWT.NONE);
				xPointLabel.setText("X:");
				xPointText = new Text(group, SWT.BORDER);
				VerifyListener verifyListener = createVerifyListener(parent
						.getShell());
				xPointText.addVerifyListener(verifyListener);
				Label yPointLabel = new Label(group, SWT.NONE);
				yPointLabel.setText("Y:");
				yPointText = new Text(group, SWT.BORDER);
				yPointText.addVerifyListener(verifyListener);
				Label widthLabel = new Label(group, SWT.NONE);
				widthLabel.setText("Width:");
				widthText = new Text(group, SWT.BORDER);
				widthText.addVerifyListener(verifyListener);
				Label heighttLabel = new Label(group, SWT.NONE);
				heighttLabel.setText("Height:");
				heightText = new Text(group, SWT.BORDER);
				heightText.addVerifyListener(verifyListener);

				return composite;
			}

			@Override
			protected void buttonPressed(int buttonId) {
				if (IDialogConstants.OK_ID == buttonId) {
					String xPoint = xPointText.getText();
					String yPoint = yPointText.getText();
					String width = widthText.getText();
					String height = heightText.getText();
					if (xPoint.length() == 0 || yPoint.length() == 0
							|| width.length() == 0 || height.length() == 0) {
						MessageDialog.openWarning(parent.getShell(),
								"Input value empty",
								"Value shoud not be empty!");
					} else {
						valueText.setText(xPoint + "," + yPoint + "," + width
								+ "," + height);
						okPressed();
					}
				} else if (IDialogConstants.CANCEL_ID == buttonId) {
					cancelPressed();
				}
			}
		};
	}

	/**
	 * create verify Listener
	 * 
	 * @param shell
	 * @return
	 */
	public VerifyListener createVerifyListener(final Shell shell) {
		return new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				char c = e.character;
				if ("0123456789".indexOf(c) == -1) {
					e.doit = false;
					MessageDialog.openWarning(shell, "Input value error",
							"Only numeric is allowed!");
					return;
				}
			}
		};
	}

	private void handleTextEvent(String property, Text valueText) {
		if (property == null || valueText == null || valueText.isDisposed()) {
			return;
		}
		String value = valueText.getText();
		if (value.equals("")) {
			value = null;
		}
		data.put(property, value);
	}

	protected void handleCheckBoxEvent(String property, boolean selection) {
		if (property == null) {
			return;
		}
		data.put(property, Boolean.toString(selection));
	}

//	private Group createProductGroup(Composite control) {
//		Group proGroup = new Group(control, SWT.NONE);
//		proGroup.setText("Product");
//
//		proGroup.setLayout(new GridLayout(2, false));
//
//		Label proNameLabel = new Label(proGroup, SWT.NONE);
//		proNameLabel.setText("Name:");
//
//		proNameText = new Text(proGroup, SWT.BORDER);
//		proNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//
//		proNameText.addListener(SWT.Modify, new Listener() {
//			public void handleEvent(Event event) {
//				handleTextEvent(PRODUCT_NAME, proNameText);
//			}
//		});
//		return proGroup;
//	}
	
	private Group createWebAppGroup(Composite control) {
		Group webAppGroup = new Group(control, SWT.NONE);
		webAppGroup.setText("Web application parameters:");

		webAppGroup.setLayout(new GridLayout(2, false));

		Label contextPathLabel = new Label(webAppGroup, SWT.NONE);
		contextPathLabel.setText("Context Path:");

		contextPathText = new Text(webAppGroup, SWT.BORDER);
		contextPathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		contextPathText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				handleTextEvent(CONTEXT_PATH, contextPathText);
			}
		});
		
		Label portLabel = new Label(webAppGroup, SWT.NONE);
		portLabel.setText("Port:");

		portText = new Text(webAppGroup, SWT.BORDER);
		portText.setText("8080");
		portText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		portText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				handleTextEvent(PORT, portText);
			}
		});
		
		return webAppGroup;
	}

	protected PropertyData[] getPropertyData() {
		if (PROPERTIES == null) {
			PROPERTIES = new PropertyData[] {
					new PropertyData(
							IProductConstants.PREFERENCE_CUSTOMIZATION,
							"Preference Customization:", "", String.class, true),
					new PropertyData(CLEAR_PERSISTED_STATE,
							"Enable development mode for application model",
							"true", Boolean.class, true,
							"Add option -clearPersistedState to the Product's Program Arguments") }; // plugin_customization.ini
		}
		return PROPERTIES;
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible && PROPERTIES == null) {
			String contextPath = pluginData.getId().replace('.', '-');
			contextPathText.setText(contextPath);

			for (PropertyData property : getPropertyData()) {
				createPropertyItem(propertyGroup, property);
			}
			propertyGroup.getParent().layout();
		}
		super.setVisible(visible);
	}

	/**
	 * @return the data
	 */
	public Map<String, String> getData() {
		if (PROPERTIES == null) {
			for (PropertyData property : getPropertyData()) {
				data.put(property.getName(), property.getValue());
			}
		}
		
		data.put(PRODUCT_NAME, pluginData.getId());
		data.put(CONTEXT_PATH, contextPathText.getText());
		data.put(PORT, portText.getText());
		
		Map<String, String> map = new HashMap<String, String>();
		map.putAll(data);
		return map;
	}
}
