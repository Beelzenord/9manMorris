package com.s3plan.gw.ninemanmorris.Model;

import android.util.Log;

import java.util.Random;

/**
 * Basic AI for the NineMenMorris game.
 * The AI will prioritize making a mill,
 * then try to prevent the opponent from making a mill
 * then place their checker at a random free spot
 * then place their checker at any free spot.
 */
public class NineMenMorrisAI {
    private NineMenMorrisRules nmmr;
    private int myTurn;
    private int myMarker;
    private int opTurn;
    private int opMarker;
    private int[] gameplan;
    Random random;

    public NineMenMorrisAI(NineMenMorrisRules nmmr, int myColor) {
        this.nmmr = nmmr;
        this.random = new Random();
        if (myColor == NineMenMorrisRules.BLUE_MARKER) {
            myTurn = NineMenMorrisRules.BLUE_MOVES;
            myMarker = NineMenMorrisRules.BLUE_MARKER;
            opTurn = NineMenMorrisRules.RED_MOVES;
            opMarker = NineMenMorrisRules.RED_MARKER;
        } else {
            myTurn = NineMenMorrisRules.RED_MOVES;
            myMarker = NineMenMorrisRules.RED_MARKER;
            opTurn = NineMenMorrisRules.BLUE_MOVES;
            opMarker = NineMenMorrisRules.BLUE_MARKER;
        }
    }

    public void setGame(NineMenMorrisRules nmmr) {
        this.nmmr = nmmr;
    }

    /**
     * The AI will make a move, using the place feature if available, otherwise the move feature
     * @return Returns true is the move was successful.
     */
    public boolean makeMove() {
        gameplan = nmmr.getGameplan();
        nmmr.showGamePlane();
        boolean res;
        if (nmmr.getMarker(myMarker) > 0 || ((nmmr.getOnboardMarker(myMarker) == 3) && (nmmr.getMarker(myMarker) <= 0))) {
            // this may be false if nmmr.tryLegalMove return false -> still AI's turn
            res = tryPlaceMakeMill(1, myMarker);
        }
        else { // if the AI only can move a piece (DRAG)
            res = tryMoveMakeMill(1, 1, myMarker);
        }
        return res;
    }

    /**
     * The AI removes a checker from the opponent.
     * @return Return true if the remove was successful.
     */
    public boolean remove() {
        boolean res = removeTryDetectMill(1);
        return res;
    }

    /**
     * Tries to destroy a mill of the opponent.
     * @param pos The position of the board.
     * @return Return true if the remove was successful.
     */
    private boolean removeTryDetectMill(int pos) {
        if (gameplan[pos] == opMarker && nmmr.isThreeInARowAtPositionTo(pos)) {
            return nmmr.remove(pos, opMarker);
        }
        if (pos >= 24)
            return removeRandomizedMarker(1);
        return removeTryDetectMill(++pos);
    }

    /**
     * Tries to remove a random checker from the opponent.
     * @param pos The position of the board.
     * @return Return true if the remove was successful.
     */
    private boolean removeRandomizedMarker(int pos) {
        if (gameplan[pos] == opMarker) {
            if (randomizer((gameplan.length - pos) / 2))
                return nmmr.remove(pos, opMarker);
        }
        if (pos >= 24)
            return removeAnyMarker(1);
        return removeRandomizedMarker(++pos);
    }

    /**
     * Removes any available marker from the opponent.
     * @param pos The position of the board.
     * @return Return true if the remove was successful.
     */
    private boolean removeAnyMarker(int pos) {
        if (gameplan[pos] == opMarker)
            return nmmr.remove(pos, opMarker);
        if (pos >= 24)
            return false;
        return removeAnyMarker(++pos);
    }

    /**
     * Tries to make a MOVE i.e drags a piece one position to make a mill.
     * If no mill can be made, it will try to prevent a mill of the opponent.
     * @param pos The position of the board.
     * @param from The position which the checker is dragged from.
     * @param marker The color of the marker that may have the mill.
     * @return Return true if the remove was successful.
     */
    private boolean tryMoveMakeMill(int pos, int from, int marker) {
        if (gameplan[from] != myMarker && from < 24)
            return tryMoveMakeMill(1, ++from, marker);
        if (nmmr.isValidMove(pos, from)) {
            gameplan[pos] = marker;
            gameplan[from] = nmmr.EMPTY_SPACE;
            if (nmmr.isThreeInARowAtPositionTo(pos)) {
                gameplan[pos] = NineMenMorrisRules.EMPTY_SPACE;
                gameplan[from] = marker;
                return nmmr.tryLegalMove(pos, from, myTurn);
            }
            gameplan[pos] = NineMenMorrisRules.EMPTY_SPACE;
            gameplan[from] = marker;
        }
        if (pos >= 24 && from < 24)
            return tryMoveMakeMill(1, ++from, marker);
        // try to block opponents mill
//        if (pos >= 24 && from >= 24 && marker == myMarker)
//            return tryMoveMakeMill(1, 1, opMarker);
//        if (pos >= 24 && from >= 24 && marker == opMarker)
//            return tryMoveAnyRandomizedMove(1, 1);
        if (pos >= 24 && from >= 24)
            return tryMoveAnyMove(1, 1);
        return tryMoveMakeMill(++pos, from, marker);
    }

    /**
     * Tries to make a MOVE i.e drags a piece one position to a random free position.
     * @param pos The position of the board.
     * @param from The position which the checker is dragged from.
     * @return Return true if the remove was successful.
     */
    private boolean tryMoveAnyRandomizedMove(int pos, int from) {
        if (gameplan[from] != myMarker && from < 24)
            return tryMoveAnyRandomizedMove(1, ++from);
        if (nmmr.isValidMove(pos, from)) {
            if (moveRandomizer(gameplan.length - pos, gameplan.length - from)) {
                return nmmr.tryLegalMove(pos, from, myTurn);
            }
        }
        if (pos >= 24 && from < 24)
            return tryMoveAnyRandomizedMove(1, ++from);
//        if (pos >= 24 && from >= 24 && marker == myMarker)
//            return tryMoveAnyRandomizedMove(1, 1, opMarker);
//        if (pos >= 24 && from >= 24 && marker == opMarker)
//            return tryMoveAnyMove(1, 1);
        if (pos >= 24 && from >= 24)
            tryMoveAnyMove(1, 1);
        return tryMoveAnyRandomizedMove(++pos, from);
    }

    /**
     * Tries to make a MOVE i.e drags a piece one position to any free position.
     * @param pos The position of the board.
     * @param from The position which the checker is dragged from.
     * @return Return true if the remove was successful.
     */
    private boolean tryMoveAnyMove(int pos, int from) {
        if (gameplan[from] != myMarker && from < 24)
            return tryMoveAnyMove(1, ++from);
        if (nmmr.isValidMove(pos, from)) {
            return nmmr.tryLegalMove(pos, from, myTurn);
        }

        if (pos >= 24 && from < 24)
            return tryMoveAnyMove(1, ++from);
        if (pos >= 24 && from >= 24)
            return false;
        return tryMoveAnyMove(++pos, from);
    }

    /**
     * Tries to place a piece at a position to make a mill.
     * If no mill can be made, it will try to prevent a mill of the opponent.
     * @param pos The position of the board.
     * @param marker The color of the marker that may have the mill.
     * @return Return true if the remove was successful.
     */
    private boolean tryPlaceMakeMill(int pos, int marker) {
        if (gameplan[pos] == NineMenMorrisRules.EMPTY_SPACE) {
            gameplan[pos] = marker;
            if (nmmr.isThreeInARowAtPositionTo(pos)) {
                gameplan[pos] = NineMenMorrisRules.EMPTY_SPACE;
                return nmmr.tryLegalMove(pos, -1, myTurn);
            }
            gameplan[pos] = NineMenMorrisRules.EMPTY_SPACE;
        }
        // try blocking enemy mill
        if (pos >= 24 && marker == myMarker)
            return tryPlaceMakeMill(1, opMarker);
        if (pos >= 24 && marker == opMarker)
            return tryPlaceAnyRandomizedMove(1);
        return tryPlaceMakeMill(++pos, marker);
    }

    /**
     * Tries to place a checker at a random available position.
     * @param pos The position of the board.
     * @return Return true if the remove was successful.
     */
    private boolean tryPlaceAnyRandomizedMove(int pos) {
        if (gameplan[pos] == NineMenMorrisRules.EMPTY_SPACE) {
            if (randomizer(gameplan.length - pos))
                return nmmr.tryLegalMove(pos, -1, myTurn);
        }
        if (pos >= 24)
            return tryPlaceAnyMove(1);
        return tryPlaceAnyRandomizedMove(++pos);
    }

    /**
     * Tries to place a checker at any available position.
     * @param pos The position of the board.
     * @return Return true if the remove was successful.
     */
    private boolean tryPlaceAnyMove(int pos) {
        if (gameplan[pos] == NineMenMorrisRules.EMPTY_SPACE) {
            return nmmr.tryLegalMove(pos, -1, myTurn);
        }
        if (pos >= 24)
            return false;
        return tryPlaceAnyMove(++pos);
    }

    /**
     * Randomizes if a move should be made.
     * It uses the probability of the average of p and f divided by 2.
     * @param p The position to go to.
     * @param f The position the checker is from.
     * @return Returns true if the randomization was successful.
     */
    private boolean moveRandomizer(int p, int f) {
        int c = (p + f) / 4;
        if (c < 2) c = 1;
        return 1 == random.nextInt(c);
    }

    /**
     * Randomizes if a place of a checker should be made.
     * It uses the probability of c divided by 2.
     * @param c The number of positions left that might be available.
     * @return Returns true if the randomization was successful.
     */
    private boolean randomizer(int c) {
        if (c < 2) c = 1;
        else c = c / 2;
        return 1 == random.nextInt(c);
    }
}
