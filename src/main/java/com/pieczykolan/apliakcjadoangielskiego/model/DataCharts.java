package com.pieczykolan.apliakcjadoangielskiego.model;

public class DataCharts {
    private TypeOfWord typeOfWord;
    private int level;

    public DataCharts(TypeOfWord typeOfWord, int level) {
        this.typeOfWord = typeOfWord;
        this.level = level;
    }

    public TypeOfWord getTypeOfWord() {
        return typeOfWord;
    }

    public void setTypeOfWord(TypeOfWord typeOfWord) {
        this.typeOfWord = typeOfWord;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
