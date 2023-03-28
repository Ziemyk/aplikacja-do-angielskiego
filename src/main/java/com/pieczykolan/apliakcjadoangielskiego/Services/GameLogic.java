package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.View.Game;
import com.vaadin.flow.component.UI;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameLogic extends TimerTask  {
    private GameSettings gameSettings = new GameSettings();

    private Game game ;
    private String currentPassword;
    private String currentHashPassword;
    private int currentIterationOfPassword = 0;
    static private List<String> words  ;

    //private int numberOfRounds = words.size();
    private int currentIterationOfImage = 0;

    private final UI ui;
    public boolean is = false;
    public GameLogic(Game game, UI ui) {
        this.game = game;
        this.ui = ui;
        words = new ArrayList<>();

    }
    public void setCurrentIterationOfImage(int currentNumberOfImage) {
        this.currentIterationOfImage = currentNumberOfImage;
        if(currentNumberOfImage == words.size()){
            endGame();
        }
    }
    private void endGame() {
        System.out.println("koniec gry");
    }

    public  void checkLetter(String guessLetter ) {

        if(currentPassword == null || guessLetter == null ){
            return;
        }
        int indexOfGuessWord;
        validateLetterWithPassword(guessLetter);
        if(currentHashPassword.equals(currentPassword)){
            currentIterationOfPassword++;
            startGame();
        }
    }

    private void validateLetterWithPassword(String guessLetter) {
        int indexOfGuessWord;
        String tmpCurrentPassword = currentPassword.toLowerCase();
        for(int i = 0; i < tmpCurrentPassword.length(); i++) {
            if (tmpCurrentPassword.indexOf(i) == guessLetter.charAt(0)) {
                char[] myNameChars = currentHashPassword.toCharArray();
                myNameChars[i] = guessLetter.charAt(0);
                currentHashPassword = String.valueOf(myNameChars);
                game.updatePassword(currentHashPassword);
            } else {
                setCurrentIterationOfImage(++currentIterationOfImage);
                game.setHangmanImage(currentIterationOfImage);
            }
        }
//        if (currentPassword.toLowerCase().contains(guessLetter)) {
//            indexOfGuessWord = currentPassword.indexOf(guessLetter.charAt(0));
//            char[] myNameChars = currentHashPassword.toCharArray();
//            myNameChars[indexOfGuessWord] = guessLetter.charAt(0);
//            currentHashPassword = String.valueOf(myNameChars);
//            game.updatePassword(currentHashPassword);
//        } else {
//            setCurrentIterationOfImage(++currentIterationOfImage);
//            game.setHangmanImage(currentIterationOfImage);
//        }
    }


    @Override
    public void run(){
        try {

            setCurrentIterationOfImage(++currentIterationOfImage);
            ui.access(() -> {
                game.setHangmanImage(currentIterationOfImage);
                ui.push();
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startGame()   {
        words = gameSettings.numberOfWords(gameSettings.getLevel());

        currentPassword = words.get(currentIterationOfPassword).toLowerCase();

        currentHashPassword = game.setHashPassword(currentPassword.length()).toLowerCase();




    }


}
