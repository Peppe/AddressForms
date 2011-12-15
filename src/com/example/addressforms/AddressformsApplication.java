package com.example.addressforms;

import com.example.addressforms.views.AddressBookView;
import com.vaadin.Application.LegacyApplication;
import com.vaadin.ui.Root;

public class AddressformsApplication extends LegacyApplication {
    @Override
    public void init() {
        AddressBookView view = new AddressBookView();
        Root mainWindow = new Root("Addressforms Application", view);
        setMainWindow(mainWindow);
        setTheme("address-theme");
    }

}
