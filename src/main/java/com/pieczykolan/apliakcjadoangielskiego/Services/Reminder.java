package com.pieczykolan.apliakcjadoangielskiego.Services;

import com.pieczykolan.apliakcjadoangielskiego.View.Game;
import com.vaadin.flow.component.UI;

import java.util.Timer;
import java.util.TimerTask;

public class Reminder extends TimerTask {
    private final UI ui;
    Game game;
    GameLogic gameLogic;
    public Reminder(UI ui, Game game,GameLogic gameLogic){
        this.ui = ui;
        this.game = game;
        this.gameLogic = gameLogic;

    }

    @Override
    public void run(){
        try {
            ui.access(() -> {
                game.setHangmanImage(5);
            });
        } catch (Exception ex) {
                    throw ex;
        }
    }





}
