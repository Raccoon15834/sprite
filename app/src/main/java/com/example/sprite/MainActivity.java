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
    Lion mL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrn = (MyCanvas)  findViewById(R.id.scrn);

        setUpArrows();//TODO rotate arrow images, sensing swipe direction,
    }

    private void setUpArrows() {
        tArr = (ImageView) findViewById(R.id.toptri);
        tArr.setOnDragListener(new myArrowListener());
    }

    public void startPaint(){
        Intent intent = new Intent(getApplicationContext(), PaintActivity.class);
        //intent.putExtra() put bundles of information
        startActivity(intent);
    }

    private class myArrowListener implements View.OnDragListener {
        //DrageEvent, MotionEvent
        //https://developer.android.com/reference/android/view/View.OnDragListener
        //https://developer.android.com/reference/android/view/DragEvent
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            //dragEvent.getAction().x
            switch(dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_LOCATION: // GOING
                    dragEvent.getX();
                    mL.centerX();
                    break;
                case DragEvent.ACTION_DROP: // RELEASE
                    break;

            }

            return true;
        }
    }

}