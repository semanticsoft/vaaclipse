package org.vaadin.peter.contextmenu;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.vaadin.peter.contextmenu.client.ui.VContextMenu;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Component;

/**
 * ContextMenu is a popup menu that can be placed to an arbitrary location
 * inside Vaadin application's bounds.
 * 
 * Items can be added to ContextMenu by calling ContextMenu.addItem(String).
 * Returned value represents ContextMenuItem that is attached to added item. If
 * ContextMenu.ClickListener is attached to component,
 * ContextMenu.ClickListener.getClickedItem() can be used to retrieve the
 * clicked item.
 * 
 * It is also possible to set ContextMenuItem disabled or invisible using
 * ContextMenuItem.setEnabled(boolean) and ContextMenuItem.setVisible(boolean)
 * methods.
 * 
 * @author Peter Lehto / Vaadin Ltd
 */
@ClientWidget(VContextMenu.class)
public class ContextMenu extends AbstractComponent {

	private static final long serialVersionUID = 1729861951779648682L;

	private boolean visible;

	private int x;
	private int y;

	private final List<ContextMenuItem> items;

	private String selectedComponentId = "";

	private final Map<Integer, ContextMenuItem> itemIds;

	private int itemIdIndex;

	private boolean enabled;

	/**
	 * Creates new empty context menu
	 */
	public ContextMenu() {
		this.items = new LinkedList<ContextMenuItem>();
		this.itemIds = new HashMap<Integer, ContextMenuItem>();
		setVisible(false);
		setEnabled(true);
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);

		this.renderInnerItems(this.items, target);

		if (visible) {
			target.addAttribute("show", true);
			target.addAttribute("component_locator", this.selectedComponentId);
			target.addAttribute("left", x);
			target.addAttribute("top", y);
		} else {
			target.addAttribute("show", false);
		}

		this.visible = false;
	}

	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		super.changeVariables(source, variables);

		if (variables.containsKey("itemId")) {
			int itemId = (Integer) variables.get("itemId");

			if (this.itemIds.containsKey(itemId)) {
				fireClick(itemIds.get(itemId));
			}
		}
	}

	/**
	 * Pops up context menu in given coordinates
	 * 
	 * @param left
	 *            - pixels from application's left border
	 * @param top
	 *            - pixels from application's top border
	 */
	public void show(int left, int top) {
		if (!this.isEnabled()) {
			return;
		}

		this.setVisible(true);

		this.visible = true;
		this.x = left;
		this.y = top;

		this.selectedComponentId = "";

		this.requestRepaint();
	}

	/**
	 * Pops up context menu next to given component
	 * 
	 * @param component
	 * @throws IllegalArgumentException
	 *             if given component doesn't have debug id specified
	 */
	public void show(Component component) {
		if (component.getDebugId() == null) {
			throw new IllegalArgumentException(
					"Given component must have debug id specified");
		}

		if (!this.isEnabled()) {
			return;
		}

		if (component.getDebugId() != null) {
			this.selectedComponentId = component.getDebugId();

			this.setVisible(true);
			this.visible = true;
			this.requestRepaint();
		}
	}

	/**
	 * Hides context menu
	 */
	public void hide() {
		visible = false;

		requestRepaint();
	}

	/**
	 * Adds new item to context menu using given name. Returns corresponding
	 * ContextMenuItem object that can be used for click event handling and
	 * settings added item visible or enabled.
	 * 
	 * @param name
	 * @return ContextMenuItem
	 */
	public ContextMenuItem addItem(String name) {
		ContextMenuItem item = new ContextMenuItem(name);
		this.items.add(item);

		requestRepaint();

		return item;
	}

	/**
	 * Removes given context menu item from the root level's context menu. If
	 * you want to remove menu item from a sub menu, call removeItem for that
	 * ContextMenuItem
	 * 
	 * @param item
	 *            to remove
	 */
	public void removeItem(ContextMenuItem item) {
		if (!items.contains(item)) {
			return;
		}

		this.itemIds.remove(item.getItemId());
		this.items.remove(item);

		requestRepaint();
	}

	/**
	 * Enables or disabled context menu component. If component is disabled it
	 * will not popup when show methods are called.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return true if this component is enabled, false otherwise
	 */
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		throw new UnsupportedOperationException(
				"Context menu does not support read only");
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	/**
	 * Renders the internal structure of context menu to given paint target
	 * 
	 * @param items
	 * @param target
	 * @throws PaintException
	 */
	private void renderInnerItems(List<ContextMenuItem> items,
			PaintTarget target) throws PaintException {
		target.startTag("items");

		for (ContextMenuItem item : items) {
			if (!item.isVisible()) {
				// let's not draw hidden items
				continue;
			}

			target.startTag("item");

			target.addAttribute("id", item.getItemId());
			target.addAttribute("name", item.getName());
			target.addAttribute("enabled", item.isEnabled());
			target.addAttribute("style", item.getStyleName());
			target.addAttribute("separator", item.hasSeparator());

			if (item.hasIcon()) {
				target.addAttribute("icon", item.getIcon());
			}

			// Draw all the sub items
			if (item.hasSubMenus()) {
				renderInnerItems(item.getChildren(), target);
			}

			target.endTag("item");
		}

		target.endTag("items");
	}

	/**
	 * ContextMenu's ClickListener that is called when an item from context menu
	 * is clicked.
	 * 
	 * @author Peter Lehto / IT Mill Oy Ltd
	 */
	public interface ClickListener extends Serializable {

		/**
		 * Called when ContextMenuItem is clicked from ContextMenu
		 * 
		 * @param event
		 */
		public void contextItemClick(ClickEvent event);
	}

	/**
	 * Adds the context item click listener.
	 * 
	 * @param listener
	 *            the Listener to be added.
	 */
	public void addListener(ClickListener listener) {
		addListener(ClickEvent.class, listener, BUTTON_CLICK_METHOD);
	}

	/**
	 * Removes the context item click listener.
	 * 
	 * @param listener
	 *            the Listener to be removed.
	 */
	public void removeListener(ClickListener listener) {
		removeListener(ClickEvent.class, listener, BUTTON_CLICK_METHOD);
	}

	protected void fireClick(ContextMenuItem contextMenuItem) {
		fireEvent(new ContextMenu.ClickEvent(this, contextMenuItem));
	}

	private void setItemIdFor(ContextMenuItem item) {
		int index = itemIdIndex++;
		item.setItemId(index);
		this.itemIds.put(index, item);
	}

	private static final Method BUTTON_CLICK_METHOD;

	static {
		try {
			BUTTON_CLICK_METHOD = ClickListener.class.getDeclaredMethod(
					"contextItemClick", new Class[] { ClickEvent.class });
		} catch (final java.lang.NoSuchMethodException e) {
			throw new java.lang.RuntimeException(
					"Internal error finding methods in ContextMenu");
		}
	}

	/**
	 * ClickEvent is fired when context item is clicked from ContextMenu
	 * 
	 * @author Peter Lehto / IT Mill Oy Ltd
	 */
	public class ClickEvent extends Component.Event {

		private static final long serialVersionUID = -7705638357488426038L;

		private final ContextMenuItem clickedItem;

		/**
		 * New instance of context item click event.
		 * 
		 * @param source
		 *            the Source of the event.
		 */
		public ClickEvent(Component source, ContextMenuItem clickedItem) {
			super(source);

			this.clickedItem = clickedItem;
		}

		/**
		 * Gets the ContextMenu where the event occurred.
		 * 
		 * @return the Source of the event.
		 */
		public ContextMenu getContextMenu() {
			return (ContextMenu) getSource();
		}

		/**
		 * @return Item that was clicked
		 */
		public ContextMenuItem getClickedItem() {
			return clickedItem;
		}
	}

	/**
	 * ContextMenuItem is POJO that can be used to access items in ContextMenu.
	 * ContextMenuItem has methods for hiding and disabling
	 * 
	 * @author Peter Lehto / IT Mill Oy Ltd
	 */
	public class ContextMenuItem implements Serializable {

		private static final long serialVersionUID = 3828334687114823216L;

		private Integer itemId;
		private final String name;
		private boolean enabled;
		private boolean visible;
		private Resource icon;
		private final List<String> styleNames;

		private final List<ContextMenuItem> children;

		private boolean separator;

		private ContextMenuItem(String name) {
			if (name == null) {
				throw new IllegalArgumentException(
						"Menu item name cannot be null");
			}

			this.styleNames = new LinkedList<String>();
			this.children = new LinkedList<ContextMenuItem>();

			this.name = name;
			this.visible = true;
			this.enabled = true;

			ContextMenu.this.setItemIdFor(this);
		}

		/**
		 * Adds given css style name to this context menu item
		 * 
		 * @param styleName
		 */
		public void addStyleName(String styleName) {
			if (styleName == null || styleName.equals("")) {
				return;
			}

			this.styleNames.add(styleName);
		}

		/**
		 * Sets given css style name to this context menu item
		 * 
		 * @param styleName
		 */
		public void setStyleName(String styleName) {
			this.styleNames.clear();
			this.addStyleName(styleName);
		}

		/**
		 * Removes given style name from this context menu item
		 * 
		 * @param styleName
		 */
		public void removeStyleName(String styleName) {
			if (styleName == null || styleName.equals("")) {
				return;
			}

			this.styleNames.remove(styleName);
		}

		/**
		 * @return Name of this context menu item
		 */
		public String getName() {
			return name;
		}

		/**
		 * Enables or disables this menu item
		 * 
		 * @param enabled
		 */
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
			requestRepaint();
		}

		/**
		 * @return true if menu item is enabled, false otherwise
		 */
		public boolean isEnabled() {
			return enabled;
		}

		/**
		 * Sets visible or hides this menu item
		 * 
		 * @param visible
		 */
		public void setVisible(boolean visible) {
			this.visible = visible;
			requestRepaint();
		}

		/**
		 * @return true if menu item is visible, false otherwise
		 */
		public boolean isVisible() {
			return visible;
		}

		/**
		 * Sets icon for this context menu item. Default and recommended icon
		 * size is 16x16 pixels.
		 * 
		 * @param icon
		 */
		public void setIcon(Resource icon) {
			this.icon = icon;
		}

		/**
		 * @return current icon for this context menu item
		 */
		public Resource getIcon() {
			return this.icon;
		}

		/**
		 * Adds new item to context menu as this menu's sub menu. Returns
		 * corresponding ContextMenuItem object that can be used for click event
		 * handling and settings added item visible or enabled.
		 * 
		 * @param name
		 * @return ContextMenuItem
		 */
		public ContextMenuItem addItem(String name) {
			ContextMenuItem item = new ContextMenuItem(name);
			this.children.add(item);
			requestRepaint();

			return item;
		}

		/**
		 * Removes given context menu item from this sub menu. If you want to
		 * remove menu item from a sub menu, call removeItem for that
		 * ContextMenuItem
		 * 
		 * @param item
		 *            to remove
		 */
		public void removeItem(ContextMenuItem child) {
			if (!children.contains(child)) {
				return;
			}

			children.remove(child);
			ContextMenu.this.itemIds.remove(child.getItemId());
		}

		/**
		 * Sets or disables separator line under this item
		 * 
		 * @param visible
		 */
		public void setSeparatorVisible(boolean visible) {
			this.separator = visible;
		}

		/**
		 * @return true if separator line is visible after this item, false
		 *         otherwise
		 */
		public boolean hasSeparator() {
			return this.separator;
		}

		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}

			if (other instanceof ContextMenuItem) {
				return this.itemId.equals(((ContextMenuItem) other).itemId);
			}

			return false;
		}

		@Override
		public int hashCode() {
			return this.itemId.hashCode();
		}

		/**
		 * @return list of submenus
		 */
		List<ContextMenuItem> getChildren() {
			return this.children;
		}

		/**
		 * @return true if this menu has sub menus
		 */
		boolean hasSubMenus() {
			return this.children.size() > 0;
		}

		/**
		 * Sets itemId to this context menu item
		 * 
		 * @param itemId
		 */
		void setItemId(int itemId) {
			this.itemId = itemId;
		}

		/**
		 * @return itemId
		 */
		int getItemId() {
			return this.itemId;
		}

		/**
		 * @return true if this item has an icon, false otherwise
		 */
		boolean hasIcon() {
			return this.icon != null;
		}

		/**
		 * @return combined style name
		 */
		String getStyleName() {
			String styleName = "";

			Iterator<String> iterator = styleNames.iterator();

			while (iterator.hasNext()) {
				styleName += iterator.next();

				if (iterator.hasNext()) {
					styleName += " ";
				}
			}

			return styleName;
		}
	}
}
