package com.example.addressforms.views;

import com.example.addressforms.data.Person;
import com.example.addressforms.views.AddressBookView.FormInterface;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.NestedMethodProperty;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class FormSection extends Section {

    private final FormInterface formInterface;
    private PersonForm form;
    private CssLayout buttonBar;
    private Label errorMessage;

    public FormSection(FormInterface formInterface) {
        super("Update person info", SectionColor.GREEN);
        this.formInterface = formInterface;

        form = buildForm();
        buttonBar = buildButtonBar();
        errorMessage = new Label();

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.addComponent(buttonBar);
        formLayout.addComponent(form);
        formLayout.addComponent(errorMessage);
        setContent(formLayout);
    }

    private PersonForm buildForm() {
        PersonForm form = new PersonForm(formInterface);
        return form;
    }

    private CssLayout buildButtonBar() {
        CssLayout layout = new CssLayout();
        layout.setWidth("100%");
        layout.addStyleName("button-bar");
        layout.setHeight(null);
        Button save = new Button("Save", new ClickListener() {
            private static final long serialVersionUID = -1109162377825202491L;

            public void buttonClick(ClickEvent event) {
                try {
                    form.commit();
                } catch (CommitException e) {
                    // Todo should we do anything anymore here?
                }
            }
        });
        Button discard = new Button("Discard", new ClickListener() {
            private static final long serialVersionUID = 2098293913889446104L;

            public void buttonClick(ClickEvent event) {
                form.discard();
            }
        });

        save.setTabIndex(11);
        discard.setTabIndex(12);
        layout.addComponent(save);
        layout.addComponent(discard);
        layout.setEnabled(false);
        return layout;
    }

    public BeanItem<Person> buildBeanItem(Person person) {
        BeanItem<Person> item = new BeanItem<Person>(person);
        // FIXME: should the generics be the same type as the bean field?
        item.addItemProperty("address.street",
                new NestedMethodProperty<String>(person, "address.street"));
        item.addItemProperty("address.zip", new NestedMethodProperty<String>(
                person, "address.zip"));
        item.addItemProperty("address.city", new NestedMethodProperty<String>(
                person, "address.city"));
        item.addItemProperty("address.country",
                new NestedMethodProperty<String>(person, "address.country"));
        return item;
    }

    public void editPerson(Person person) {
        BeanItem<Person> item = buildBeanItem(person);
        form.setItemDataSource(item);
        buttonBar.setEnabled(true);
        form.focus();
    }

    public void disableControls() {
        BeanItem<Person> item = buildBeanItem(new Person());
        form.setItemDataSource(item);
        buttonBar.setEnabled(false);
        form.setEnabled(false);
    }

}
