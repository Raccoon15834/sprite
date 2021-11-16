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
    private int SPEED = 10;
    Resources res;
    Bitmap mCroc;

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
    public Croc(Resources res, int l, int t){
        this.res = res;
        Drawable d = res.getDrawable(R.drawable.ic_croc);
        //mCroc = BitmapFactory.decodeResource(res, R.drawable.ic_croc);
        mCroc = drawableToBitmap(d);
        //mCroc= VectorDrawableCompat.create(res, R.drawable.ic_croc, null);
        left = l;
        top= t;
        right = l+mCroc.getWidth();
        bottom = t+mCroc.getHeight();;
       // mCroc.setBounds((int)left, (int)top,(int)right, (int)bottom);
    }

    public void left(){//offsets x and y
        offset(-SPEED, 0);
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
       // mCroc.draw(canvas);
//        Drawable c = mCroc.mutate();
//        Bitmap.createBitmap()
//        Bitmap bitmapCroc = Bitmap.createBitmap(c.getIntrinsicWidth(),
//                c.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Rect src = new Rect(0, 0, 50, 50);//todo change frame
        canvas.drawBitmap(mCroc, src, this, null);//todo crop!!!
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }//DONT NEED THIS PART?

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}

