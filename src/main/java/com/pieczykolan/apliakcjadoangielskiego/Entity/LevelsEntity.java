package com.pieczykolan.apliakcjadoangielskiego.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class LevelsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int levelOfVerb;
    private int levelOfNoun;
    private int levelOfAdjective;
    private int levelOfAdverbial;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevelOfVerb() {
        return levelOfVerb;
    }

    public void setLevelOfVerb(int levelOfVerb) {
        this.levelOfVerb = levelOfVerb;
    }

    public int getLevelOfNoun() {
        return levelOfNoun;
    }

    public void setLevelOfNoun(int levelOfNoun) {
        this.levelOfNoun = levelOfNoun;
    }

    public int getLevelOfAdjective() {
        return levelOfAdjective;
    }

    public void setLevelOfAdjective(int levelOfAdjective) {
        this.levelOfAdjective = levelOfAdjective;
    }

    public int getLevelOfAdverbial() {
        return levelOfAdverbial;
    }

    public void setLevelOfAdverbial(int levelOfAdverbial) {
        this.levelOfAdverbial = levelOfAdverbial;
    }



}
