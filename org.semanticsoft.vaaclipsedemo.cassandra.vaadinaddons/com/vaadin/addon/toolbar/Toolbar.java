package com.vaadin.addon.toolbar;

import java.io.Serializable;

import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;

import com.vaadin.terminal.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;

public interface Toolbar extends Layout, org.vaadin.peter.contextmenu.ContextMenu.ClickListener {

    public Button createButton(final String caption, final Resource icon);
    public Button createMenu(final String caption);
    public Button createButton(final String caption, final Resource icon, final String width);
    public Button createMenu(final String caption, final String width);
    @Override
    public void addComponent(final Component c);
    public Button createAndAddButton(final String caption, final Resource icon);
    public Button createAndAddMenu(final String caption);
    public Button createAndAddButton(final String caption, final Resource icon, final String width);
    public Button createAndAddMenu(final String caption, final String width);

    /*
     * The menu item needs to have a unique caption
     */
    public void addMenuItem(final Button menu, final String caption, final Resource icon,
            final boolean separator, final Toolbar.Command command);

    public interface Command extends Serializable {
        public void menuSelected(ContextMenuItem selectedItem);
    }

    public void groupTwoButtons(final Button firstButton, final Button nextButton);
}
