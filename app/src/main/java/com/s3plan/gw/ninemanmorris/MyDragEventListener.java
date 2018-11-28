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

    private Drawable player1StationedIcon;

    private static final String BIN_TAG = "BIN_TAG";
    private Drawable player2StationedIcon;


    private  NineMenMorrisRules nineMenMorrisRules;
    public MyDragEventListener(Context context, NineMenMorrisRules nineMenMorrisRules) {
        this.context = context;
        this.nineMenMorrisRules = nineMenMorrisRules;
        this.player1StationedIcon = this.context.getDrawable(R.drawable.playeronestationed);
        this.player2StationedIcon = this.context.getDrawable(R.drawable.playertwostationed);
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

                System.out.println("TAG of entered : " + v.getTag().toString());
                System.out.println("ENTERED " + v.getId() + " " + event.getClipDescription().getLabel().toString());


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

                System.out.println("Entered Dropped ");
                nineMenMorrisRules.getTurnStatement();
                if(v.getTag().toString().trim().equals(BIN_TAG)){
                   if(nineMenMorrisRules.gameHandler.getGameState() == GameState.DELETE){
                       if(Util.isThePieceOnThBoard((String)draggedView.getTag().toString())){
                           handleDelete(draggedView,v,event);
                           
                           int idToBeDeleted = Util.getIdNumberOfTheOccupiedPlaceHolder(draggedView.getTag().toString());
                           int playerPieceToBeRemoved = Util.getColorOfDraggedPiece(draggedView.getTag().toString());
                           if(nineMenMorrisRules.remove(idToBeDeleted,playerPieceToBeRemoved)){
                               System.out.println("REMOVED");
                               nineMenMorrisRules.showGamePlane();
                           }
                           else{
                               System.out.println("NOT REMOVED");
                           }
                           if(nineMenMorrisRules.allCheckersOnTheBoard(1) && nineMenMorrisRules.allCheckersOnTheBoard(2)){
                               nineMenMorrisRules.gameHandler.setState(GameState.DRAG);
                           }
                           else{
                               nineMenMorrisRules.gameHandler.setState(GameState.PLACE);    
                           }
                           return true;
                       }
                   }
                   else{
                       Toast.makeText(this.context,"Can't make that move",Toast.LENGTH_SHORT).show();
                   }

                   return false;
                }
                else{
                    String data = event.getClipDescription().getLabel().toString();
                    int radius = (v.getRight() - v.getLeft()) / 2;
                    //identify which piece (blue or red)
                    int redOrBlue = Util.getPlayerIdentiferFromCheckerPiece(data.trim());
                    System.out.println("Data used to get ID: " + data.trim() + " id " + Util.getPlayerIdentiferFromCheckerPiece(data.trim()) + " dID " + draggedView.getId() + " dTag " + draggedView.getTag().toString());
                   // Integer.parseInt(data.trim().substring(data.length() - 1));
                    if (nineMenMorrisRules.gameHandler.getGameState() == GameState.PLACE ) {
                        System.out.println(" id " +v.getId() + "redOrBlue " + redOrBlue);
                        int from = 0;

                        //if the piece is from the board and and there is still pieces outside the board, because he can't drag cancel
                        // this is here in case one player is on drag but the other player still has pieces
                        if(Util.isThePieceOnThBoard(draggedView.getTag().toString()) && !nineMenMorrisRules.allCheckersOnTheBoard(redOrBlue)){
                            Toast.makeText(this.context,"Use pieces outside the board",Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        //else
                        if (nineMenMorrisRules.tryLegalMove(v.getId(), 0, redOrBlue)) {
                            System.out.println("(nineMenMorrisRules.tryLegalMove(v.getId(), 0, redOrBlue))");
                            updateNewPosition(draggedView,p,radius,rl,v);
                            if (nineMenMorrisRules.isThreeInARowAtPositionTo(v.getId())) {
                                Toast.makeText(this.context, "MILL!", Toast.LENGTH_SHORT).show();
                                nineMenMorrisRules.gameHandler.setState(GameState.DELETE);

                                nineMenMorrisRules.showGamePlane();
                                if (redOrBlue == 1) {
                                    nineMenMorrisRules.setTurn(1);
                                    System.out.println("Blue scored");
                                } else {
                                    nineMenMorrisRules.setTurn(2);
                                    System.out.println("Red scored");
                                }
                                // nineMenMorrisRules.toggleTurn();
                                return true;
                            }
                            AIMover aiMover = AIMover.getInstance();
                            GameHandler gameHandler = GameHandler.getInstance();
                            if (gameHandler.makeAIMove()) {
                                Log.i("Test", "AI MADE A MOVE");
                                aiMover.updateUIfromAImove(nineMenMorrisRules.getLatestTo(), nineMenMorrisRules.getLatestFrom());
                            }
                            else {
                                Log.i("Test", "AI did not make a move");
                            }
                            return true;
                }
                else{

                            Toast.makeText(this.context,"Not legal move",Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        v.setBackground(normal);
                        Toast.makeText(this.context, "Move is not permitted", Toast.LENGTH_SHORT).show();

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
