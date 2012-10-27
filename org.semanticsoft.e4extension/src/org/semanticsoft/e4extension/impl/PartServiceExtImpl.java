/**
 * 
 */
package org.semanticsoft.e4extension.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.semanticsoft.e4extension.service.EPartServiceExt;

import e4modelextension.EditorPartDescriptor;
import e4modelextension.VaaclipseApplication;

/**
 * @author rushan
 *
 */
public class PartServiceExtImpl implements EPartServiceExt
{
	@Inject
	private IEclipseContext eclipseContext;
	
	@Inject 
	private MApplication application;
	
	@Inject
	EPartService partService;
	
	@Inject
	EModelService modelService;
	
	Map<String, Pattern> patterns = new HashMap<String, Pattern>();
	

	@Override
	public MInputPart createInputPart(String id)
	{
		MPartDescriptor descriptor = findDescriptor(id);
		return createInputPart(descriptor);
	}
	
	private MInputPart createInputPart(MPartDescriptor descriptor) {
		if (descriptor == null) {
			return null;
		}

		MInputPart part = BasicFactoryImpl.eINSTANCE.createInputPart();
		part.setElementId(descriptor.getElementId());
		part.getMenus().addAll(EcoreUtil.copyAll(descriptor.getMenus()));
		if (descriptor.getToolbar() != null) {
			part.setToolbar((MToolBar) EcoreUtil.copy((EObject) descriptor.getToolbar()));
		}
		part.setContributorURI(descriptor.getContributorURI());
		part.setCloseable(descriptor.isCloseable());
		part.setContributionURI(descriptor.getContributionURI());
		part.setLabel(descriptor.getLabel());
		part.setIconURI(descriptor.getIconURI());
		part.setTooltip(descriptor.getTooltip());
		part.getHandlers().addAll(EcoreUtil.copyAll(descriptor.getHandlers()));
		part.getTags().addAll(descriptor.getTags());
		part.getBindingContexts().addAll(descriptor.getBindingContexts());
		part.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);
		return part;
	}

	@Override
	public MPartDescriptor findDescriptor(String id)
	{
		for (MPartDescriptor descriptor : application.getDescriptors()) {
			if (descriptor.getElementId().equals(id)) {
				return descriptor;
			}
		}
		return null;
	}
	
	@Override
	public EditorPartDescriptor findEditorPartDescriptor(String inputUri)
	{
		for (EditorPartDescriptor d : ((VaaclipseApplication)application).getEditorDescriptors())
		{
			if (d.getUriFilter() != null)
			{
				String filter = d.getUriFilter().trim();
				if (!filter.isEmpty())
				{
					if (!patterns.containsKey(filter))
					{
						patterns.put(filter, Pattern.compile(filter));
					}
					Pattern pattern = patterns.get(filter);
					Matcher matcher = pattern.matcher(inputUri);
					if (matcher.matches())
					{
						return d;
					}
				}
			}
		}
		return null;
	}

	@Override
	public MInputPart openUri(MElementContainer<?> area, String inputUri)
	{
		EditorPartDescriptor editorPartDescriptor = findEditorPartDescriptor(inputUri);
		MInputPart part = ensurePartAdded(getWindow(), area, editorPartDescriptor, inputUri);
		
		partService.showPart(part, PartState.ACTIVATE);
		return part;
	}
	
	private MInputPart ensurePartAdded(MWindow window, MElementContainer<?> area, EditorPartDescriptor editorPartDescriptor, String inputUri)
	{
		MPerspectiveStack stack = null;
		for (MUIElement e : window.getChildren()) {
			if (e instanceof MPerspectiveStack)
			{
				stack = (MPerspectiveStack) e;
				break;
			}
		}
		
		MPerspective persp = stack != null ? stack.getSelectedElement() : null;
		
		MElementContainer<?> container = persp != null ? persp : window;
		
		List<MPart> parts = modelService.findElements(container, null, MPart.class, null,
				EModelService.OUTSIDE_PERSPECTIVE | EModelService.IN_ACTIVE_PERSPECTIVE
						| EModelService.IN_SHARED_AREA);
		
		MInputPart part = null;
		for (MPart p : parts)
		{
			if (p instanceof MInputPart)
			{
				String _inputUri = ((MInputPart) p).getInputURI();
				if (inputUri.equals(_inputUri))
				{
					part = (MInputPart) p;
					break;
				}
			}
		}
		
		if (part == null)
		{//else we create new part and add it with specified logic
			
			//create part
			part = createInputPart(editorPartDescriptor);
			part.setInputURI(inputUri);
			
			//create context for add logic and set context info
			IEclipseContext localContext = application.getContext().createChild();
			localContext.set(MPart.class, part);
			localContext.set(MElementContainer.class, area);
			localContext.set(MInputPart.class, part);
			localContext.set(MWindow.class, window);
			
			//obtain adding logic
			IContributionFactory contributionFactory = (IContributionFactory) localContext.get(IContributionFactory.class
					.getName());
			Object addLogic = contributionFactory.create(editorPartDescriptor.getPartAddingLogicUri(), localContext);
			
			//execute adding logic
			ContextInjectionFactory.invoke(addLogic, Execute.class, localContext);
			
			//now part is added to window, so we all work is done
		}
		
		return part;
	}
	
	private MWindow getWindow() {
		if (application.getSelectedElement() != null)
			return application.getSelectedElement();
		List<MWindow> windows = application.getChildren();
		if (windows.size() != 0)
			return windows.get(0);
		return null;
	}

}
