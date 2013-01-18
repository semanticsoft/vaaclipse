/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/
package org.semanticsoft.vaaclipse.presentation.widgets;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * @author rushan
 * 
 */
public class TopbarComponent extends CustomComponent {
	private GridLayout rootLayout;

	private Label edge_top_left = new Label();
	private Label edge_top_middle = new Label();
	private Label edge_top_right = new Label();

	private Label edge_center_left = new Label();
	private Label edge_center_right = new Label();

	private Label edge_bottom_left = new Label();
	private Label edge_bottom_middle = new Label();
	private Label edge_bottom_right = new Label();

	private Component content;

	public TopbarComponent() {
		addStyleName("trimbar");
		rootLayout = new GridLayout(3, 3);
		rootLayout.setSizeFull();

		setSizeUndefined();
		setWidth("100%");
		setHeight("42px");

		// create the elements
		//
		rootLayout.addComponent(edge_top_left, 0, 0);
		rootLayout.addComponent(edge_top_middle, 1, 0);
		rootLayout.addComponent(edge_top_right, 2, 0);
		rootLayout.addComponent(edge_center_left, 0, 1);
		setContent(new CssLayout());
		rootLayout.addComponent(edge_center_right, 2, 1);
		rootLayout.addComponent(edge_bottom_left, 0, 2);
		rootLayout.addComponent(edge_bottom_middle, 1, 2);
		rootLayout.addComponent(edge_bottom_right, 2, 2);

		// style the elements
		//
		edge_top_left.addStyleName("edge_top_left");
		// edge_top_left.setWidth("8px");
		// edge_top_left.setHeight("8px");

		edge_top_middle.addStyleName("edge_top_middle");
		// edge_top_middle.setWidth("100%");
		// edge_top_middle.setHeight("8px");

		edge_top_right.addStyleName("edge_top_right");
		// edge_top_right.setWidth("6px");
		// edge_top_right.setHeight("8px");

		edge_center_left.addStyleName("edge_center_left");
		// edge_center_left.setHeight("100%");
		// edge_center_left.setWidth("8px");

		edge_center_right.addStyleName("edge_center_right");
		// edge_center_right.setHeight("100%");
		// edge_center_right.setWidth("8px");
		edge_bottom_left.addStyleName("edge_bottom_left");
		edge_bottom_middle.addStyleName("edge_bottom_middle");
		edge_bottom_right.addStyleName("edge_bottom_right");

		// set the width of at least one side element for proper layouting
		edge_bottom_left.setWidth("8px");
		edge_bottom_right.setWidth("6px");

		// expand the center column to its maximum
		// left and right column will gain their set size
		rootLayout.setColumnExpandRatio(1, 1.0f);

		setCompositionRoot(rootLayout);

	}

	public Component getContent() {
		return content;
	}

	/**
	 * Sets the content of the top bar.
	 * 
	 * @param content
	 */
	public void setContent(Component content) {
		if (this.content != null) {
			rootLayout.removeComponent(this.content);
		}
		rootLayout.addComponent(content, 1, 1);

		this.content = content;
//		 content.setSizeFull();
		content.addStyleName("content");
	}
}
