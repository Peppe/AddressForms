package com.example.addressforms.fields;

import com.example.addressforms.data.Address;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class AddressPopup extends CustomField<Address> {

    private FieldGroup fieldGroup;

    @Override
    protected Component initContent() {
        FormLayout layout = new FormLayout();
        final Window window = new Window("Edit address", layout);
        TextArea street = new TextArea("Street address:");
        TextField zip = new TextField("Zip code:");
        TextField city = new TextField("City:");
        TextField country = new TextField("Country:");
        layout.addComponent(street);
        layout.addComponent(zip);
        layout.addComponent(city);
        layout.addComponent(country);

        fieldGroup = new BeanFieldGroup<Address>(Address.class);
        fieldGroup.bind(street, "street");
        fieldGroup.bind(zip, "zip");
        fieldGroup.bind(city, "city");
        fieldGroup.bind(country, "country");
        Button button = new Button("Open address editor", new ClickListener() {

            public void buttonClick(ClickEvent event) {
                getRoot().addWindow(window);
            }
        });
        window.addListener(new CloseListener() {
            public void windowClose(CloseEvent e) {
                try {
                    fieldGroup.commit();
                } catch (CommitException ex) {
                    ex.printStackTrace();
                }
            }
        });

        window.center();
        window.setWidth(null);
        layout.setWidth(null);
        layout.setMargin(true);
        return button;
    }

    @Override
    public Class<Address> getType() {
        return Address.class;
    }

    @Override
    protected void setInternalValue(Address address) {
        super.setInternalValue(address);
        fieldGroup.setItemDataSource(new BeanItem<Address>(address));
    }
}
