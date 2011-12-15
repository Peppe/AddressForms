package com.example.addressforms.views;

import java.util.List;

import com.example.addressforms.backend.Backend;
import com.example.addressforms.backend.Backend.NameClashException;
import com.example.addressforms.backend.Backend.UpdatingNonexistantPersonException;
import com.example.addressforms.data.Person;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;

public class AddressBookView extends CssLayout {
    private static final long serialVersionUID = 1365260373356069762L;
    private final Backend backend;
    private TableSection tableSection;
    private FormSection formSection;

    public interface TableInterface {
        public void editPerson(Person person);

        public List<Person> getPersons();

        public void deletePerson(Person value);
    }

    public interface FormInterface {
        public void storePerson(Person person) throws NameClashException,
                UpdatingNonexistantPersonException;
    }

    private TableInterface tableInterface = new TableInterface() {
        public void editPerson(Person person) {
            if (person != null) {
                formSection.editPerson(person);
            } else {
                formSection.disableControls();
            }
        }

        public List<Person> getPersons() {
            return backend.getPersons();
        }

        public void deletePerson(Person person) {
            backend.deletePerson(person);
        }
    };

    private FormInterface formInterface = new FormInterface() {

        public void storePerson(Person person) throws NameClashException,
                UpdatingNonexistantPersonException {
            backend.storePerson(person);
            tableSection.updateTableData();
            formSection.disableControls();
        }

    };

    public AddressBookView() {
        addStyleName("address-view");
        setWidth("100%");
        setHeight(null);
        backend = new Backend();

        CssLayout innerLayout = new CssLayout();
        innerLayout.addStyleName("address-view-inner");
        innerLayout.setHeight(null);
        addComponent(innerLayout);

        // Label headerCaption = new Label("The Address Book");
        Embedded headerCaption = new Embedded(null, new ThemeResource(
                "header.png"));
        headerCaption.setSizeUndefined();
        headerCaption.addStyleName("view-caption");
        tableSection = new TableSection(tableInterface);
        formSection = new FormSection(formInterface);
        innerLayout.addComponent(headerCaption);
        innerLayout.addComponent(tableSection);
        innerLayout.addComponent(formSection);
    }

}
