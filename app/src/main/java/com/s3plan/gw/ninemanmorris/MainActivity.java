package com.s3plan.gw.ninemanmorris;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.s3plan.gw.ninemanmorris.IO.FileManager;
import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.Model.SaveHandler;
import com.s3plan.gw.ninemanmorris.Model.SavedGames;

public class MainActivity extends AppCompatActivity implements View.OnDragListener{
    public static final int SELECT_SAVEDGAME = 0;
    public static final String SAVEDGAME_RESULT = "SAVEDGAME_RESULT";
    private GameHandler gameHandler;
    private SavedGames savedGames;
    private ImageView nmnImg;
    private ImageButton pinkButton;
    private ConstraintLayout constraintLayout;
    private FrameLayout outerMost;
    private static final String IMAGEVIEW_TAG = "icon bitmap";
    private View imageView;

    int x;
    int y;
    int leftMost;
    int rightMost;

    //private MyDragEventListener myDragEventListener;

    private MyTouchListener myTouchListener;
    private LinearLayout linearLayout;


    //Version 2


    private MyDragEventListener myDragEventListener;




    @Override
    protected void onStart() {
        super.onStart();
        gameHandler = GameHandler.getInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameHandler = GameHandler.getInstance();
        if (!gameHandler.isOngoingGame()) {
            try { // might not need try catch but why not
                SaveHandler.readSaveFile(this, getResources().getString(R.string.pathToSaveFile));
            } catch (Exception e) {
                gameHandler.restartGame();
            }
        }
        gameHandler.setOngoingGame(true);
        savedGames = SavedGames.getInstance();
        if (savedGames.getSavedGames().size() <= 0) {
            SaveHandler.readSavedGames(this, getResources().getString(R.string.pathToSavedGamesFile));
        }
        /** for testing **/
        gameHandler.tryLegalMove(1, 0, 2);
        addSavedGame("first");
        gameHandler.restartGame();
        gameHandler.tryLegalMove(2, 0, 2);
        gameHandler.tryLegalMove(3, 0, 1);
        addSavedGame("second");





       // myDragEventListener = new MyDragEventListener();

        imageView = (View) findViewById(R.id.middleRightBall);
        linearLayout = (LinearLayout) findViewById(R.id.playerPieces);


       //Version 1
        myTouchListener = new MyTouchListener();


        //Version 2

        myDragEventListener = new MyDragEventListener();
        imageView.setOnDragListener(this);
        //imageView.setOnDragListener(this);
        //linearLayout.setOnDragListener(this);

        //linearLayout.setOnDragListener(this);
      //  imageView.setOnDragListener(this);
       // imageView.setOnDragListener(myDragListener);
        initCheckers();
        //pinkButton.setOnDragListener(myDragListener);
      // pinkButton.setOnDragListener(myDragListener);
        //linearLayout.setOnDragListener(myDragListener);
       // pinkButton.setOnTouchListener(myTouchListener);





    }

    private void initCheckers() {
        View[] viewsPlayer1 = new View[9];
        View[] viewsPlayer2 = new View[9];
        Drawable drawable = getDrawable(R.drawable.circleplayerone);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
       /* for(View v : viewsPlayer1){


            ImageButton btnTag = new ImageButton(this);

            btnTag.setOnTouchListener(myTouchListener);
            btnTag.setImageResource(R.drawable.circleplayerone);
            btnTag.setBackgroundColor(Color.TRANSPARENT);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            linearLayout.addView(btnTag);
        }*/

       // initialize checkers for player 1
        for (int i = 0; i < 3; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 3; j++) {
                ImageButton btnTag = new ImageButton(this);
                btnTag.setOnTouchListener(myTouchListener);
                btnTag.setImageResource(R.drawable.circleplayerone);
                btnTag.setBackgroundColor(Color.TRANSPARENT);
                btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                row.addView(btnTag);
            }
            linearLayout.addView(row);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
       /* if (hasFocus) {
            System.out.println("Right:"+outerMost.getRight());
            System.out.println("Left:"+outerMost.getLeft());
            System.out.println("Top:"+outerMost.getTop());
        }*/
    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        super.onPostResume();
    }

    // Handle Drag Events
    @Override
    public boolean onDrag(View v, DragEvent event) {
        final int action = event.getAction();
        System.out.println("Something here");
        // Handles each of the expected events
        Drawable drawable = getDrawable(R.drawable.circlesample);
        Drawable normal = getDrawable(R.drawable.circle);

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
                System.out.println("ENTERED");
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


                System.out.println("Fuckin exiting");
                v.invalidate();
                v.setVisibility(View.VISIBLE);

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

                View view2 = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) view2.getParent();
                owner.removeView(view2 );//remove the dragged view

             //   v.invalidate();
                v.setVisibility(View.VISIBLE);


                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:

                System.out.println("should end");

                // Turns off any color tinting
                //    v.clearColorFilter();

                // Invalidates the view to force a redraw
            //    v.invalidate();

                System.out.println("DROP");

                if(v.getVisibility() == View.VISIBLE){
                    System.out.println("should be visible");
                }


                View view = (View) event.getLocalState();
              //  view.setX(x_cord - (view.getWidth() / 2));
               // view.setY(y_cord - (view.getWidth() / 2));
                view.setVisibility(View.VISIBLE);


                // Does a getResult(), and displays what happened.
                if (event.getResult()) {
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG).show();

                }

                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                break;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveHandler.createSaveFile(this, getResources().getString(R.string.pathToSaveFile));
    }

    /**
     * The options menu for the activity is created.
     * @param menu The menu to be created.
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Handles a click on the options menu.
     * @param item The item clicked on.
     * @return true.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_game :
                gameHandler.restartGame();
                //TODO: update view
                return true;
            case R.id.menu_load_game :
                Intent intent = new Intent(MainActivity.this, LoadGameActivity.class);
                startActivityForResult(intent, SELECT_SAVEDGAME);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SELECT_SAVEDGAME:
                    String name = result.getStringExtra(SAVEDGAME_RESULT);
                    StringBuilder sb = new StringBuilder();
                    sb.append(getResources().getString(R.string.pathToSaveFilePrefix));
                    sb.append(name);
                    sb.append(getResources().getString(R.string.pathToSaveFileSuffix));
                    SaveHandler.readSaveFile(this, sb.toString());
                    break;
            }
        }
    }

    public void addSavedGame(String name) {
        if (!savedGames.getSavedGames().contains(name)) {
            StringBuilder sb = new StringBuilder();
            sb.append(getResources().getString(R.string.pathToSaveFilePrefix));
            sb.append(name);
            sb.append(getResources().getString(R.string.pathToSaveFileSuffix));
            SaveHandler.createSaveFile(this, sb.toString());
            savedGames.add(name);
            SaveHandler.createSavedGamesFile(this, getResources().getString(R.string.pathToSavedGamesFile));
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(getResources().getString(R.string.toastNameAlreadyExists));
            showToast(sb.toString());
        }

    }

    /**
     * Shows a short toast message in the middle of the screen.
     * @param msg The message to show.
     */
    private void showToast(String msg) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.setGravity(Gravity.CENTER, getResources().getInteger(R.integer.toastOffsetX), getResources().getInteger(R.integer.toastOffsetY));
        toast.getView().getBackground().setColorFilter(getResources().getColor(R.color.toastBackground), PorterDuff.Mode.SRC.SRC_IN);
        toast.show();
    }
}





