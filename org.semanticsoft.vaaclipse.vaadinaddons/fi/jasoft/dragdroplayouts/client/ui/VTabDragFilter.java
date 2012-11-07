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
package fi.jasoft.dragdroplayouts.client.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ValueMap;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer;

/**
 * A filter which uses the tabs index to recognice to draggability
 * 
 * @author John Ahlroos
 * @since 0.6.3
 */
public class VTabDragFilter extends VDragFilter {

	private final VDDTabContainer tabsheet;
	
	private final Map<Integer, Boolean> dragmap = new HashMap<Integer, Boolean>();
	
	/**
	 * Default constructor
	 * 
	 * @param root
	 * 		The Tabsheet to check the tabs of
	 */
	public VTabDragFilter(VDDTabContainer root) {
		tabsheet = root;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.jasoft.dragdroplayouts.client.ui.VDragFilter#update(com.vaadin.terminal.gwt.client.UIDL, com.vaadin.terminal.gwt.client.ApplicationConnection)
	 */
	@Override
	public void update(UIDL uidl, ApplicationConnection client) {
		dragmap.clear();
    	if(uidl.hasAttribute(DRAGMAP_ATTRIBUTE)){	
    		ValueMap vmap = uidl.getMapAttribute(DRAGMAP_ATTRIBUTE);
    		Set<String> indexes = vmap.getKeySet();
    		for(String index : indexes){
    			boolean draggable = vmap.getBoolean(index);
    			dragmap.put(Integer.valueOf(index), draggable);
    		}
    	}
    	
    	//hack - now we initialize all visible tabs with draggable=true, because there are strange bug - dragmap is empty
    	if (tabsheet instanceof VDDTabSheet)
    	{
    		VDDTabSheet ts = (VDDTabSheet) tabsheet;
    		for (int i = 0; i < ts.getWidgetCount(); i++)
    		{
    			Widget w = ts.getWidget(i);
    			if (w.isVisible())
    			{
    				dragmap.put(Integer.valueOf(i), true);
    			}
    		}
    	}
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.jasoft.dragdroplayouts.client.ui.VDragFilter#isDraggable(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean isDraggable(Widget widget) {
		int index = tabsheet.getTabContentPosition(widget);
		if(dragmap.containsKey(index)){
    		return dragmap.get(index);
    	}
    	return false;
	}
}
