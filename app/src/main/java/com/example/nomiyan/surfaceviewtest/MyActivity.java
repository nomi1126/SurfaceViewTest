package com.example.nomiyan.surfaceviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;


public class MyActivity extends Activity {

    AnimationSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surfaceView = new AnimationSurfaceView(this);
        setContentView(surfaceView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                surfaceView.addBall(50, (int)event.getX(), (int)event.getY(), (int)event.getX()/10);
        }
        return true;
    }
}
