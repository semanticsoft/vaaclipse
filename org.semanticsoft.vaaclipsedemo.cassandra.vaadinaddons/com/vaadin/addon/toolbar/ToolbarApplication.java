package com.vaadin.addon.toolbar;

import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;

import com.vaadin.Application;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ToolbarApplication extends Application {

    private static final long serialVersionUID = -2449768291413727223L;
    private VerticalLayout mainContentLayout;
    private final Window mainWindow = new Window("Toolbar Demo");

    // icon resources
    public static final ThemeResource arrowLeftIcon = new ThemeResource("../images/arrow_left.png");
    public static final ThemeResource funnelIcon = new ThemeResource("../images/funnel.png");
    public static final ThemeResource eyeIcon = new ThemeResource("../images/eye.png");
    public static final ThemeResource functionIcon = new ThemeResource("../images/function.png");
    public static final ThemeResource pieChartIcon = new ThemeResource("../images/chart_pie.png");
    public static final ThemeResource homeIcon =
            new ThemeResource("../images/application_home.png");
    public static final ThemeResource mapIcon = new ThemeResource("../images/map_magnify.png");
    public static final ThemeResource binocularIcon = new ThemeResource("../images/binocular.png");
    public static final ThemeResource exitIcon = new ThemeResource("../images/Exit.png");
    public static final ThemeResource plusIcon = new ThemeResource("../images/toggle-expand.png");
    public static final ThemeResource minusIcon = new ThemeResource("../images/toggle.png");

    public static final ThemeResource printIcon = new ThemeResource("../images/printer.png");
    public static final ThemeResource excelIcon = new ThemeResource("../images/table-excel.png");
    public static final ThemeResource arrowRight = new ThemeResource("../images/arrow_right.png");
    public static final ThemeResource zoom = new ThemeResource("../images/zoom.png");

    @Override
    public void init() {
        setTheme("toolbartheme");
        // setTheme("reindeermods");
        // mainContentLayout = new WeeLayout(Direction.VERTICAL);
        mainContentLayout = new VerticalLayout();
        mainContentLayout.setImmediate(true);
        mainContentLayout.setWidth("100%");
        mainContentLayout.setHeight("100%");
        mainContentLayout.setMargin(true);

        // mainToolbar
        final Label descriptionLabel =
                new Label(
                        "<b>NativeToolbar</b> Uses NativeButtons and CSS from the tutorial.<br>"
                                + "<b>SegmentToolbar</b> and <b>CssToolbar</b> use parts of ChameleonTheme demo.<br><br>");
        descriptionLabel.setContentMode(Label.CONTENT_XHTML);
        mainContentLayout.addComponent(descriptionLabel);

        final Label sp0 = new Label("");
        sp0.setHeight("5px");
        mainContentLayout.addComponent(sp0);

        mainContentLayout.addComponent(new Label("NativeToolbar - Widths Specified"));
        addToolbar(new NativeToolbar("62px"), mainContentLayout, true);
        mainContentLayout.addComponent(new Label("NativeToolbar - Widths NOT Specified"));
        addToolbar(new NativeToolbar("62px"), mainContentLayout, false);
        final Label sp1 = new Label("");
        sp1.setHeight("10px");
        mainContentLayout.addComponent(sp1);

        mainContentLayout.addComponent(new Label("SegmentToolbar - Widths Specified"));
        addToolbar(new SegmentToolbar("62px"), mainContentLayout, true);
        mainContentLayout.addComponent(new Label("SegmentToolbar - Widths NOT Specified"));
        addToolbar(new SegmentToolbar("62px"), mainContentLayout, false);
        final Label sp2 = new Label("");
        sp2.setHeight("10px");
        mainContentLayout.addComponent(sp2);

        mainContentLayout.addComponent(new Label("CssToolbar - Widths Specified"));
        addToolbar(new CssToolbar("62px"), mainContentLayout, true);
        mainContentLayout.addComponent(new Label("CssToolbar - Widths NOT Specified"));
        addToolbar(new CssToolbar("62px"), mainContentLayout, false);

        final AbsoluteLayout layoutWithMargin = new AbsoluteLayout();
        layoutWithMargin.addComponent(mainContentLayout,
                "top:20px;left:20px;bottom:10px;right:10px");
        mainWindow.setContent(layoutWithMargin);
        setMainWindow(mainWindow);
    }

    private Toolbar addToolbar(final Toolbar toolbar, final Layout toAddTo,
            final boolean specifyWidth) {
        setUpToolbar(toolbar, specifyWidth);
        toAddTo.addComponent(toolbar);
        final Label verticalSpacer = new Label("");
        verticalSpacer.setHeight("5px");
        toAddTo.addComponent(verticalSpacer);
        return toolbar;
    }

    @SuppressWarnings("unused")
    private void setUpToolbar(final Toolbar bar, final boolean specifyWidth) {
        // undefined ensures no space between components
        bar.setWidth("-1px");
        Button homeButton, backButton, functionButton, mapButton, menuExampleButton, exitButton, menuTwoButton, pieChartButton;
        if (specifyWidth) {
            homeButton = bar.createAndAddButton("Home", homeIcon, "65px");
            backButton = bar.createAndAddButton("Back", arrowLeftIcon, "50px");
            menuExampleButton = bar.createAndAddMenu("Menu Example", "70px");
            functionButton = bar.createAndAddButton("Function", functionIcon, "60px");
            mapButton = bar.createAndAddButton("Map", mapIcon, "50px");
            menuTwoButton = bar.createAndAddMenu("Menu Two", "70px");
            pieChartButton = bar.createAndAddButton("", pieChartIcon, "40px");
            bar.groupTwoButtons(menuTwoButton, pieChartButton);
            exitButton = bar.createAndAddButton("Exit", exitIcon, "45px");
        } else {
            homeButton = bar.createAndAddButton("Home", homeIcon);
            backButton = bar.createAndAddButton("Back", arrowLeftIcon);
            menuExampleButton = bar.createAndAddMenu("Menu Example");
            functionButton = bar.createAndAddButton("Function", functionIcon);
            mapButton = bar.createAndAddButton("Map", mapIcon);
            menuTwoButton = bar.createAndAddMenu("Menu Two");
            pieChartButton = bar.createAndAddButton("", pieChartIcon);
            bar.groupTwoButtons(menuTwoButton, pieChartButton);
            exitButton = bar.createAndAddButton("Exit", exitIcon);
        }
        final Toolbar.Command menuCommand = new Toolbar.Command() {
            private static final long serialVersionUID = 5744979083564825236L;

            @Override
            public void menuSelected(final ContextMenuItem selectedItem) {
                ToolbarApplication.this.getMainWindow().showNotification(
                        "Selected: " + selectedItem.getName());
            }
        };
        bar.addMenuItem(menuExampleButton, "Arrow Right", arrowRight, false, menuCommand);
        bar.addMenuItem(menuExampleButton, "Print Report", printIcon, true, menuCommand);
        bar.addMenuItem(menuExampleButton, "Excel", excelIcon, true, menuCommand);
        bar.addMenuItem(menuExampleButton, "Zoom", zoom, false, menuCommand);
        bar.addMenuItem(menuTwoButton, "Funnel", funnelIcon, false, menuCommand);
    }
}
