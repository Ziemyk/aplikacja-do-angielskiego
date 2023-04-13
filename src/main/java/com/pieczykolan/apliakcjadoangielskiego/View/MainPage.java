package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import org.springframework.beans.factory.annotation.Autowired;
//@PageTitle("EnglishStudy")
//@CssImport("./styles/shared-styles.css")


@Route("EnglishStudy")
public class MainPage extends VerticalLayout  {
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
        user = VaadinSession.getCurrent().getAttribute(User.class);
        //getElement().getStyle().set("background","gray");
        add(userView(),lvlView());

        //userView();
        //lvlView();
    }
//    public HorizontalLayout horizontallayout(){
//       user = authService.updateData(user.getNickName());
//       HorizontalLayout horizontalLayout = new HorizontalLayout();
//       horizontalLayout.add(setLevelIcon("level" + user.getLevel() + ".png"),userView());
//       setHorizontalComponentAlignment(Alignment.END,userView());
//       //horizontalLayout.setSpacing(true);
//       return horizontalLayout;
//    }

    public VerticalLayout userView() {
        VerticalLayout verticalLayout = new VerticalLayout();
        user = authService.updateData(user.getNickName());
        Notification.show("Hello " + user.getNickName()).setPosition(Notification.Position.TOP_STRETCH);
        Label labelName = new Label(user.getNickName());
        labelName.getStyle().set("font-weight","bold");
        Image image = authService.setImageFormDatabase(user.getId());
        image.setHeight(200, Unit.PIXELS);
        image.setWidth(200, Unit.PIXELS);


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
        Image iconLevel = setLevelIcon("level" + user.getLevel() + ".png");
        Tooltip tooltip ;
        HorizontalLayout horizontalLayoutIcons = new HorizontalLayout();
        horizontalLayoutIcons.add(iconLevel,image);
        HorizontalLayout horizontalLayoutButtons = new HorizontalLayout();
        horizontalLayoutButtons.add(buttonAchievements, buttonAccount, buttonLogout);
        setHorizontalComponentAlignment(Alignment.END, horizontalLayoutIcons, labelName, horizontalLayoutButtons);
        setHorizontalComponentAlignment(Alignment.START,iconLevel);
        //setHorizontalComponentAlignment(Alignment.BASELINE, buttonLogout);
        setHorizontalComponentAlignment(Alignment.CENTER, buttonPlay);
       // setLevelIcon("level" + user.getLevel() + ".png");
        verticalLayout.add(horizontalLayoutButtons, horizontalLayoutIcons, labelName, buttonPlay);
        return verticalLayout;
    }


    private Button createButton(int level){
        Button button = new Button("Level" + level);
        button.setHeight(80,Unit.PIXELS);
        button.setWidth(200,Unit.PIXELS);
        button.addClickListener(e -> this.level = level);
        if(level > user.getLevel()) {
            button.setEnabled(false);

        }
        return button;
    }
    private Scroller lvlView(){
        Section levelSection = new Section();
        levelSection.add(new H3("Levels"));
        for(int i = 1;i <= 10; i++){
            levelSection.add(createButton(i));
        }
        Scroller scrollerButtons = new Scroller(new Div(levelSection));
        scrollerButtons.setHeight(500,Unit.PIXELS);
        scrollerButtons.setWidth(450,Unit.PIXELS);
        scrollerButtons.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scrollerButtons.getStyle()
                .set("border-bottom", "1px soli  d var(--lumo-contrast-20pct)")
                .set("padding", "var(--lumo-space-m)");
        return scrollerButtons;

    }
    private Image setLevelIcon(String image){
        StreamResource imageResource;
        imageResource = new StreamResource(image,
                () -> getClass().getResourceAsStream("/IconsOfLevel/" + image));
        Image imageLevel = new Image();
        imageLevel.setWidth(100,Unit.PIXELS);
        imageLevel.setHeight(100,Unit.PIXELS);
        imageLevel.setSrc(imageResource);
        return imageLevel;

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
