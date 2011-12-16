package com.example.addressforms;

import com.example.addressforms.views.AddressBookView;
import com.vaadin.annotations.RootTheme;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.Root;

@RootTheme("address-theme")
public class AddressFormsRoot extends Root {

    @Override
    protected void init(WrappedRequest request) {
        AddressBookView view = new AddressBookView();
        setContent(view);
    }
}
