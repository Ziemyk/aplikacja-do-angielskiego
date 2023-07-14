package com.pieczykolan.apliakcjadoangielskiego.model;

public class TranslateWord {

    String word;
    String translateWord;

    public TranslateWord(String word, String translateWord) {
        this.word = word;
        this.translateWord = translateWord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslateWord() {
        return translateWord;
    }

    public void setTranslateWord(String translateWord) {
        this.translateWord = translateWord;
    }
}
