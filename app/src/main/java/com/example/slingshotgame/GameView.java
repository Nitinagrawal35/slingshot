package com.example.slingshotgame;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Paint paint;
    private Projectile projectile;
    private float slingX, slingY;
    private boolean isDragging = false;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        paint = new Paint();
        projectile = new Projectile(300, 800, 50);
        slingX = 300;
        slingY = 800;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    public void update() {
        projectile.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (projectile.contains(touchX, touchY)) {
                    isDragging = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    projectile.setPosition(touchX, touchY);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isDragging) {
                    isDragging = false;
                    float dx = slingX - touchX;
                    float dy = slingY - touchY;
                    projectile.setVelocity(dx / 5, dy / 5);
                }
                break;
        }

        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.LTGRAY);
            paint.setStrokeWidth(10);
            canvas.drawLine(slingX, slingY, projectile.getX(), projectile.getY(), paint);

            projectile.draw(canvas);
        }
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
    @Override public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
