package com.pieczykolan.apliakcjadoangielskiego.Layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.page.AppShellConfigurator;


@CssImport("themes/my-theme/styles.css")
public class MainLayout extends AppLayout implements AppShellConfigurator {

    private H1 viewTitle = new H1("English Study");

    public MainLayout(){
        //HorizontalLayout header = new HorizontalLayout(viewTitle);

        viewTitle.setWidth("100%");
        addToNavbar(viewTitle);

    }


}
