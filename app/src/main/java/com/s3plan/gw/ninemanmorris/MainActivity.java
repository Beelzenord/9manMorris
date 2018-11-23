package com.s3plan.gw.ninemanmorris;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.opengl.Matrix;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ImageView nmnImg;
    Button enroll;
    Button ccfront;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;

        System.out.println("width : " + width + " " + height);

        int x = (9 * width) / 100;
        int y = (80 * height) / 100;

        /*Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
                R.drawable.morrisboard);


        Bitmap bitmap = bitmapOrg;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        long lengthbmp = imageInByte.length;
        System.out.println("length is + " + lengthbmp);



        enroll = new Button(this);
        verify = new Button(this);
        ccfront= new Button(this);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fframe);
        ViewGroup.LayoutParams params= frameLayout.getLayoutParams();
        int frameWidth = frameLayout.getWidth();
        int frameHeight = frameLayout.getHeight();

        Button button = new Button(this);
        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        p.leftMargin = frameWidth * (90 / 100);
        p.topMargin = frameHeight * (99 / 100);
        button.setLayoutParams(params);

        frameLayout.addView(button, p);*/




      /*  nmnImg = (ImageView) findViewById(R.id.nmID);

        int scaleWidth =  nmnImg.getWidth();
        int scaleHeight = nmnImg.getHeight();
        int maxHeight = nmnImg.getMaxHeight();
        int maxWidth = nmnImg.getMaxWidth();
        android.graphics.Matrix matrix = nmnImg.getImageMatrix();
        float[] matrixValues = new float[50];
        matrix.getValues(matrixValues);

        Log.i("Arrays" , Arrays.toString(matrixValues));

        Log.i("ToString" , "SW: "+ scaleWidth + ", SH: " + scaleHeight + ", MH " + maxHeight + ", MW " + maxWidth);*/
    }
}
