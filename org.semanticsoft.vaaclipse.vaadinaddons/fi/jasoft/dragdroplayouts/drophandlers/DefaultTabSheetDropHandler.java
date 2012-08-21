/*
 * Copyright 2011 John Ahlroos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.jasoft.dragdroplayouts.drophandlers;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TabSheet.Tab;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.DDTabSheet;
import fi.jasoft.dragdroplayouts.DDTabSheet.TabSheetTargetDetails;
import fi.jasoft.dragdroplayouts.events.HorizontalLocationIs;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;

@SuppressWarnings("serial")
public class DefaultTabSheetDropHandler extends
		AbstractDefaultLayoutDropHandler {

	public AcceptCriterion getAcceptCriterion() {
		// Only allow drops between tabs
		return new Not(HorizontalLocationIs.CENTER);
	}

	@Override
	protected void handleComponentReordering(DragAndDropEvent event) {
		LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
				.getTransferable();
		TabSheetTargetDetails details = (TabSheetTargetDetails) event
				.getTargetDetails();
		DDTabSheet tabSheet = (DDTabSheet) details.getTarget();
		Component c = transferable.getComponent();
		Tab tab = tabSheet.getTab(c);
		HorizontalDropLocation location = details.getDropLocation();
		int idx = details.getOverIndex();

		if (location == HorizontalDropLocation.LEFT) {
			// Left of previous tab
			int originalIndex = tabSheet.getTabPosition(tab);
			if (originalIndex > idx) {
				tabSheet.setTabPosition(tab, idx);
			} else if (idx - 1 >= 0) {
				tabSheet.setTabPosition(tab, idx - 1);
			}

		} else if (location == HorizontalDropLocation.RIGHT) {
			// Right of previous tab
			int originalIndex = tabSheet.getTabPosition(tab);
			if (originalIndex > idx) {
				tabSheet.setTabPosition(tab, idx + 1);
			} else {
				tabSheet.setTabPosition(tab, idx);
			}
		}

	}

	@Override
	protected void handleDropFromLayout(DragAndDropEvent event) {
		LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
				.getTransferable();
		TabSheetTargetDetails details = (TabSheetTargetDetails) event
				.getTargetDetails();
		DDTabSheet tabSheet = (DDTabSheet) details.getTarget();
		Component c = transferable.getComponent();
		HorizontalDropLocation location = details.getDropLocation();
		int idx = details.getOverIndex();
		ComponentContainer source = (ComponentContainer) transferable
				.getSourceComponent();

		source.removeComponent(c);
		if (location == HorizontalDropLocation.LEFT) {
			tabSheet.addTab(c, idx);
		} else if (location == HorizontalDropLocation.RIGHT) {
			tabSheet.addTab(c, idx + 1);
		}

	}

}
