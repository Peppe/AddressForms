package com.example.addressforms.views;

import com.example.addressforms.backend.Backend.NameClashException;
import com.example.addressforms.backend.Backend.UpdatingNonexistantPersonException;
import com.example.addressforms.data.Person;
import com.example.addressforms.views.AddressBookView.FormInterface;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.NestedMethodProperty;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class FormSection extends Section {

    private final FormInterface formInterface;
    private Form form;
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

    private Form buildForm() {
        PersonForm form = new PersonForm();
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
                    formInterface.storePerson(((BeanItem<Person>) form
                            .getItemDataSource()).getBean());
                } catch (NameClashException e) {
                    // TODO Revert commit.
                    e.printStackTrace();
                } catch (InvalidValueException e) {
                    // don't do anything if validation fails.
                } catch (UpdatingNonexistantPersonException e) {
                    getWindow()
                            .showNotification(
                                    "An system error has occured. We're terribly sorry!",
                                    Notification.TYPE_ERROR_MESSAGE);
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
        item.addItemProperty("address.street", new NestedMethodProperty(person,
                "address.street"));
        item.addItemProperty("address.zip", new NestedMethodProperty(person,
                "address.zip"));
        item.addItemProperty("address.city", new NestedMethodProperty(person,
                "address.city"));
        item.addItemProperty("address.country", new NestedMethodProperty(
                person, "address.country"));
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
