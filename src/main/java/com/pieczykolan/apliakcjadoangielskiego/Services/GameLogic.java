package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.View.Game;
import com.pieczykolan.apliakcjadoangielskiego.View.MainPage;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class GameLogic extends TimerTask{
    private GameSettings gameSettings = new GameSettings();

    private Game game ;
    private String currentPassword;
    private String hashPassword;
    private int currentNumberOfPassword = 0;
    static private List<String> words  ;
    //private int numberOfRounds = words.size();
    private int currentNumberOfImage = 0;
    Timer timer;
    private final UI ui;
    public boolean is = false;
    public GameLogic(Game game, UI ui) {
        this.game = game;
        this.ui = ui;

        words = new ArrayList<>();

        //new Reminder(10);
        //taskReminder.scheduledExecutionTime();
        //game.setHangmanImage(currentNumberOfImage);
    }

    public  void checkLetter(String guessLetter ) {

        if(currentPassword == null || guessLetter == null ){
            return;
        }
        int indexOfGuessWord;
        if(currentPassword.toLowerCase().contains(guessLetter)){
              indexOfGuessWord = currentPassword.indexOf(guessLetter.charAt(0));
              char[] myNameChars = hashPassword.toCharArray();
              myNameChars[indexOfGuessWord] = guessLetter.charAt(0);
              hashPassword = String.valueOf(myNameChars);
              game.updatePassword(hashPassword);
        }else{
            currentNumberOfImage += 1;
            game.setHangmanImage(currentNumberOfImage);
        }
        if(hashPassword.equals(currentPassword)){
            currentNumberOfPassword++;
            startGame();
        }
    }
    @Override
    public void run(){
        try {
            //System.out.println("Task Timer on Fixed Rate");
            Thread.sleep(500);
            currentNumberOfImage =+ 1;
            ui.access(() -> {
                System.out.println("Task Timer on Fixed Rate");
                //game.getUI();
                //game.setHangmanImage(3);
                game.setHashPassword(5);
                //game.getUI().get().push();
                //ui.push();
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startGame()   {
        words = gameSettings.numberOfWords(gameSettings.getLevel());
        // for(int i= 0; i<words.size();i++) {
        currentPassword = words.get(currentNumberOfPassword).toLowerCase();

        hashPassword = game.setHashPassword(currentPassword.length()).toLowerCase();

//

//        game.addAttachListener(e -> {
//            UI ui = e.getUI();
//            timer.schedule(new Reminder(ui, game),0,2000);
//        });
//        game.addDetachListener(e -> timer.cancel());
//        timer.schedule(new TimerTask() {
//            public void run() {
//                currentNumberOfPassword++;
//                game.setHangmanImage(currentNumberOfImage);
//            }
//        },0,5*1000);
            //checkLetter(currentPassword);



      //  }
    }


}
