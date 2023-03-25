package com.pieczykolan.apliakcjadoangielskiego.View;

import com.nimbusds.jose.util.Resource;
import com.pieczykolan.apliakcjadoangielskiego.Services.GameLogic;
import com.pieczykolan.apliakcjadoangielskiego.Services.GameSettings;

import com.pieczykolan.apliakcjadoangielskiego.Services.Reminder;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;


import java.util.Timer;
import java.util.TimerTask;


@Route("Game/level/:chosenlevel")
public class Game extends VerticalLayout implements BeforeEnterObserver {
    //TODO tutaj odbieramy operując na klasie GameLogic prowadzimy gre wizualnie
    // ta klasa nie jest zależna od levelu ona zawsze bedzie wyświetlała to samo
    //
    private String chosenLevel;
    private int level;
    private Image hangmanImage = new Image();
    private Label label = new Label();
    Button buttonConfirm = new Button("Confirm");
    private TextField textFieldLetter = new TextField("Enter the letter");
    private String letter;
    Timer timer = new Timer();
    UI ui = UI.getCurrent();
    GameLogic gameLogic = new GameLogic(Game.this,ui);

    public Game() {
        // verticalLayout = new VerticalLayout();
        Label lvlLabel = new Label("Level " + chosenLevel);
        setHangmanImage(0);
        Button startButton = new Button("Start");
        startButton.addClickListener(e -> {
            gameLogic.startGame();
            timer.scheduleAtFixedRate(gameLogic,500,2000 );

        });
        Button b = new Button("XDD");
        b.addClickListener(e -> {

        });
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("Task Timer on Fixed Rate");
//            }
//        };
        ;
        //UI ui = UI.getCurrent();
       //timer.scheduleAtFixedRate(new Reminder(ui, this,gameLogic), 0, 2000);
       // addDetachListener(event -> timer.cancel());
        buttonConfirm.addClickListener(e -> {
            gameLogic.checkLetter(textFieldLetter.getValue());
            textFieldLetter.clear();
        });
        //TODO sprawdzić czy z tym innym timerem bedzie działo
        //verticalLayout.add(lvlLabel,hangmanImage,startButton);
        add(lvlLabel, hangmanImage, startButton, textFieldLetter, label, buttonConfirm, b);
    }




    public String setHashPassword(int numberOfWords) {
        label.setText("");
        for (int i = 0; i < numberOfWords; i++) {
            label.add("_");
            //label.add(String.valueOf(i));
        }
        return label.getText();

    }
    public void setHangmanImage(int numberOfPicture) {
        StreamResource imageResource;
        if (numberOfPicture == 1) {
            imageResource = new StreamResource("s0.jpg",
                    () -> getClass().getResourceAsStream("/hangmanImages/s0.jpg"));
        } else {
            imageResource = new StreamResource("s" + numberOfPicture + ".jpg",
                    () -> getClass().getResourceAsStream("/hangmanImages/s" +
                            numberOfPicture + ".jpg"));
        }
        hangmanImage.setSrc(imageResource);
        System.out.println(imageResource);

    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        chosenLevel = event.getRouteParameters().get("chosenlevel").orElse("1");
        level = Integer.parseInt(chosenLevel);
    }


    public void updatePassword(String currentWord) {
        label.setText(currentWord);
    }
}








