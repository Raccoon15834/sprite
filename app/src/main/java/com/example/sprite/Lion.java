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

    }
}

