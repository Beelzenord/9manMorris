package com.s3plan.gw.ninemanmorris;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.opengl.Matrix;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private GameHandler gameHandler;
    private ImageView nmnImg;
    private ImageButton pinkButton;
    private ConstraintLayout constraintLayout;
    private FrameLayout outerMost;
    private static final String IMAGEVIEW_TAG = "icon bitmap";

    int x;
    int y;
    int leftMost;
    int rightMost;




    @Override
    protected void onStart() {
        super.onStart();

        gameHandler = GameHandler.getInstance();
        gameHandler.setOngoingGame(true);
        System.out.println(gameHandler.typeOfCheckerAtPosition(1));
        gameHandler.tryLegalMove(1, 0, 1);
        System.out.println(gameHandler.typeOfCheckerAtPosition(1));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameHandler = GameHandler.getInstance();
        pinkButton = (ImageButton) findViewById(R.id.pinkball);

        pinkButton.setTag(IMAGEVIEW_TAG);

        pinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

                ClipData dragData = new ClipData(
                        (CharSequence) v.getTag(),
                        new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                        item);


                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(pinkButton);

                // Starts the drag

                v.startDrag(dragData,  // the data to be dragged
                        myShadow,  // the drag shadow builder
                        null,      // no need to use local data
                        0          // flags (not currently used, set to 0)
                );

            }


        });

            //pinkButton.setOnClickListener(new );



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_game:
                gameHandler.restartGame();
                //TODO: Reset view
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
