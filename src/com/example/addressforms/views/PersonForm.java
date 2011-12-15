package com.example.addressforms.views;

import java.util.HashMap;
import java.util.Map;

import com.example.addressforms.fields.SimpleDateField;
import com.vaadin.data.Item;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PersonForm extends Form implements FormFieldFactory {
    private static final long serialVersionUID = -5518333607893495007L;

    HorizontalLayout layout;

    private Map<Object, Field> fields = new HashMap<Object, Field>();

    public PersonForm() {
        setWriteThrough(false);

        layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.setHeight(null);
        layout.setSpacing(true);
        TextField firstName = new TextField();
        firstName.setWidth("100%");
        firstName.setInputPrompt("First name");
        firstName.addStyleName("very-big");
        TextField lastName = new TextField();
        lastName.setWidth("100%");
        lastName.setInputPrompt("Last name");
        lastName.addStyleName("very-big");
        TextField phoneNumber = new TextField("Phone Number:");
        TextField email = new TextField("E-mail address:");
        SimpleDateField dateOfBirth = new SimpleDateField("Date of birth:");
        TextArea comments = new TextArea("Comments:");

        TextArea street = new TextArea("Street address:");

        TextField zip = new TextField("Zip code:");
        TextField city = new TextField("City:");
        TextField country = new TextField("Country:");

        zip.addValidator(new IntegerValidator("Zip code is not a number"));
        firstName.setNullRepresentation("");
        lastName.setNullRepresentation("");
        phoneNumber.setNullRepresentation("");
        email.setNullRepresentation("");
        comments.setNullRepresentation("");
        street.setNullRepresentation("");
        zip.setNullRepresentation("");
        city.setNullRepresentation("");
        country.setNullRepresentation("");

        phoneNumber.setWidth("100%");
        email.setWidth("100%");
        dateOfBirth.setWidth("100%");
        comments.setWidth("100%");

        street.setWidth("100%");
        zip.setWidth("100%");
        city.setWidth("100%");
        country.setWidth("100%");

        firstName.setTabIndex(1);
        lastName.setTabIndex(2);
        phoneNumber.setTabIndex(3);
        email.setTabIndex(4);
        dateOfBirth.setTabIndex(5);
        comments.setTabIndex(6);
        street.setTabIndex(7);
        zip.setTabIndex(8);
        city.setTabIndex(9);
        country.setTabIndex(10);

        fields.put("firstName", firstName);
        fields.put("lastName", lastName);
        fields.put("phoneNumber", phoneNumber);
        fields.put("email", email);
        fields.put("dateOfBirth", dateOfBirth);
        fields.put("comments", comments);
        fields.put("address.street", street);
        fields.put("address.zip", zip);
        fields.put("address.city", city);
        fields.put("address.country", country);

        VerticalLayout leftColumn = new VerticalLayout();
        VerticalLayout rightColumn = new VerticalLayout();
        FormLayout leftForm = new FormLayout();
        FormLayout rightForm = new FormLayout();
        layout.addComponent(leftColumn);
        layout.addComponent(rightColumn);
        layout.setExpandRatio(leftColumn, 1);
        layout.setExpandRatio(rightColumn, 1);

        leftForm.addComponent(phoneNumber);
        leftForm.addComponent(email);
        leftForm.addComponent(dateOfBirth);
        leftForm.addComponent(comments);

        rightForm.addComponent(street);
        rightForm.addComponent(zip);
        rightForm.addComponent(city);
        rightForm.addComponent(country);

        leftColumn.addComponent(firstName);
        leftColumn.addComponent(leftForm);
        rightColumn.addComponent(lastName);
        rightColumn.addComponent(rightForm);

        setLayout(layout);
        setFormFieldFactory(this);
        setEnabled(false);
    }

    @Override
    public void setItemDataSource(Item newDataSource) {
        super.setItemDataSource(newDataSource);
        if (newDataSource == null) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

    @Override
    protected void attachField(Object propertyId, Field field) {
    }

    @Override
    protected void detachField(Field field) {
    }

    public Field createField(Item item, Object propertyId, Component uiContext) {
        return fields.get(propertyId);
    }

    @Override
    public void focus() {
        Field field = fields.get("firstName");
        if (field != null) {
            field.focus();
        } else {
            super.focus();
        }
    }
}
