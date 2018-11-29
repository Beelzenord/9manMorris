package com.s3plan.gw.ninemanmorris;

import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.Model.NineMenMorrisRules;

import java.util.ArrayList;

/**
 * This class is used to update the UI for AI moves.
 */
public class UiUpdaterForAI {
    private static UiUpdaterForAI uiUpdaterForAi;
    private View[] imageViews;
    private static GameHandler gameHandler;
    private ArrayList<ImageButton> redCheckers;
    private ArrayList<ImageButton> blueCheckers;

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

    /**
     * Updates the view when the AI removed a checkers from the board.
     * @param pos The position of the checker that was moved.
     */
    public void handleDelete(int pos) {
        StringBuilder tag = new StringBuilder();
        tag.append("R,2");
        tag.append(pos);
        View tmp = imageViews[1];
        ViewGroup vg = (ViewGroup) tmp.getParent();
        ConstraintLayout rl = vg.findViewById(R.id.mainConstraint);
        StringBuilder sb = new StringBuilder();
        View view = null;
        for (int i = rl.getChildCount() - 1; i >= 0; i--) {
            view = rl.getChildAt(i);
            if (view != null) {
                if (tag.toString().equals(view.getTag()))
                    break;
            }
        sb.append(view.getTag() + " ");
        }
        Log.i("Test", "the tag: " + view.getTag());
        Log.i("Test", "Arr: " + sb.toString());
        view.setVisibility(View.INVISIBLE);
        vg.removeView(view);
    }

    /**
     * Updates the view from an AI move.
     * @param to To position moved to.
     * @param from The position moved from.
     */
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
                if (tag.toString().equals(fromView.getTag())) //
                    break;
            }
            ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) toView.getLayoutParams();
            int radius = (toView.getRight() - toView.getLeft()) / 2;

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
        }
    }

    /**
     * Updates the position of the board when the AI moved a checkers.
     * @param draggedView The checker moved.
     * @param p The parameters for the constraint layout.
     * @param radius The radius of the checker.
     * @param rl The constraintlayout.
     * @param v The view the checker is to be placed on.
     */
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
        for (ImageView im : blueCheckers) {
            if (im != null) {
                if (((String)im.getTag()).length() <=3)
                    return im;
            }
        }
        return null;
    }

    /**
     * Updates the view for the move that was just made by the AI.
     */
    public void updateViewFromAIMove() {
        NineMenMorrisRules nineMenMorrisRules = gameHandler.getTheGame();
        updateUIfromAImove(nineMenMorrisRules.getLatestTo(), nineMenMorrisRules.getLatestFrom());
        if (nineMenMorrisRules.isThreeInARowAtPositionTo(nineMenMorrisRules.getLatestTo())) {
            nineMenMorrisRules.showGamePlane();
            nineMenMorrisRules.setTurn(nineMenMorrisRules.BLUE_MOVES);
            gameHandler.makeAIRemove();
            uiUpdaterForAi.handleDelete(nineMenMorrisRules.getLatestRemove());
            nineMenMorrisRules.setTurn(nineMenMorrisRules.RED_MOVES);
            nineMenMorrisRules.showGamePlane();
        }
    }

    public void setRedCheckers(ArrayList<ImageButton> redCheckers) {
        this.redCheckers = redCheckers;
    }

    public void setBlueCheckers(ArrayList<ImageButton> blueCheckers) {
        this.blueCheckers = blueCheckers;
    }
}
