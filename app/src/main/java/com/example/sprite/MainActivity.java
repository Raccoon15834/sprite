package com.example.sprite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
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
        mL = scrn.getLion();

        setUpArrows();
//        Matrix matrix = new Matrix();
//        imageView.setScaleType(ImageView.ScaleType.MATRIX);   //required
//        matrix.postRotate((float) angle, pivotX, pivotY);
//        imageView.setImageMatrix(matrix);
//        imageView.getDrawable().getBounds().width()/2
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
        tArr.setOnClickListener(new myArrowListener());
        bArr.setOnClickListener(new myArrowListener());
        rArr.setOnClickListener(new myArrowListener());
        lArr.setOnClickListener(new myArrowListener());
    }

    public void startPaint(){
        Intent intent = new Intent(getApplicationContext(), PaintActivity.class);
        //intent.putExtra() put bundles of information
        startActivity(intent);
    }

    private class myArrowListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case (int)tArr.getId():
                    mL.up(); break;
            }
        }
    }
}