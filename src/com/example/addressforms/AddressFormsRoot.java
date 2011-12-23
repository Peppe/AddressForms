package com.example.addressforms;

import com.example.addressforms.views.AddressBookView;
import com.vaadin.annotations.Theme;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.Root;

@Theme("address-theme")
public class AddressFormsRoot extends Root {

    @Override
    protected void init(WrappedRequest request) {
        AddressBookView view = new AddressBookView();
        setCaption("The Address Book");
        setContent(view);
    }
}
