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
package org.semanticsoft.vaaclipse.presentation.dnd;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.commons.geom.Side;
import org.semanticsoft.vaaclipse.widgets.StackWidget;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;

/**
 * @author rushan
 *
 */
public class VaadinDropHandler implements DropHandler
{
	private MPartStack targetPartStack;
	private StackWidget targetTabSheet;
	
	@Inject
	EModelService modelService;
	
	@Inject
	public VaadinDropHandler(MPartStack targetPartStack) 
	{
		this.targetPartStack = targetPartStack;
		targetTabSheet = (StackWidget) targetPartStack.getWidget();
	}
	
	public AcceptCriterion getAcceptCriterion() {
        // Only allow dropping between tabs
        //return new Not(HorizontalLocationIs.CENTER);
		
		return AcceptAll.get();
    }

//	public void drop(DragAndDropEvent event) {
//		if (!(event.getTransferable() instanceof LayoutBoundTransferable) )
//			return;
//		
//		LayoutBoundTransferable transferable = (LayoutBoundTransferable) event.getTransferable();
//		TabSheetTargetDetails details = (TabSheetTargetDetails) event.getTargetDetails();
//		
//		if (transferable.getSourceComponent() instanceof StackWidget)
//		{
//			StackWidget sourceTabSheet = (StackWidget) transferable.getSourceComponent();
//			
//			MPartStack targetFolder = targetPartStack;
//			MPartStack sourceFolder = (MPartStack) sourceTabSheet.getData();
//			
//			// this component is dragged by user
//			AbstractComponent draggingComponent = (AbstractComponent) transferable.getComponent();
//			//This is model element that match to draggingComponent
//			MStackElement draggingElement = (MStackElement) draggingComponent.getData();
//			
//			//draggingElement must be in sourceFolder
//			if (!sourceFolder.getChildren().contains(draggingElement))
//				return;
//			
//			String dropType = (String) details.getData("dropType");
//			
//			if (dropType.equals("DropToTabsheetBar"))
//			{
//				int idx = details.getOverIndex();
//				HorizontalDropLocation location = details.getDropLocation();
//
//				if (draggingComponent == details.getOverComponent()) {
//					// Dropped on itself
//					return;
//				}
//				
//				if (sourceTabSheet == targetTabSheet) {//reordering tabs
//					
//					if (sourceFolder != targetFolder)
//						return;
//					int modelOldPos = sourceFolder.getChildren().indexOf(draggingElement);
//					
//					Tab tabInNewPos = targetTabSheet.getTab(idx);
//					
//					if (tabInNewPos == null)
//						return;
//					
//					MUIElement elementInInsertPos = (MUIElement) ((AbstractComponent)tabInNewPos.getComponent()).getData();
//					int modelNewPos = targetPartStack.getChildren().indexOf(elementInInsertPos);
//					
//					targetPartStack.getChildren().remove(draggingElement);
//					
//					if (location == HorizontalDropLocation.LEFT) {
//						if (modelOldPos > modelNewPos)
//							targetPartStack.getChildren().add(modelNewPos, draggingElement);
//						else
//							targetPartStack.getChildren().add(modelNewPos - 1, draggingElement);
//
//					} else if (location == HorizontalDropLocation.RIGHT) {
//
//						if (modelOldPos > modelNewPos)
//							targetPartStack.getChildren().add(modelNewPos + 1, draggingElement);
//						else
//							targetPartStack.getChildren().add(modelNewPos, draggingElement);
//					}
//				}
//				else
//				{// Adding new tab
//					sourceFolder.getChildren().remove(draggingElement);
//					
//					if (idx == -1)
//						targetFolder.getChildren().add(draggingElement);
//					else
//					{
//						Tab tabInNewPos = targetTabSheet.getTab(idx);
//						MUIElement elementInNewPos = (MUIElement) ((AbstractComponent)tabInNewPos.getComponent()).getData();
//						int modelNewPos = targetPartStack.getChildren().indexOf(elementInNewPos);
//						
//						int newPos = -1;
//						if (location == HorizontalDropLocation.LEFT) {
//							targetFolder.getChildren().add(modelNewPos, draggingElement);
//						} else if (location == HorizontalDropLocation.RIGHT) {
//							targetFolder.getChildren().add(modelNewPos + 1, draggingElement);
//						}	
//					}
//				}
//				targetFolder.setSelectedElement(draggingElement);
//			}
//			else if (dropType.equals("DropToTabsheetBody"))
//			{
//				//target widget coordinates:
//				int x0 = (Integer) details.getData("targetWidgetAbsoluteLeft");
//				int y0 = (Integer) details.getData("targetWidgetAbsoluteTop");
//				int dx = (Integer) details.getData("targetWidgetOffsetWidth");
//				int dy = (Integer) details.getData("targetWidgetOffsetHeight");
//				
//				int docPrcnt = 30;
//				double docX = dx * docPrcnt / 100;
//				double docY = dy * docPrcnt / 100;
//				
//				int mouseX = details.getMouseEvent().getClientX();
//				int mouseY = details.getMouseEvent().getClientY();
//				
//				Vector mousePos = Vector.valueOf(mouseX, mouseY);
//				
//				Side side = GeomUtils.findDockSide(x0, y0, dx, dy, docX, docY, mousePos);
//				if (side != null)
//				{
//					targetPartStack.getChildren().remove(draggingElement);
//					
//					if (side == Side.CENTER)
//					{//drop in center is equivalent tab adding at the end of target folder
//						targetFolder.getChildren().add(draggingElement);
//						targetFolder.setSelectedElement(draggingElement);
//					}
//					else
//					{//if drop to sides, we must different what kind is target widget - tabsheet or area
//						boolean onEdge = false;
//						
//						// Find the root of the targetFolder's sash structure
//						//This root - is area, perspective or window
//						MUIElement outerRelTo = targetFolder.getParent();
//						if (outerRelTo instanceof MPartSashContainer) {
//							while (outerRelTo != null && !(outerRelTo instanceof MArea || outerRelTo instanceof MPerspective || outerRelTo instanceof MWindow))
//								outerRelTo = outerRelTo.getParent();
//						}
//						
//						//Outer element can not be null, becouse mpartstack or his sash structure must be located in one of place: area, perspective or window
//						if (outerRelTo == null)
//							return;
//						
//						if (outerRelTo.getParent() == null && outerRelTo.getCurSharedRef() != null)
//						{
//							outerRelTo = outerRelTo.getCurSharedRef();
//						}
//						
//						MPartSashContainerElement relTo = null;
//
//						if (outerRelTo instanceof MArea || onEdge) 
//							relTo = (MPartSashContainerElement) outerRelTo;
//						else
//							relTo = targetFolder;
//						
//						//store original container data for using after model operations
//						String containerDataBeforeModelOperations = targetFolder.getContainerData();
//						
//						// wrap it in a stack if it's a part
//						MPartStack toInsert;
//						MStackElement stackElement = (MStackElement) draggingElement;
//						toInsert = BasicFactoryImpl.eINSTANCE.createPartStack();
//						toInsert.getChildren().add(stackElement);
//						toInsert.setSelectedElement(stackElement);
//
//						float pct = (float) (onEdge ? 0.15 : 0.3);
//						MUIElement relToParent = relTo.getParent();
//						modelService.insert(toInsert, relTo, convert(side), pct);
//
//						// Force the new sash to have the same weight as the original element
//						if (relTo.getParent() != relToParent && !onEdge)
//							relTo.getParent().setContainerData(containerDataBeforeModelOperations);
//					}
//				}
//			}
//		}
//	}
	
	@Override
	public void drop(DragAndDropEvent event) {
		// TODO Auto-generated method stub
		
	}
	
//	private int convert(Side side)
//	{
//		if (side == Side.LEFT)
//			return EModelService.LEFT_OF;
//		else if (side == Side.RIGHT)
//			return EModelService.RIGHT_OF;
//		else if (side == Side.BOTTOM)
//			return EModelService.BELOW;
//		else if (side == Side.TOP)
//			return EModelService.ABOVE;
//		else
//			throw new IllegalArgumentException();
//	}
}
