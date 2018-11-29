package com.s3plan.gw.ninemanmorris;

import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;

public class UiUpdaterForAI {
    private static UiUpdaterForAI uiUpdaterForAi;
    private View[] imageViews;
    private LinearLayout linearLayoutPlayer1;
    private LinearLayout linearLayoutPlayer2;
    private static GameHandler gameHandler;

    private UiUpdaterForAI() {
        gameHandler = GameHandler.getInstance();
    }

    public static UiUpdaterForAI getInstance() {
        if (uiUpdaterForAi == null)
            uiUpdaterForAi = new UiUpdaterForAI();
        return uiUpdaterForAi;
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

    public void handleDelete(int pos) {
        StringBuilder tag = new StringBuilder();
        tag.append("R,2");
        tag.append(pos);
        View tmp = imageViews[1];
        ViewGroup vg = (ViewGroup) tmp.getParent();
        ConstraintLayout rl = vg.findViewById(R.id.mainConstraint);
        View view = null;
        for (int i = rl.getChildCount() - 1; i >= 0; i--) {
            view = rl.getChildAt(i);
            if (tag.toString().equals(view.getTag()))
                break;
        }
        Log.i("Test", "the tag: " + view.getTag());
        view.setVisibility(View.INVISIBLE);
        vg.removeView(view);
    }

    public void updateUIfromAImove(int to, int from) {
        if (from > 0) {
            StringBuilder tag = new StringBuilder();
            tag.append("B,1");
            tag.append(from);
            View toView = imageViews[to];
            ViewGroup vg = (ViewGroup) toView.getParent();
            ConstraintLayout rl = vg.findViewById(R.id.mainConstraint);
            View fromView = null;
            for (int i = rl.getChildCount() - 1; i >= 0; i--) {
                fromView = rl.getChildAt(i);
                if (tag.toString().equals(fromView.getTag()))
                    break;
            }
            ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) toView.getLayoutParams();
            int radius = (fromView.getRight() - fromView.getLeft()) / 2;
            updateNewPosition(fromView, p, radius, rl, toView);
        }
        else {
            View fromView = getFromView();
            View toView = imageViews[to];
            ViewGroup vg = (ViewGroup) toView.getParent();
            ConstraintLayout rl = (ConstraintLayout) vg.findViewById(R.id.mainConstraint);
            ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) toView.getLayoutParams();
            int radius = (fromView.getRight() - fromView.getLeft()) / 2;
            updateNewPosition(fromView, p, radius, rl, toView);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rl.getChildCount(); i++) {
                View v = rl.getChildAt(i);
                sb.append((String)v.getTag() + " ");
            }
//            Log.i("Test", "tags: " + sb.toString());
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

    private View getFromView() {
        int t = gameHandler.getTheGame().getBluemarker() % 3;
        LinearLayout row = (LinearLayout)linearLayoutPlayer1.getChildAt(t);
        View view = row.getChildAt(0);
        return view;
    }

    public void doAIMove() {
        NineMenMorrisRules nineMenMorrisRules = gameHandler.getTheGame();
        updateUIfromAImove(nineMenMorrisRules.getLatestTo(), nineMenMorrisRules.getLatestFrom());
        if (nineMenMorrisRules.isThreeInARowAtPositionTo(nineMenMorrisRules.getLatestTo())) {
            nineMenMorrisRules.showGamePlane();
            nineMenMorrisRules.setTurn(1);
            gameHandler.makeAIRemove();
            uiUpdaterForAi.handleDelete(nineMenMorrisRules.getLatestRemove());
            nineMenMorrisRules.setTurn(2);
            nineMenMorrisRules.showGamePlane();
            // nineMenMorrisRules.toggleTurn();
        }
    }

}
