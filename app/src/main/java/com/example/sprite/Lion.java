package com.example.sprite;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.widget.SeekBar;

public class Lion extends RectF {
    private int dX, dY;
    private int SPEED = 10;
    public Lion(float left, float top, float right, float bottom, int dX, int dY) {
        super(left, top, right, bottom);
        this.dX = dX;
        this.dY = dY;
    }

    public Lion() {
        this(1, 1, 1, 1, 1, 1);
    }
    //can reuse your own construc tor in other custom constructors (kind of like super)
    public void moveLeft(){
        offset(SPEED, SPEED);
    }
    public void draw(Canvas canvas){

    }
}

