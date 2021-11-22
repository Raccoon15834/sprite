package com.example.sprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Iterator;
import java.util.LinkedList;

public class MyPaintCanvas extends View {
    LinkedList<Circle> myCircs;
    Bitmap bmp;
    public MyPaintCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        myCircs = new LinkedList<Circle>();
    }

    public boolean checkAnswer() {//todo also check if there is enough circles in the first place
        int len =0;
        int numTrue = 0;
        Iterator<Circle> it= myCircs.iterator();
        while (it.hasNext()){
            if (it.next().overlap(bmp)) numTrue++;//always returns true?
            len++;
        }
        if (len==0) return false;
        int percentageCorrect = (numTrue/len*100);
        Log.i("percentage data", percentageCorrect+"%");
        Log.i("percentage data", len+" total");
        Log.i("percentage data", numTrue+" true");
        if (percentageCorrect>80) return true;
        return false;
    }

    public void clear() {
        myCircs = new LinkedList<Circle>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);//background color
        bmp = Croc.drawableToBitmap(getResources().getDrawable(R.drawable.ic_letter));
        Rect wholeBmp = new Rect(0,0,bmp.getWidth(), bmp.getHeight());
        canvas.drawBitmap(bmp, wholeBmp, canvas.getClipBounds(), null);
        Iterator<Circle> it= myCircs.iterator();
        while (it.hasNext()){
            it.next().draw(canvas);
        }
        invalidate();
    }
    public void addCirc(int x, int y){
        myCircs.add(new Circle(x, y));
    }

    private class Circle extends RectF {
        int x, y;
        int s = 30;
        public Circle(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void draw(Canvas canvas){
            canvas.drawCircle(x, y, s, new Paint(Color.YELLOW));//todo fix color
        }
        public boolean overlap(Bitmap bmpLetter){//thick letter,
            //if letter has center of circle
            if(bmpLetter.getPixel(x,y)==255) return false;
            return true;
        }
    }
}
