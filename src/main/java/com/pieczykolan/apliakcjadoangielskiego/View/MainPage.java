package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

//@PageTitle("EnglishStudy")
@Route("EnglishStudy")
public class MainPage extends VerticalLayout {
    private User user ;
    static public int level = 1;
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {

        this.level = level;
    }
    AuthService authService;
   @Autowired
    public MainPage(AuthService authService)  {
        this.authService = authService;
        userView();
        lvlView();
    }

    public void userView() {
        user = VaadinSession.getCurrent().getAttribute(User.class);
        user = authService.updateData(user.getNickName());
        Notification.show("Hello " + user.getNickName()).setPosition(Notification.Position.TOP_STRETCH);
        Label labelName = new Label(user.getNickName());
        Image image = authService.setImageFormDatabase(user.getId());
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
        setLevelIcon("level" + user.getLevel() + ".png");
        add(horizontalLayout, image, labelName, buttonPlay);
    }


    private Button createButton(int level){
        Button button = new Button("level "+ level);
        button.addClickListener(e -> this.level = level);
        if(level > user.getLevel()) {
            button.setEnabled(false);

        }
        return button;
    }
    private void lvlView(){
        VerticalLayout verticalLayoutButtons = new VerticalLayout();
        for(int i = 1;i <= 10; i++){
            verticalLayoutButtons.add(createButton(i));

        }
        add(verticalLayoutButtons);

    }
    private void setLevelIcon(String image){
        StreamResource imageResource;
        imageResource = new StreamResource(image,
                () -> getClass().getResourceAsStream("/IconsOfLevel/" + image));
        Image imageLevel = new Image();
        imageLevel.setWidth(200,Unit.PIXELS);
        imageLevel.setHeight(200,Unit.PIXELS);
        imageLevel.setSrc(imageResource);
        add(imageLevel);

    }
    private Image setImage() {
        int id = user.getId();
        Image imageProfile = new Image();
        //imageProfile.setSrc(authService.setImageFormDatabase(id));
        return imageProfile;
    }
}
//if(imageBytes == null){
//            imageResource = new StreamResource("user",
//                    () -> authService.setImageFormDatabase(id));
//        }else{
//             imageResource = new StreamResource(imageBytes,
//                    () -> getClass().getResourceAsStream("/Images/" + image));
//        }
