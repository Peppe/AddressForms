package com.example.addressforms.views;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class Section extends CssLayout {
    private static final long serialVersionUID = -5293497353764794762L;
    private CssLayout header;
    private CssLayout content;

    public enum SectionColor {
        PINK("section-pink"), GREEN("section-green"), ORANGE("section-orange"), BLUE(
                "section-blue");

        String cssClass;

        private SectionColor(String cssClass) {
            this.cssClass = cssClass;
        }

        public String getCssClass() {
            return cssClass;
        }
    }

    public Section(String caption, SectionColor color) {
        setWidth("840px");
        addStyleName("section");
        header = new CssLayout();
        header.addStyleName("section-header");
        header.addStyleName(color.getCssClass());
        header.setWidth("100%");
        header.setHeight("60px");
        Label captionLabel = new Label(caption);
        captionLabel.setWidth(null);
        header.addComponent(captionLabel);
        content = new CssLayout();
        content.addStyleName("section-content");
        addComponent(header);
        addComponent(content);
    }

    public void setContent(Component newContent) {
        content.removeAllComponents();
        content.addComponent(newContent);
    }
}
