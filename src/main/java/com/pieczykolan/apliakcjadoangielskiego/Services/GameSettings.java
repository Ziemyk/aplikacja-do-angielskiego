package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.View.MainPage;
import com.pieczykolan.apliakcjadoangielskiego.model.GameSetup;
import com.pieczykolan.apliakcjadoangielskiego.model.LevelOfWord;
import com.pieczykolan.apliakcjadoangielskiego.repo.GameSetupRepo;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public class GameSettings  {

    private int level;
    private String type;
    private List<String> wordList ;

    private GameSetupRepo gameSetupRepo;

    public GameSettings(int level, String type,GameSetupRepo gameSetupRepo){
        //MainPage mainPage = new MainPage();
        this.level = level;
        this.type = type;
        this.gameSetupRepo = gameSetupRepo;
        wordList = new ArrayList<>();


    }

    public List<GameSetup> numberOfWords(int level){
        List<GameSetup> chosenWords = new ArrayList<>();
        chosenWords = gameSetupRepo.findAllByLevelOfWordAndTypeOfWord(LevelOfWord.values()[level],type);

        return chosenWords;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<String> getWordList() {
        return wordList;
    }

    public void setWordList(List<String> wordList) {
        this.wordList = wordList;
    }



}
