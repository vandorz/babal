package com.m2dl.cracotte.babal.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final GameThread thread;
    private enum Directions {NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST};

    private float ball_pos_x;
    private float ball_pos_y;
    private float ball_speed;
    private Directions ballDirection;
    private int backgroundColor;
    private int ballColor;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        this.thread = new GameThread(getHolder(), this);
        initGame();
    }

    private void initGame(){
        initBall();
    }

    private void initBall(){
        this.ball_pos_x = getMiddleX();
        this.ball_pos_y = getMiddleY();
        this.ballDirection = Directions.NORTH;
        this.ball_speed = (float) 2;
    }

    public int getPixelWidth() {
        return this.getResources().getDisplayMetrics().widthPixels;
    }

    public int getPixelHeight() {
        return this.getResources().getDisplayMetrics().heightPixels;
    }

    public int getMiddleX() {
        return getPixelWidth() / 2;
    }

    public int getMiddleY() {
        return getPixelHeight() / 2;
    }
    
    private void processColors() {
        processBackgroundColor();
        processBallColor();
    }

    private void processBackgroundColor() {
        this.backgroundColor = Color.WHITE;
    }

    private void processBallColor() {
        float positionXPercentage = (ball_pos_x < getPixelWidth() && ball_pos_x > 0) ? ball_pos_x / getPixelWidth() * 100f : 0f;
        float positionYPercentage = (ball_pos_y < getPixelHeight() && ball_pos_y > 0) ? ball_pos_y / getPixelHeight() * 100f : 0f;
        int red = Math.round(positionXPercentage * (255f/100f));
        int green = 0;
        int blue = Math.round(positionYPercentage * (255f/100f));
        this.ballColor = Color.rgb(red, green, blue);
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
        processColors();
    }

    private void updateBallPosition(){
        float x_movement = 0;
        float y_movement = 0;
        switch (this.ballDirection){
            case NORTH:
                y_movement = -this.ball_speed;
                break;
            case NORTH_EAST:
                x_movement = this.ball_speed;
                y_movement = -this.ball_speed;
                break;
            case EAST:
                x_movement = this.ball_speed;
                break;
            case SOUTH_EAST:
                x_movement = this.ball_speed;
                y_movement = this.ball_speed;
                break;
            case SOUTH:
                y_movement = this.ball_speed;
                break;
            case SOUTH_WEST:
                x_movement = -this.ball_speed;
                y_movement = this.ball_speed;
                break;
            case WEST:
                x_movement = -this.ball_speed;
                break;
            case NORTH_WEST:
                x_movement = -this.ball_speed;
                y_movement = -this.ball_speed;
                break;
        }
        this.ball_pos_x += x_movement;
        this.ball_pos_y += y_movement;
    }

    private void updateBallMovement(){
        this.ball_speed *= 1.005;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(this.backgroundColor);
            Paint paint = new Paint();
            paint.setColor(this.ballColor);
            canvas.drawCircle(ball_pos_x, ball_pos_y, 50, paint);
        }
    }
}
