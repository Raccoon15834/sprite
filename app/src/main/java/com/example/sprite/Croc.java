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
import android.view.WindowInsetsAnimation;
import android.widget.SeekBar;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class Croc extends RectF {
    private int SPEED = 5;
    private int legspeed = 10;
    private int counter = 0;
    Resources res;
    Bitmap mCroc;
    //rows
    final int LEFTLEG = 1;
    final int RIGHTLEG = 2;
    //cols
    final int RIGHTFACE = 1;
    final int LEFTFACE = 2;
    final int BACKFACE = 3;
    //continous movement
    float offsetX, offsetY;
    //current state
    int currFace, currLeg;
    //moving restrictions
    VectorDrawableCompat i1, i2, lad;

//    //can reuse your own construc tor in other custom constructors (kind of like super)
    public Croc(Resources res){
        this.res = res;
    }
    public Croc(Resources res, int l, int t, VectorDrawableCompat i1, VectorDrawableCompat i2, VectorDrawableCompat lad){
        this(res);
        Drawable d = res.getDrawable(R.drawable.ic_croc);
        mCroc = drawableToBitmap(d);

        left = l;
        top= t;
        right = l+mCroc.getWidth()/6;
        bottom = (float)(t+mCroc.getHeight()/9);
        currFace=RIGHTFACE;
        currLeg = LEFTLEG;
        offsetX = 0;
        offsetY = 0;
        this.i1 = i1;
        this.i2 = i2;
        this.lad = lad;
    }

    public void left(){//offsets x and y
        if(contains(centerX(), i1.getBounds().centerY())||contains(centerX(), i2.getBounds().centerY())) {
            currFace = LEFTFACE;
            offsetX = -SPEED;
            offsetY = 0;
        }
    }
    public void right(){
       if(contains(centerX(), i1.getBounds().centerY())||contains(centerX(), i2.getBounds().centerY())) {
           offsetX = SPEED;
           offsetY = 0;
           currFace = RIGHTFACE;
       }
    }
    public void up(){
        if(onLadder()) {
            offsetX = 0;
            offsetY = -SPEED;
            currFace = BACKFACE;
        }
    }
    public void down(){
        if(onLadder()) {
            offsetX = 0;
            offsetY = SPEED;
            currFace = BACKFACE;
        }
    }

    private boolean onLadder() {
        int ladderCenterX = lad.getBounds().centerX();
        if(contains(ladderCenterX, centerY())) return true;
        return false;
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
        offset(offsetX, offsetY);
        if(crocFallingOff()) stopMoving();
        counter = (counter+1)%legspeed;
        if(!(offsetX==0 && offsetY==0) && counter==0) changeLeg();

        Rect src = new Rect((currLeg-1)*mCroc.getWidth()/2, (currFace-1)*mCroc.getHeight()/3,
                currLeg*mCroc.getWidth()/2, currFace*mCroc.getHeight()/3);
        canvas.drawBitmap(mCroc, src, this, null);
    }


    private boolean crocFallingOff() {
        VectorDrawableCompat currObjOn = islandOn();
        Rect b = currObjOn.getBounds();
        //to far up or down on island - based on center x of island
        if(!currObjOn.equals(lad)) {
            if (bottom < b.centerY() || top > b.centerY())
                return true;
        }else{
           // if (top < lad.getBounds().bottom || bottom > lad.getBounds().top)
               // return true;//todo fix
        }
            //to far right or left
        if (right>b.right || left<b.left) return true;
        return false;
    }
    private VectorDrawableCompat islandOn() {
        if(contains(centerX(), i1.getBounds().centerY())) return i1;
        if (contains(centerX(), i2.getBounds().centerY())) return i2;
        return lad;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    public void stopMoving(){
        offsetX = 0;
        offsetY = 0;
    }
}

