package com.example.slingshotgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Target {
    private float x, y, radius;
    private boolean isHit = false;

    public Target(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(isHit ? Color.GREEN : Color.RED);
        canvas.drawCircle(x, y, radius, paint);
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        this.isHit = hit;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }
}
