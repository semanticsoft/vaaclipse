package fi.jasoft.dragdroplayouts.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.vaadin.codelabel.CodeLabel;

import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

public class DragdropApplication extends Application {

    private class MainWindow extends Window {

        private TabSheet tabs;

        private CodeLabel code;

        public MainWindow() {
            setTheme("dragdrop");

            VerticalSplitPanel content = new VerticalSplitPanel();
            content.setSizeFull();

            tabs = new TabSheet();
            tabs.setSizeFull();
            tabs.setImmediate(true);

            tabs.addComponent(new DragdropAbsoluteLayoutDemo());
            tabs.addComponent(new DragdropVerticalLayoutDemo());
            tabs.addComponent(new DragdropHorizontalLayoutDemo());
            tabs.addComponent(new DragdropGridLayoutDemo());
            tabs.addComponent(new DragdropCssLayoutDemo());
            tabs.addComponent(new DragdropLayoutDraggingDemo());
            tabs.addComponent(new DragdropHorizontalSplitPanelDemo());
            tabs.addComponent(new DragdropVerticalSplitPanelDemo());
            tabs.addComponent(new DragdropTabsheetDemo());
            tabs.addComponent(new DragdropAccordionDemo());
            tabs.addComponent(new DragdropDragFilterDemo());

            tabs.addListener(new TabSheet.SelectedTabChangeListener(){
                public void selectedTabChange(SelectedTabChangeEvent event) {
                    tabChanged(event.getTabSheet().getSelectedTab());
                }
            });
            
            content.addComponent(tabs);

            code = new CodeLabel("");

            Panel codePanel = new Panel();
            codePanel.setSizeFull();
            codePanel.addComponent(code);
            content.addComponent(codePanel);

            setContent(content);

            tabChanged(tabs.getComponentIterator().next());
        }

        private void tabChanged(Component tab) {
            try {
                String path = getClass().getCanonicalName().replaceAll("\\.", "/")+".java";

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(getClass().getClassLoader()
                                .getResourceAsStream(path)));

                StringBuilder codelines = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                	if(line.startsWith("import")){
                		// Remove imports
                	} else if(line.startsWith("package")){
                		// Remove package declaration
                	} else {
                		 codelines.append(line);
                         codelines.append("\n");
                	}
                
                    line = reader.readLine();
                }

                reader.close();
                
                String code = codelines.toString();
               
                code = Pattern.compile("public String getCodePath.*?}",Pattern.MULTILINE|Pattern.DOTALL).matcher(code).replaceAll("");
                
                this.code.setValue(code.trim());
                
            } catch (Exception e) {
                code.setValue("No code available.");
            }
        }
    }

    @Override
    public void init() {
        setMainWindow(new MainWindow());
    }
}
