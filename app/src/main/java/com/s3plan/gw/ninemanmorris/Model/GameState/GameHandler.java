package com.s3plan.gw.ninemanmorris.Model.GameState;

import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;

import java.io.Serializable;

public class GameHandler implements Serializable {
    private GameState gameState;
    private NineMenMorrisRules theGame;
    private boolean ongoingGame;
    private static GameHandler gameHandler;

    private GameHandler() {
        gameState = GameState.PLACE;
        theGame = new NineMenMorrisRules();
        ongoingGame = false;
    }

    public static GameHandler getInstance() {
        if (gameHandler == null)
            gameHandler = new GameHandler();
        return gameHandler;
    }

    public void restartGame() {
        gameState = GameState.PLACE;
        theGame = new NineMenMorrisRules();
        ongoingGame = true;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public GameState getState() {
        return gameState;
    }

    public void setState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean tryLegalMove(int to, int from, int color) {
        return theGame.tryLegalMove(to, from, color);
    }

    public boolean isThreeInARowAtPositionTo(int to) {
        return theGame.isThreeInARowAtPositionTo(to);
    }

    public boolean remove(int from, int color) {
        return theGame.remove(from, color);
    }

    public boolean win(int color) {
        return theGame.win(color);
    }

    public int typeOfCheckerAtPosition(int from) {
        return theGame.typeOfManAtPosition(from);
    }

    public boolean isOngoingGame() {
        return ongoingGame;
    }

    public void setOngoingGame(boolean ongoingGame) {
        this.ongoingGame = ongoingGame;
    }
}
