package com.example.addressforms.views;

import com.example.addressforms.backend.Backend.NameClashException;
import com.example.addressforms.backend.Backend.UpdatingNonexistantPersonException;
import com.example.addressforms.data.Person;
import com.example.addressforms.fields.AddressPopup;
import com.example.addressforms.fields.SimpleDateField;
import com.example.addressforms.views.AddressBookView.FormInterface;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PersonFormWithAddressAsButton extends HorizontalLayout {
    private static final long serialVersionUID = -5518333607893495007L;

    private final FieldGroup fieldGroup = new BeanFieldGroup<Person>(
            Person.class);

    private TextField firstName;
    private TextField lastName;
    private TextField phoneNumber;
    private TextField email;
    private SimpleDateField dateOfBirth;
    private TextArea comments;
    private AddressPopup address;

    public PersonFormWithAddressAsButton(final FormInterface formInterface) {
        fieldGroup.addCommitHandler(new CommitHandler() {

            public void preCommit(CommitEvent commitEvent)
                    throws CommitException {
            }

            public void postCommit(CommitEvent commitEvent)
                    throws CommitException {
                try {
                    formInterface.storePerson(((BeanItem<Person>) fieldGroup
                            .getItemDataSource()).getBean());
                } catch (NameClashException e) {
                    throw new CommitException(e);
                } catch (UpdatingNonexistantPersonException e) {
                    getRoot()
                            .showNotification(
                                    "An system error has occured. We're terribly sorry!",
                                    Notification.TYPE_ERROR_MESSAGE);
                    throw new CommitException(e);
                }
            }
        });
        setWidth("100%");
        setHeight(null);
        setSpacing(true);

        firstName = new TextField();
        firstName.setWidth("100%");
        firstName.setInputPrompt("First name");
        firstName.addStyleName("very-big");
        lastName = new TextField();
        lastName.setWidth("100%");
        lastName.setInputPrompt("Last name");
        lastName.addStyleName("very-big");
        phoneNumber = new TextField("Phone Number:");
        email = new TextField("E-mail address:");
        dateOfBirth = new SimpleDateField("Date of birth:");
        comments = new TextArea("Comments:");
        address = new AddressPopup();

        firstName.setNullRepresentation("");
        lastName.setNullRepresentation("");
        phoneNumber.setNullRepresentation("");
        email.setNullRepresentation("");
        comments.setNullRepresentation("");

        phoneNumber.setWidth("100%");
        email.setWidth("100%");
        dateOfBirth.setWidth("100%");
        comments.setWidth("100%");

        firstName.setTabIndex(1);
        lastName.setTabIndex(2);
        phoneNumber.setTabIndex(3);
        email.setTabIndex(4);
        dateOfBirth.setTabIndex(5);
        comments.setTabIndex(6);
        address.setTabIndex(7);

        fieldGroup.buildAndBindMemberFields(this);

        VerticalLayout leftColumn = new VerticalLayout();
        VerticalLayout rightColumn = new VerticalLayout();
        FormLayout leftForm = new FormLayout();
        FormLayout rightForm = new FormLayout();
        addComponent(leftColumn);
        addComponent(rightColumn);
        setExpandRatio(leftColumn, 1);
        setExpandRatio(rightColumn, 1);

        leftForm.addComponent(phoneNumber);
        leftForm.addComponent(email);
        leftForm.addComponent(dateOfBirth);
        leftForm.addComponent(address);
        rightForm.addComponent(comments);
        leftColumn.addComponent(firstName);
        leftColumn.addComponent(leftForm);
        rightColumn.addComponent(lastName);
        rightColumn.addComponent(rightForm);
        setEnabled(false);
    }

    public Item getItemDataSource() {
        return fieldGroup.getItemDataSource();
    }

    public void setItemDataSource(Item newDataSource) {
        fieldGroup.setItemDataSource(newDataSource);
        if (newDataSource == null) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

    @Override
    public void focus() {
        firstName.focus();
    }

    public void commit() throws CommitException {
        fieldGroup.commit();
    }

    public void discard() {
        fieldGroup.discard();
    }

}