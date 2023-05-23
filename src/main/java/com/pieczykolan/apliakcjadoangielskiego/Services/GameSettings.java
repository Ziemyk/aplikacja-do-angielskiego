package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.View.MainPage;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public class GameSettings  {

    private int level;
    private List<String> wordList ;

    public GameSettings(int level){
        //MainPage mainPage = new MainPage();
        this.level = level;
        wordList = new ArrayList<>();
        generateWord();

    }

    public List<String> numberOfWords(int level){
        List<String> chosenWords = new ArrayList<>();
        Random random =  new Random();
        for(int i=0;i<level*2;i++){
            chosenWords.add(wordList.get(random.nextInt(wordList.size())));
        }
        return chosenWords;
    }
    public void generateWord(){
        wordList.add("house");
        wordList.add("can");
        wordList.add("phone");
        wordList.add("door");
        wordList.add("picture");
        wordList.add("water");
        wordList.add("mouse");
        wordList.add("laptop");
        wordList.add("chair");
        wordList.add("watch");

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
