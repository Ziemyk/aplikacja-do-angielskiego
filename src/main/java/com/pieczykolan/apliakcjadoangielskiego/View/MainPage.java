package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.model.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.transform.stream.StreamSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
//@PageTitle("EnglishStudy")

@Route("EnglishStudy")
@AnonymousAllowed
public class MainPage extends VerticalLayout {
    private User user;

    public MainPage()  {
//
          setView();
    }

    public void setView() {
        user = VaadinSession.getCurrent().getAttribute(User.class);
        Notification.show("Hello " + user.getNickName()).setPosition(Notification.Position.TOP_STRETCH);
        Label labelName = new Label(user.getNickName());
        Image image = new Image(setImage(user.getImage()), "My Streamed Image");
        image.setHeight(200, Unit.PIXELS);
        image.setWidth(200, Unit.PIXELS);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button buttonLogout = new Button("Log out");
        buttonLogout.addClickListener(e -> {
            UI.getCurrent().getPage().setLocation("StartPage");
            VaadinSession.getCurrent().setAttribute(User.class, null);
        });
        Button buttonAccount = new Button("My Account");
        Button buttonAchievements = new Button("My Achievements");
        Button buttonPlay = new Button("Play");
        buttonPlay.addClickListener(e -> buttonAccount.setVisible(false));
        horizontalLayout.add(buttonAchievements, buttonAccount, buttonLogout);
        setHorizontalComponentAlignment(Alignment.END, image, labelName, horizontalLayout);
        setHorizontalComponentAlignment(Alignment.BASELINE, buttonLogout);
        setHorizontalComponentAlignment(Alignment.CENTER, buttonPlay);

         add(horizontalLayout, image, labelName, buttonPlay);
    }
    private StreamResource setImage(String image) {
        StreamResource imageResource;
        if(image == null){
            imageResource= new StreamResource("men.jpg",
                    () -> getClass().getResourceAsStream("/Images/men.jpg"));
        }else{
             imageResource = new StreamResource(image,
                    () -> getClass().getResourceAsStream("/Images/" + image));
        }
        return imageResource;

    }


    // public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
    //    image.getSrc()
    // }
    // zrobić klase dla user zalogowanego i tam tworzyć i wykorzystywac obiekt
    // tej klasy na stronie głownej po zalogowaniu
}
