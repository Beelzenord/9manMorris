package com.s3plan.gw.ninemanmorris;

import android.view.View;

public class Util {

    public static void boardPosition(int id, View view, int radius){

        System.out.println("obtained radius " + radius);

        //reset translation

        view.setTranslationX(0);
        view.setTranslationY(0);


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
            case 1:case 2:case 3: view.setTranslationX(-radius); view.setTranslationY(-radius);break;
            case 4: case 5: case 6: view.setTranslationY(-radius);break;
            case 7: case 8: case 9: view.setTranslationX(radius);view.setTranslationY(-radius); break;
            case 10: case 11: case 12 : view.setTranslationX(radius);break;
            case 13: case 14: case 15 : view.setTranslationX(radius); view.setTranslationY(radius);break;
            case 16: case 17: case 18 : view.setTranslationY(radius);break;
            case 19: case 20: case 21 : view.setTranslationX(-radius);view.setTranslationY(radius); break;
            case 22: case 23: case 24 : view.setTranslationX(-radius);
        }
    }
}
