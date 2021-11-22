package com.example.sprite;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class MyCanvas extends View {

    Croc mL;
    Cloud[] clouds;
    Resources res  = getResources();
    VectorDrawableCompat island1, island2, ladder;
    VectorDrawableCompat[] myBg;//todo wind affect

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //can use percentage of getWidth, getHeight
        int w = getWidth();
        int h = getHeight();
        clouds = new Cloud[7];
        for(int i=0; i<clouds.length; i++) clouds[i] = new Cloud(w, h);

        island1 = VectorDrawableCompat.create(res, R.drawable.ic_island1, null);
        island1.setBounds(w/6, h/8, w/6+island1.getMinimumWidth()/2, h/8+island1.getMinimumHeight()/2);
        island2 = VectorDrawableCompat.create(res, R.drawable.ic_island2, null);
        island2.setBounds(0, 5*h/8, 0+island2.getMinimumWidth()/2, 5*h/8+island2.getMinimumHeight()/2);
        VectorDrawableCompat palmtree = VectorDrawableCompat.create(res, R.drawable.ic_palmtree, null);
        int ih= island1.getIntrinsicHeight();
        int iw= island1.getIntrinsicWidth();
        palmtree.setBounds(island1.getBounds().left, island1.getBounds().top-ih/15, island1.getBounds().left+iw/5,
                island1.getBounds().top+4*ih/15);
        ladder = VectorDrawableCompat.create(res, R.drawable.ic_ladder, null);
        ladder.setBounds(island1.getBounds().right-iw/5, island1.getBounds().top + ih/6,
                island1.getBounds().right, island2.getBounds().top+ ih/6);//todo set bounds

        myBg = new VectorDrawableCompat[]{palmtree, island1, island2, ladder};
        mL = new Croc(getResources(), island1.getBounds().left + iw/15, island1.getBounds().top +ih/8,
                island1, island2, ladder);//sets top left corner
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0; i<=clouds.length/2; i++) clouds[i].draw(canvas);
        for(VectorDrawableCompat i: myBg) i.draw(canvas);
        mL.draw(canvas);
        for(int i=clouds.length/2+1; i<clouds.length; i++) clouds[i].draw(canvas);


//        VectorDrawableCompat i1 = island1;
//        int w = i1.getIntrinsicWidth();
//        int h = i1.getIntrinsicHeight();
//        Rect i1bounds = i1.getBounds();
//        canvas.drawCircle(40, 40, 50, new Paint(Color.GREEN));
//        canvas.drawRect(i1bounds.left, i1bounds.top, i1bounds.left+50, i1bounds.top + 50, new Paint(Color.BLUE));
        invalidate();
    }
    public Croc getCroc() {
        return mL;
    }
    private class Cloud extends RectF {
        Bitmap bmp;
        int myForm;
        int speed;
        int w;
        public Cloud(int w, int h){
            this.w = w;
            bmp = Croc.drawableToBitmap(getResources().getDrawable(R.drawable.ic_clouds));
            top = (int)(30+Math.random()*(h-100));
            left=(int)(Math.random()*w);
            bottom = top + bmp.getHeight()/20;
            right = left + bmp.getWidth()/4;
            myForm = (int)(1+Math.random()*5);//5 different cloud shapes
            speed = (int)(1+Math.random()*3);//speed 2-6
        }
        public void draw(Canvas c){
            Rect src = new Rect(0, (myForm-1)*bmp.getHeight()/5,
                    bmp.getWidth(), myForm*bmp.getHeight()/5-20);
            c.drawBitmap(bmp, src, this, null);
            offset(speed, 0);//speed
            if(left>w) offset(-w-bmp.getWidth(), 0);
        }
    }
}

//once lands on a letter takes you to canvas to draw with "pencil" letter