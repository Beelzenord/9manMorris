package com.s3plan.gw.ninemanmorris;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
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

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GameHandler gameHandler;
    private ImageView nmnImg;
    private ImageButton pinkButton;
    private ConstraintLayout constraintLayout;
    private FrameLayout outerMost;
    private static final String IMAGEVIEW_TAG = "icon bitmap";
    private View imageView;
    private NineMenMorrisRules nineMenMorrisRules;
    private int playerTurn = 1;
    int x;
    int y;
    int leftMost;
    int rightMost;

    private static final String PLAYER1_RED = "RED1";
    private static final String PLAYER2_BLUE = "BLUE2";


    //private MyDragEventListener myDragEventListener;

    private MyTouchListener myTouchListener;
    private LinearLayout linearLayoutPlayer1;
    private LinearLayout linearLayoutPlayer2;


    //Version 2


    private MyDragEventListener myDragEventListener;




    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameHandler = GameHandler.getInstance();


       // myDragEventListener = new MyDragEventListener();

        imageView = (View) findViewById(R.id.middleRightBall);
        linearLayoutPlayer1 = (LinearLayout) findViewById(R.id.playerPieces);
        linearLayoutPlayer2 = (LinearLayout) findViewById(R.id.playerPieces2);


       //Version 1
        myTouchListener = new MyTouchListener();


        //Version 2

        nineMenMorrisRules = new NineMenMorrisRules();
        myDragEventListener = new MyDragEventListener(this,nineMenMorrisRules);
        imageView.setOnDragListener(myDragEventListener);

        initCheckers();
        initPlaceHolders();


        //now the model objects












    }





    @SuppressLint("ResourceType")
    private void initPlaceHolders() {
        View[] imageViews = new View[25];


        imageViews[3] = findViewById(R.id.outerMostTopLeft);
        imageViews[3].setId(3);
        //imageViews[3].setTag(2,"HOLDER_03");
        imageViews[3].setOnDragListener(myDragEventListener);


        imageViews[6] = findViewById(R.id.outerMostTopCenter);
        imageViews[6].setId(6);
       // imageViews[6].setTag(6,"HOLDER_06");
        imageViews[6].setOnDragListener(myDragEventListener);


        imageViews[9] = findViewById(R.id.outerMostTopRight);
        imageViews[9].setId(9);
       // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[9].setOnDragListener(myDragEventListener);


        imageViews[2] = findViewById(R.id.middleTopLeft);
        imageViews[2].setId(2);
       // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[2].setOnDragListener(myDragEventListener);

        imageViews[5] = findViewById(R.id.middleMostTopCenter);
        imageViews[5].setId(5);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[5].setOnDragListener(myDragEventListener);

        imageViews[8] = findViewById(R.id.middleMostTopRight);
        imageViews[8].setId(8);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[8].setOnDragListener(myDragEventListener);


        imageViews[1] = findViewById(R.id.innerTopLeft);
        imageViews[1].setId(1);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[1].setOnDragListener(myDragEventListener);

        imageViews[4] = findViewById(R.id.innerMostTopCenter);
        imageViews[4].setId(4);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[4].setOnDragListener(myDragEventListener);


        imageViews[7] = findViewById(R.id.innerMostTopRight);
        imageViews[7].setId(7);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[7].setOnDragListener(myDragEventListener);


        imageViews[24] = findViewById(R.id.middleLeftBall);
        imageViews[24].setId(24);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[24].setOnDragListener(myDragEventListener);

        imageViews[23] = findViewById(R.id.middleLeftmiddle);
        imageViews[23].setId(23);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[23].setOnDragListener(myDragEventListener);

        imageViews[22] = findViewById(R.id.innerLeftmiddle);
        imageViews[22].setId(22);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[22].setOnDragListener(myDragEventListener);

        imageViews[10] = findViewById(R.id.innerRightmiddleBall);
        imageViews[10].setId(10);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[10].setOnDragListener(myDragEventListener);

        imageViews[11] = findViewById(R.id.middleRightmiddleBall);
        imageViews[11].setId(11);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[11].setOnDragListener(myDragEventListener);

        imageViews[12] = findViewById(R.id.middleRightBall);
        imageViews[12].setId(12);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[12].setOnDragListener(myDragEventListener);

        imageViews[19] = findViewById(R.id.bottomLeftBallInner);
        imageViews[19].setId(19);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[19].setOnDragListener(myDragEventListener);


        imageViews[16] = findViewById(R.id.bottomCenterBallInner);
        imageViews[16].setId(16);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[16].setOnDragListener(myDragEventListener);

        imageViews[13] = findViewById(R.id.bottomRightBallInner);
        imageViews[13].setId(13);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[13].setOnDragListener(myDragEventListener);

        imageViews[20] = findViewById(R.id.bottomLeftBallMiddle);
        imageViews[20].setId(20);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[20].setOnDragListener(myDragEventListener);


        imageViews[17] = findViewById(R.id.bottomCenterBallMiddle);
        imageViews[17].setId(17);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[17].setOnDragListener(myDragEventListener);

        imageViews[14] = findViewById(R.id.bottomRightBallMiddle);
        imageViews[14].setId(14);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[14].setOnDragListener(myDragEventListener);


        imageViews[21] = findViewById(R.id.bottomLeftBallOuter);
        imageViews[21].setId(21);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[21].setOnDragListener(myDragEventListener);

        imageViews[18] = findViewById(R.id.bottomCenterBallOuter);
        imageViews[18].setId(18);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[18].setOnDragListener(myDragEventListener);

        imageViews[15] = findViewById(R.id.bottomRightBallOuter);
        imageViews[15].setId(15);
        // imageViews[9].setTag(9,"HOLDER_09");
        imageViews[15].setOnDragListener(myDragEventListener);





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
                btnTag.setTag(PLAYER1_RED);
                btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                row.addView(btnTag);
            }
            linearLayoutPlayer1.addView(row);
        }

        for (int i = 0; i < 3; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 3; j++) {
                ImageButton btnTag = new ImageButton(this);
                btnTag.setOnTouchListener(myTouchListener);
                btnTag.setImageResource(R.drawable.circleplayertwo);
                btnTag.setBackgroundColor(Color.TRANSPARENT);
                btnTag.setTag(PLAYER2_BLUE);
                btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                row.addView(btnTag);
            }
            linearLayoutPlayer2.addView(row);
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
            case R.id.menu_new_game:
                gameHandler.restartGame();
                //TODO: update view
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}





