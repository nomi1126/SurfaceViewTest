package com.example.nomiyan.surfaceviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class AnimationSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;
    Thread thread;
    int screen_width, screen_height;

    ArrayList<Ball> ballList = new ArrayList<Ball>();

    public AnimationSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
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

        while(thread != null){

            try{
                canvas = surfaceHolder.lockCanvas();

                // canvasを塗りつぶす（リセット効果）
                canvas.drawRect(0, 0, screen_width, screen_height, bgPaint);

                for (int i = 0, len = ballList.size(); i < len; i++) {
                    Ball tempBall = ballList.get(i);

                    int new_cx = tempBall.posX + tempBall.velocity * tempBall.dirX;
                    int new_cy = tempBall.posY + tempBall.velocity * tempBall.dirY;

                    // 跳ね返りの計算
                    if (new_cx - tempBall.radius < 0 || new_cx + tempBall.radius > screen_width) {
                        tempBall.updateDirX();
                        tempBall.setColor();
                    }
                    if (new_cy - tempBall.radius < 0 || new_cy + tempBall.radius > screen_height) {
                        tempBall.updateDirY();
                        tempBall.setColor();
                    }

                    // 色を設定
                    paint.setColor(tempBall.color);

                    // ボールの位置を更新
                    tempBall.setPosX(tempBall.velocity * tempBall.dirX);
                    tempBall.setPosY(tempBall.velocity * tempBall.dirY);

                    // 描画
                    canvas.drawCircle(tempBall.posX, tempBall.posY, tempBall.radius, paint);
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
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
        Ball ball = new Ball(50, 200, 300, 30, 1, 1);
        ballList.add(ball);
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

    /**
     * ボールの追加
     * @param radius
     * @param x
     * @param y
     * @param velocity
     */
    public void addBall(int radius, int x, int y, int velocity) {
        int dirArray[] = {1, -1};
        Random random  = new Random();
        Ball ball      = new Ball(radius, x, y, velocity, dirArray[random.nextInt(2)], dirArray[random.nextInt(2)]);
        ballList.add(ball);
    }
}