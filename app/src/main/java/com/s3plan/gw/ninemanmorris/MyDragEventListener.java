package com.s3plan.gw.ninemanmorris;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

                // Determines if this View can accept the dragged data

                /**
                 * Get A tag to Show whether or not we should accept this
                 */
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    // As an example of what your application might do,
                    // applies a blue color tint to the View to indicate that it can accept
                    // data.
                    // System.out.println("can insert here " + v.getId());
                    //v.setColorFilter(Color.BLUE);
                    //  v.setBackground(drawable);
                    // Invalidate the view to force a redraw in the new tint
                    // v.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
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

                // Applies a green tint to the View. Return true; the return value is ignored.

                //  v.setColorFilter(Color.GREEN);


                // Invalidate the view to force a redraw in the new tint
                System.out.println("ENTERED " + v.getId() + " " + event.getClipDescription().getLabel().toString());

                if (!Util.isThePieceOnThBoard((String) draggedView.getTag().toString())) { // returns false if it isn't on the board

                } else {

                }
                if (nineMenMorrisRules.gameHandler.getGameState() == GameState.DELETE) {

                    return false;
                }
                if(v.getTag().toString().equals(BIN_TAG)){
                    v.setBackground(binAlter);
                }
                else{
                    v.setBackground(drawable);
                    v.invalidate();
                }


                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                //   v.setVisibility(View.VISIBLE);
                return true;

            //exit draggable area
            case DragEvent.ACTION_DRAG_EXITED:

                /* if(Util.isThePieceOnThBoard((String)draggedView.getTag().toString()) && nineMenMorrisRules.gameHandler.getGameState() == GameState.DELETE){

                     View view = (View) event.getLocalState();
                     ViewGroup owner = (ViewGroup) view.getParent();
                     owner.removeView(view);

                }*/
                if(v.getTag().toString().equals(BIN_TAG)){
                    v.setBackground(binNormal);

                }
                else{
                    v.setBackground(normal);

                }

                v.invalidate();
                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                //    v.setColorFilter(Color.BLUE);

                // Invalidate the view to force a redraw in the new tint
                // v.invalidate();

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
                           draggedView.setVisibility(View.INVISIBLE);
                           View view = (View) event.getLocalState();
                           ViewGroup owner = (ViewGroup) view.getParent();
                           owner.removeView(view);
                           System.out.println("remove " + draggedView.getTag().toString());
                           int idToBeDeleted = Util.getIdNumberOfTheOccupiedPlaceHolder(draggedView.getTag().toString());
                           int playerPieceToBeRemoved = Util.getColorOfDraggedPiece(draggedView.getTag().toString());
                           //  nineMenMorrisRules.remove()
                           System.out.println("ID " + idToBeDeleted);
                           nineMenMorrisRules.getTurnStatement();
                           if(nineMenMorrisRules.remove(idToBeDeleted,playerPieceToBeRemoved)){
                               System.out.println("REMOVED");
                               nineMenMorrisRules.showGamePlane();
                               //nineMenMorrisRules.toggleTurn();
                           }
                           else{
                               System.out.println("NOT REMOVED");
                           }
                           nineMenMorrisRules.gameHandler.setState(GameState.PLACE);
                           return true;
                       }
                   }
                   else{
                       Toast.makeText(this.context,"Can't make that move",Toast.LENGTH_SHORT).show();
                   }

                   return false;
                }
                else{

                }

                  String data = event.getClipDescription().getLabel().toString();
                int radius = (v.getRight() - v.getLeft()) / 2;


                int redOrBlue = Integer.parseInt(data.trim().substring(data.length() - 1));
                if (nineMenMorrisRules.gameHandler.getGameState() == GameState.PLACE) {

                    if (nineMenMorrisRules.tryLegalMove(v.getId(), 0, redOrBlue)) {

                        ViewGroup owner = (ViewGroup) draggedView.getParent();
                        owner.removeView(draggedView);
                        draggedView.setLayoutParams(p);
                        draggedView.setVisibility(View.VISIBLE);
                        Util.numberPiecePositionOnBoard(draggedView, v.getId());
                        Util.boardPosition(v.getId(), draggedView, radius);
                        rl.addView(draggedView);
                        if (nineMenMorrisRules.isThreeInARowAtPositionTo(v.getId())) {

                            Toast.makeText(this.context, "MILL!", Toast.LENGTH_SHORT).show();
                            nineMenMorrisRules.gameHandler.setState(GameState.DELETE);

                            nineMenMorrisRules.showGamePlane();
                            nineMenMorrisRules.getBlueRedMarker();
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

                        return true;
                    } else {

                        v.setBackground(normal);
                        Toast.makeText(this.context, "Not your turn", Toast.LENGTH_SHORT).show();

                        return false;
                    }


                }

                //  nineMenMorrisRules.tryLegalMove(v.getId(),-1,v.get)
                //remove the dragged view

                //   v.invalidate();
                //     v.setVisibility(View.VISIBLE);


                // Returns true. DragEvent.getResult() will return true.


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



                // Turns off any color tinting
                //    v.clearColorFilter();

                // Invalidates the view to force a redraw
                //    v.invalidate();


                // System.out.println("Drag " + v.getId());

             /*   if (nineMenMorrisRules.gameHandler.getGameState() != GameState.DELETE) {
                    //
                    draggedView.setVisibility(View.VISIBLE);
                } else if (!event.getResult()) {

                    System.out.println("this is where we can delete");
                    if (nineMenMorrisRules.gameHandler.getGameState() == GameState.DELETE) {
                        View view = (View) event.getLocalState();
                           view.setVisibility(View.VISIBLE);
                           vg.removeView(draggedView);*/

                        // View view = (View) event.getLocalState();
                        // view.setVisibility(View.VISIBLE);
                        // view.setBackground(player1StationedIcon);
                        /*ViewGroup owner = (ViewGroup) draggedView.getParent();
                        owner.removeView(draggedView);*/
                        //        }

                        //      }

                        //  view.setX(x_cord - (view.getWidth() / 2));
                        // view.setY(y_cord - (view.getWidth() / 2));


                        // Does a getResult(), and displays what happened.
               /* if (event.getResult()) {
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG).show();

                }*/




                // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;



        }
        return false;
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
