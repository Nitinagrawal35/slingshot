package com.example.slingshotgame;

import android.graphics.*;
import android.view.MotionEvent;

public class SlingshotBall {

    private float x, y, radius = 40;
    private float vx = 0, vy = 0;
    private float gravity = 1.5f;
    private boolean launched = false;
    private boolean dragging = false;

    public SlingshotBall(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

    public void handleTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (distance(event.getX(), event.getY(), x, y) <= radius + 30) {
                    dragging = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (dragging) {
                    x = event.getX();
                    y = event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (dragging) {
                    vx = (300 - x) / 10;
                    vy = (900 - y) / 10;
                    launched = true;
                    dragging = false;
                }
                break;
        }
    }

    public void update() {
        if (launched) {
            x += vx;
            y += vy;
            vy += gravity;
        }
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        canvas.drawCircle(x, y, radius, paint);
    }

    public boolean checkCollision(Target target) {
        return !target.isHit() &&
                distance(this.x, this.y, target.getX(), target.getY()) <= (this.radius + target.getRadius());
    }

    private float distance(float x1, float y1, float x2, float y2) {
        return (float)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void reset(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.vx = 0;
        this.vy = 0;
        this.launched = false;
        this.dragging = false;
    }

    public boolean isLaunched() {
        return launched;
    }

    public float getY() {
        return y;
    }
}
