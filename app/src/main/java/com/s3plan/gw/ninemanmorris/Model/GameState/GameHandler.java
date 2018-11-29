package com.s3plan.gw.ninemanmorris.Model.GameState;

import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisAI;
import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;

import java.io.Serializable;

/**
 * Handles the state of the game, i.e the model of the game.
 */
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
        nineMenMorrisAI = new NineMenMorrisAI(NineMenMorrisRules.BLUE_MARKER);
        isAIgame = false;
    }

    public static GameHandler getInstance() {
        if (gameHandler == null)
            gameHandler = new GameHandler();
        return gameHandler;
    }

    /**
     * Resets the state of the game to the very beginning.
     */
    public void restartGame() {
        gameState = GameState.PLACE;
        theGame = new NineMenMorrisRules();
        ongoingGame = false;
        nineMenMorrisAI = new NineMenMorrisAI(NineMenMorrisRules.BLUE_MARKER);
        isAIgame = false;
    }

    /**
     * Updates this gameHandler with data from a saved gameHandler.
     * @param gameHandler The GameHandler that contains the saved data.
     */
    public void setGameHandler(GameHandler gameHandler) {
        this.gameState = gameHandler.gameState;
        this.theGame = gameHandler.theGame;
        this.ongoingGame = gameHandler.ongoingGame;
        this.isAIgame = gameHandler.isAIgame;
    }

    /**
     * Turn AI on if it was off and off if it was on.
     * The Red player must be on move to be able to toggle the AI.
     */
    public void toggleAI() {
        if (theGame.getTurn() == theGame.RED_MOVES) {
            if (isAIgame)
                isAIgame = false;
            else
                isAIgame = true;
        }
    }

    /**
     * The AI will make a move using the current game state.
     * @return True if the AI-move was successful.
     */
    public boolean makeAIMove() {
        return nineMenMorrisAI.makeMove(theGame);
    }

    /**
     * The AI will remove a piece from the opponent.
     * @return True if the AI-remove was successful.
     */
    public boolean makeAIRemove() {
        return nineMenMorrisAI.remove();
    }

    public GameState getState() {
        return gameState;
    }

    public void setState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Tries to invoke a move on the game board.
     * @param to The position to move to.
     * @param from The position to move from.
     * @param color The checker making the move.
     * @return True if the move was successful.
     */
    public boolean tryLegalMove(int to, int from, int color) {
        return theGame.tryLegalMove(to, from, color);
    }

    /**
     * Tests if the are 3 checkers in a row at position to.
     * @param to The position to check.
     * @return True if 3 checkers in a row at position to.
     */
    public boolean isThreeInARowAtPositionTo(int to) {
        return theGame.isThreeInARowAtPositionTo(to);
    }

    /**
     * Tries to remove a checker from position from.
     * @param from The position to remove.
     * @param color The color at position from.
     * @return True if the remove was successful.
     */
    public boolean remove(int from, int color) {
        return theGame.remove(from, color);
    }

    /**
     * What color checker is at position from.
     * @param from The position to check.
     * @return 4 from red is at position from, 5 if blue and 0 if it's empty.
     */
    public int typeOfCheckerAtPosition(int from) {
        return theGame.typeOfManAtPosition(from);
    }

    /**
     * Returns true if there is an ongoingGame.
     * @return Returns true if there is an ongoingGame.
     */
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
    }

    public NineMenMorrisRules getTheGame() {
        return theGame;
    }

    public boolean isAIgame() {
        return isAIgame;
    }

    public void setAIgame(boolean AIgame) {
        isAIgame = AIgame;
    }
}
