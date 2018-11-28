package com.s3plan.gw.ninemanmorris;

import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;

public class AIMover {
    private static AIMover aiMover;
    private View[] imageViews;
    private LinearLayout linearLayoutPlayer1;
    private LinearLayout linearLayoutPlayer2;
//    private MainActivity activity;
    private GameHandler gameHandler;

    private AIMover() {
        gameHandler = GameHandler.getInstance();
    }

    public static AIMover getInstance() {
        if (aiMover == null)
            aiMover = new AIMover();
        return aiMover;
    }

    public void setImageViews(View[] imageViews) {
        this.imageViews = imageViews;
    }

    public void setLinearLayoutPlayer1(LinearLayout linearLayoutPlayer1) {
        this.linearLayoutPlayer1 = linearLayoutPlayer1;
    }

    public void setLinearLayoutPlayer2(LinearLayout linearLayoutPlayer2) {
        this.linearLayoutPlayer2 = linearLayoutPlayer2;
    }

    public void updateUIfromAImove(int to, int from) {
        if (gameHandler.getTheGame().getBluemarker() > 0) {
            View fromView = getFromView(from);
            View toView = imageViews[to];
            ViewGroup vg = (ViewGroup) toView.getParent();
            ConstraintLayout rl = (ConstraintLayout) vg.findViewById(R.id.mainConstraint);
            ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) toView.getLayoutParams();
            int radius = (fromView.getRight() - fromView.getLeft()) / 2;
            updateNewPosition(fromView, p, radius, rl, toView);
        }

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

    private View getFromView(int from) {
        int t = gameHandler.getTheGame().getBluemarker() % 3;
        Log.i("Test", "T:: " + t);
        LinearLayout row = (LinearLayout)linearLayoutPlayer1.getChildAt(t);
        Log.i("Test", "crash");
        View fromView = row.getChildAt(0);
        return fromView;
    }
}
