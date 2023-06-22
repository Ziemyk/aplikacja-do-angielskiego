package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.model.GameSetup;
import com.pieczykolan.apliakcjadoangielskiego.model.LevelOfWord;
import com.pieczykolan.apliakcjadoangielskiego.repo.GameSetupRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public List<GameSetup> getGameSetup(int level){
        List<GameSetup> chosenWords = new ArrayList<>();
        List<GameSetup> chosenWordsAfterRandom = new ArrayList<>();
        chosenWords = gameSetupRepo.findAllByLevelOfWordLessThanEqualAndTypeOfWord(LevelOfWord.values()[level],type);
        Random random = new Random();
        int randomIndex;
        for(int i=0 ;i<level*2;i++){
            randomIndex = random.nextInt(chosenWords.size());
            if(chosenWordsAfterRandom.contains(chosenWords.get(randomIndex))){
                i--;
            }else {
                chosenWordsAfterRandom.add(chosenWords.get(randomIndex));
            }
        }
        chosenWords.clear();
        return chosenWordsAfterRandom;
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
