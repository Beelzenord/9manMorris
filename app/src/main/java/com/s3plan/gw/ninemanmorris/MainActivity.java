package com.s3plan.gw.ninemanmorris;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;
import com.s3plan.gw.ninemanmorris.Model.SaveHandler;
import com.s3plan.gw.ninemanmorris.Model.SavedGames;

public class MainActivity extends AppCompatActivity {
    public static final int SELECT_SAVEDGAME = 0;
    public static final String SAVEDGAME_RESULT = "SAVEDGAME_RESULT";
    private GameHandler gameHandler;
    private SavedGames savedGames;
    private ImageView nmnImg;
    private ImageButton pinkButton;
    private ConstraintLayout constraintLayout;
    private FrameLayout outerMost;
    private FrameLayout bin;
    private static final String IMAGEVIEW_TAG = "icon bitmap";
    private static final String BIN_TAG = "BIN_TAG";
    private View imageView;
    private NineMenMorrisRules nineMenMorrisRules;
    private UiUpdaterForAI uiUpdaterForAi;
    private View[] imageViews;


    int x;
    int y;
    int leftMost;
    int rightMost;

    private static final String PLAYER2_RED = "R,2";
    private static final String PLAYER1_BLUE = "B,1";


    //private MyDragEventListener myDragEventListener;

    private MyTouchListener myTouchListener;
    private LinearLayout linearLayoutPlayer1;
    private LinearLayout linearLayoutPlayer2;


    //Version 2


    private MyDragEventListener myDragEventListener;


    @Override
    protected void onStart() {
        super.onStart();
        gameHandler = GameHandler.getInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Test" , "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameHandler = GameHandler.getInstance();
        uiUpdaterForAi = UiUpdaterForAI.getInstance();
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
//        gameHandler.tryLegalMove(1, 0, 2);
//        addSavedGame("first");
//        gameHandler.restartGame();
//        gameHandler.tryLegalMove(2, 0, 2);
//        gameHandler.tryLegalMove(3, 0, 1);
//        addSavedGame("second");


        // myDragEventListener = new MyDragEventListener();
        bin = (FrameLayout) findViewById(R.id.binID);

        imageView = (View) findViewById(R.id.middleRightBall);
        linearLayoutPlayer1 = (LinearLayout) findViewById(R.id.playerPieces);
        linearLayoutPlayer2 = (LinearLayout) findViewById(R.id.playerPieces2);


        nineMenMorrisRules = new NineMenMorrisRules();
        nineMenMorrisRules.gameHandlerCohesion(gameHandler);
        gameHandler.setTheGame(nineMenMorrisRules);
        gameHandler.setAIgame(false);
        myTouchListener = new MyTouchListener(nineMenMorrisRules, this);


        myDragEventListener = new MyDragEventListener(this, nineMenMorrisRules);
        //imageView.setOnDragListener(myDragEventListener);

        initCheckers();
        initPlaceHolders();


        //now the model objects


    }


    @SuppressLint("ResourceType")
    private void initPlaceHolders() {
        bin.setOnDragListener(myDragEventListener);
        bin.setTag(BIN_TAG);
        imageViews = new View[25];


        imageViews[3] = findViewById(R.id.outerMostTopLeft);
        imageViews[3].setId(3);
        imageViews[3].setTag("HOLDER_03");
        imageViews[3].setOnDragListener(myDragEventListener);


        imageViews[6] = findViewById(R.id.outerMostTopCenter);
        imageViews[6].setId(6);
        imageViews[6].setTag("HOLDER_06");
        imageViews[6].setOnDragListener(myDragEventListener);


        imageViews[9] = findViewById(R.id.outerMostTopRight);
        imageViews[9].setId(9);
        imageViews[9].setTag("HOLDER_09");
        imageViews[9].setOnDragListener(myDragEventListener);


        imageViews[2] = findViewById(R.id.middleTopLeft);
        imageViews[2].setId(2);
        imageViews[2].setTag("HOLDER_02");
        imageViews[2].setOnDragListener(myDragEventListener);

        imageViews[5] = findViewById(R.id.middleMostTopCenter);
        imageViews[5].setId(5);
        imageViews[5].setTag("HOLDER_05");
        imageViews[5].setOnDragListener(myDragEventListener);

        imageViews[8] = findViewById(R.id.middleMostTopRight);
        imageViews[8].setId(8);
        imageViews[8].setTag("HOLDER_08");
        imageViews[8].setOnDragListener(myDragEventListener);


        imageViews[1] = findViewById(R.id.innerTopLeft);
        imageViews[1].setId(1);
        imageViews[1].setTag("HOLDER_01");
        imageViews[1].setOnDragListener(myDragEventListener);

        imageViews[4] = findViewById(R.id.innerMostTopCenter);
        imageViews[4].setId(4);
        imageViews[4].setTag("HOLDER_04");
        imageViews[4].setOnDragListener(myDragEventListener);


        imageViews[7] = findViewById(R.id.innerMostTopRight);
        imageViews[7].setId(7);
        imageViews[7].setTag("HOLDER_07");
        imageViews[7].setOnDragListener(myDragEventListener);


        imageViews[24] = findViewById(R.id.middleLeftBall);
        imageViews[24].setId(24);
        imageViews[24].setTag("HOLDER_24");
        imageViews[24].setOnDragListener(myDragEventListener);

        imageViews[23] = findViewById(R.id.middleLeftmiddle);
        imageViews[23].setId(23);
        imageViews[23].setTag("HOLDER_23");
        imageViews[23].setOnDragListener(myDragEventListener);

        imageViews[22] = findViewById(R.id.innerLeftmiddle);
        imageViews[22].setId(22);
        imageViews[22].setTag("HOLDER_22");
        imageViews[22].setOnDragListener(myDragEventListener);

        imageViews[10] = findViewById(R.id.innerRightmiddleBall);
        imageViews[10].setId(10);
        imageViews[10].setTag("HOLDER_10");
        imageViews[10].setOnDragListener(myDragEventListener);

        imageViews[11] = findViewById(R.id.middleRightmiddleBall);
        imageViews[11].setId(11);
        imageViews[11].setTag("HOLDER_11");
        imageViews[11].setOnDragListener(myDragEventListener);

        imageViews[12] = findViewById(R.id.middleRightBall);
        imageViews[12].setId(12);
        imageViews[12].setTag("HOLDER_12");
        imageViews[12].setOnDragListener(myDragEventListener);

        imageViews[19] = findViewById(R.id.bottomLeftBallInner);
        imageViews[19].setId(19);
        imageViews[19].setTag("HOLDER_19");
        imageViews[19].setOnDragListener(myDragEventListener);


        imageViews[16] = findViewById(R.id.bottomCenterBallInner);
        imageViews[16].setId(16);
        imageViews[16].setTag("HOLDER_16");
        imageViews[16].setOnDragListener(myDragEventListener);

        imageViews[13] = findViewById(R.id.bottomRightBallInner);
        imageViews[13].setId(13);
        imageViews[13].setTag("HOLDER_13");
        imageViews[13].setOnDragListener(myDragEventListener);

        imageViews[20] = findViewById(R.id.bottomLeftBallMiddle);
        imageViews[20].setId(20);
        imageViews[20].setTag("HOLDER_20");
        imageViews[20].setOnDragListener(myDragEventListener);


        imageViews[17] = findViewById(R.id.bottomCenterBallMiddle);
        imageViews[17].setId(17);
        imageViews[17].setTag("HOLDER_17");
        imageViews[17].setOnDragListener(myDragEventListener);

        imageViews[14] = findViewById(R.id.bottomRightBallMiddle);
        imageViews[14].setId(14);
        imageViews[14].setTag("HOLDER_14");
        imageViews[14].setOnDragListener(myDragEventListener);


        imageViews[21] = findViewById(R.id.bottomLeftBallOuter);
        imageViews[21].setId(21);
        imageViews[21].setTag("HOLDER_21");
        imageViews[21].setOnDragListener(myDragEventListener);

        imageViews[18] = findViewById(R.id.bottomCenterBallOuter);
        imageViews[18].setId(18);
        imageViews[18].setTag("HOLDER_18");
        imageViews[18].setOnDragListener(myDragEventListener);

        imageViews[15] = findViewById(R.id.bottomRightBallOuter);
        imageViews[15].setId(15);
        imageViews[15].setTag("HOLDER_15");
        imageViews[15].setOnDragListener(myDragEventListener);

        uiUpdaterForAi.setImageViews(imageViews);



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
                btnTag.setTag(PLAYER1_BLUE);
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
                btnTag.setTag(PLAYER2_RED);
                btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                row.addView(btnTag);
            }
            linearLayoutPlayer2.addView(row);
        }
        uiUpdaterForAi.setLinearLayoutPlayer1(linearLayoutPlayer1);
        uiUpdaterForAi.setLinearLayoutPlayer2(linearLayoutPlayer2);

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
    protected void onDestroy() {
        super.onDestroy();
        SaveHandler.createSaveFile(this, getResources().getString(R.string.pathToSaveFile));
    }

    /**
     * The options menu for the activity is created.
     *
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
     *
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
            case R.id.menu_load_game:
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
                    Log.i("Test", "Intent result");
                    String name = result.getStringExtra(SAVEDGAME_RESULT);
                    StringBuilder sb = new StringBuilder();
                    sb.append(getResources().getString(R.string.pathToSaveFilePrefix));
                    sb.append(name);
                    sb.append(getResources().getString(R.string.pathToSaveFileSuffix));
                    SaveHandler.readSaveFile(this, sb.toString());
                    initCheckersFromModel();
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
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(getResources().getString(R.string.toastNameAlreadyExists));
            showToast(sb.toString());
        }

    }

    /**
     * Shows a short toast message in the middle of the screen.
     *
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

    private void initCheckersFromModel() {
        NineMenMorrisRules nineMenMorrisRules2 = gameHandler.getTheGame();
        int c = nineMenMorrisRules2.getBluemarker();
        linearLayoutPlayer1.removeAllViews();
        LinearLayout[] rows = new LinearLayout[3];
        for (int i = 0; i < 3; i++) {
            rows[i] = new LinearLayout(this);
            rows[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayoutPlayer1.addView(rows[i]);
        }
        while (c > 0) {
            if (c != 0)
                rows[0].addView(makeBlueView());
            c--;
            if (c != 0)
                rows[1].addView(makeBlueView());
            c--;
            if (c != 0)
                rows[2].addView(makeBlueView());
            c--;
        }


        int c2 = nineMenMorrisRules2.getRedmarker();
        linearLayoutPlayer2.removeAllViews();
        LinearLayout[] rows2 = new LinearLayout[3];
        for (int i = 0; i < 3; i++) {
            rows2[i] = new LinearLayout(this);
            rows2[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayoutPlayer2.addView(rows2[i]);
        }
        while (c2 > 0) {
            if (c2 != 0)
                rows2[0].addView(makeRedView());
            c2--;
            if (c2 != 0)
                rows2[1].addView(makeRedView());
            c2--;
            if (c2 != 0)
                rows2[2].addView(makeRedView());
            c2--;
        }
        uiUpdaterForAi.setLinearLayoutPlayer1(linearLayoutPlayer1);
        uiUpdaterForAi.setLinearLayoutPlayer2(linearLayoutPlayer2);
        initBoardFromModel();
    }

    private void initBoardFromModel() {
        View view;
        int[] board = gameHandler.getTheGame().getGameplan();
        for (int i = 1; i < 25; i++) {
            int m;
            if ((m = board[i]) > 0) {
                if (m == 4)
                    view = makeBlueView();
                else
                    view = makeRedView();
                View toView = imageViews[m];
                ViewGroup vg = (ViewGroup) toView.getParent();
                ConstraintLayout rl = (ConstraintLayout) vg.findViewById(R.id.mainConstraint);
                ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) toView.getLayoutParams();
                int radius = (view.getRight() - view.getLeft()) / 2;
                Util.updateNewPositionFromModel(view, p, radius, rl, toView);
            }
        }

    }

    private View makeBlueView() {
        ImageButton btnTag = new ImageButton(this);
        btnTag.setOnTouchListener(myTouchListener);
        btnTag.setImageResource(R.drawable.circleplayerone);
        btnTag.setBackgroundColor(Color.TRANSPARENT);
        btnTag.setTag(PLAYER1_BLUE);
        btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return btnTag;
    }
    private View makeRedView() {
        ImageButton btnTag = new ImageButton(this);
        btnTag.setOnTouchListener(myTouchListener);
        btnTag.setImageResource(R.drawable.circleplayertwo);
        btnTag.setBackgroundColor(Color.TRANSPARENT);
        btnTag.setTag(PLAYER2_RED);
        btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return btnTag;
    }
}





