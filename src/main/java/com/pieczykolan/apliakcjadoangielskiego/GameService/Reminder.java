package com.pieczykolan.apliakcjadoangielskiego.GameService;

import com.pieczykolan.apliakcjadoangielskiego.View.Game;
import com.vaadin.flow.component.UI;
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
            gameLogic.setCurrentIterationOfImage(gameLogic.getCurrentIterationOfImage()+1);
            ui.access(() -> {
                game.setHangmanImage(gameLogic.getCurrentIterationOfImage());
                game.restartTimer();
                ui.push();
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }





}
