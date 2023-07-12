package com.pieczykolan.apliakcjadoangielskiego.model;

import com.vaadin.flow.component.html.Image;

public class Users {
    private String username;
    private Image userAvatar;
    private int verb;
    private int noun;
    private int adjective;
    private int adverbial;

    public Users(String username, Image userAvatar, int verb, int noun, int adjective, int adverbial) {
        this.username = username ;
        this.userAvatar = userAvatar ;
        this.verb = verb;
        this.noun = noun;
        this.adjective = adjective;
        this.adverbial = adverbial;
    }
    public  Users(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Image getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(Image userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getVerb() {
        return verb;
    }

    public void setVerb(int verb) {
        this.verb = verb;
    }

    public int getNoun() {
        return noun;
    }

    public void setNoun(int noun) {
        this.noun = noun;
    }

    public int getAdjective() {
        return adjective;
    }

    public void setAdjective(int adjective) {
        this.adjective = adjective;
    }

    public int getAdverbial() {
        return adverbial;
    }

    public void setAdverbial(int adverbial) {
        this.adverbial = adverbial;
    }
}
