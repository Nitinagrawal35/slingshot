package com.example.slingshotgame;

import android.content.Context;
import android.graphics.*;
import android.view.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private SlingshotBall ball;
    private ArrayList<Target> targets;
    private Paint backgroundPaint, textPaint;
    private int score = 0;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        ball = new SlingshotBall(300, 900);

        targets = new ArrayList<>();
        addTargets();

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLACK);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        setFocusable(true);
    }

    private void addTargets() {
        targets.clear();
        targets.add(new Target(900, 700, 80));
        targets.add(new Target(1100, 600, 80));
        targets.add(new Target(850, 500, 80));
    }

    public void resetGame() {
        ball.reset(300, 900);
        score = 0;
        addTargets();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ball.handleTouch(event);
        return true;
    }

    public void update() {
        ball.update();

        boolean hitSomething = false;
        for (Target target : targets) {
            if (!target.isHit() && ball.checkCollision(target)) {
                target.setHit(true);
                score += 10;
                hitSomething = true;
            }
        }

        if (ball.isLaunched() && (ball.getY() > getHeight() || hitSomething)) {
            ball.reset(300, 900);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        ball.draw(canvas);

        for (Target target : targets) {
            target.draw(canvas);
        }

        canvas.drawText("Score: " + score, 50, 100, textPaint);
    }
}
