package com.pieczykolan.apliakcjadoangielskiego.model;

import javax.persistence.*;

@Entity
public class GameSetup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String word;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte [] wordIcon;
    private String typeOfWord;

    public String getTranslateWord() {
        return translateWord;
    }

    public void setTranslateWord(String translateWord) {
        this.translateWord = translateWord;
    }

    private String translateWord;
    private LevelOfWord levelOfWord;
    public GameSetup(){
        super();
    }

    public GameSetup( String word, byte[] wordIcon, String typeOfWord, String translateWord, LevelOfWord levelOfWord) {
        this.word = word;
        this.wordIcon = wordIcon;
        this.typeOfWord = typeOfWord;
        this.translateWord = translateWord;
        this.levelOfWord = levelOfWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public byte[] getWordIcon() {
        return wordIcon;
    }

    public void setWordIcon(byte[] wordIcon) {
        this.wordIcon = wordIcon;
    }
}
