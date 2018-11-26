package com.s3plan.gw.ninemanmorris;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.MotionEvent;
import android.view.View;

public class MyTouchListener implements View.OnTouchListener{
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
           // String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
           // ClipData data = ClipData.newPlainText("", (String)v.getTag());

            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);

            System.out.println(" on Touch data " + data.toString());
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
