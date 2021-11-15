package com.example.sprite;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.widget.SeekBar;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class Croc extends RectF {
    private int dX, dY;
    private int SPEED = 10;
    Resources res;

//    public Croc(float left, float top, float right, float bottom, int dX, int dY) {
//        super(left, top, right, bottom);
//        this.dX = dX;
//        this.dY = dY;
//    }
//    public Croc() {
//        this(1, 1, 1, 1, 1, 1);
//    }
//    //can reuse your own construc tor in other custom constructors (kind of like super)
    public Croc(Resources res){
        this.res = res;
    }
    public void left(){
        offset(-SPEED, 0);
        //automatically adjusts draw frame???
    }
    public void right(){
        offset(SPEED, 0);
    }
    public void up(){
        offset(0, -SPEED);
    }
    public void down(){
        offset(0, SPEED);
    }

    public void draw(Canvas canvas){
        VectorDrawableCompat mCroc = VectorDrawableCompat.create(res, R.drawable.ic_croc, null);
        mCroc.setBounds((int)left, (int)top,(int)right, (int)bottom);//todo fix bounds
        mCroc.draw(canvas);
    }
}

