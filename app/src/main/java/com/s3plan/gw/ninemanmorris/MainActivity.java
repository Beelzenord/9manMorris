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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;
import com.s3plan.gw.ninemanmorris.Model.SaveHandler;
import com.s3plan.gw.ninemanmorris.Model.SavedGames;

import java.util.ArrayList;

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
    private boolean useSavedUI;
    private ArrayList<ImageButton> viewsBlue;
    private ArrayList<ImageButton> viewsRed;


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

    private TextView player1TextView;

    private TextView player2TextView;

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
        uiUpdaterForAi = UiUpdaterForAI.getInstance();


        if (!gameHandler.isOngoingGame()) {
            if (SaveHandler.readSaveFile(this, getResources().getString(R.string.pathToSaveFile))) {
//                initSavedGame();
                useSavedUI = true;
            }
            else {
                useSavedUI = false;
                initFreshGame();
            }
        }


        /*gameHandler.setOngoingGame(true);
        savedGames = SavedGames.getInstance();
        if (savedGames.getSavedGames().size() <= 0) {
            SaveHandler.readSavedGames(this, getResources().getString(R.string.pathToSavedGamesFile));
        }*/




        /** for testing **/
//        gameHandler.tryLegalMove(1, 0, 2);
//        addSavedGame("first");
//        gameHandler.restartGame();
//        gameHandler.tryLegalMove(2, 0, 2);
//        gameHandler.tryLegalMove(3, 0, 1);
//        addSavedGame("second");


        // myDragEventListener = new MyDragEventListener();

    }

    private void initFreshGame() {
        bin = (FrameLayout) findViewById(R.id.binID);
        imageView = (View) findViewById(R.id.middleRightBall);
        linearLayoutPlayer1 = (LinearLayout) findViewById(R.id.playerPieces);
        linearLayoutPlayer2 = (LinearLayout) findViewById(R.id.playerPieces2);
        gameHandler.restartGame();
        gameHandler.setAIgame(false);
        initTextViews();
        myTouchListener = new MyTouchListener(this);
        myDragEventListener = new MyDragEventListener(this, player1TextView, player2TextView);
        initCheckers();
        initPlaceHolders();
    }

    private void resetFreshGame() {
        gameHandler.restartGame();
        myTouchListener = null;
        myTouchListener = new MyTouchListener(this);
        myDragEventListener = null;
        myDragEventListener = new MyDragEventListener(this, player1TextView, player2TextView);
        initTextViews();
        initCheckers();
    }

    private void initSavedGame() {
        bin = (FrameLayout) findViewById(R.id.binID);
        imageView = (View) findViewById(R.id.middleRightBall);
        linearLayoutPlayer1 = (LinearLayout) findViewById(R.id.playerPieces);
        linearLayoutPlayer2 = (LinearLayout) findViewById(R.id.playerPieces2);
        gameHandler.setAIgame(false);
        initTextViews();
        myTouchListener = new MyTouchListener(this);
        myDragEventListener = new MyDragEventListener(this, player1TextView, player2TextView);
        initCheckersFromModel();
        initPlaceHolders();
        initBoardFromModel();
    }

    private void resetSavedGame() {
        initTextViews();
        myTouchListener = new MyTouchListener(this);
        myDragEventListener = new MyDragEventListener(this, player1TextView, player2TextView);
        initCheckersFromModel();
        initBoardFromModel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Test", "onDestroy");
//        SaveHandler.createSaveFile(this, getResources().getString(R.string.pathToSaveFile));
    }

    private void findStuff(ViewGroup viewGroup, ArrayList<View> views, ArrayList<ViewGroup> viewGroups) {
        for (int i = 0, N = viewGroup.getChildCount(); i < N; i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                viewGroup.addView((ViewGroup) child);
                findStuff((ViewGroup) child,views,viewGroups);
            }
            else if(child instanceof View){
                views.add((View)child);
            }
        }
    }

    private void initTextViews() {

        player1TextView = (TextView) findViewById(R.id.player1Textfield);
        player2TextView = (TextView) findViewById(R.id.player2Textfield);
        if (gameHandler.getTheGame().getTurn() == 2) {
            player2TextView.setText("Your turn");
            player1TextView.setText("");
        } else {
            player1TextView.setText("Your turn");
            player2TextView.setText("");
        }
    }


    @SuppressLint("ResourceType")
    private void initPlaceHolders() {
        bin.setOnDragListener(myDragEventListener);
        bin.setTag(BIN_TAG);
        imageViews = Util.initViews(this, myDragEventListener);
        uiUpdaterForAi.setImageViews(imageViews);
    }

    private void initCheckers() {
        viewsBlue = new ArrayList<ImageButton>();
        viewsRed = new ArrayList<ImageButton>();

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
                viewsBlue.add(btnTag);
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
                viewsRed.add(btnTag);
            }
            linearLayoutPlayer2.addView(row);
        }
        uiUpdaterForAi.setLinearLayoutPlayer1(linearLayoutPlayer1);
        uiUpdaterForAi.setLinearLayoutPlayer2(linearLayoutPlayer2);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (useSavedUI) {
            initSavedGame();
            useSavedUI = false;
        }
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
                clearCheckers();
                gameHandler.restartGame();
                resetFreshGame();
                return true;
            case R.id.menu_load_game:
                Intent intent = new Intent(MainActivity.this, LoadGameActivity.class);
                startActivityForResult(intent, SELECT_SAVEDGAME);
                break;
            case R.id.menu_toggle_AI:
                gameHandler.toggleAI();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearCheckers() {
        if(viewsRed ==null || viewsBlue == null ){
            return;
        }
        for(ImageButton  im : viewsRed){
            if (im != null) {
                ViewGroup viewGroup = (ViewGroup) im.getParent();
                if (viewGroup != null)
                    viewGroup.removeView(im);
            }
        }
        for(ImageButton  im : viewsBlue){
            if (im != null) {
                ViewGroup viewGroup = (ViewGroup) im.getParent();
                if (viewGroup != null)
                    viewGroup.removeView(im);
            }
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
                    clearCheckers();
                    resetSavedGame();
                    break;
            }
        }

       /* ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        ArrayList<View> view = new ArrayList<View>();
        ArrayList<ViewGroup> viewGroups = new ArrayList<ViewGroup>();
        viewGroup.addView(viewGroup);
        findStuff(viewGroup,view,viewGroups);
        for(int i = 0  ; i < viewGroups.size() ; i++){
            System.out.println("VIEWGROUP " + viewGroups.get(i));
        }*/
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
        viewsBlue = new ArrayList<ImageButton>();
        viewsRed = new ArrayList<ImageButton>();

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
    }

    private void initBoardFromModel() {
        View view;
        int[] board = gameHandler.getTheGame().getGameplan();
        for (int i = 1; i < 25; i++) {
            int m;
            if ((m = board[i]) > 0) {
                if (m == gameHandler.getTheGame().BLUE_MARKER)
                    view = makeBlueView();
                else
                    view = makeRedView();
                View toView = imageViews[i];
                ViewGroup vg = (ViewGroup) toView.getParent();
                ConstraintLayout rl = (ConstraintLayout) vg.findViewById(R.id.mainConstraint);
                ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) toView.getLayoutParams();
                int radius = (toView.getRight() - toView.getLeft()) / 2;
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
        viewsBlue.add(btnTag);
        return btnTag;
    }
    private View makeRedView() {
        ImageButton btnTag = new ImageButton(this);
        btnTag.setOnTouchListener(myTouchListener);
        btnTag.setImageResource(R.drawable.circleplayertwo);
        btnTag.setBackgroundColor(Color.TRANSPARENT);
        btnTag.setTag(PLAYER2_RED);
        btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        viewsRed.add(btnTag);
        return btnTag;
    }
}





