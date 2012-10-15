package org.vaadin.codelabel;

import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class CodeLabelDemo extends Application {
    @Override
    public void init() {
        Window mainWindow = new Window("CodeLabel Demo");
        Label label = new CodeLabel(
                "package org.vaadin.codelabel;\n"
                        + "\n"
                        + "import com.vaadin.Application;\n"
                        + "import com.vaadin.ui.*;\n"
                        + "\n"
                        + "public class CodeLabelDemo extends Application {\n"
                        + "        @Override\n"
                        + "        public void init() {\n"
                        + "                Window mainWindow = new Window(\"CodeLabel Demo\");\n"
                        + "                Label label = new CodeLabel(\"\");\n"
                        + "                mainWindow.addComponent(label);\n"
                        + "                setMainWindow(mainWindow);\n"
                        + "        }\n" + "}");
        mainWindow.addComponent(label);
        setMainWindow(mainWindow);
    }
}
