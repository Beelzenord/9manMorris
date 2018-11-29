package com.s3plan.gw.ninemanmorris;

import android.support.constraint.ConstraintLayout;
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
            case 4:  case 5:  case 6: view.setTranslationY(-radius);break;
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
        draggedView.setVisibility(View.VISIBLE);
        Util.numberPiecePositionOnBoard(draggedView, v.getId());
        Util.boardPosition(v.getId(), draggedView, radius);
        rl.addView(draggedView);
    }

    public static int getColorOfDraggedPiece(String RorB){
        if( RorB.charAt(0) == 'B'){
            return 4;
        }
        else{
            return 5;
        }
    }


}
