package com.example.sprite;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.SeekBar;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class Croc extends RectF {
    private int SPEED = 5;
    Resources res;
    Bitmap mCroc;
    //rows
    final int LEFTLEG = 1;
    final int RIGHTLEG = 2;
    //cols
    final int RIGHTFACE = 1;
    final int LEFTFACE = 2;
    final int BACKFACE = 3;
    //current state
    int currFace, currLeg;


//    //can reuse your own construc tor in other custom constructors (kind of like super)
    public Croc(Resources res){
        this.res = res;
    }
    public Croc(Resources res, int l, int t){
        this.res = res;
        Drawable d = res.getDrawable(R.drawable.ic_croc);
        mCroc = drawableToBitmap(d);

        left = l;
        top= t;
        right = l+mCroc.getWidth()/6;
        bottom = (float)(t+mCroc.getHeight()/9);
        currFace=RIGHTFACE;
        currLeg = LEFTLEG;
    }

    public void left(){//offsets x and y
        offset(-SPEED, 0);
        changeLeg();
    }
    public void right(){
        offset(SPEED, 0);
        changeLeg();
    }
    public void up(){
        offset(0, -SPEED);
        changeLeg();
    }
    public void down(){
        offset(0, SPEED);
        changeLeg();
    }
    private void changeLeg() {
        if (currLeg == LEFTLEG) {
            currLeg = RIGHTLEG;
            offset(25, 0);
        }
        else{
            currLeg = LEFTLEG;
            offset(-25, 0);
        }
    }

    public void draw(Canvas canvas){

        Rect src = new Rect((currLeg-1)*mCroc.getWidth()/2, (currFace-1)*mCroc.getHeight()/3,
                currLeg*mCroc.getWidth()/2, currFace*mCroc.getHeight()/3);
        canvas.drawBitmap(mCroc, src, this, null);
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}

