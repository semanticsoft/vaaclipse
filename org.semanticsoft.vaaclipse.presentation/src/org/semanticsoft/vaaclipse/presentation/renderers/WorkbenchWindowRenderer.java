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

package org.semanticsoft.vaaclipse.presentation.renderers;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindowElement;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler.Save;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.commons.general.Condition;
import org.semanticsoft.vaaclipse.presentation.engine.PresentationEngine;
import org.semanticsoft.vaaclipse.presentation.engine.VaadinPresentationEngine;
import org.semanticsoft.vaaclipse.publicapi.editor.SavePromptSetup;
import org.semanticsoft.vaaclipse.publicapi.model.Tags;
import org.semanticsoft.vaaclipse.widgets.WorkbenchWindow;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;

import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.ResizeEvent;

@SuppressWarnings("restriction")
public class WorkbenchWindowRenderer extends VaadinRenderer
{

	@Inject
	private IEclipseContext eclipseContext;

	@Inject
	MApplication app;

	@Inject
	Application vaadinapp;

	@Inject
	EventBroker eventBroker;

	@Inject
	EPartService partService;

	// Save support
	private MPart saveCandidate;
	private Save s;

	EventHandler trimHandler = new EventHandler() {

		@Override
		public void handleEvent(Event event)
		{
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MTrimmedWindow))
				return;

			MTrimmedWindow window = (MTrimmedWindow) event.getProperty(UIEvents.EventTags.ELEMENT);
			WorkbenchWindow vWindow = (WorkbenchWindow) window.getWidget();
			PresentationEngine engine = (PresentationEngine) context.get(IPresentationEngine.class.getName());
			Object attType = event.getProperty(UIEvents.EventTags.TYPE);

			Component c;
			MTrimBar trimBar;
			if (attType.equals("ADD"))
			{
				trimBar = (MTrimBar) event.getProperty(UIEvents.EventTags.NEW_VALUE);
				c = trimBar.getWidget() == null ? (Component) engine.createGui(trimBar) : (Component) trimBar
						.getWidget();
			}
			else
			{
				trimBar = (MTrimBar) event.getProperty(UIEvents.EventTags.OLD_VALUE);
				c = null;
			}

			switch (trimBar.getSide())
			{
			case BOTTOM:
				vWindow.setBottomBar(c);
				break;
			case LEFT:
				vWindow.setLeftBar(c);
				break;
			case RIGHT:
				vWindow.setRightBar(c);
				break;
			case TOP:
				vWindow.setTopBar(c);
				break;
			}
		}
	};

	@PostConstruct
	public void init()
	{
		eventBroker.subscribe(UIEvents.TrimmedWindow.TOPIC_TRIMBARS, trimHandler);
	}

	@PreDestroy
	public void deinit()
	{
		eventBroker.unsubscribe(trimHandler);
	}

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		if (element instanceof MWindow)
		{
			final MWindow mWindow = (MWindow) element;
			Window currentMainWindow = vaadinapp.getMainWindow();
			if (element.getTags().contains(Tags.MAIN_WINDOW))
			{

				WorkbenchWindow window = new WorkbenchWindow();
				window.setPositionX(mWindow.getX());
				window.setPositionY(mWindow.getY());
				window.setWidth(mWindow.getWidth());
				window.setHeight(mWindow.getHeight());
				window.setCaption(mWindow.getLocalizedLabel());
				element.setWidget(window);
				((MWindow) element).getContext().set(Window.class, window);
				window.setSizeFull();

				vaadinapp.setMainWindow(window);
				if (currentMainWindow != null)
				{
					currentMainWindow.open(new ExternalResource(vaadinapp.getURL()));
				}

				app.setSelectedElement(mWindow);
				mWindow.getContext().activate();

				eclipseContext.set(Window.class, window);
			}
			// case child windows
			else
			{
				if (currentMainWindow != null)
				{
					WorkbenchWindow window = new WorkbenchWindow();
					window.setImmediate(true);
					window.setPositionX(mWindow.getX());
					window.setPositionY(mWindow.getY());
					window.setWidth(mWindow.getWidth());
					window.setHeight(mWindow.getHeight());
					window.setCaption(mWindow.getLocalizedLabel());
					element.setWidget(window);
					eclipseContext.set(Window.class, window);
					currentMainWindow.addWindow(window);
				}
				else
				{
					throw new IllegalStateException("Can not add child window because application has not main window");
				}
			}

			IEclipseContext localContext = getContext(element);
			localContext.set(ISaveHandler.class, new ISaveHandler() {
				public Save promptToSave(MPart dirtyPart)
				{
					// Object[] elements =
					// promptForSave(Collections.singleton(dirtyPart));
					// if (elements == null) {
					// return Save.CANCEL;
					// }
					// return elements.length == 0 ? Save.NO : Save.YES;

					if (saveCandidate == null)
					{
						saveCandidate = dirtyPart;
						
						PartRenderer partRenderer = (PartRenderer) saveCandidate.getRenderer();
						SavePromptSetup setup = partRenderer.getSavePromptSetup(saveCandidate);
						String caption = setup.getCaption() != null ? setup.getCaption() : "Save";
						String msg = setup.getMessage() != null ? setup.getMessage() :
							String.format("%s has been modified. Save changes?", saveCandidate instanceof MInputPart ? ((MInputPart)saveCandidate).getInputURI() : "Data");
						
						OptionDialog.show((Window) mWindow.getWidget(), caption,
								msg, 
								new String[] {"Yes", "No", "Cancel"},
								new OptionDialog.OptionListener() {

									@Override
									public void optionSelected(int optionId)
									{
										switch (optionId)
										{
										case 0:
											s = Save.YES;
											break;
										case 1:
											s = Save.NO;
											break;
										case 2:
											s = Save.CANCEL;
											break;

										default:
											s = null;
										}
										
										if (partService.savePart(saveCandidate, true))
										{
											partService.hidePart(saveCandidate);
										}
										saveCandidate = null;
									}
								}, 400, 80, Component.UNITS_PIXELS);

						return Save.CANCEL;
					}
					else
					{
						return s;
					}

				}

				public Save[] promptToSave(Collection<MPart> dirtyParts)
				{
					// List<MPart> parts = new ArrayList<MPart>(dirtyParts);
					//
					// Save[] response = new Save[dirtyParts.size()];
					// Object[] elements = promptForSave(parts);
					// if (elements == null) {
					// Arrays.fill(response, Save.CANCEL);
					// } else {
					// Arrays.fill(response, Save.NO);
					// for (int i = 0; i < elements.length; i++) {
					// response[parts.indexOf(elements[i])] = Save.YES;
					// }
					// }
					// return response;

					throw new RuntimeException("Multiple saving is not implemented yet");
				}
			});
		}
	}

	@Override
	public void hookControllerLogic(final MUIElement element)
	{
		if (element instanceof MWindow)
		{
			final MWindow mWindow = (MWindow) element;

			if (!element.getTags().contains(Tags.MAIN_WINDOW))
			{// only for child windows (main window not need that)
				final Window window = (Window) mWindow.getWidget();

				window.addListener(new Window.ResizeListener() {

					@Override
					public void windowResized(ResizeEvent e)
					{
						mWindow.setWidth((int) window.getWidth());
						mWindow.setHeight((int) window.getHeight());

						// System.out.println(String.format("%s, %s, %s, %s",
						// mWindow.getX(), mWindow.getY(), mWindow.getWidth(),
						// mWindow.getHeight()));
					}
				});

				// TODO: there are no window move listener in vaadin, implement
				// it later
			}
		}
	}

	@Override
	public void processContents(MElementContainer<MUIElement> element)
	{
		if ((MUIElement) element instanceof MWindow)
		{
			MWindow window = (MWindow) ((MUIElement) element);
			WorkbenchWindow vWindow = (WorkbenchWindow) element.getWidget();
			PresentationEngine engine = (PresentationEngine) context.get(IPresentationEngine.class.getName());

			for (MUIElement e : element.getChildren())
			{
				if (e.getWidget() != null)
				{
					if (e instanceof MPerspectiveStack)
					{
						PerspectiveStackRenderer perspectiveStackRenderer = (PerspectiveStackRenderer) e.getRenderer();
						if (perspectiveStackRenderer.getPerspectiveStackForSwitcher() == e)
						{
							final HorizontalLayout perspectiveStackPanel = perspectiveStackRenderer.getPerspectiveSwitcher();
							vWindow.setPerspectiveStackPanel(perspectiveStackPanel);	
						}
					}

					vWindow.getClientArea().addComponent((com.vaadin.ui.Component) e.getWidget());
				}
			}

			if (window.getMainMenu() != null)
			{
				engine.createGui(window.getMainMenu());
				MenuBar menu = (MenuBar) window.getMainMenu().getWidget();
				vWindow.setMenuBar(menu);
			}

			// -------------------------------------------------------------------
			if (window instanceof MTrimmedWindow)
			{
				MTrimmedWindow tWindow = (MTrimmedWindow) window;
				for (MTrimBar trim : tWindow.getTrimBars())
				{
					Component c = (com.vaadin.ui.Component) engine.createGui(trim);
					switch (trim.getSide())
					{
					case BOTTOM:
						vWindow.setBottomBar(c);
						break;
					case LEFT:
						vWindow.setLeftBar(c);
						break;
					case RIGHT:
						vWindow.setRightBar(c);
						break;
					case TOP:
						vWindow.setTopBar(c);
						break;
					}
				}
			}
		}
	}

	// @Override
	// public void refreshPlatformElement(MElementContainer<?> element) {
	// if ((MUIElement) element instanceof MTrimmedWindow) {
	// MTrimmedWindow window = (MTrimmedWindow) ((MUIElement) element);
	// WorkbenchWindow vWindow = (WorkbenchWindow) element.getWidget();
	//
	// for (MTrimBar trim : window.getTrimBars()) {
	// Component c = (Component) trim.getWidget();
	// switch (trim.getSide()) {
	// case BOTTOM:
	// vWindow.setBottomBar(c);
	// break;
	// case LEFT:
	// vWindow.setLeftBar(c);
	// break;
	// case RIGHT:
	// vWindow.setRightBar(c);
	// break;
	// case TOP:
	// vWindow.setTopBar(c);
	// break;
	// }
	// }
	//
	// }
	// }

	// @Override
	// public void addChild(MUIElement child, MElementContainer<MUIElement>
	// element)
	// {
	// if (!(child instanceof MWindowElement && (MElementContainer<?>)element
	// instanceof MWindow))
	// return;
	//
	// super.addChild(child, element);
	//
	// }

	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MWindowElement))
			return;

		WorkbenchWindow vWindow = (WorkbenchWindow) element.getWidget();

		if (child instanceof MPerspectiveStack)
		{
			PerspectiveStackRenderer perspectiveStackRenderer = (PerspectiveStackRenderer) child.getRenderer();
			if (perspectiveStackRenderer.getPerspectiveStackForSwitcher() == child)
			{
				final HorizontalLayout perspectiveStackPanel = perspectiveStackRenderer.getPerspectiveSwitcher();
				vWindow.setPerspectiveStackPanel(perspectiveStackPanel);	
			}
		}
		else
		{
			int index = indexOf(child, element, new Condition<MUIElement>() {

				@Override
				public boolean check(MUIElement child)
				{
					return !(child instanceof MPerspectiveStack);
				}
			});

			vWindow.getClientArea().addComponent((com.vaadin.ui.Component) child.getWidget(), index);
		}
	}

	@Override
	public void removeChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MWindowElement))
			return;

		WorkbenchWindow vWindow = (WorkbenchWindow) element.getWidget();

		if (child instanceof MPerspectiveStack)
		{
			vWindow.setPerspectiveStackPanel(null);
		}
		else
		{
			vWindow.getClientArea().removeComponent((com.vaadin.ui.Component) child.getWidget());
		}
	}

	@Override
	public void setVisible(MUIElement changedElement, boolean visible)
	{
		if (changedElement instanceof MWindow
				&& !(changedElement.getTags().contains(Tags.MAIN_WINDOW)))
		{
			super.setVisible(changedElement, visible);
		}
	}

	private Object[] promptForSave(Collection<MPart> saveableParts)
	{
		// SaveablePartPromptDialog dialog = new SaveablePartPromptDialog(
		// parentShell, saveableParts);
		// if (dialog.open() == Window.CANCEL) {
		// return null;
		// }
		//
		// return dialog.getCheckedElements();
		return null;
	}
}
