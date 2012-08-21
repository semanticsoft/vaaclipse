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
package fi.jasoft.dragdroplayouts.events;

import java.util.Map;

import com.vaadin.event.TransferableImpl;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.ui.Component;

/**
 * Abstract base class for layout based transferable's.
 */
@SuppressWarnings("serial")
public class LayoutBoundTransferable extends TransferableImpl {

    /**
     * Default constructor
     * 
     * @param sourceComponent
     *            The layout from where the drag started
     * @param rawVariables
     *            The details of the drag
     */
    public LayoutBoundTransferable(Component sourceComponent,
            Map<String, Object> rawVariables) {
        super(sourceComponent, rawVariables);
    }

    /**
     * Get the component being dragged
     * 
     * @return The component
     */
    public Component getComponent() {
        return (Component) getData("component");
    }

    /**
     * @return the mouse down event that started the drag and drop operation
     */
    public MouseEventDetails getMouseDownEvent() {
        return MouseEventDetails.deSerialize((String) getData("mouseDown"));
    }
}
