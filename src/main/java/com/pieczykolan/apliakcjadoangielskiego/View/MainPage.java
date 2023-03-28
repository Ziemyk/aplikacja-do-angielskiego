package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.model.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

//@PageTitle("EnglishStudy")

@Route("EnglishStudy")
public class MainPage extends VerticalLayout {
    private User user;
    static public int level = 1;
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public MainPage()  {
            lvlView();
          userView();
    }
    private void lvlView(){

        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.START);
        Button buttonLvl1 = new Button("Level 1");
        buttonLvl1.addClickListener(e -> level = 1);
        Button buttonLvl2 = new Button("Level 2");
        buttonLvl2.addClickListener(e -> level = 2);
        Button buttonLvl3 = new Button("Level 3");
        buttonLvl3.addClickListener(e -> level = 3);
        Button buttonLvl4 = new Button("Level 4");
        buttonLvl4.addClickListener(e -> level = 4);
        layout.add(buttonLvl1,buttonLvl2,buttonLvl3,buttonLvl4);
        //RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();
        //radioButtonGroup.setLabel("Spacing");
        //radioButtonGroup.setItems(ENABLED_OPTION, DISABLED_OPTION);
        //.setValue(ENABLED_OPTION);

        add(layout);
    }


    public void userView() {
        //VaadinSession.getCurrent().setAttribute(User.class,user);
        user = VaadinSession.getCurrent().getAttribute(User.class);
        //System.out.println(user.toString());
        //System.out.println(user.getImage());
        Notification.show("Hello " + user.getNickName()).setPosition(Notification.Position.TOP_STRETCH);
        Label labelName = new Label(user.getNickName());
        Image image = new Image(setImage(user.getImage()), "My Streamed Image");
        image.setHeight(200, Unit.PIXELS);
        image.setWidth(200, Unit.PIXELS);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button buttonLogout = new Button("Log out");
        buttonLogout.addClickListener(e -> {
            UI.getCurrent().getPage().setLocation("/");
            VaadinSession.getCurrent().setAttribute(User.class, null);
        });
        
        Button buttonAccount = new Button("My Account");
        Button buttonAchievements = new Button("My Achievements");
        Button buttonPlay = new Button("Play");
        buttonPlay.addClickListener(e ->{
            UI.getCurrent().getPage().setLocation("Game/level/"+ level);
        });
        buttonPlay.addClickListener(e -> buttonAccount.setVisible(false));
        horizontalLayout.add(buttonAchievements, buttonAccount, buttonLogout);
        setHorizontalComponentAlignment(Alignment.END, image, labelName, horizontalLayout);
        setHorizontalComponentAlignment(Alignment.BASELINE, buttonLogout);
        setHorizontalComponentAlignment(Alignment.CENTER, buttonPlay);

         add(horizontalLayout, image, labelName, buttonPlay);
         //VaadinSession.getCurrent().close();
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



}
