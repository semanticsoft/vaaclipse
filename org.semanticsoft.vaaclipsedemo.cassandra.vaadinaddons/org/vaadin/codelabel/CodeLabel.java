package org.vaadin.codelabel;

import com.vaadin.ui.Label;

/**
 * Server side component for the VCodeLabel widget.
 */
@com.vaadin.ui.ClientWidget(org.vaadin.codelabel.client.ui.VCodeLabel.class)
public class CodeLabel extends Label {

    private static final long serialVersionUID = -2254748201597189586L;

    public CodeLabel() {
        setContentMode(CONTENT_PREFORMATTED);
    }

    public CodeLabel(String content) {
        super(content, CONTENT_PREFORMATTED);
    }

    @Override
    public void setContentMode(int contentMode) {
        if (contentMode != CONTENT_PREFORMATTED) {
            throw new UnsupportedOperationException(
                    "Only preformatted content supported");
        }
        super.setContentMode(CONTENT_PREFORMATTED);
    }

}
