package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.MainView.KeyboardComponent;
import com.pieczykolan.apliakcjadoangielskiego.MainView.SecondsCounter;
import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.Services.GameLogic;

import com.pieczykolan.apliakcjadoangielskiego.repo.GameSetupRepo;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.atmosphere.config.service.EndpointMapperService;
import org.springframework.beans.factory.annotation.Autowired;


import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;


@Route("Game/type/:type/level/:chosenlevel")
@CssImport("themes/my-theme/game.css")
public class Game extends VerticalLayout implements BeforeEnterObserver {
    private String chosenLevel;
    private int level;
    private String type;
    private Image hangmanImage = new Image();
    private Label labelHashPassword = new Label();
    private Button buttonConfirmWord = new Button("Confirm whole word");
    private Button buttonNextWord = new Button("Next Word");
    private TextField textFieldWord =  new TextField("Entry the word");
    private Image iconOfWord = new Image();
    private Button startButton = new Button("Start");
    private ListBox<String> listBoxOfWords = new ListBox<>();
    private ProgressBar progressBar =  new ProgressBar();
    private KeyboardComponent keyboardComponent;
    private SecondsCounter secondsCounter = new SecondsCounter();
    Label lvlLabel = new Label();
    UI ui = UI.getCurrent();
    GameLogic gameLogic;
    HorizontalLayout horizontalLayout= new HorizontalLayout();
    HorizontalLayout horizontalLayoutForTextAndButton = new HorizontalLayout();
    private VerticalLayout verticalLayoutForGuessedWords = new VerticalLayout();
    private VerticalLayout verticalLayoutForStartAndTimer = new VerticalLayout();

    private AppLayout appLayout = new AppLayout();
    private H1 header = new H1();

    @Autowired
    public Game(AuthService authService, GameSetupRepo gameSetupRepo) {
        gameLogic = new GameLogic(Game.this,ui,authService,gameSetupRepo);
        keyboardComponent = new KeyboardComponent(gameLogic);
        setHangmanImage(0);
        progressBar.setValue(0);
        setClassName();
        startButton.addClickListener(e -> {
            secondsCounter.startCounter();
            gameLogic.startGame();
            startButton.setEnabled(false);
        });
        textFieldWord.addKeyPressListener(Key.ENTER,keyPressEvent ->{
            gameLogic.checkWord(textFieldWord.getValue());
            textFieldWord.clear();
        });
        buttonConfirmWord.addClickListener(e -> {
            gameLogic.checkWord(textFieldWord.getValue());
            textFieldWord.clear();
        });
        iconOfWord.setSrc("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAACoCAMAAABt9SM9AAAAA1BMVEWJz/C4QSRIAAAAR0lEQVR4nO3BAQEAAACCIP+vbkhAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAO8GxYgAAb0jQ/cAAAAASUVORK5CYII=");
        horizontalLayoutForTextAndButton.add(textFieldWord, buttonConfirmWord);
        verticalLayoutForStartAndTimer.add(secondsCounter,startButton);
        horizontalLayout.add(iconOfWord,hangmanImage,verticalLayoutForStartAndTimer);
        setHeader();
        add( appLayout, horizontalLayout, labelHashPassword, horizontalLayoutForTextAndButton, keyboardComponent, progressBar );

    }


    public void setClassName(){
        hangmanImage.setClassName("hangmanImage");
        keyboardComponent.setClassName("keyboard");
        secondsCounter.setClassName("secondCounter");
        listBoxOfWords.setClassName("guessedWords");
        startButton.setClassName("startButton");
        lvlLabel.setClassName("lvlLabel");
        labelHashPassword.setClassName("labelHashPassword");
        buttonConfirmWord.setClassName("buttonConfirmWord");
        progressBar.setClassName("progressBar");
        textFieldWord.setClassName("textFieldLetter");
        listBoxOfWords.setClassName("listBoxOfWords");
        iconOfWord.setClassName("iconOfWord");
        horizontalLayoutForTextAndButton.setClassName("horizontalLayoutForTextAndButton");
        verticalLayoutForStartAndTimer.setClassName("verticalLayoutForStartAndTimer");
        horizontalLayout.setClassName("horizontalLayout");
        appLayout.setClassName("appLayout");
        header.setClassName("header");


    }
    public void setHeader(){
        header.setText("English Study");
        appLayout.addToNavbar(lvlLabel,header);
    }
    public String getType() {
        return type;
    }
    public int getLevel() {
        return level;
    }
    public void setProgressBar(int min, int max, int value){
        progressBar.setMin(min);
        progressBar.setMax(max);
        progressBar.setValue(value);

    }
    public String setHashPassword(int numberOfWords) {
        labelHashPassword.setText("");
        for (int i = 0; i < numberOfWords; i++) {
            labelHashPassword.add("_");
            //label.add(String.valueOf(i));
        }
        return labelHashPassword.getText();
    }
    public void setHangmanImage(int numberOfPicture) {
        StreamResource imageResource;
        if (numberOfPicture == 0) {
            imageResource = new StreamResource("s0.jpg",
                    () -> getClass().getResourceAsStream("/hangmanImages/s0.jpg"));
        } else {
            imageResource = new StreamResource("s" + numberOfPicture + ".jpg",
                    () -> getClass().getResourceAsStream("/hangmanImages/s" +
                            numberOfPicture + ".jpg"));
        }
        hangmanImage.setSrc(imageResource);
        //System.out.println(imageResource);
    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        chosenLevel = event.getRouteParameters().get("chosenlevel").orElse("1");
        level = Integer.parseInt(chosenLevel);
        type = event.getRouteParameters().get("type").orElse("NOUN");
        lvlLabel.setText("Level " + level + "type " + type);
    }


    public void updatePassword(String currentWord) {
        labelHashPassword.setText(currentWord);
    }

    public void winView(List<String> words, int earnedPoints) {
        disableComponents();
        Dialog dialogWin = new Dialog();
        dialogWin.setHeight(300, Unit.PIXELS);
        dialogWin.setWidth(300,Unit.PIXELS);
        dialogWin.setHeaderTitle("Win");
        VerticalLayout verticalLayout = new VerticalLayout();
        Label labelLvl = new Label("Przeszedłeś level "+ level +"\n");
        Label labelWords = new Label(words.get(0) + " , " + words.get(1));
        verticalLayout.add(labelLvl,labelWords);
        Button buttonNextLevel = new Button("Next level", e -> passToNextLevel());
        Button buttonMenu = new Button("Menu", e -> UI.getCurrent().getPage().setLocation("EnglishStudy"));
        dialogWin.add(verticalLayout);
        dialogWin.getFooter().add(buttonMenu,buttonNextLevel);
        dialogWin.setModal(false);
        add(dialogWin);
        dialogWin.open();

    }
    public void disableComponents(){
        textFieldWord.setVisible(false);
        buttonConfirmWord.setVisible(false);
        startButton.setVisible(false);
        labelHashPassword.setVisible(false);

    }
    private void passToNextLevel() {
        level = level + 1;
        UI.getCurrent().getPage().setLocation("Game/level/"+ level);
    }
    private void passToTheSameLevel() {
        level = level;
        UI.getCurrent().getPage().setLocation("Game/level/"+ level);
    }
    public void loseView(List<String> words, int earnedPoints) {
        Dialog dialogWin = new Dialog();
        dialogWin.setHeight(300, Unit.PIXELS);
        dialogWin.setWidth(300,Unit.PIXELS);
        dialogWin.setHeaderTitle("Lose");
        VerticalLayout verticalLayout = new VerticalLayout();
        Label labelLvl = new Label("Nie udało sie spróbuj ponownie.");
        verticalLayout.add(labelLvl);
        Button buttonTryAgain = new Button("Try Again",e -> passToTheSameLevel());
        Button buttonMenu = new Button("Menu", e -> UI.getCurrent().getPage().setLocation("EnglishStudy"));
        dialogWin.add(verticalLayout);
        dialogWin.getFooter().add(buttonMenu,buttonTryAgain);
        dialogWin.setModal(false);
        add(dialogWin);
        dialogWin.open();
    }

    public void displayNotification() {
        Notification.show("Word guessed. Congratulations !!!");
    }

    public void setListBoxOfWord(String guessedWord) {
        //listBoxOfWords.add(new Label(guessedWord));
        verticalLayoutForGuessedWords.add(guessedWord);
        listBoxOfWords.add(verticalLayoutForGuessedWords);
        //TODO chyba zrobimy to tak ze te słowa wpisze odrazu i przestawie je na disable i
        // w momencie odgadnie przestawie na visible

    }

    public void restartTimer() {
        secondsCounter.restartCounter();
    }

    public void stopTimer(){
        secondsCounter.stopCounter();
    }

    public void setWordIcon(byte[] bytes){
        StreamResource streamResource = new StreamResource("word",() -> {
            return new ByteArrayInputStream(bytes);
        } );
        iconOfWord.setSrc(streamResource);
    }

    public void setVirtualKeyboard() {
        keyboardComponent.restartKeyboard();
    }
}
