package com.example.sprite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    MyCanvas scrn;
    ImageView tArr, bArr, rArr, lArr;
    Croc mL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrn = (MyCanvas)  findViewById(R.id.scrn);

        setUpArrows();//TODO rotate arrow images
    }

    private void setUpArrows() {
        tArr = (ImageView) findViewById(R.id.toptri);
        bArr = (ImageView) findViewById(R.id.bottom);
        rArr = (ImageView) findViewById(R.id.righttri);
        lArr = (ImageView) findViewById(R.id.lefttri);
        int centerX = tArr.getDrawable().getBounds().width()/2;
        int centerY = tArr.getDrawable().getBounds().height()/2;
        //rotate arrows
        Matrix matrix = new Matrix();
        bArr.setScaleType(ImageView.ScaleType.MATRIX);
        matrix.postRotate(180f, centerX, centerY);
        bArr.setImageMatrix(matrix);

        myArrowListener myListener = new myArrowListener();
        tArr.setOnTouchListener(myListener);
        bArr.setOnTouchListener(myListener);
        rArr.setOnTouchListener(myListener);
        lArr.setOnTouchListener(myListener);
    }

    public void startPaint(){
        Intent intent = new Intent(getApplicationContext(), PaintActivity.class);
        //intent.putExtra() put bundles of information
        startActivity(intent);
    }

    private class myArrowListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView editedV = (ImageView)findViewById(v.getId());
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                editedV.setImageResource(R.drawable.arrowselect);
                if(editedV.equals(tArr)) mL.up();
                if(editedV.equals(bArr)) mL.down();
                if(editedV.equals(rArr)) mL.left();
                if(editedV.equals(lArr)) mL.right();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                editedV.setImageResource(R.drawable.arrowselect);
            }
            return true;
        }
    }


    //Drag Event, MotionEvent
        //https://developer.android.com/reference/android/view/View.OnDragListener
        //https://developer.android.com/reference/android/view/DragEvent

}