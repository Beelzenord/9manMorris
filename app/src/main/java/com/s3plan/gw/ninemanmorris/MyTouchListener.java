package com.s3plan.gw.ninemanmorris;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.Model.GameState.GameState;
import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;

public class MyTouchListener implements View.OnTouchListener{
    private Context context;
    private GameHandler gameHandler;

    public MyTouchListener(Context context) {
        this.gameHandler = GameHandler.getInstance();
        this.context = context;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
           // String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
           // ClipData data = ClipData.newPlainText("", (String)v.getTag());

            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);


            System.out.print("touch shows game plan ");
            String tag = new String( (String) v.getTag().toString());
            int viewID =  tag.charAt(2) - '0';
             //  System.out.println("Dragged Tag : " + (String) v.getTag() + " now: " + tag.charAt(2) );
            gameHandler.getTheGame().showGamePlane();
            if(gameHandler.getGameState() == GameState.DELETE){

                /*
                * public static final int BLUE_MOVES = 1; if it's 1, RED DELETES
	            public static final int RED_MOVES = 2; if it's 2 Blue deletes
                *
                * */

                //block Blue from
                //if it's red turns to delete from a blue but clicks a red
                if(gameHandler.getTheGame().getTurn()==2 && viewID ==2){
                    Toast.makeText(this.context,"Player 2 (red) must remove a piece",Toast.LENGTH_SHORT).show();
                    System.out.println("RED deletes ");
                    return false;

                }
                else if(gameHandler.getTheGame().getTurn() == 1 && viewID==1){
                    Toast.makeText(this.context,"Player 1 (blue) must remove a piece",Toast.LENGTH_SHORT).show();
                    System.out.println("BLUE deletes");
                    return false;
                }
            }
            else if(gameHandler.getGameState() == GameState.GAMEOVER){
                Toast.makeText(this.context,"Game is over",Toast.LENGTH_SHORT).show();
                return false;
            }
           // event.getClipData().getItemAt(0);


            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    v);
            v.startDrag(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }


    }
}
