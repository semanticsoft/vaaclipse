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

/**
 * LayoutDragMode specifies how dragging is visualized.
 * 
 * @since 6.5
 */
public enum LayoutDragMode {
	
    /**
     * Disables dragging components from a layout. This is the default
     * behaviour.
     */
    NONE,

    /**
     * Makes a copy of the component which is shown when dragging.
     */
    CLONE;
}
