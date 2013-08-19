package org.semanticsoft.vaaclipse.p2.install.ui.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.semanticsoft.vaaclipse.p2.install.ui.IBasicUI;
import org.semanticsoft.vaaclipse.p2.install.ui.IContainerP2Views;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

public class ContainerP2Views implements IContainerP2Views {

	List<IBasicUI> listUI = new ArrayList<>();
	Button buttonNext = new Button("Next");
	Button buttonPrevies = new Button("Previews");
	VerticalLayout mainLayout = new VerticalLayout();
	IBasicUI selectedBasicUI = null;
	int maxViews = 2;

	public ContainerP2Views(IBasicUI... listUI) {
		super();

		addViews(Arrays.asList(listUI));
		

	}

	@Override
	public Object getUIComponent() {
		// TODO Auto-generated method stub
		return mainLayout;
	}

	@Override
	public void initUI() {
		// TODO Auto-generated method stub

		mainLayout.addComponent((Component) listUI.get(0).getUIComponent());

		selectedBasicUI = listUI.get(0);
		CssLayout cssLayout = new CssLayout();

		cssLayout.addComponent(buttonNext);
		cssLayout.addComponent(buttonPrevies);

		Button.ClickListener listenerButton = new Button.ClickListener() {

			int index = 0;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				if (event.getButton() == buttonNext) {

					if (index < maxViews&&listUI.get(index).validate()) {

						index++;
						mainLayout.removeAllComponents();
						mainLayout.addComponent((Component) listUI.get(index)
								.getUIComponent());
					} else {

					}
				} else if (event.getButton() == buttonPrevies) {

					if (index > -1) {

						index--;
						mainLayout.removeAllComponents();
						mainLayout.addComponent((Component) listUI.get(index)
								.getUIComponent());
					} else {

					}
				}
			}
		};
		buttonNext.addClickListener(listenerButton);
		buttonPrevies.addClickListener(listenerButton);
		mainLayout.addComponent(cssLayout);
	}

	@Override
	public String errorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addViews(List<IBasicUI> views) {
		// TODO Auto-generated method stub

		if (views == null) {
			throw new IllegalArgumentException("Views must not be null");

		}
		maxViews = views.size();
		listUI.addAll(views);
	}

}
