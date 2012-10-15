package org.vaadin.peter.contextmenu.client.ui;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ui.VOverlay;

/**
 * Menu is a visible item of ContextMenu component. For every child menu new
 * instance of Menu class will be instantiated.
 * 
 * @author Peter Lehto / IT Mill Oy Ltd
 */
class VMenu extends VOverlay {

	public static final String CLASSNAME = "v-ctxmenu";

	private final VContextMenu app;
	private final VMenu parent;

	private final FlowPanel root;

	private Timer openTimer;
	private boolean immediateOpen;

	private final List<VMenuItem> menuItems;

	public VMenu(VContextMenu app, VMenu parentMenu) {
		super(true, false, true);

		this.app = app;
		this.parent = parentMenu;

		setStyleName("v-ctxmenu-container");

		root = new FlowPanel();
		root.setStyleName(CLASSNAME);

		menuItems = new LinkedList<VMenuItem>();

		add(root);

		if (app.hasDefinedWidth()) {
			this.setWidth(app.getDefinedWidth() + "px");
		}
	}

	@Override
	protected com.google.gwt.user.client.Element getStyleElement() {
		if (this.root == null) {
			return super.getStyleElement();
		}

		return root.getElement();
	}

	public VMenuItem addMenuItem(int itemId, String name, String styleName,
			boolean enabled, String iconUri, boolean separator) {
		VMenuItem item = new VMenuItem(this, itemId, name, styleName, enabled,
				iconUri, separator);
		this.menuItems.add(item);
		this.root.add(item);
		return item;
	}

	@Override
	public void hide() {
		super.hide();

		for (VMenuItem child : menuItems) {
			child.hideSubMenu();
		}
	}

	public List<VMenuItem> getMenuItems() {
		return this.menuItems;
	}

	/**
	 * @return number of visible items in this menu
	 */
	public int getNumberOfItems() {
		return this.menuItems.size();
	}

	public boolean hasParent() {
		return this.parent != null;
	}

	public VMenu getParentMenu() {
		return this.parent;
	}

	/**
	 * ContextMenuItem is a clickable label that represents one option in a menu
	 * component.
	 * 
	 * @author Peter Lehto / IT Mill Oy Ltd
	 */
	class VMenuItem extends Widget {
		private final VMenu owner;
		private final int itemId;
		private final boolean enabled;
		private VMenu subMenu;
		private final boolean separator;

		public VMenuItem(VMenu owner, int itemId, String name,
				String styleName, boolean enabled, String iconUri,
				boolean separator) {
			this.owner = owner;
			this.itemId = itemId;
			this.enabled = enabled;
			this.separator = separator;

			sinkEvents(Event.ONCLICK);
			sinkEvents(Event.MOUSEEVENTS);

			Element menuItem = DOM.createDiv();

			if (separator) {
				menuItem.setClassName("ctxmenu-menu-item separator");
			} else {
				menuItem.setClassName("ctxmenu-menu-item");
			}

			setElement(menuItem);

			Element iconContainer = DOM.createDiv();
			iconContainer.setClassName("ctxmenu-icon-holder");

			Element text = DOM.createDiv();
			text.setClassName("ctxmenu-name");
			text.setInnerText(name);

			menuItem.appendChild(iconContainer);
			menuItem.appendChild(text);

			if (styleName != null && !styleName.equals("")) {
				addStyleName(styleName);
			}

			if (!enabled) {
				addStyleName("v-disabled");
			}

			if (iconUri != null) {
				Element icon = DOM.createImg();
				icon.setClassName("ctxmenu-icon");
				DOM.setElementProperty(icon, "src", iconUri);

				iconContainer.appendChild(icon);
			}
		}

		/**
		 * @return item id of this item
		 */
		public int getItemId() {
			return this.itemId;
		}

		/**
		 * Hides the sub menu that was opened by this item
		 */
		public void hideSubMenu() {
			if (this.hasSubMenu()) {
				subMenu.hide();
			}
		}

		/**
		 * Sets given menu as this menu item's sub menu
		 * 
		 * @param subMenu
		 */
		public void setSubMenu(VMenu subMenu) {
			this.subMenu = subMenu;
			this.addStyleName("ctxmenu-submenu");
		}

		/**
		 * @return sub menu that will be opened when this item is clicked
		 */
		public VMenu getSubMenu() {
			return this.subMenu;
		}

		/**
		 * @return true if this menu item has a sub menu, false otherwise
		 */
		public boolean hasSubMenu() {
			return this.subMenu != null;
		}

		/**
		 * @return true if this item has a separator
		 */
		public boolean hasSeparator() {
			return this.separator;
		}

		@Override
		public void onBrowserEvent(Event event) {
			event.preventDefault();

			switch (event.getTypeInt()) {
			case Event.ONCLICK: {
				if (enabled) {
					if (openTimer != null) {
						openTimer.cancel();
						immediateOpen = false;
					}
					app.itemClicked(this);
				}
				break;
			}
			case Event.ONMOUSEOVER: {
				// automatically open submenus after one second of hovering
				if (enabled && this.hasSubMenu() && immediateOpen == false) {
					openTimer = new Timer() {
						@Override
						public void run() {
							app.itemClicked(VMenuItem.this);
							immediateOpen = true;
						}
					};
					openTimer.schedule(1000);
				}

				// open other submenus from same menu immediately when hovered
				// if user has already hovered another item in the same menu
				if (enabled && immediateOpen && this.hasSubMenu()) {
					app.itemClicked(this);
				}

				break;
			}
			case Event.ONMOUSEOUT: {
				if (openTimer != null) {
					openTimer.cancel();
				}
				break;
			}
			}
		}

		/**
		 * @return menu that owns this menu item
		 */
		VMenu getOwener() {
			return this.owner;
		}

		/**
		 * @return List of sibling sub menus of this menu item.
		 */
		List<VMenu> getSiblingSubMenus() {
			List<VMenu> siblingSubMenus = new LinkedList<VMenu>();

			for (VMenuItem item : this.getOwener().getMenuItems()) {
				if (item != this) {
					if (item.hasSubMenu()) {
						siblingSubMenus.add(item.getSubMenu());
					}
				}
			}

			return siblingSubMenus;
		}
	}
}
