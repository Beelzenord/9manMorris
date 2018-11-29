package com.s3plan.gw.ninemanmorris;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

public class Util {

    public static void boardPosition(int id, View view, int radius){

        System.out.println("obtained radius " + radius);

        //reset translation

        view.setTranslationX(0);
        view.setTranslationY(0);

        System.out.println("INVOKING BOARD POSITION " + view.getTag().toString());


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



        System.out.println("in switch ID " + id + " " + view.getTranslationX() + " " + view.getTranslationY());


        switch (id){
            case 1:  case 2:  case 3: view.setTranslationX(-radius); view.setTranslationY(-radius);break;
            case 4:  case 5:  case 6:
                System.out.println("Make translation here"); view.setTranslationY(-radius);break;
            case 7:  case 8:  case 9: view.setTranslationX(radius);view.setTranslationY(-radius); break;
            case 10: case 11: case 12 : view.setTranslationX(radius);break;
            case 13: case 14: case 15 : view.setTranslationX(radius); view.setTranslationY(radius);break;
            case 16: case 17: case 18 : view.setTranslationY(radius);break;
            case 19: case 20: case 21 : view.setTranslationX(-radius);view.setTranslationY(radius); break;
            case 22: case 23: case 24 : view.setTranslationX(-radius);
        }
    }

    public static int getIDOfDraggable(String tag){
        return Integer.valueOf(tag.indexOf(2));
    }
    public static boolean isThePieceOnThBoard(String tag){
        if(tag.length() < 4){
            System.out.println("not big enough");
            return false;
        }
        else{
         //   System.out.println("TAG : " + tag + " " + tag.charAt(3));
            int l = tag.length();
            String mini = tag.substring(3,tag.length());
         //   System.out.println("mini  " + mini );
         //   System.out.println("tag " + tag);
            int miniID = Integer.parseInt(mini);
        //    System.out.println("mini int " + mini);

            if(miniID > 0 && miniID < 25){
                return true;
            }
            return false;

        }
        }
       // System.out.println("is piece on the board: " + tag.substring(3,tag.length()-1));

        //int id = Integer.parseInt(tag.substring(3,tag.length()-1));
      /*  if(id >0 && id < 25){
            return true;
        }
        else{
            return false;
        }*/

    public static void numberPiecePositionOnBoard(View v, int id){
        String tag = (String)v.getTag();
        System.out.println("CURRENT TAG " + tag);
        String newID = String.valueOf(id);
        if(tag.length() > 3){
            System.out.println("substring: " +tag.substring(0,3));
            tag = tag.substring(0,3);
            tag +=newID;
            System.out.println("new tag(i): " + tag);
            v.setTag(tag);
        }
        else{
            tag +=newID;
            System.out.println("new tag(ii): " + tag);
            v.setTag(tag);
        }

    }

    public static int getIdNumberOfTheOccupiedPlaceHolder(String tag){
        return Integer.parseInt(tag.substring(3));
    }

    public static int getPlayerIdentiferFromCheckerPiece(String tag){
        System.out.println("TAG is " + tag + " " + tag.charAt(2));
        char tmp = tag.charAt(2);
        int a=Character.getNumericValue(tmp);
        return a;

    }

    public static void updateNewPositionFromModel(View draggedView, ConstraintLayout.LayoutParams p, int radius, ConstraintLayout rl, View v) {
        draggedView.setLayoutParams(p);
        Util.numberPiecePositionOnBoard(draggedView, v.getId());
        //Util.boardPosition(v.getId(), draggedView, radius);
        System.out.println("Updating new position " +v.getId()  + " " + draggedView.getTag().toString() + " " + radius);
        Util.boardPosition(v.getId(), draggedView, radius);
        rl.addView(draggedView);
        draggedView.setVisibility(View.VISIBLE);
    }

    public static int getColorOfDraggedPiece(String RorB){
        if( RorB.charAt(0) == 'B'){
            return 4;
        }
        else{
            return 5;
        }
    }

    @SuppressLint("ResourceType")
    public static View[] initViews(Activity activity, MyDragEventListener myDragEventListener){
/*
        int orientation = activity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("Landscape");
        } else {
            System.out.println("Portrait");
        }*/

        View[] imageViews = new View[25];
        imageViews[3] = activity.findViewById(R.id.outerMostTopLeft);
        imageViews[3].setId(3);
        imageViews[3].setTag("HOLDER_03");
        imageViews[3].setOnDragListener(myDragEventListener);


        imageViews[6] = activity.findViewById(R.id.outerMostTopCenter);
        imageViews[6].setId(6);
        imageViews[6].setTag("HOLDER_06");
        imageViews[6].setOnDragListener(myDragEventListener);


        imageViews[9] = activity.findViewById(R.id.outerMostTopRight);
        imageViews[9].setId(9);
        imageViews[9].setTag("HOLDER_09");
        imageViews[9].setOnDragListener(myDragEventListener);


        imageViews[2] = activity.findViewById(R.id.middleTopLeft);
        imageViews[2].setId(2);
        imageViews[2].setTag("HOLDER_02");
        imageViews[2].setOnDragListener(myDragEventListener);

        imageViews[5] = activity.findViewById(R.id.middleMostTopCenter);
        imageViews[5].setId(5);
        imageViews[5].setTag("HOLDER_05");
        imageViews[5].setOnDragListener(myDragEventListener);

        imageViews[8] = activity.findViewById(R.id.middleMostTopRight);
        imageViews[8].setId(8);
        imageViews[8].setTag("HOLDER_08");
        imageViews[8].setOnDragListener(myDragEventListener);


        imageViews[1] = activity.findViewById(R.id.innerTopLeft);
        imageViews[1].setId(1);
        imageViews[1].setTag("HOLDER_01");
        imageViews[1].setOnDragListener(myDragEventListener);

        imageViews[4] = activity.findViewById(R.id.innerMostTopCenter);
        imageViews[4].setId(4);
        imageViews[4].setTag("HOLDER_04");
        imageViews[4].setOnDragListener(myDragEventListener);


        imageViews[7] = activity.findViewById(R.id.innerMostTopRight);
        imageViews[7].setId(7);
        imageViews[7].setTag("HOLDER_07");
        imageViews[7].setOnDragListener(myDragEventListener);


        imageViews[24] = activity.findViewById(R.id.middleLeftBall);
        imageViews[24].setId(24);
        imageViews[24].setTag("HOLDER_24");
        imageViews[24].setOnDragListener(myDragEventListener);

        imageViews[23] = activity.findViewById(R.id.middleLeftmiddle);
        imageViews[23].setId(23);
        imageViews[23].setTag("HOLDER_23");
        imageViews[23].setOnDragListener(myDragEventListener);

        imageViews[22] = activity.findViewById(R.id.innerLeftmiddle);
        imageViews[22].setId(22);
        imageViews[22].setTag("HOLDER_22");
        imageViews[22].setOnDragListener(myDragEventListener);

        imageViews[10] = activity.findViewById(R.id.innerRightmiddleBall);
        imageViews[10].setId(10);
        imageViews[10].setTag("HOLDER_10");
        imageViews[10].setOnDragListener(myDragEventListener);

        imageViews[11] = activity.findViewById(R.id.middleRightmiddleBall);
        imageViews[11].setId(11);
        imageViews[11].setTag("HOLDER_11");
        imageViews[11].setOnDragListener(myDragEventListener);

        imageViews[12] = activity.findViewById(R.id.middleRightBall);
        imageViews[12].setId(12);
        imageViews[12].setTag("HOLDER_12");
        imageViews[12].setOnDragListener(myDragEventListener);

        imageViews[19] = activity.findViewById(R.id.bottomLeftBallInner);
        imageViews[19].setId(19);
        imageViews[19].setTag("HOLDER_19");
        imageViews[19].setOnDragListener(myDragEventListener);


        imageViews[16] = activity.findViewById(R.id.bottomCenterBallInner);
        imageViews[16].setId(16);
        imageViews[16].setTag("HOLDER_16");
        imageViews[16].setOnDragListener(myDragEventListener);

        imageViews[13] = activity.findViewById(R.id.bottomRightBallInner);
        imageViews[13].setId(13);
        imageViews[13].setTag("HOLDER_13");
        imageViews[13].setOnDragListener(myDragEventListener);

        imageViews[20] = activity.findViewById(R.id.bottomLeftBallMiddle);
        imageViews[20].setId(20);
        imageViews[20].setTag("HOLDER_20");
        imageViews[20].setOnDragListener(myDragEventListener);


        imageViews[17] = activity.findViewById(R.id.bottomCenterBallMiddle);
        imageViews[17].setId(17);
        imageViews[17].setTag("HOLDER_17");
        imageViews[17].setOnDragListener(myDragEventListener);

        imageViews[14] = activity.findViewById(R.id.bottomRightBallMiddle);
        imageViews[14].setId(14);
        imageViews[14].setTag("HOLDER_14");
        imageViews[14].setOnDragListener(myDragEventListener);


        imageViews[21] = activity.findViewById(R.id.bottomLeftBallOuter);
        imageViews[21].setId(21);
        imageViews[21].setTag("HOLDER_21");
        imageViews[21].setOnDragListener(myDragEventListener);

        imageViews[18] = activity.findViewById(R.id.bottomCenterBallOuter);
        imageViews[18].setId(18);
        imageViews[18].setTag("HOLDER_18");
        imageViews[18].setOnDragListener(myDragEventListener);

        imageViews[15] = activity.findViewById(R.id.bottomRightBallOuter);
        imageViews[15].setId(15);
        imageViews[15].setTag("HOLDER_15");
        imageViews[15].setOnDragListener(myDragEventListener);

        return  imageViews;
    }


}
