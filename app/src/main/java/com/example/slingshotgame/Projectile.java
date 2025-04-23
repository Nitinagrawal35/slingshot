package com.example.slingshotgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Projectile {
    private float x, y, radius;
    private float vx = 0, vy = 0;
    private float gravity = 0.5f;

    public Projectile(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void update() {
        x += vx;
        y += vy;
        vy += gravity;
    }

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
    }

    public boolean contains(float tx, float ty) {
        return Math.pow(tx - x, 2) + Math.pow(ty - y, 2) <= Math.pow(radius, 2);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        canvas.drawCircle(x, y, radius, paint);
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
