package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.View.Game;
import com.pieczykolan.apliakcjadoangielskiego.model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GameLogic  {
    private Game game ;
    private GameSettings gameSettings;
    private String currentPassword;
    private String currentHashPassword;
    private int currentIterationOfPassword = 0;
    static private List<String> words  ;
    private int delay = 15000, period = 15000;
    private int currentIterationOfImage = 0;
    Timer timer = new Timer();
    private int earnedPoints = 0;
    private int numberOfRounds ;
    //private boolean isWinOrLose;
    public int getCurrentIterationOfImage() {
        return currentIterationOfImage;
    }
    public int getCurrentIterationOfPassword() {
        return currentIterationOfPassword;
    }
    private final UI ui;
    private User user;
    private AuthService authService;
    private String nickName;

    public GameLogic(Game game, UI ui, AuthService authService) {
        this.game = game;
        this.ui = ui;
        this.authService = authService;
        words = new ArrayList<>();
        user = VaadinSession.getCurrent().getAttribute(User.class);

    }
    public void setCurrentIterationOfImage(int currentNumberOfImage) {
        this.currentIterationOfImage = currentNumberOfImage;
        if(currentNumberOfImage == 9){
            endGame( false);
        }
    }
    private void endGame(boolean isWinOrLose) {
        if(isWinOrLose){
            earnedPoints++;
            authService.updateDatabase(game.getLevel()+1, user.getNickName());
            game.winView(words,earnedPoints);

        }else{
            earnedPoints = 0;
            game.loseView(words,earnedPoints);
        }
        timer.cancel();
        game.stopTimer();
    }
    public void checkWord(String word) {

        if(word.equals(currentPassword)) {
            game.setListBoxOfWord(words.get(currentIterationOfPassword));
            currentIterationOfPassword++;
            game.setProgressBar(0, numberOfRounds, currentIterationOfPassword);
            restartTimer();
            game.displayNotification();
            if (currentIterationOfPassword == numberOfRounds) {
                endGame(true);
            } else {
                nextWord();
            }
        }else{
            setCurrentIterationOfImage(currentIterationOfImage+1);
            game.setHangmanImage(currentIterationOfImage);
        }
    }
    public  void checkLetter(String guessLetter ) {
        guessLetter.toLowerCase();
        if(currentPassword == null || guessLetter == null || guessLetter.isEmpty()){
            return;
        }
        validateLetterWithPassword(guessLetter);
        if(currentHashPassword.equals(currentPassword)){
            currentIterationOfPassword++;
            game.setProgressBar(0, numberOfRounds, currentIterationOfPassword);
            game.displayNotification();
            if(currentIterationOfPassword == numberOfRounds){
                endGame(true);
            }else {
                nextWord();
            }
        }
    }

    private void nextWord() {
        currentPassword = words.get(currentIterationOfPassword).toLowerCase();
        currentHashPassword = game.setHashPassword(currentPassword.length()).toLowerCase();
        timer.scheduleAtFixedRate(new Reminder(ui,game,this), delay, period );
    }

    private void validateLetterWithPassword(String guessLetter) {
        guessLetter = guessLetter.toLowerCase().toString();
        String tmpCurrentPassword = currentPassword.toLowerCase();
        if (tmpCurrentPassword.contains(guessLetter)) {
            for (int i = 0; i < tmpCurrentPassword.length(); i++) {
                if (tmpCurrentPassword.charAt(i) == guessLetter.charAt(0)) {
                    char[] myNameChars = currentHashPassword.toCharArray();
                    myNameChars[i] = guessLetter.charAt(0);
                    currentHashPassword = String.valueOf(myNameChars);
                    game.updatePassword(currentHashPassword);
                }
            }

        }else {
            setCurrentIterationOfImage(currentIterationOfImage+1);
            game.setHangmanImage(currentIterationOfImage);
        }
        restartTimer();

    }

    public void restartTimer(){
        timer.cancel();
        timer.purge();
        timer = new Timer();
        timer.scheduleAtFixedRate(new Reminder(ui,game,this), delay, period);
        game.restartTimer();

    }
    public void startGame()   {
        gameSettings = new GameSettings(game.getLevel());
        words = gameSettings.numberOfWords(gameSettings.getLevel());
        numberOfRounds = words.size();
        game.setProgressBar(0,numberOfRounds,0);
        for(int i=0 ;i<numberOfRounds; i++ ){
            System.out.println(words.get(i));
        }
        nextWord();

    }


}
