package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.Layout.MainLayout;
import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.Entity.LevelsEntity;
import com.pieczykolan.apliakcjadoangielskiego.model.TypeOfWord;
import com.pieczykolan.apliakcjadoangielskiego.Entity.User;
import com.pieczykolan.apliakcjadoangielskiego.model.Users;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Route(value = "EnglishStudy")
@CssImport("themes/my-theme/mainPageStyle.css")
public class MainPage extends VerticalLayout  {
    private User user ;
    static public int currentLevel = 1;
    public TypeOfWord currentTypeOfWord;
    public final List<Integer> arrayOfTypes = new ArrayList<>();
    private final List<Image> arrayOfIconsLevel = new ArrayList<>();
    private final List<Label> arrayOfLabelType = new ArrayList<>();
    private final List<TypeOfWord> arrayOfTypeWord =  new ArrayList<>();

    private List<User> arrayOfUser = new ArrayList<>();
    private List<LevelsEntity> arrayOfLevels = new ArrayList<>();
    private Optional<LevelsEntity> levelsEntity;
    AuthService authService;
    //Layouts
    private final MainLayout mainLayout = new MainLayout();
    private final VerticalLayout verticalLayoutUserView = new VerticalLayout();
    HorizontalLayout horizontalLayoutBoardLevel = new HorizontalLayout();
    HorizontalLayout horizontalLayoutUsernameAvatar = new HorizontalLayout();
    HorizontalLayout horizontalLayoutCrudAndUserView = new HorizontalLayout();
    private VerticalLayout verticalLayoutForChosen = new VerticalLayout();
    //Components
    private final Label labelUsername = new Label();

    private Image avatarImage = new Image();
    private Button buttonLogout = new Button("Wyloguj się");
    private Button buttonPlay = new Button("Start");
    private Image iconLevel = new Image();
    private Label labelChosenType = new Label("Nie wybrałeś jeszcze żadnego trybu");
    private Label labelChosenLevel = new Label("Nie wybrałeś jeszcze żadnego poziomu");
    private Crud<Users> crud;


    @Autowired
    public MainPage(AuthService authService)  {
        this.authService = authService;
        user = VaadinSession.getCurrent().getAttribute(User.class);
        levelsEntity = authService.getLevelsEntity(user.getId());
        user = authService.updateData(user.getNickName());
        setArrayOfLevels();
        setCrud();
        Notification.show("Hello " + user.getNickName()).setPosition(Notification.Position.TOP_START);
        setMainLayout();
        setHorizontalLayoutCrudAndUserView();
        userView();
        lvlView();
        add(mainLayout, horizontalLayoutCrudAndUserView,horizontalLayoutBoardLevel);
        //TODO wystylizowac mainPage zaokrąglic zdj, poprawić layouty itp
        // rozważyć zrobienie konta admin dla nauczyciel aktóry bedzie mógł oglądac wyniki swoich uczniów


    }
    public void setMainLayout(){
        mainLayout.addToNavbar( buttonLogout);
    }
    public void setHorizontalLayoutCrudAndUserView(){
        horizontalLayoutCrudAndUserView.add(crud,verticalLayoutUserView);
        setHorizontalComponentAlignment(Alignment.START,crud);
    }
    public void setCrud(){
        crud = new Crud<>(Users.class,createGrid(), creatEditor());
        crud.setDataProvider(setArrayOfCrudUsers());
        crud.setToolbarVisible(false);
        crud.setHeight(270, Unit.PIXELS);

    }
    public ListDataProvider<Users> setArrayOfCrudUsers(){
        List<Users> arrayOfCrudUsers = new ArrayList<>();
        arrayOfUser = authService.getAllUsers();
        arrayOfLevels = authService.getAllLevels();
        for(int i=0;i<arrayOfUser.size();i++){
            Users users = new Users();
            users.setUsername(arrayOfUser.get(i).getNickName());
            users.setUserAvatar(authService.setImageFormDatabase(arrayOfUser.get(i).getId()));
            users.setVerb(arrayOfLevels.get(i).getLevelOfVerb());
            users.setNoun(arrayOfLevels.get(i).getLevelOfNoun());
            users.setAdjective(arrayOfLevels.get(i).getLevelOfAdjective());
            users.setAdverbial(arrayOfLevels.get(i).getLevelOfAdverbial());
            arrayOfCrudUsers.add(users);
            arrayOfCrudUsers.get(i).getUserAvatar().addClassName("user-avatar-crud");
        }
        return new ListDataProvider<>(arrayOfCrudUsers);
    }
    private CrudEditor<Users> creatEditor() {
        Label username =  new Label("Nazwa Użytkownika");
        Image userAvatar = new Image();
        //userAvatar.addClassName("user-avatar-crud");
        Label verb = new Label("Czasowniki");
        Label noun= new Label("Rzeczowniki");
        Label adjective = new Label("Przymiotniki");
        Label adverbial = new Label("Okoliczniki");
        FormLayout form = new FormLayout(username,userAvatar,verb,noun,adjective,adverbial);
        Binder<Users> binder = new Binder<>(Users.class);

        return new BinderCrudEditor<>(binder, form);

    }

    private Grid<Users> createGrid() {
        Grid<Users> grid = new Grid<>();
        grid.setWidth("700px");
        grid.addColumn(Users::getUsername).setHeader("Nazwa Użytkownika").setSortable(true)
            .setWidth("50px");
        grid.addComponentColumn(Users::getUserAvatar).setHeader("Avatar")
                .setWidth("50px");
        grid.addColumn(Users::getVerb).setHeader("Czasownik").setSortable(true)
                .setWidth("50px");
        grid.addColumn(Users::getNoun).setHeader("Rzeczownik").setSortable(true)
                .setWidth("50px");
        grid.addColumn(Users::getAdjective).setHeader("Przymiotnik").setSortable(true)
                .setWidth("50px");
        grid.addColumn(Users::getAdverbial).setHeader("Okolicznik").setSortable(true)
                .setWidth("50px");
        Crud.addEditColumn(grid);
        return grid;
}

    public void userView() {
        setHorizontalLayoutUsernameAvatar();
        buttonLogout.addClickListener(e -> {
            getUI().get().getSession().close();
            UI.getCurrent().getPage().setLocation("/");
            VaadinSession.getCurrent().setAttribute(User.class, null);

        });
        verticalLayoutUserView.setWidth("30%");
        setHorizontalComponentAlignment(Alignment.END,verticalLayoutUserView);
        verticalLayoutUserView.add(horizontalLayoutUsernameAvatar);
        setArrayOfIconsLevel();
        setClassName();
        setVisualAspects();
    }
    public void setArrayOfIconsLevel(){

        arrayOfIconsLevel.add(setLevelIcon("level" + levelsEntity.get().getLevelOfVerb() + ".png"));
        arrayOfIconsLevel.add(setLevelIcon("level" + levelsEntity.get().getLevelOfNoun() + ".png"));
        arrayOfIconsLevel.add(setLevelIcon("level" + levelsEntity.get().getLevelOfAdjective() + ".png"));
        arrayOfIconsLevel.add(setLevelIcon("level" + levelsEntity.get().getLevelOfAdverbial() + ".png"));
        arrayOfLabelType.add(new Label("  CZASOWNIKI  " + " "));
        arrayOfLabelType.add(new Label(" RZECZOWNIKI  " + " "));
        arrayOfLabelType.add(new Label("PRZYMIOTNIKI  "));
        arrayOfLabelType.add(new Label(" OKOLICZNIKI  " + " "));
        for(int i=0;i<4;i++) {
            HorizontalLayout horizontalLayoutForLevelIconAndType = new HorizontalLayout();
            arrayOfIconsLevel.get(i).setClassName("icon-level");
            arrayOfLabelType.get(i).setClassName("label-type");
            horizontalLayoutForLevelIconAndType.add(arrayOfLabelType.get(i),new Icon(VaadinIcon.ARROW_RIGHT), arrayOfIconsLevel.get(i));
            verticalLayoutUserView.add(horizontalLayoutForLevelIconAndType);
        }
    }
    public void setHorizontalLayoutUsernameAvatar(){
        avatarImage = authService.setImageFormDatabase(user.getId());
        labelUsername.setText(user.getNickName());
        horizontalLayoutUsernameAvatar.add(labelUsername,avatarImage);
    }
    private void lvlView() {
        verticalLayoutForChosen.add(labelChosenType,labelChosenLevel);
        setArrayOfTypeWord();
        horizontalLayoutBoardLevel.add(verticalLayoutForChosen);
        for (int typeOfWord = 0; typeOfWord < 4; typeOfWord++) {
            Board levelBoard = new Board();
            Row row = new Row();
            String wordType =arrayOfTypeWord.get(typeOfWord).toString();
            row.add(createCellText(wordType));
            row.setClassName("row");
            levelBoard.add(row);
            for (int level = 1; level <= 5; level++) {
                levelBoard.add(createCellButton(createButton(wordType,level, typeOfWord)));
            }
            horizontalLayoutBoardLevel.add(levelBoard);
        }
        buttonPlay.addClickListener(e ->{
            UI.getCurrent().getPage().setLocation("Game/type/"+ currentTypeOfWord.toString() +"/level/"+ currentLevel);
            e.getSource().setVisible(false);
        });
        horizontalLayoutBoardLevel.add(buttonPlay);

    }
    public void setArrayOfLevels(){
        arrayOfTypes.add(levelsEntity.get().getLevelOfVerb());
        arrayOfTypes.add(levelsEntity.get().getLevelOfNoun());
        arrayOfTypes.add(levelsEntity.get().getLevelOfAdjective());
        arrayOfTypes.add(levelsEntity.get().getLevelOfAdverbial());
    }
    public void setArrayOfTypeWord(){
        arrayOfTypeWord.add(TypeOfWord.VERB);
        arrayOfTypeWord.add(TypeOfWord.NOUN);
        arrayOfTypeWord.add(TypeOfWord.ADJECTIVE);
        arrayOfTypeWord.add(TypeOfWord.ADVERBIAL);
    }
    private Button createButton(String wordType ,int level, int typeOfWord){
        Button button = new Button("Poziom : " + level);
        button.addClassName("level-button");
        //button.getStyle().set("color", "black");
        button.addClickListener(e -> {
            this.currentLevel = level;
            this.currentTypeOfWord = TypeOfWord.values()[typeOfWord];
            labelChosenType.setText("Wybrałeś tryb: " + wordType);
            labelChosenLevel.setText("Wybrałeś poziom: " + level);

        });
        if(level > arrayOfTypes.get(typeOfWord)) {
            button.setEnabled(false);
        }
        return button;
    }


    public void setClassName(){
        avatarImage.setClassName("avatar-image");
        iconLevel.setClassName("icon-level");
        buttonLogout.setClassName("button-logout");
        buttonPlay.setClassName("button-play");
        labelUsername.setClassName("label-name");
        verticalLayoutUserView.setClassName("vertical-user-view");
        horizontalLayoutBoardLevel.setClassName("horizontal-board-level");
        labelChosenType.setClassName("labelChosenType");
        labelChosenLevel.setClassName("labelChosenLevel");
        verticalLayoutForChosen.setClassName("verticalForChosen");

    }

    public int getLevel() {
        return currentLevel;
    }
    public void setLevel(int level) {

        this.currentLevel = level;
    }
    public void setVisualAspects(){
        buttonLogout.setIcon(new Icon(VaadinIcon.SIGN_OUT));
        buttonPlay.setIcon(new Icon(VaadinIcon.PLAY));
    }

    private static Component createCellText(String text) {
        Div div = new Div();
        div.setText(text);
        div.addClassNames("cellText");

        return div;
    }
    private static Component createCellButton(Button b) {
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

