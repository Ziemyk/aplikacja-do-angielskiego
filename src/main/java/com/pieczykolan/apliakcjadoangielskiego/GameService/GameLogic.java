package com.pieczykolan.apliakcjadoangielskiego.GameService;

import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.View.Game;
import com.pieczykolan.apliakcjadoangielskiego.Entity.GameSetup;
import com.pieczykolan.apliakcjadoangielskiego.Entity.LevelsEntity;
import com.pieczykolan.apliakcjadoangielskiego.Entity.User;
import com.pieczykolan.apliakcjadoangielskiego.repo.GameSetupRepo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.stream.Collectors;

public class GameLogic  {
    private Game game ;
    private GameSettings gameSettings;
    private String currentPassword;
    private String currentHashPassword;
    private int currentIterationOfPassword = 0;
    static private List<GameSetup> listGameSetup;
    static private List<String> words  ;
    static private List<byte []> iconsOfWords;
    static private List<String> translateWord;
    private int delay = 15000, period = 15000;
    private int currentIterationOfImage = 0;
    Timer timer = new Timer();
    private int earnedPoints = 0;
    private int numberOfRounds ;
    //private boolean isWinOrLose;

    private final UI ui;
    private User user;
    private AuthService authService;
    private String nickName;



    private GameSetupRepo gameSetupRepo;

    public GameLogic(Game game, UI ui, AuthService authService,GameSetupRepo gameSetupRepo) {
        this.game = game;
        this.ui = ui;
        this.authService = authService;
        this.gameSetupRepo = gameSetupRepo;

        words = new ArrayList<>();
        user = VaadinSession.getCurrent().getAttribute(User.class);
    }
    public int getCurrentIterationOfImage() {
        return currentIterationOfImage;
    }
    public int getCurrentIterationOfPassword() {
        return currentIterationOfPassword;
    }
    public void setCurrentIterationOfImage(int currentNumberOfImage) {
        this.currentIterationOfImage = currentNumberOfImage;
        if(currentNumberOfImage == 9){
            endGame( false);
        }
    }
    private void endGame(boolean isWinOrLose) {
        timer.cancel();
        game.stopTimer();
        if(isWinOrLose){
            earnedPoints++;
            authService.updateLevelsEntity(user.getId(), updateLevelInLevelsEntity(game.getType()));
            game.winView(words,earnedPoints);

        }else{
            earnedPoints = 0;
            game.loseView(words,earnedPoints);
        }

    }
    public void checkWord(String word) {

        if(word.equals(currentPassword)) {
            game.setListBoxOfWord(words.get(currentIterationOfPassword));
            currentIterationOfPassword++;
            game.setProgressBar(0, numberOfRounds, currentIterationOfPassword);
            restartTimer();
            game.displayNotification(currentPassword, translateWord.get(currentIterationOfPassword-1));
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
            game.displayNotification(currentPassword, translateWord.get(currentIterationOfPassword-1));
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
        game.setWordIcon(iconsOfWords.get(currentIterationOfPassword));
        restartTimer();
        game.setVirtualKeyboard();

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
        gameSettings = new GameSettings(game.getLevel(),game.getType(),gameSetupRepo);
        listGameSetup = gameSettings.getGameSetup(game.getLevel());
        words = listGameSetup.stream().map(gameSetup -> gameSetup.getWord()).collect(Collectors.toList());
        iconsOfWords = listGameSetup.stream().map(gameSetup -> gameSetup.getWordIcon()).collect(Collectors.toList());
        translateWord = listGameSetup.stream().map(gameSetup -> gameSetup.getTranslateWord()).collect(Collectors.toList());
        numberOfRounds = words.size();
        game.setProgressBar(0,numberOfRounds,0);
        for(int i=0 ;i<numberOfRounds; i++ ){
            System.out.println(words.get(i));
        }
        nextWord();

    }
    public LevelsEntity updateLevelInLevelsEntity(String typeOfWord){
        Optional<LevelsEntity> levelsEntity = authService.getLevelsEntity(user.getId());
        if(typeOfWord.equals("ADJECTIVE") && levelsEntity.get().getLevelOfAdjective() == game.getLevel()){
            levelsEntity.get().setLevelOfAdjective(levelsEntity.get().getLevelOfAdjective()+1);
        }else if(typeOfWord.equals("VERB") &&  levelsEntity.get().getLevelOfVerb() == game.getLevel()){
            levelsEntity.get().setLevelOfVerb(levelsEntity.get().getLevelOfVerb()+1);
        }
        else if(typeOfWord.equals("NOUN") &&  levelsEntity.get().getLevelOfNoun() == game.getLevel()){
            levelsEntity.get().setLevelOfNoun(levelsEntity.get().getLevelOfNoun()+1);
        }
        else if(typeOfWord.equals("ADVERBIAL") &&  levelsEntity.get().getLevelOfAdverbial() == game.getLevel()){
            levelsEntity.get().setLevelOfAdverbial(levelsEntity.get().getLevelOfAdverbial()+1);
        }
        return levelsEntity.get();
    }


}
