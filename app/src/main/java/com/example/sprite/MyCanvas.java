package com.example.sprite;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class MyCanvas extends View {

    Croc mL;
    Resources res  = getResources();
    VectorDrawableCompat[] myBg;//todo wind affect, and moving clouds
    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //can use percentage of getWidth, getHeight
        int w = getWidth();
        int h = getHeight();
        mL = new Croc(getResources(), 0, h/5);

        VectorDrawableCompat island1 = VectorDrawableCompat.create(res, R.drawable.ic_island1, null);
        island1.setBounds(w/6, h/8, w/6+island1.getMinimumWidth()/2, h/8+island1.getMinimumHeight()/2);
        VectorDrawableCompat island2 = VectorDrawableCompat.create(res, R.drawable.ic_island2, null);
        island2.setBounds(0, 5*h/8, 0+island2.getMinimumWidth()/2, 5*h/8+island2.getMinimumHeight()/2);
        VectorDrawableCompat ladder = VectorDrawableCompat.create(res, R.drawable.ic_ladder, null);
        ladder.setBounds(w/3, 1*h/5, w/4, 3*h/4);//todo set bounds
        VectorDrawableCompat palmtree = VectorDrawableCompat.create(res, R.drawable.ic_palmtree, null);
        palmtree.setBounds(w/3, 3*h/5, 3*w/4, 3*h/4);//todo set bounds

        myBg = new VectorDrawableCompat[]{island1, island2, ladder, palmtree};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mL.draw(canvas);
        //Add areas
        for(VectorDrawableCompat i: myBg) i.draw(canvas);
        //invalidate();
    }
    public Croc getCroc() {
        return mL;
    }
}
//dark cozy forest backdrop
//move lion floating toggle button in bottom right corner

//once lands on a letter takes you to canvas to draw with "pencil" letter