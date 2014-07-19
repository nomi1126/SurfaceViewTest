package com.example.nomiyan.surfaceviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class AnimationSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    static final long FPS        = 20;
    static final long FRAME_TIME = 1000 / FPS;
    static final int BALL_R      = 100;
    SurfaceHolder surfaceHolder;
    Thread thread;
    int cx       = BALL_R,
            cy       = BALL_R,
            velocity = 20,
            dir_x    = 1,
            dir_y    = 1,
            screen_width,
            screen_height;

    public AnimationSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    /**
     * 乱数を返す
     * @param maxNum
     * @return
     */
    private int getRandomNum(int maxNum) {
        Random random = new Random();
        return random.nextInt(maxNum);
    }

    @Override
    public void run() {
        Canvas canvas = null;
        Paint paint   = new Paint();
        Paint bgPaint = new Paint();

        // Background
        bgPaint.setStyle(Style.FILL);
        bgPaint.setColor(Color.WHITE);

        // Ball
        paint.setStyle(Style.FILL);
        paint.setColor(Color.argb(255, getRandomNum(255), getRandomNum(255), getRandomNum(255)));

        long loopCount = 0;
        long waitTime  = 0;
        long startTime = System.currentTimeMillis();

        while(thread != null){

            try{
                loopCount++;
                canvas = surfaceHolder.lockCanvas();

                int new_cx = cx + velocity * dir_x;
                int new_cy = cy + velocity * dir_y;

                // 跳ね返りの計算
                if (new_cx - BALL_R < 0 || new_cx + BALL_R > screen_width) {
                    dir_x *= -1;
                    paint.setColor(Color.argb(255, getRandomNum(255), getRandomNum(255), getRandomNum(255)));
                }
                if (new_cy - BALL_R < 0 || new_cy + BALL_R > screen_height) {
                    dir_y *= -1;
                    paint.setColor(Color.argb(255, getRandomNum(255), getRandomNum(255), getRandomNum(255)));
                }

                // 描画
                canvas.drawRect(0, 0, screen_width, screen_height, bgPaint);
                canvas.drawCircle(cx += (velocity * dir_x), cy += (velocity * dir_y), BALL_R, paint);

                surfaceHolder.unlockCanvasAndPost(canvas);

//                waitTime = (loopCount * FRAME_TIME)
//                        - (System.currentTimeMillis() - startTime);
//                Log.d("surface", Long.toString(waitTime));

//                if( waitTime > 0 ){
//                    Thread.sleep(waitTime);
//                }
            }
            catch(Exception e){}
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screen_width  = width;
        screen_height = height;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

}