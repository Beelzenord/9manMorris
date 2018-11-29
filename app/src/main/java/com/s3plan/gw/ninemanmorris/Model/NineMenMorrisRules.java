package com.s3plan.gw.ninemanmorris.Model;

import android.util.Log;

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.Model.GameState.GameState;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Jonas W�hsl�n, jwi@kth.se. 
 * Revised by Anders Lindstr�m, anderslm@kth.se
 */

/*
 * The game board positions
 *
 * 03           06           09
 *     02       05       08
 *         01   04   07
 * 24  23  22        10  11  12
 *         19   16   13
 *     20       17       14
 * 21           18           15
 * 
 */

public class NineMenMorrisRules implements Serializable {
	private int[] gameplan;
	private int bluemarker, redmarker;
	private int blueonboardmarker, redonboardmarker;
	private int latestTo, latestFrom, latestRemove;
	private int turn; // player in turn

	public static final int BLUE_MOVES = 1;
	public static final int RED_MOVES = 2;

	public static final int EMPTY_SPACE = 0;
	public static final int BLUE_MARKER = 4;
	public static final int RED_MARKER = 5;


	public NineMenMorrisRules() {
		gameplan = new int[25]; // zeroes
		bluemarker = 9;
		redmarker = 9;
		blueonboardmarker = 0;
		redonboardmarker = 0;
		latestTo = -1; latestFrom = -1; latestRemove = -1;
		turn = RED_MOVES;

	}

	/**
	 * Returns true if a move is successful
	 */
	public boolean tryLegalMove(int To, int From, int color) {
		System.out.println("TRY LEGAL MOVE " + color + " " + turn);
        System.out.println("From: " + From + " To: " + To);
        if (gameplan[To] != EMPTY_SPACE)
        	return false;
		if (color == turn) {
			if (turn == RED_MOVES) {
				if (redmarker > 0) {
					if (gameplan[To] == EMPTY_SPACE) {
						gameplan[To] = RED_MARKER;
						if(redmarker!=0){
						 	redmarker--;
						}
						redonboardmarker++;
						setLatestMove(To, -1);
						turn = BLUE_MOVES;
						return true;
					}
				}
				if (redmarker <= 0 && redonboardmarker <= 3) {
					gameplan[To] = RED_MARKER;
					gameplan[From] = EMPTY_SPACE;
					setLatestMove(To, From);
					turn = BLUE_MOVES;
					return true;
				}
				/*else*/
				if (gameplan[To] == EMPTY_SPACE) {
					boolean valid = isValidMove(To, From);
					if (valid == true) {
						gameplan[To] = RED_MARKER;
						gameplan[From] = EMPTY_SPACE;
						turn = BLUE_MOVES;
                        setLatestMove(To, From);
						return true;
					} else {
						System.out.println("FIRST False");
						return false;
					}
				} else {
					System.out.println("SECOND False");
					return false;
				}
			} else {
				if (bluemarker > 0) {
					if (gameplan[To] == EMPTY_SPACE) {
						gameplan[To] = BLUE_MARKER;
						if(bluemarker!=0){
							bluemarker--;
						}
						blueonboardmarker++;
						turn = RED_MOVES;
                        setLatestMove(To, -1);
						System.out.println("blue made a move");
						return true;
					}
				}
				if (bluemarker <= 0 && blueonboardmarker <= 3) {
					gameplan[To] = BLUE_MARKER;
					gameplan[From] = EMPTY_SPACE;
					setLatestMove(To, From);
					turn = RED_MOVES;
					return true;
				}
				if (gameplan[To] == EMPTY_SPACE) {
				    showGamePlane();
				    Log.i("Test", "To: " + To + " From: " + From);
					boolean valid = isValidMove(To, From);
					if (valid == true) {
						gameplan[To] = BLUE_MARKER;
                        gameplan[From] = EMPTY_SPACE;
						turn = RED_MOVES;
                        setLatestMove(To, From);
						return true;
					} else {
							System.out.println("THIRD False");
						return false;
					}
				} else {
						System.out.println("FOURTH False");
					return false;
				}
			}
		} else {
			System.out.println("FIFTH FALSE");
			return false;
		}
	}

	/**
	 * Returns true if position "to" is part of three in a row.
	 */
	public boolean isThreeInARowAtPositionTo(int to) {

		if ((to == 1 || to == 4 || to == 7) && gameplan[1] == gameplan[4]
				&& gameplan[4] == gameplan[7]) {

			return true;
		} else if ((to == 2 || to == 5 || to == 8)
				&& gameplan[2] == gameplan[5] && gameplan[5] == gameplan[8]) {

			return true;
		} else if ((to == 3 || to == 6 || to == 9)
				&& gameplan[3] == gameplan[6] && gameplan[6] == gameplan[9]) {
			return true;
		} else if ((to == 7 || to == 10 || to == 13)
				&& gameplan[7] == gameplan[10] && gameplan[10] == gameplan[13]) {

			return true;
		} else if ((to == 8 || to == 11 || to == 14)
				&& gameplan[8] == gameplan[11] && gameplan[11] == gameplan[14]) {

			return true;
		} else if ((to == 9 || to == 12 || to == 15) 
				&& gameplan[9] == gameplan[12] && gameplan[12] == gameplan[15]) {

			return true;
		} else if ((to == 13 || to == 16 || to == 19)
				&& gameplan[13] == gameplan[16] && gameplan[16] == gameplan[19]) {

			return true;
		} else if ((to == 14 || to == 17 || to == 20)
				&& gameplan[14] == gameplan[17] && gameplan[17] == gameplan[20]) {

			return true;
		} else if ((to == 15 || to == 18 || to == 21)
				&& gameplan[15] == gameplan[18] && gameplan[18] == gameplan[21]) {

			return true;
		} else if ((to == 1 || to == 22 || to == 19)
				&& gameplan[1] == gameplan[22] && gameplan[22] == gameplan[19]) {

			return true;
		} else if ((to == 2 || to == 23 || to == 20)
				&& gameplan[2] == gameplan[23] && gameplan[23] == gameplan[20]) {

			return true;
		} else if ((to == 3 || to == 24 || to == 21)
				&& gameplan[3] == gameplan[24] && gameplan[24] == gameplan[21]) {

			return true;
		} else if ((to == 22 || to == 23 || to == 24)
				&& gameplan[22] == gameplan[23] && gameplan[23] == gameplan[24]) {

			return true;
		} else if ((to == 4 || to == 5 || to == 6)
				&& gameplan[4] == gameplan[5] && gameplan[5] == gameplan[6]) {

			return true;
		} else if ((to == 10 || to == 11 || to == 12)
				&& gameplan[10] == gameplan[11] && gameplan[11] == gameplan[12]) {

			return true;
		} else if ((to == 16 || to == 17 || to == 18)
				&& gameplan[16] == gameplan[17] && gameplan[17] == gameplan[18]) {

			return true;
		}
		return false;
	}

	/**
	 * Request to isThreeInARowAtPositionTo a marker for the selected player.
	 * Returns true if the marker where successfully removed
	 */
	public boolean remove(int From, int color) {
		if (gameplan[From] == color) {
			gameplan[From] = EMPTY_SPACE;
			if (color == BLUE_MARKER || color == BLUE_MOVES){
			   	blueonboardmarker--;
			   	turn = BLUE_MOVES;
			}

			else{
				redonboardmarker--;
				turn = RED_MOVES;
			}
			latestRemove = From;
			return true;
		} else
			return false;
	}

	/**
	 * Returns 4 is blue won (BLUE_MARKER), 5 if red won (RED_MARKER)
	 * or -1 if nobody has won yet.
	 * @return The color who won or -1 if nobody has won yet.
	 */
	public int win() {
		if (redonboardmarker < 3 && redmarker <= 0)
			return BLUE_MARKER;
		else if (blueonboardmarker < 3 && bluemarker <= 0)
			return RED_MARKER;
		return -1;
	}

	/**
	 *  Returns true if the selected player have less than three markers left.
	 */                            //blue
	public boolean win(int color) {
		int countMarker = 0;
		int count = 0;
		while (count < 23) {        // has something, red.
			if (gameplan[count] != EMPTY_SPACE && gameplan[count] != color)
				countMarker++;
			count++;
		}
		if (bluemarker <= 0 && redmarker <= 0 && countMarker < 3)
			return true;
		else
			return false;
	}

	public boolean lose(int color){
		if(color == RED_MOVES){
			if(redmarker <= 0 && redonboardmarker < 3){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			if(bluemarker <= 0 && blueonboardmarker <3 ){
				return true;
			}
			else{
			   return false;
			}
		}
	}

	/**
	 * Returns EMPTY_SPACE = 0 BLUE_MARKER = 4 READ_MARKER = 5
	 */
	public int typeOfManAtPosition(int From) {
		return gameplan[From];
	}
	
	/**
	 * Check whether this is a legal move.
	 */
	public boolean isValidMove(int to, int from, int color){
		if(color == turn){
			if(isValidMove(to,from)){
				 if(turn == RED_MOVES){
				 	this.gameplan[to] = RED_MARKER;
				 	this.gameplan[from] = EMPTY_SPACE;
				 	turn = BLUE_MOVES;
				 	return true;
				 }
				 else{
				 	this.gameplan[to] = BLUE_MARKER;
				 	this.gameplan[from] = EMPTY_SPACE;
				 	turn = RED_MOVES;
				 	return true;
				 }
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}

	}
	public boolean isValidMove(int to, int from) {
        if (to > 24 || from > 24) return false;
		if(this.gameplan[to] != EMPTY_SPACE) return false;
		
		switch (to) {
		case 1:
			return (from == 4 || from == 22);
		case 2:
			return (from == 5 || from == 23);
		case 3:
			return (from == 6 || from == 24);
		case 4:
			return (from == 1 || from == 7 || from == 5);
		case 5:
			return (from == 4 || from == 6 || from == 2 || from == 8);
		case 6:
			return (from == 3 || from == 5 || from == 9);
		case 7:
			return (from == 4 || from == 10);
		case 8:
			return (from == 5 || from == 11);
		case 9:
			return (from == 6 || from == 12);
		case 10:
			return (from == 11 || from == 7 || from == 13);
		case 11:
			return (from == 10 || from == 12 || from == 8 || from == 14);
		case 12:
			return (from == 11 || from == 15 || from == 9);
		case 13:
			return (from == 16 || from == 10);
		case 14:
			return (from == 11 || from == 17);
		case 15:
			return (from == 12 || from == 18);
		case 16:
			return (from == 13 || from == 17 || from == 19);
		case 17:
			return (from == 14 || from == 16 || from == 20 || from == 18);
		case 18:
			return (from == 17 || from == 15 || from == 21);
		case 19:
			return (from == 16 || from == 22);
		case 20:
			return (from == 17 || from == 23);
		case 21:
			return (from == 18 || from == 24);
		case 22:
			return (from == 1 || from == 19 || from == 23);
		case 23:
			return (from == 22 || from == 2 || from == 20 || from == 24);
		case 24:
			return (from == 3 || from == 21 || from == 23);
		}
		return false;
	}

	public int[] getGameplan() {
		return gameplan;
	}

	public int[] getGameplanClone() {
		return gameplan.clone();
	}

	public int getBluemarker() {
		return bluemarker;
	}

	public int getRedmarker() {
		return redmarker;
	}

	public int getBlueonboardmarker() {
		return blueonboardmarker;
	}

	public int getRedonboardmarker() {
		return redonboardmarker;
	}

	public int getMarker(int color) {
		if (color == BLUE_MARKER)
			return bluemarker;
		return redmarker;
	}

	public int getOnboardMarker(int color) {
		if (color == BLUE_MARKER)
			return blueonboardmarker;
		return redonboardmarker;
	}

	public void showGamePlane(){
		System.out.println(Arrays.toString(gameplan));
	}

    public int getTurn() {
        return turn;
    }

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void getTurnStatement(){
		if(turn== RED_MOVES){
			System.out.println("RED TURN");
		}
		if(turn == BLUE_MOVES){
			System.out.println("BLUE MOVES");
		}
	}

    public boolean allCheckersOnTheBoard(int color){

	   if(color == RED_MOVES){
	   	 if(redmarker<=0){
			 System.out.println("all checkers boolean " + color  + " " + redmarker);
	   	 	return true;
		 }
		 else{
			 System.out.println("return false 1");
	   	 	return false;
		 }
	   }
	   if(color == BLUE_MOVES){
	   	    if(bluemarker<=0){
	   	     System.out.println("all checkers boolean " + color  + " " + redmarker);
	   	    	return true;
			}
			else{
	   	    	 System.out.println("return false 2");
	   	    	return false;
			}
	   }
	    System.out.println("return false 3");
		    return false;
	}

	public int getRightColorMarker(int color){
		if(color == RED_MOVES){
			return BLUE_MARKER;
		}
		else{
			return RED_MARKER;
		}

	}

	private void setLatestMove(int to, int from) {
	    latestTo = to;
	    latestFrom = from;
    }

    public int getLatestTo() {
        return latestTo;
    }

    public int getLatestFrom() {
        return latestFrom;
    }

    public int getLatestRemove() {
        return latestRemove;
    }

	/**
	 * Return true if player with color marker can't make another move.
	 * @param color The marker color for the player to check.
	 * @return Return true if no more moves can be made.
	 */
	public boolean noMoveMoves(int color) {
		if (color == RED_MARKER) {
			if (redmarker > 0)
				return false;
			if (redmarker <= 0 && redonboardmarker <= 3)
				return false;
		}
		if (color == BLUE_MARKER) {
			if (bluemarker > 0)
				return false;
			if (bluemarker <= 0 && blueonboardmarker <= 3)
				return false;
		}
		for (int i = 1; i < gameplan.length; i++) {
			Log.i("Test", "gp " + gameplan[i] + "color: " +color);
			if (gameplan[i] == color) {
				Log.i("Test", "isdoing");
				boolean result = isValidMove(i);
				if (result == true)
					return false;
				Log.i("Test", "bool: " + result);
			}
		}
		return true;
	}

    @Override
	public String toString() {
		return "NineMenMorrisRules{" +
				"bluemarker=" + bluemarker +
				", redmarker=" + redmarker +
				", blueonboardmarker=" + blueonboardmarker +
				", redonboardmarker=" + redonboardmarker +
				", turn=" + turn +
				'}';
	}


	/**
	 * Checks if there are any valid moves from position from.
	 * @param from The position check.
	 * @return Return true if there is a valid move from position from.
	 */
	public boolean isValidMove(int from) {
		switch (from) {
			case 1:
				return (gameplan[4] == EMPTY_SPACE || gameplan[22] == EMPTY_SPACE);
			case 2:
				return (gameplan[5] == EMPTY_SPACE || gameplan[23] == EMPTY_SPACE);
			case 3:
				return (gameplan[6] == EMPTY_SPACE || gameplan[24] == EMPTY_SPACE);
			case 4:
				return (gameplan[1] == EMPTY_SPACE || gameplan[7] == EMPTY_SPACE || gameplan[5] == EMPTY_SPACE);
			case 5:
				return (gameplan[4] == EMPTY_SPACE || gameplan[6] == EMPTY_SPACE || gameplan[2] == EMPTY_SPACE || gameplan[8] == EMPTY_SPACE);
			case 6:
				return (gameplan[3] == EMPTY_SPACE || gameplan[5] == EMPTY_SPACE || gameplan[9] == EMPTY_SPACE);
			case 7:
				return (gameplan[4] == EMPTY_SPACE || gameplan[10] == EMPTY_SPACE);
			case 8:
				return (gameplan[5] == EMPTY_SPACE || gameplan[11] == EMPTY_SPACE);
			case 9:
				return (gameplan[6] == EMPTY_SPACE || gameplan[12] == EMPTY_SPACE);
			case 10:
				return (gameplan[11] == EMPTY_SPACE || gameplan[7] == EMPTY_SPACE || gameplan[13] == EMPTY_SPACE);
			case 11:
				return (gameplan[10] == EMPTY_SPACE || gameplan[12] == EMPTY_SPACE || gameplan[8] == EMPTY_SPACE || gameplan[14] == EMPTY_SPACE);
			case 12:
				return (gameplan[11] == EMPTY_SPACE || gameplan[15] == EMPTY_SPACE || gameplan[9] == EMPTY_SPACE);
			case 13:
				return (gameplan[16] == EMPTY_SPACE || gameplan[10] == EMPTY_SPACE);
			case 14:
				return (gameplan[11] == EMPTY_SPACE || gameplan[17] == EMPTY_SPACE);
			case 15:
				return (gameplan[12] == EMPTY_SPACE || gameplan[18] == EMPTY_SPACE);
			case 16:
				return (gameplan[13] == EMPTY_SPACE || gameplan[17] == EMPTY_SPACE || gameplan[19] == EMPTY_SPACE);
			case 17:
				return (gameplan[14] == EMPTY_SPACE || gameplan[16] == EMPTY_SPACE || gameplan[20] == EMPTY_SPACE || gameplan[18] == EMPTY_SPACE);
			case 18:
				return (gameplan[17] == EMPTY_SPACE || gameplan[21] == EMPTY_SPACE || gameplan[15] == EMPTY_SPACE);
			case 19:
				return (gameplan[16] == EMPTY_SPACE || gameplan[22] == EMPTY_SPACE);
			case 20:
				return (gameplan[17] == EMPTY_SPACE || gameplan[23] == EMPTY_SPACE);
			case 21:
				return (gameplan[18] == EMPTY_SPACE || gameplan[24] == EMPTY_SPACE);
			case 22:
				return (gameplan[1] == EMPTY_SPACE || gameplan[19] == EMPTY_SPACE || gameplan[23] == EMPTY_SPACE);
			case 23:
				return (gameplan[2] == EMPTY_SPACE || gameplan[22] == EMPTY_SPACE || gameplan[20] == EMPTY_SPACE || gameplan[24] == EMPTY_SPACE);
			case 24:
				return (gameplan[3] == EMPTY_SPACE || gameplan[21] == EMPTY_SPACE || gameplan[23] == EMPTY_SPACE);
		}
		return true;
	}
}