package com.s3plan.gw.ninemanmorris;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyDragEventListener implements View.OnDragListener {
    @Override
    public boolean onDrag(View v, DragEvent event) {
        final int action = event.getAction();
        System.out.println("Something here");
        // Handles each of the expected events
        switch(action) {

            case DragEvent.ACTION_DRAG_STARTED:

                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    // As an example of what your application might do,
                    // applies a blue color tint to the View to indicate that it can accept
                    // data.
                    //   v.setColorFilter(Color.BLUE);

                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }
                v.invalidate();
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:

                // Applies a green tint to the View. Return true; the return value is ignored.

                //  v.setColorFilter(Color.GREEN);

                // Invalidate the view to force a redraw in the new tint
                v.invalidate();

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                //    v.setColorFilter(Color.BLUE);

                // Invalidate the view to force a redraw in the new tint
                v.invalidate();
                return true;

            case DragEvent.ACTION_DROP:

                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                //     dragData = item.getText();

                // Displays a message containing the dragged data.
                //    Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_LONG).show();

                // Turns off any color tints
                //      v.clearColorFilter();

                // Invalidates the view to force a redraw
                System.out.println("should invalidate");
                v.invalidate();



                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:

                System.out.println("should end");

                // Turns off any color tinting
                //    v.clearColorFilter();

                // Invalidates the view to force a redraw
                v.invalidate();


                System.out.println("DROP");

                /*View view = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);//remove the dragged view
                LinearLayout container = (LinearLayout) v;//caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                container.addView(view);//Add the dragged view
                view.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE


                // Does a getResult(), and displays what happened.
                /*if (event.getResult()) {
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_LONG).show();

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
}
