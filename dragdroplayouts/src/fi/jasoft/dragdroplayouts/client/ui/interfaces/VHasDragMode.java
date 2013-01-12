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
package fi.jasoft.dragdroplayouts.client.ui.interfaces;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

/**
 * Interface for layout which acts as a drag source
 */
public interface VHasDragMode {
	
	public static final String DRAGMODE_ATTRIBUTE = "dragMode";
	
    /**
     * Returns the drag mode
     * 
     * @return
     */
    LayoutDragMode getDragMode();
}

