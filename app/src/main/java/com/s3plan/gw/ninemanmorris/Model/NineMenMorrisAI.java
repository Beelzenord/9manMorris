/*
package com.s3plan.gw.ninemanmorris.Model;

public class NineMenMorrisAI {
    private NineMenMorrisRules nmmr;
    private int myTurn;
    private int myMarker;
    private int opTurn;
    private int opMarker;
    private int[] gameplanClone;

    public NineMenMorrisAI(NineMenMorrisRules nmmr, int myColor) {
        this.nmmr = nmmr;
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

    public boolean makeMove() {
        gameplanClone = nmmr.getGameplanClone();
        if (nmmr.getMarker(myMarker) >= 0 || ((nmmr.getOnboardMarker(myMarker) == 3) && (nmmr.getMarker(myMarker) <= 0))) {
            // this may be false if nmmr.tryLegalMove return false -> still AI's turn
            boolean res = tryPlaceMakeMill(1, myColor);
            return res;
        }
    }

    private boolean tryPlaceMakeMill(int pos, int color) {
        if (gameplanClone[pos] == NineMenMorrisRules.EMPTY_SPACE) {
            gameplanClone[pos] = color;
            if (nmmr.isThreeInARowAtPositionTo(pos))
                return nmmr.tryLegalMove(pos, -1, 1);
            gameplanClone[pos] = NineMenMorrisRules.EMPTY_SPACE;
        }
        if (pos >= 24 && color == myColor )
            return tryPlaceMakeMill(1, opColor);
        if (pos >= 24 && color == myColor)
            return tryPlaceAnyMove(1, myColor);
        return tryPlaceMakeMill(pos++, color);
    }

    private boolean tryPlaceAnyMove(int pos, int color) {
        if (gameplanClone[pos] == NineMenMorrisRules.EMPTY_SPACE)
            return nmmr.tryLegalMove(pos, -1, color);
        return tryPlaceAnyMove(pos++, color);
    }
}
*/
