package com.pieczykolan.apliakcjadoangielskiego.View;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

@Theme("my-theme")
public class MainLayout extends AppLayout implements AppShellConfigurator {

    private H1 viewTitle = new H1("Siema");

    public MainLayout(){
        HorizontalLayout header = new HorizontalLayout(viewTitle);
        header.setWidth("100%");
        addToNavbar(header);
    }

}
