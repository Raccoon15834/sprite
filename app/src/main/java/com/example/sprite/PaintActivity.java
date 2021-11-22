package com.example.sprite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class PaintActivity extends Activity {
    MyPaintCanvas scrn;
    AppCompatButton subBtn, clearBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        scrn = (MyPaintCanvas) findViewById(R.id.paintscrn);
        subBtn = (AppCompatButton) findViewById(R.id.subbtn);
        clearBtn = (AppCompatButton) findViewById(R.id.clearbtn);

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scrn.checkAnswer()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else scrn.clear();
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrn.clear();
            }
        });
        scrn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action=event.getAction();
                int touchX=(int)event.getX();
                int touchY=(int)event.getY();
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        break;

                    case MotionEvent.ACTION_MOVE:
                        scrn.addCirc(touchX,touchY);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

}
