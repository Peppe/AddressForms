package com.example.addressforms;

import com.example.addressforms.views.AddressBookView;
import com.vaadin.Application;
import com.vaadin.ui.Window;

public class AddressformsApplication extends Application {
    @Override
    public void init() {
        AddressBookView view = new AddressBookView();
        Window mainWindow = new Window("Addressforms Application", view);
        setMainWindow(mainWindow);
        setTheme("address-theme");
    }

}
