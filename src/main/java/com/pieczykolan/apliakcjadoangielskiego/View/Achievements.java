package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.model.User;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("Achievements")
public class Achievements extends VerticalLayout {
    User user =  VaadinSession.getCurrent().getAttribute(User.class);
    public Achievements() {
        Label labelUsername = new Label(user.getNickName());
        Image imageUser = new Image();
       // imageUser.setSrc(user.getImage());

        setHorizontalComponentAlignment(Alignment.CENTER, labelUsername, imageUser);

    }
}