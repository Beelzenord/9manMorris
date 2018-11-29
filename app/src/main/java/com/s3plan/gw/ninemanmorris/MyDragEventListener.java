package com.s3plan.gw.ninemanmorris;

import android.content.ClipDescription;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.Model.GameState.GameState;
import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;

public class MyDragEventListener implements View.OnDragListener {

    private Context context;
    private GameHandler gameHandler;
    private UiUpdaterForAI uiUpdaterForAi;

    private Drawable player1StationedIcon;

    private static final String BIN_TAG = "BIN_TAG";
    private Drawable player2StationedIcon;


    private  NineMenMorrisRules nineMenMorrisRules;
    public MyDragEventListener(Context context, NineMenMorrisRules nineMenMorrisRules) {
        this.context = context;
        this.nineMenMorrisRules = nineMenMorrisRules;
        this.player1StationedIcon = this.context.getDrawable(R.drawable.playeronestationed);
        this.player2StationedIcon = this.context.getDrawable(R.drawable.playertwostationed);
        this.gameHandler = GameHandler.getInstance();
        this.uiUpdaterForAi = UiUpdaterForAI.getInstance();
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
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

                if (nineMenMorrisRules.gameHandler.getGameState() == GameState.DELETE) {
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

                nineMenMorrisRules.getTurnStatement();
                if(v.getTag().toString().trim().equals(BIN_TAG)){
                   if(nineMenMorrisRules.gameHandler.getGameState() == GameState.DELETE){
                       if(Util.isThePieceOnThBoard((String)draggedView.getTag().toString())){
                           handleDelete(draggedView,v,event);
                           int redOrBlue  = Util.getPlayerIdentiferFromCheckerPiece(draggedView.getTag().toString());
                           int idToBeDeleted = Util.getIdNumberOfTheOccupiedPlaceHolder(draggedView.getTag().toString());
                           int playerPieceToBeRemoved = Util.getColorOfDraggedPiece(draggedView.getTag().toString());
                           if(nineMenMorrisRules.remove(idToBeDeleted,playerPieceToBeRemoved)){
                               if(nineMenMorrisRules.win(nineMenMorrisRules.getRightColorMarker(redOrBlue))){
                                   System.out.println("win");
                                   playerWinToast(redOrBlue);
                                   return true;
                               }
                               nineMenMorrisRules.showGamePlane();
                           }
                           System.out.println("ID to be deleted " + redOrBlue);
                           if(nineMenMorrisRules.gameHandler.getGameState() == GameState.DRAG){
                               if (gameHandler.isAIgame()) {
                                   gameHandler.makeAIMove();
                                   uiUpdaterForAi.doAIMove();
                               }
                               return true;
                           }
                           if(nineMenMorrisRules.allCheckersOnTheBoard(1) && nineMenMorrisRules.allCheckersOnTheBoard(2)){
                               nineMenMorrisRules.gameHandler.setState(GameState.DRAG);
                           }
                           else{
                               nineMenMorrisRules.gameHandler.setState(GameState.PLACE);
                           }
                           if (gameHandler.isAIgame()) {
                               gameHandler.makeAIMove();
                               uiUpdaterForAi.doAIMove();
                           }
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
                    //false if taking from a board

                    //checkIfIts from the side

                    //reject it


                    /***/

                    if (Util.isThePieceOnThBoard(draggedView.getTag().toString()) && !nineMenMorrisRules.allCheckersOnTheBoard(redOrBlue)) {
                        Toast.makeText(this.context, "Use pieces outside the board", Toast.LENGTH_SHORT).show();
                        return false;
                    } else if ((Util.isThePieceOnThBoard(draggedView.getTag().toString()) && nineMenMorrisRules.allCheckersOnTheBoard(redOrBlue)) ||
                            nineMenMorrisRules.gameHandler.getGameState() == GameState.DRAG) {
                        from = Util.getIdNumberOfTheOccupiedPlaceHolder(draggedView.getTag().toString());

                        //do the move here
                        if(nineMenMorrisRules.isValidMove(v.getId(),from,redOrBlue)){
                            madeSuccessfulMove = true;
                            updateNewPosition(draggedView, p, radius, rl, v);
                        }
                        else{
                            Toast.makeText(this.context,"Can't make that move",Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                   // Log.i("Test", "To: " + v.getId() + " From: " + from);
                   /* if(nineMenMorrisRules.tryLegalMove(v.getId(),from,redOrBlue)){
                        //if in DRAG STATE
//                        if((Util.isThePieceOnThBoard(draggedView.getTag().toString()) && nineMenMorrisRules.allCheckersOnTheBoard(redOrBlue)) ||
//                                nineMenMorrisRules.gameHandler.getGameState() == GameState.DRAG){
//                            nineMenMorrisRules.switchPlace(from,playerPieceToBeRemoved);
//                        }

                        updateNewPosition(draggedView,p,radius,rl,v);*/
                    else if(nineMenMorrisRules.gameHandler.getGameState() == GameState.PLACE){
                        if (nineMenMorrisRules.tryLegalMove(v.getId(), from, redOrBlue)) {
                            updateNewPosition(draggedView, p, radius, rl, v);
                            madeSuccessfulMove = true;

                        } else {
                            Toast.makeText(this.context, "Illegal Move ", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                    }
                    if(madeSuccessfulMove){
                        System.out.println("ID of successful move " + v.getId());
                        if (nineMenMorrisRules.isThreeInARowAtPositionTo(v.getId())) {
                            Toast.makeText(this.context, "MILL!", Toast.LENGTH_SHORT).show();
                            nineMenMorrisRules.gameHandler.setState(GameState.DELETE);

                            nineMenMorrisRules.showGamePlane();
                            if (redOrBlue == 1) {
                                nineMenMorrisRules.setTurn(1);
                            } else {
                                nineMenMorrisRules.setTurn(2);
                            }
                            return true;
                        }
                        if (gameHandler.isAIgame()) {
                            gameHandler.makeAIMove();
                            uiUpdaterForAi.doAIMove();
                        }

                      return true;
                    }
                    else{
                        Toast.makeText(this.context,"Illegal Move ",Toast.LENGTH_SHORT).show();
                        return false;
                    }


                }





            case DragEvent.ACTION_DRAG_ENDED:



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

    private void playerWinToast(int loser) {
        String winner;
        if(loser ==1 ){
            winner = "Player 2 (red) wins";
        }
        else{
           winner = "Player 1 (blue) wins";
        }
        Toast.makeText(this.context, winner, Toast.LENGTH_SHORT).show();
    }

    private void updateNewPosition(View draggedView, ConstraintLayout.LayoutParams p, int radius, ConstraintLayout rl, View v) {
        ViewGroup owner = (ViewGroup) draggedView.getParent();
        owner.removeView(draggedView);
        draggedView.setLayoutParams(p);
        draggedView.setVisibility(View.VISIBLE);
        Util.numberPiecePositionOnBoard(draggedView, v.getId());
        Util.boardPosition(v.getId(), draggedView, radius);
        rl.addView(draggedView);
    }

    private void handleDelete(View draggedView, View v, DragEvent event) {
        draggedView.setVisibility(View.INVISIBLE);
        View view = (View) event.getLocalState();
        ViewGroup owner = (ViewGroup) view.getParent();
        owner.removeView(view);
    }

    private void updateBoardButton(int redOrBlue, View boardView) {
        if(redOrBlue==1){ // BLUE == 1
           boardView.setBackground(this.player1StationedIcon);
        }
        else{
            boardView.setBackground(this.player2StationedIcon);
        }
    }
}
