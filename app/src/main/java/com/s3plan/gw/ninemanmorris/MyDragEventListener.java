package com.s3plan.gw.ninemanmorris;

import android.app.Activity;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.Model.GameState.GameState;
import com.s3plan.gw.ninemanmorris.Model.SaveHandler;

public class MyDragEventListener implements View.OnDragListener {

    private Context context;
    private GameHandler gameHandler;
    private UiUpdaterForAI uiUpdaterForAi;

    private Drawable player1StationedIcon;

    private static final String BIN_TAG = "BIN_TAG";
    private Drawable player2StationedIcon;

    private TextView player1TextView;

    private TextView player2TextView;


//    private  NineMenMorrisRules nineMenMorrisRules;

    /**
     *
     * @param context : context from mainActivity
     * @param player1TextView: TextView on the corner left for player 1
     * @param player2TextView : TextView on the corner right for player 2
     */
    public MyDragEventListener(Context context, TextView player1TextView, TextView player2TextView) {
        this.context = context;
        this.player1StationedIcon = this.context.getDrawable(R.drawable.playeronestationed);
        this.player2StationedIcon = this.context.getDrawable(R.drawable.playertwostationed);
        this.gameHandler = GameHandler.getInstance();
//        this.nineMenMorrisRules = gameHandler.getTheGame();
        this.uiUpdaterForAi = UiUpdaterForAI.getInstance();
        this.player1TextView = player1TextView;
        this.player2TextView = player2TextView;
    }

    /**
     * Handles events based on the logic provided by GameState and player's actions
     * @param v : view of the object that was dragged on
     * @param event :
     * @return
     */
    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (gameHandler.getGameState().equals(GameState.GAMEOVER))
            return false;
        final int action = event.getAction();

        // Handles each of the expected events
        Drawable drawable = this.context.getDrawable(R.drawable.circlesample);
        Drawable normal = this.context.getDrawable(R.drawable.circle);
        Drawable binAlter = this.context.getDrawable(R.drawable.binalter);
        Drawable binNormal = this.context.getDrawable(R.drawable.binnormal);
        View draggedView = (View) event.getLocalState();
        ViewGroup vg = (ViewGroup) v.getParent();
        ConstraintLayout rl = (ConstraintLayout) vg.findViewById(R.id.mainConstraint);
        ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) v.getLayoutParams();

        boolean madeAMill = false;


        switch (action) {

            case DragEvent.ACTION_DRAG_STARTED:


                /**
                 * Get A tag to Show whether or not we should accept this
                 */
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    v.setVisibility(View.VISIBLE);
                    return true;

                }
                // v.invalidate();
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                v.setVisibility(View.VISIBLE);
                return false;

            //When users entered droppable area
            case DragEvent.ACTION_DRAG_ENTERED:

                System.out.println(gameHandler.getTheGame().toString());

                if (gameHandler.getGameState() == GameState.DELETE) {
                    return false;
                }
                if(v.getTag().toString().equals(BIN_TAG)){
                    v.setBackground(binAlter);
                }
                else{
                    v.setBackground(drawable);

                }
                v.invalidate();

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                //   v.setVisibility(View.VISIBLE);
                return true;

            //exit draggable area
            case DragEvent.ACTION_DRAG_EXITED:
                //recolour views to original color
                if(v.getTag().toString().equals(BIN_TAG)){
                    v.setBackground(binNormal);

                }
                else{
                    v.setBackground(normal);

                }

                v.invalidate();

                return true;

            case DragEvent.ACTION_DROP:



                // Gets the item containing the dragged data
                //  ClipData.Item item = event.getClipData().getItemAt(0);
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

                gameHandler.getTheGame().getTurnStatement();
                if(v.getTag().toString().trim().equals(BIN_TAG)){
                   if(gameHandler.getGameState() == GameState.DELETE){
                       if(Util.isThePieceOnThBoard((String)draggedView.getTag().toString())){
                           handleDelete(draggedView,v,event);

                           int redOrBlue  = Util.getPlayerIdentiferFromCheckerPiece(draggedView.getTag().toString());
                           int idToBeDeleted = Util.getIdNumberOfTheOccupiedPlaceHolder(draggedView.getTag().toString());
                           int playerPieceToBeRemoved = Util.getColorOfDraggedPiece(draggedView.getTag().toString());
                           if(gameHandler.getTheGame().remove(idToBeDeleted,playerPieceToBeRemoved)){
                               gameHandler.getTheGame().showGamePlane();
                               int won = gameHandler.getTheGame().win();
                               if (won > 0) {
                                   gameHandler.setState(GameState.GAMEOVER);
                                   playerWinToast(won,player1TextView,player2TextView);
                                   gameHandler.restartGame();
                                   SaveHandler.createSaveFile((Activity)context, (context).getResources().getString(R.string.pathToSaveFile));
                                   return true;
                               }
                               int winner = Util.checkForNoMoreMoves(gameHandler);
                               if (winner > 0)
                                   playerWinToast(winner, player1TextView, player2TextView);
                               else{
                                   showWhosTurn(player1TextView,player2TextView,false);
                               }
                           }
                           System.out.println("ID to be deleted " + redOrBlue);
                           if(gameHandler.getGameState() == GameState.DRAG){
                               if (gameHandler.isAIgame()) {
                                   makeAiMove();
                               }
                               return true;
                           }
                           if(gameHandler.getTheGame().allCheckersOnTheBoard(1) && gameHandler.getTheGame().allCheckersOnTheBoard(2))
                               gameHandler.setState(GameState.DRAG);
                           else
                               gameHandler.setState(GameState.PLACE);
                           if (gameHandler.isAIgame())
                               makeAiMove();
                           else
                               SaveHandler.createSaveFile((Activity)context, (context).getResources().getString(R.string.pathToSaveFile));
                           return true;
                       }
                   }
                   else{
                       Toast.makeText(this.context,"Can't make that move",Toast.LENGTH_SHORT).show();
                   }
                   return false;
                }
                else {

                    String data = event.getClipDescription().getLabel().toString();
                    int radius = (v.getRight() - v.getLeft()) / 2;
                    //identify which piece (blue or red)
                    int redOrBlue = Util.getPlayerIdentiferFromCheckerPiece(data.trim());
                    int playerPieceToBeRemoved = Util.getColorOfDraggedPiece(draggedView.getTag().toString());

                    int from = 0;
                    boolean madeSuccessfulMove = false;

                    if (Util.isThePieceOnThBoard(draggedView.getTag().toString()) && !gameHandler.getTheGame().allCheckersOnTheBoard(redOrBlue)) {
                        Toast.makeText(this.context, "Use pieces outside the board", Toast.LENGTH_SHORT).show();
                        return false;
                    } else if ((Util.isThePieceOnThBoard(draggedView.getTag().toString()) && gameHandler.getTheGame().allCheckersOnTheBoard(redOrBlue)) ||
                            gameHandler.getGameState() == GameState.DRAG) {
                        from = Util.getIdNumberOfTheOccupiedPlaceHolder(draggedView.getTag().toString());

                        //do the move here
                        if(gameHandler.getTheGame().tryLegalMove(v.getId(),from,redOrBlue)){
                            madeSuccessfulMove = true;
                            showWhosTurn(player1TextView,player2TextView, madeAMill);
                            updateNewPosition(draggedView, p, radius, rl, v);
                        }
                        else{
                            Toast.makeText(this.context,"Can't make that move",Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                    else if(gameHandler.getGameState() == GameState.PLACE){
                        if (gameHandler.getTheGame().tryLegalMove(v.getId(), from, redOrBlue)) {
                            updateNewPosition(draggedView, p, radius, rl, v);
                            showWhosTurn(player1TextView,player2TextView, madeAMill);
                            madeSuccessfulMove = true;

                        } else {
                            Toast.makeText(this.context, "Illegal Move1 ", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                    }
                    if(madeSuccessfulMove){
                        System.out.println("ID of successful move " + v.getId());
                        if (gameHandler.getTheGame().isThreeInARowAtPositionTo(v.getId())) {
                            Toast.makeText(this.context, "MILL!", Toast.LENGTH_SHORT).show();
                            madeAMill = true;
                            gameHandler.setState(GameState.DELETE);

                            gameHandler.getTheGame().showGamePlane();
                            if (redOrBlue == 1) {
                                gameHandler.getTheGame().setTurn(1);
                            } else {
                                gameHandler.getTheGame().setTurn(2);
                            }
                            showWhosTurn(player1TextView,player2TextView,madeAMill);
                            SaveHandler.createSaveFile((Activity)context, (context).getResources().getString(R.string.pathToSaveFile));
                            return true;
                        }
                        int winner = Util.checkForNoMoreMoves(gameHandler);
                        if (winner > 0)
                            playerWinToast(winner, player1TextView, player2TextView);

                        if (gameHandler.isAIgame()) {
                            makeAiMove();
                            int winner2 = Util.checkForNoMoreMoves(gameHandler);
                            if (winner2 > 0)
                                playerWinToast(winner2, player1TextView, player2TextView);
                        }
                        SaveHandler.createSaveFile((Activity)context, (context).getResources().getString(R.string.pathToSaveFile));
                        return true;
                    }
                    else{
                        Toast.makeText(this.context,"Illegal Move ",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            case DragEvent.ACTION_DRAG_ENDED:

                 System.out.println("Who's turn now " + gameHandler.getTheGame().getTurn());

                // ViewGroup owner = (ViewGroup) draggedView.getParent();
                if(v.getTag().toString().equals(BIN_TAG)){
                    v.setBackground(binNormal);
                }
                else{
                    v.setBackground(normal);
                }
                draggedView.setVisibility(View.VISIBLE);
                return true;
                // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }

    /**
     * Updates the TextView when a player has made a move
     * @param player1TextView
     * @param player2TextView
     * @param madeAMill
     */
    private void showWhosTurn(TextView player1TextView, TextView player2TextView, boolean madeAMill) {
        int newTurn = gameHandler.getTheGame().getTurn();
        String yourTurn = "Your Turn";
        String takeAPiece = "Remove one your opponents pieces";
        if(newTurn == 1){
            if(madeAMill){
                player1TextView.setText(takeAPiece);
            }
            else{
                player1TextView.setText(yourTurn);
            }
            player2TextView.setText("");
        }
        else{

            if(madeAMill){
                player2TextView.setText(takeAPiece);
            }
            else{
                player2TextView.setText(yourTurn);
            }
            player1TextView.setText("");
        }

    }

    /**
     * Update the TextView when the player has one.
     * @param winner
     * @param player1TextView
     * @param player2TextView
     */
    private void playerWinToast(int winner, TextView player1TextView, TextView player2TextView) {
        String winnerText = "you win";
        if(winner == 4){
            player1TextView.setText(winnerText);
            Toast.makeText(this.context,"BLUE PLAYER WON",Toast.LENGTH_LONG).show();
        }
        if(winner == 5){
            player2TextView.setText(winnerText);
            Toast.makeText(this.context,"RED PLAYER WON",Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Remove the view from it's original position through view parent and places it
     * on a new position somewhere on the board
     *
     * @param draggedView : player's piece
     * @param p : layout params for Constraint Layout
     * @param radius : radius of the circular piece
     * @param rl : Constraint layout
     * @param v : the placeholder.
     */
    private void updateNewPosition(View draggedView, ConstraintLayout.LayoutParams p, int radius, ConstraintLayout rl, View v) {
        ViewGroup owner = (ViewGroup) draggedView.getParent();
        owner.removeView(draggedView);
        draggedView.setLayoutParams(p);
        draggedView.setVisibility(View.VISIBLE);
        Util.numberPiecePositionOnBoard(draggedView, v.getId());
        Util.boardPosition(v.getId(), draggedView, radius);
        rl.addView(draggedView);
    }

    /**
     * Remove the player piece from the board externally
     * @param draggedView
     * @param v
     * @param event
     */
    private void handleDelete(View draggedView, View v, DragEvent event) {
        draggedView.setVisibility(View.INVISIBLE);
        View view = (View) event.getLocalState();
        ViewGroup owner = (ViewGroup) view.getParent();
        owner.removeView(view);
    }

    /**
     * AI makes a move logically and then it is shown visually
     */
    private void makeAiMove() {
        if (gameHandler.makeAIMove()) {
            uiUpdaterForAi.updateViewFromAIMove();
            int winner = Util.checkForNoMoreMoves(gameHandler);
            if (winner > 0)
                playerWinToast(winner, player1TextView, player2TextView);
            int won = gameHandler.getTheGame().win();
            if (won > 0) {
                gameHandler.setState(GameState.GAMEOVER);
                playerWinToast(won,player1TextView,player2TextView);
                gameHandler.restartGame();
                SaveHandler.createSaveFile((Activity)context, (context).getResources().getString(R.string.pathToSaveFile));
            }
            SaveHandler.createSaveFile((Activity)context, (context).getResources().getString(R.string.pathToSaveFile));
        } else {
            Toast.makeText(this.context,"AI cannot Move ",Toast.LENGTH_SHORT).show();
        }
    }
}
