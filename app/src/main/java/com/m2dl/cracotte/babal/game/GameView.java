package com.m2dl.cracotte.babal.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final GameThread thread;
    private enum Directions {NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST};

    private float screenHeight;
    private float screenWidth;
    private float ball_pos_x;
    private float ball_pos_y;
    private float ballSpeed;
    private float ballRadius;
    private Directions ballDirection;



    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        this.thread = new GameThread(getHolder(), this);
        initGame();
    }

    private void initGame(){
        initGameArea();
        initBall();
        thread.setRunning(true);
    }

    private void initGameArea(){
        screenWidth = this.getResources().getDisplayMetrics().widthPixels;
        screenHeight = this.getResources().getDisplayMetrics().heightPixels;
    }

    private void initBall(){
        this.ball_pos_x = getMiddleX();
        this.ball_pos_y = getMiddleY();
        this.ballDirection = Directions.NORTH;
        this.ballSpeed = (float) 2;
        this.ballRadius = 50;
    }

    public int getMiddleX() {
        return this.getResources().getDisplayMetrics().widthPixels / 2;
    }

    public int getMiddleY() {
        return this.getResources().getDisplayMetrics().heightPixels / 2;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(){
        updateBallPosition();
        updateBallMovement();
        assertBallInArea();

    }

    private void updateBallPosition(){
        float x_movement = 0;
        float y_movement = 0;
        switch (this.ballDirection){
            case NORTH:
                y_movement = -this.ballSpeed;
                break;
            case NORTH_EAST:
                x_movement = this.ballSpeed;
                y_movement = -this.ballSpeed;
                break;
            case EAST:
                x_movement = this.ballSpeed;
                break;
            case SOUTH_EAST:
                x_movement = this.ballSpeed;
                y_movement = this.ballSpeed;
                break;
            case SOUTH:
                y_movement = this.ballSpeed;
                break;
            case SOUTH_WEST:
                x_movement = -this.ballSpeed;
                y_movement = this.ballSpeed;
                break;
            case WEST:
                x_movement = -this.ballSpeed;
                break;
            case NORTH_WEST:
                x_movement = -this.ballSpeed;
                y_movement = -this.ballSpeed;
                break;
        }
        this.ball_pos_x += x_movement;
        this.ball_pos_y += y_movement;
    }

    private void updateBallMovement(){
        this.ballSpeed *= 1.005;
    }

    private void assertBallInArea(){
        if (this.ball_pos_x < 0 + this.ballRadius || this.ball_pos_y < 0 + this.ballRadius || this.ball_pos_x > screenWidth - ballRadius || this.ball_pos_y > screenHeight - ballRadius){
            stopGame();
        }
    }

    private void stopGame(){
        thread.setRunning(false);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            paint.setColor(Color.rgb(250, 0, 0));
            canvas.drawCircle(ball_pos_x, ball_pos_y, this.ballRadius, paint);
        }
    }
}
