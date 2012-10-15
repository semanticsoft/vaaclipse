package org.vaadin.peter.contextmenu.client.ui;

import java.util.Iterator;

import org.vaadin.peter.contextmenu.client.ui.VMenu.VMenuItem;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * Client side implementation for ContextMenu component
 * 
 * @author Peter Lehto / IT Mill Oy Ltd
 */

public class VContextMenu extends Widget implements Paintable {

	protected String paintableId;
	protected ApplicationConnection client;

	private int rootX;
	private int rootY;

	private Integer width;

	private String rootLocationId;

	private VMenu rootMenu;

	private boolean openToRight;

	public VContextMenu() {
		Element element = DOM.createDiv();
		setElement(element);
	}

	@Override
	public void setWidth(String width) {
		if (width == null || width.equals("")) {
			return;
		}

		this.width = Integer.parseInt(width.substring(0,
				width.lastIndexOf("px")));
	}

	@Override
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		if (client.updateComponent(this, uidl, true)) {
			return;
		}

		this.client = client;
		paintableId = uidl.getId();

		UIDL rootUidl = uidl.getChildByTagName("items");

		if (rootMenu != null) {
			rootMenu.hide();
		}

		this.rootMenu = createMenuHierarchy(rootUidl, null);

		if (this.getStyleName() != null && !this.getStyleName().equals("")) {
			this.rootMenu.addStyleName(this.getStyleName());
		}

		// Show root menu
		if (uidl.hasAttribute("show")) {
			if (uidl.getBooleanAttribute("show") == true) {
				openToRight = false;

				this.rootLocationId = uidl
						.getStringAttribute("component_locator");

				if (this.rootLocationId.equals("")) {
					rootX = uidl.getIntAttribute("left");
					rootY = uidl.getIntAttribute("top");

					rootX += Window.getScrollLeft();
					rootY += Window.getScrollTop();
				} else {
					Element positionElement = DOM
							.getElementById(this.rootLocationId);
					int left = positionElement.getAbsoluteRight() + 10;
					this.rootX = left;
					this.rootY = positionElement.getAbsoluteTop();
				}

				this.positionAndShowMenu(this.rootMenu, rootX, rootY);
			}
		}
	}

	private VMenu createMenuHierarchy(UIDL contents, VMenu parentMenu) {
		Iterator<Object> childIterator = contents.getChildIterator();
		VMenu menu = new VMenu(this, parentMenu);

		while (childIterator.hasNext()) {
			UIDL child = (UIDL) childIterator.next();

			Integer id = child.getIntAttribute("id");
			String name = child.getStringAttribute("name");
			boolean enabled = child.getBooleanAttribute("enabled");
			boolean separator = child.getBooleanAttribute("separator");

			String styleName = null;

			if (child.hasAttribute("style")) {
				styleName = child.getStringAttribute("style");
			}

			String iconUri = null;

			if (child.hasAttribute("icon")) {
				iconUri = this.client.translateVaadinUri(child
						.getStringAttribute("icon"));
			}

			VMenuItem item = menu.addMenuItem(id, name, styleName, enabled,
					iconUri, separator);

			if (child.getChildByTagName("items") != null) {
				VMenu subMenu = createMenuHierarchy(
						child.getChildByTagName("items"), menu);
				item.setSubMenu(subMenu);
			}
		}

		return menu;
	}

	private void positionAndShowMenu(VMenu menu, int left, int top) {
		int windowWidth = Window.getClientWidth();

		menu.show();

		if (left > windowWidth - menu.getOffsetWidth() || openToRight) {
			if (menu.hasParent()) {
				VMenu parentMenu = menu.getParentMenu();

				left = parentMenu.getAbsoluteLeft();
				left -= menu.getOffsetWidth();
			} else {
				left = windowWidth - menu.getOffsetWidth();
			}

			// if menu is opened leftwards, open sub menus leftwards too
			openToRight = true;
		}

		int menuHeight = menu.getNumberOfItems() * 20;

		if (top + menuHeight > Window.getClientHeight()) {
			top -= menuHeight;
		}

		menu.setPopupPosition(left, top);
	}

	/**
	 * Called when menu item is clicked. If clicked menu item has a sub menu it
	 * will be opened.
	 * 
	 * @param parent
	 * @param id
	 * @param top
	 */
	public void itemClicked(VMenuItem item) {
		VMenu ownerMenu = item.getOwener();

		if (item.hasSubMenu()) {
			for (VMenu siblingSubMenu : item.getSiblingSubMenus()) {
				siblingSubMenu.hide();
			}

			this.positionAndShowMenu(item.getSubMenu(),
					ownerMenu.getAbsoluteLeft() + ownerMenu.getOffsetWidth(),
					item.getAbsoluteTop());
		} else {
			this.rootMenu.hide();
		}

		this.client.updateVariable(paintableId, "itemId", item.getItemId(),
				true);
	}

	public Integer getDefinedWidth() {
		return this.width;
	}

	public boolean hasDefinedWidth() {
		return this.width != null;
	}
}
