package com.s3plan.gw.ninemanmorris.Model.GameState;

import java.io.Serializable;

/**
 * The current state of the game.
 * Place the player can place a piece from any position.
 * Drag the player can only move a piece to adjacent positions.
 * Gameover A player has won.
 * Delete a player made 3 in a row and must remove a piece.
 */
public enum GameState implements Serializable {
    PLACE, DRAG, GAMEOVER, DELETE
}
