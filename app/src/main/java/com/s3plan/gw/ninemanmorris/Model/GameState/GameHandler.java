package com.s3plan.gw.ninemanmorris.Model.GameState;

import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisAI;
import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;

import java.io.Serializable;

public class GameHandler implements Serializable {
    private GameState gameState;
    private NineMenMorrisRules theGame;
    private boolean ongoingGame;
    private boolean isAIgame;
    private String name;
    private static GameHandler gameHandler;
    private NineMenMorrisAI nineMenMorrisAI;

    private GameHandler() {
        gameState = GameState.PLACE;
        theGame = new NineMenMorrisRules();
        ongoingGame = false;
        nineMenMorrisAI = new NineMenMorrisAI(theGame, NineMenMorrisRules.BLUE_MARKER);
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
        this.gameState = gameHandler.gameState;
        this.theGame = gameHandler.theGame;
        this.ongoingGame = gameHandler.ongoingGame;
//        this.gameHandler = gameHandler;
    }

    public boolean makeAIMove() {
        return nineMenMorrisAI.makeMove();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setTheGame(NineMenMorrisRules theGame) {
        this.theGame = theGame;
        nineMenMorrisAI.setGame(theGame);
    }

    public NineMenMorrisRules getTheGame() {
        return theGame;
    }


}
