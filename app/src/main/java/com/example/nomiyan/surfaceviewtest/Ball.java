package com.example.nomiyan.surfaceviewtest;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by nomiyan on 2014/07/19.
 */
public class Ball {
    int radius, posX, posY, velocity, dirX, dirY, color;

    public Ball(int r, int x, int y, int v, int _dirX, int _dirY) {
        Random random = new Random();
        this.radius   = r;
        this.posX     = x;
        this.posY     = y;
        this.velocity = v;
        this.dirX     = _dirX;
        this.dirY     = _dirY;
        this.color    = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public void setPosX(int x) {
        this.posX += x;
    }

    public void setPosY(int y) {
        this.posY += y;
    }

    public void updateDirX() {
        this.dirX *= -1;
    }

    public void updateDirY() {
        this.dirY *= -1;
    }

    public void setColor() {
        Random random = new Random();
        this.color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
