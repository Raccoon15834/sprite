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
    int h2, w2;
    public MyPaintCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        myCircs = new LinkedList<Circle>();
    }

    public boolean checkAnswer() {
        int len =0;
        int numTrue = 0;
        Iterator<Circle> it= myCircs.iterator();
        while (it.hasNext()){
            if (it.next().overlap(bmp)) numTrue++;//always returns true?
            len++;
        }
        if (len==0) return false;
        double percentageCorrect= numTrue/len;
        Log.i("percentage data", percentageCorrect+"%");
        Log.i("percentage data", len+" total");
        Log.i("percentage data", numTrue+" true");
        if (percentageCorrect>0.80) return true;
        return false;
    }

    public void clear() {
        myCircs = new LinkedList<Circle>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(Color.BLACK);//background color
        bmp = Croc.drawableToBitmap(getResources().getDrawable(R.drawable.ic_letter));
        Rect wholeBmp = new Rect(0,0,bmp.getWidth(), bmp.getHeight());
        //canvas.drawBitmap(bmp, wholeBmp, canvas.getClipBounds(), null);
        //test bounds
        h2 = canvas.getHeight();
        w2 = canvas.getWidth();
        Rect position = new Rect(0,0,w2, h2);
        canvas.drawBitmap(bmp, wholeBmp, position, null);

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
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            canvas.drawCircle(x, y, s, paint);
        }
        public boolean overlap(Bitmap bmpLetter){//check on letter, same coordinates on bitmap?
            //if letter has center of circle

            Log.i("percent pixel check:",bmpLetter.getPixel(x,y)+"");
            if(bmpLetter.getPixel((int)(x),y)==0) return false; //transparent pixel, not on R
            return true;
        }
    }
}
