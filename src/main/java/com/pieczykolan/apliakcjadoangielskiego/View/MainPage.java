package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.MainView.MainLayout;
import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.model.TypeOfWord;
import com.pieczykolan.apliakcjadoangielskiego.model.User;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "EnglishStudy")
@CssImport("themes/my-theme/mainPageStyle.css")
public class MainPage extends VerticalLayout  {
    private User user ;
    static public int level = 1;
    public TypeOfWord type;
    AuthService authService;
    //Layouts
    private MainLayout mainLayout = new MainLayout();
    private VerticalLayout verticalLayoutUserView = new VerticalLayout();
    HorizontalLayout horizontalLayoutBoardLevel = new HorizontalLayout();
    //Components
    private Label labelUsername = new Label();
    private Image avatarImage = new Image();
    private Button buttonLogout = new Button("Log out");
    private Button buttonAccount = new Button("My Account");
    private Button buttonAchievements = new Button("My Achievements");
    private Button buttonPlay = new Button("Play");
    private Image iconLevel = new Image();
    


    @Autowired
    public MainPage(AuthService authService)  {
        this.authService = authService;
        user = VaadinSession.getCurrent().getAttribute(User.class);
        user = authService.updateData(user.getNickName());
        Notification.show("Hello " + user.getNickName()).setPosition(Notification.Position.TOP_START);
        setMainLayout();
        userView();
        lvlView();
        add(mainLayout,verticalLayoutUserView,horizontalLayoutBoardLevel);
        //TODO do wyswietlania tablicy wyników napewno nada sie CRUD ten board tez juz działa i to moze sie tez przydać
        // do układu componentów na stronach
    }
    public void setMainLayout(){
        mainLayout.addToNavbar( buttonLogout);
    }
    public void userView() {
        avatarImage = authService.setImageFormDatabase(user.getId());
        //iconLevel = setLevelIcon("level" + user.getLevel() + ".png");
        labelUsername.setText(user.getNickName());
        buttonLogout.addClickListener(e -> {
            UI.getCurrent().getSession().close();
            UI.getCurrent().getPage().setLocation("/");
            VaadinSession.getCurrent().setAttribute(User.class, null);
        });
        verticalLayoutUserView.setWidth("30%");
        setHorizontalComponentAlignment(Alignment.END,verticalLayoutUserView);
        verticalLayoutUserView.add(labelUsername,iconLevel, avatarImage);
        setClassName();
        setVisualAspects();
    }
    private void lvlView() {

        for (int typeOfWord = 0; typeOfWord < 4; typeOfWord++) {
            Board levelBoard = new Board();
            Row row = new Row();
            row.add(createCellText("typ"));
            levelBoard.add(row);
            for (int level = 1; level <= 5; level++) {
                levelBoard.add(createCellButton(createButton(level, typeOfWord)));
            }
            horizontalLayoutBoardLevel.add(levelBoard);
        }
        buttonPlay.addClickListener(e ->{
            UI.getCurrent().getPage().setLocation("Game/type/"+ type.toString() +"/level/"+ level);
            e.getSource().setVisible(false);
        });
        horizontalLayoutBoardLevel.add(buttonPlay);

    }

    public void setClassName(){
        avatarImage.setClassName("avatar-image");
        iconLevel.setClassName("icon-level");
        buttonLogout.setClassName("button-logout");
        buttonPlay.setClassName("button-play");
        labelUsername.setClassName("label-name");
        verticalLayoutUserView.setClassName("vertical-user-view");
        horizontalLayoutBoardLevel.setClassName("horizontal-board-level");


    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {

        this.level = level;
    }
    public void showRouterLayoutContent(HasElement content){

    }
    public void setVisualAspects(){
        buttonLogout.setIcon(new Icon(VaadinIcon.SIGN_OUT));
        buttonPlay.setIcon(new Icon(VaadinIcon.PLAY));
    }
    private Button createButton(int level, int typeOfWord){
        Button button = new Button("Level" + level);
        button.addClickListener(e -> {
            this.level = level;
            this.type = TypeOfWord.values()[typeOfWord];
        });
       // if(level > user.getLevel()) {
       //     button.setEnabled(false);
     //   }
        return button;
    }

    private static Div createCellText(String text) {
        Div div = new Div();
        div.setText(text);
        div.addClassNames("cellText");

        return div;
    }
    private static Div createCellButton(Button b) {
        Div div = new Div();
        div.add(b);
        div.addClassNames("cellButton");

        return div;
    }
    private Image setLevelIcon(String image){
        StreamResource imageResource;
        imageResource = new StreamResource(image,
                () -> getClass().getResourceAsStream("/IconsOfLevel/" + image));
        Image imageLevel = new Image();
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

