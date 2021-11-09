package com.example.sprite;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCanvas extends View {

    Lion mL;
    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //can use percentage of getWidth, getHeight
        mL = new Lion();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(Color.GREEN);
        mL.draw(canvas);
        //TODO draw wavy trees in back ground
        //Add areas
        invalidate();
    }
    public Lion getLion() {
        return mL;
    }
}
//dark cozy forest backdrop
//move lion floating toggle button in bottom right corner

//once lands on a letter takes you to canvas to draw with "pencil" letter