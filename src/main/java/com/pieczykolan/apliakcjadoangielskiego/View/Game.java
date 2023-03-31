package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.Services.GameLogic;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;


@Route("Game/level/:chosenlevel")

public class Game extends VerticalLayout implements BeforeEnterObserver {

    private String chosenLevel;
    private int level;
    private Image hangmanImage = new Image();
    private Label labelHashPassword = new Label();
    private Button buttonConfirmLetter = new Button("Confirm letter");
    private Button  buttonConfirmWord = new Button("Confirm whole word");
    private TextField textFieldLetter = new TextField("Enter the letter");
    private TextField textFieldWord =  new TextField("Entry the word");
    private Button startButton = new Button("Start");
    Label lvlLabel = new Label();
    UI ui = UI.getCurrent();
    GameLogic gameLogic;
    @Autowired
    public Game(AuthService authService) {
        // verticalLayout = new VerticalLayout();

        gameLogic = new GameLogic(Game.this,ui,authService);
        setHangmanImage(0);

        startButton.addClickListener(e -> {
            gameLogic.startGame();
        });


        textFieldLetter.addKeyPressListener(Key.ENTER,keyPressEvent ->{
            gameLogic.checkLetter(textFieldLetter.getValue());
            textFieldLetter.clear();
        });
        buttonConfirmLetter.addClickListener(e -> {
            gameLogic.checkLetter(textFieldLetter.getValue());
            textFieldLetter.clear();
        });
        textFieldWord.addKeyPressListener(Key.ENTER,keyPressEvent ->{
            gameLogic.checkWord(textFieldWord.getValue());
            textFieldWord.clear();
        });
        buttonConfirmWord.addClickListener(e -> {
            gameLogic.checkWord(textFieldWord.getValue());
            textFieldWord.clear();
        });
        //verticalLayout.add(lvlLabel,hangmanImage,startButton);
        add( lvlLabel, hangmanImage, startButton, textFieldLetter, labelHashPassword, buttonConfirmLetter,textFieldWord,buttonConfirmWord );
    }
    //KeyModifier keyModifier = new KeyEventListener<>();
    public int getLevel() {
        return level;
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
        System.out.println(chosenLevel);
        level = Integer.parseInt(chosenLevel);
        lvlLabel.setText("Level " + level);
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
        textFieldLetter.setVisible(false);
        buttonConfirmLetter.setVisible(false);
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
        //Label labelWords = new Label(words.get(0) + " , " + words.get(1));
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
}








