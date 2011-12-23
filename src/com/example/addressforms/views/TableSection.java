package com.example.addressforms.views;

import java.util.List;

import com.example.addressforms.data.Person;
import com.example.addressforms.views.AddressBookView.TableInterface;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ChameleonTheme;

public class TableSection extends Section {

    private final TableInterface tableInterface;
    private CssLayout tableControls;
    private Table table;

    public TableSection(TableInterface tableInterface) {
        super("Persons", SectionColor.PINK);
        this.tableInterface = tableInterface;

        tableControls = buildTableControls();
        table = buildTable();

        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.addComponent(tableControls);
        tableLayout.addComponent(table);
        setContent(tableLayout);
        updateTableData();
    }

    private CssLayout buildTableControls() {
        CssLayout layout = new CssLayout();
        layout.setWidth("100%");
        layout.addStyleName("table-controls");
        layout.setHeight(null);
        Button add = new Button("Add", new ClickListener() {
            private static final long serialVersionUID = -1109162377825202491L;

            public void buttonClick(ClickEvent event) {
                tableInterface.editPerson(new Person());
            }
        });
        add.addStyleName("button-add");

        Button delete = new Button("Delete", new ClickListener() {
            private static final long serialVersionUID = 2098293913889446104L;

            public void buttonClick(ClickEvent event) {
                tableInterface.deletePerson((Person) table.getValue());
                updateTableData();
            }
        });
        delete.addStyleName("button-delete");

        layout.addComponent(add);
        layout.addComponent(delete);
        return layout;
    }

    private Table buildTable() {
        final Table table = new Table(null);
        table.addStyleName(ChameleonTheme.TABLE_STRIPED);
        table.setPageLength(15);
        table.setWidth("100%");
        table.setHeight(null);

        table.setSelectable(true);
        table.setImmediate(true);
        table.setColumnCollapsingAllowed(true);
        table.addListener(new ValueChangeListener() {
            private static final long serialVersionUID = 4583734858457402068L;

            public void valueChange(ValueChangeEvent event) {
                Person person = (Person) table.getValue();
                tableInterface.editPerson(person);
            }
        });
        return table;
    }

    public void updateTableData() {
        table.setContainerDataSource(buildContainer());
        table.setVisibleColumns(new String[] { "firstName", "lastName",
                "phoneNumber", "email", "dateOfBirth", "comments",
                "address.street", "address.zip", "address.city",
                "address.country" });
        table.setColumnHeaders(new String[] { "First name", "Last name",
                "Phone number", "E-mail address", "Date of birth", "Comments",
                "Street", "Zip", "City", "Country" });
        table.setColumnCollapsed("dateOfBirth", true);
        table.setColumnCollapsed("comments", true);
        table.setColumnCollapsed("address.street", true);
        table.setColumnCollapsed("address.zip", true);
        table.setColumnCollapsed("address.city", true);
        table.setColumnCollapsed("address.country", true);
        table.sort(new Object[] { "id" }, new boolean[] { true });
        table.select(null);
    }

    private Container buildContainer() {
        List<Person> persons = tableInterface.getPersons();
        BeanItemContainer<Person> container = new BeanItemContainer<Person>(
                Person.class, persons);
        container.addNestedContainerProperty("address.street");
        container.addNestedContainerProperty("address.zip");
        container.addNestedContainerProperty("address.city");
        container.addNestedContainerProperty("address.country");
        return container;
    }

}
