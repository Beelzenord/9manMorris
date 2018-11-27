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

import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;

public class MyDragEventListener implements View.OnDragListener {

    private Context context;

    private Drawable player1StationedIcon;

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

        View draggedView = (View) event.getLocalState();
        ViewGroup vg = (ViewGroup) v.getParent();
        ConstraintLayout rl = (ConstraintLayout) vg.findViewById(R.id.mainConstraint);
        ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) v.getLayoutParams();



        switch(action) {

            case DragEvent.ACTION_DRAG_STARTED:

                // Determines if this View can accept the dragged data

                /**
                 * Get A tag to Show whether or not we should accept this
                 */
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    // As an example of what your application might do,
                    // applies a blue color tint to the View to indicate that it can accept
                    // data.
                    System.out.println("can insert here");
                    //v.setColorFilter(Color.BLUE);
                    //  v.setBackground(drawable);
                    // Invalidate the view to force a redraw in the new tint
                    // v.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }
                // v.invalidate();
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                v.setVisibility(View.VISIBLE);
                return false;

            //When users entered droppable area
            case DragEvent.ACTION_DRAG_ENTERED:

                // Applies a green tint to the View. Return true; the return value is ignored.

                //  v.setColorFilter(Color.GREEN);

                // Invalidate the view to force a redraw in the new tint
                System.out.println("ENTERED " +  v.getId() + " " + event.getClipDescription().getLabel().toString());

//                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
               // String dragData = item.getText().toString();

                //Toast.makeText(context, dragData, Toast.LENGTH_SHORT).show();

                //v.setBackground(drawable);


                //ImageView imageView = (ImageView) v;
                //   imageView.setImageResource(R.drawable.circlesample);
                // v.setBackgroundColor(getResources().getColor(R.color.colorAccent);
                // v.setBackgroundResource(R.drawable.circlesample);
                //   v.setVisibility(View.INVISIBLE);
                v.setBackground(drawable);
                v.invalidate();

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                //   v.setVisibility(View.VISIBLE);
                return true;

            //exit draggable area
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackground(normal);
                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                //    v.setColorFilter(Color.BLUE);

                // Invalidate the view to force a redraw in the new tint
                // v.invalidate();

                v.setVisibility(View.VISIBLE);

                v.invalidate();


                return true;

            case DragEvent.ACTION_DROP:

                // Gets the item containing the dragged data
              //  ClipData.Item item = event.getClipData().getItemAt(0);

                String data =  event.getClipDescription().getLabel().toString();

                int redOrBlue = Integer.parseInt(data.trim().substring(data.length() - 1));



                // Gets the text data from the item.
                //     dragData = item.getText();

                // Displays a message containing the dragged data.
                //    Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_LONG).show();

                // Turns off any color tints
                //      v.clearColorFilter();

                // Invalidates the view to force a redraw
                System.out.println("should invalidate " + redOrBlue);

                if(nineMenMorrisRules.tryLegalMove(v.getId(),0,redOrBlue)){

                    ViewGroup owner = (ViewGroup) draggedView.getParent();
                    owner.removeView(draggedView);
                    draggedView.setLayoutParams(p);
                    draggedView.setVisibility(View.VISIBLE);
                  //  View view2 = (View) event.getLocalState();
                   // ViewGroup owner = (ViewGroup) view2.getParent();
                   // owner.removeView(view2 );
                    // take the radius of the view so that the placed draggable is exactly where
                    //the circular place holder was originally
                    int radius = (v.getRight() - v.getLeft()) / 2;

                    Util.boardPosition(v.getId(),draggedView,radius);

                    rl.addView(draggedView);
                    v.setVisibility(View.INVISIBLE);

                    nineMenMorrisRules.showGamePlane();
                    return true;
                   // updateBoardButton(redOrBlue,v);
                }
                else{
                    System.out.println("It didn't work ");
                    v.setBackground(normal);
                    Toast.makeText(this.context,"It's not your move, bucko!",Toast.LENGTH_SHORT).show();

                    return false;
                }

              //  nineMenMorrisRules.tryLegalMove(v.getId(),-1,v.get)
              //remove the dragged view

                //   v.invalidate();
           //     v.setVisibility(View.VISIBLE);


                // Returns true. DragEvent.getResult() will return true.


            case DragEvent.ACTION_DRAG_ENDED:



                // Turns off any color tinting
                //    v.clearColorFilter();

                // Invalidates the view to force a redraw
                //    v.invalidate();




                View view = (View) event.getLocalState();
                //  view.setX(x_cord - (view.getWidth() / 2));
                // view.setY(y_cord - (view.getWidth() / 2));
                view.setVisibility(View.VISIBLE);


                // Does a getResult(), and displays what happened.
               /* if (event.getResult()) {
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG).show();

                }*/

                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
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
