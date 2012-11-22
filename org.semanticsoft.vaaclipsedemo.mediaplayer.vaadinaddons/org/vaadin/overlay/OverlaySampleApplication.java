package org.vaadin.overlay;

import com.vaadin.Application;
import com.vaadin.terminal.ClassResource;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class OverlaySampleApplication extends Application {

    private static final long serialVersionUID = 7831853143147236971L;

    @Override
    public void init() {
        final Window mainWindow = new Window("Overlay Sample Application");
        setMainWindow(mainWindow);


        final Label label = new Label("Alignment.TOP_LEFT");
        mainWindow.addComponent(label);

        for (int i = 0; i < 20; i++) {

            Button button = new Button("Sample Button");
            mainWindow.addComponent(button);

            final ImageOverlay io = new ImageOverlay(button);
            Resource res = new ClassResource(this.getClass(), "icon-new.png",
                    this);
            io.setImage(res);
            io.setComponentAnchor(Alignment.TOP_LEFT); // Top left of the button
            io.setOverlayAnchor(Alignment.MIDDLE_CENTER); // Center of the image
            io.setClickListener(new OverlayClickListener() {

                public void overlayClicked(CustomClickableOverlay overlay) {
                    mainWindow.showNotification("ImageOverlay Clicked!");
                }
            });
            mainWindow.addComponent(io);

            final TextOverlay to = new TextOverlay(button, "New!");
            to.setComponentAnchor(Alignment.TOP_LEFT); // Top left of the button
            to.setOverlayAnchor(Alignment.MIDDLE_CENTER); // Center of the image
            to.setClickListener(new OverlayClickListener() {

                public void overlayClicked(CustomClickableOverlay overlay) {
                    mainWindow.showNotification("TextOverlay Clicked!");
                }
            });
            mainWindow.addComponent(to);

            button.addListener(new Button.ClickListener() {

                private static final long serialVersionUID = 1L;

                public void buttonClick(ClickEvent event) {
                    Alignment a = io.getComponentAnchor();
                    String s = "";
                    if (Alignment.TOP_LEFT == a) {
                        a = Alignment.TOP_CENTER;
                        s = "TOP_CENTER";
                    } else if (Alignment.TOP_CENTER == a) {
                        a = Alignment.TOP_RIGHT;
                        s = "TOP_RIGHT";
                    } else if (Alignment.TOP_RIGHT == a) {
                        a = Alignment.MIDDLE_RIGHT;
                        s = "MIDDLE_RIGHT";
                    } else if (Alignment.MIDDLE_RIGHT == a) {
                        a = Alignment.BOTTOM_RIGHT;
                        s = "BOTTOM_RIGHT";
                    } else if (Alignment.BOTTOM_RIGHT == a) {
                        a = Alignment.BOTTOM_CENTER;
                        s = "BOTTOM_CENTER";
                    } else if (Alignment.BOTTOM_CENTER == a) {
                        a = Alignment.BOTTOM_LEFT;
                        s = "BOTTOM_LEFT";
                    } else if (Alignment.BOTTOM_LEFT == a) {
                        a = Alignment.MIDDLE_LEFT;
                        s = "MIDDLE_LEFT";
                    } else if (Alignment.MIDDLE_LEFT == a) {
                        a = Alignment.TOP_LEFT;
                        s = "TOP_LEFT";
                    }
                    io.setComponentAnchor(a);
                    label.setValue("Alignment." + s);
                }
            });

        }

    }
}
