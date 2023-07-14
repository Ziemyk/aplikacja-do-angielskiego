package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.Component.KeyboardComponent;
import com.pieczykolan.apliakcjadoangielskiego.Component.SecondsCounter;
import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.GameService.GameLogic;

import com.pieczykolan.apliakcjadoangielskiego.model.TranslateWord;
import com.pieczykolan.apliakcjadoangielskiego.repo.GameSetupRepo;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;

import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
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
    private final ProgressBar progressBar =  new ProgressBar();
    private KeyboardComponent keyboardComponent;
    private SecondsCounter secondsCounter = new SecondsCounter();


    private List<TranslateWord> words = new ArrayList<>();


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
    public Game(AuthService authService,GameSetupRepo gameSetupRepo) {
        gameLogic = new GameLogic(Game.this,ui,authService, gameSetupRepo);
        keyboardComponent = new KeyboardComponent(gameLogic);
        keyboardComponent.test ++;
        System.out.println(keyboardComponent.test);
        setHangmanImage(0);
        progressBar.setValue(0);
        setClassName();
        startButton.addClickListener(e -> {
            secondsCounter.startCounter();
            gameLogic.startGame();
            startButton.setEnabled(false);
        });
        textFieldWord.addKeyPressListener(Key.ENTER, keyPressEvent ->{
            gameLogic.checkWord(textFieldWord.getValue());
            textFieldWord.clear();
        });
        buttonConfirmWord.addClickListener(e -> {
            gameLogic.checkWord(textFieldWord.getValue());
            textFieldWord.clear();
        });
        iconOfWord.setSrc("data:image/png;base64,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");
        iconOfWord.getStyle().set("opacity","0");
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
        lvlLabel.setText("Level: " + level+" || " + "type: " + type);
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
        dialogWin.getFooter().add(buttonMenu, buttonNextLevel);
        dialogWin.setModal(false);
        add(dialogWin);
        dialogWin.open();

    }
    public void disableComponents(){
        //textFieldWord.setVisible(false);
        //buttonConfirmWord.setVisible(false);
        startButton.setVisible(false);
        //labelHashPassword.setVisible(false);

    }
    private void passToNextLevel() {
        level = level + 1;
        UI.getCurrent().getPage().setLocation("Game/type/"+ type + "/level/"+ level);
    }
    private void passToTheSameLevel() {
        level = level;
        UI.getCurrent().getPage().setLocation("Game/type/"+ type + "/level/"+ level);
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
    private final ComponentRenderer<Component, TranslateWord> wordCardRenderer = new ComponentRenderer<>(
            word -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);
                cardLayout.addClassName("words");
                cardLayout.add(new Div( new Text(word.getWord())));
                cardLayout.add(new Div( new Text(" - ")));
                cardLayout.add(new Div( new Text(word.getTranslateWord())));
                return cardLayout;
            }
    );
    public void displayNotification(String word, String translateWord) {
        Notification.show("Word guessed. Congratulations !!!");
        VirtualList<TranslateWord> guessedWord = new VirtualList<>();
        words.add(new TranslateWord(word,translateWord));
        guessedWord.setRenderer(wordCardRenderer);
        guessedWord.setItems(words);
        guessedWord.addClassName("wordsLayout");
        add(guessedWord);
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
        iconOfWord.getStyle().set("opacity","1");

    }

    public void setVirtualKeyboard() {
        keyboardComponent.restartKeyboard();
    }
}
