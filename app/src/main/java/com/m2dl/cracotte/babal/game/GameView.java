package com.m2dl.cracotte.babal.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.m2dl.cracotte.babal.game.domain.Ball;
import com.m2dl.cracotte.babal.game.domain.Direction;
import com.m2dl.cracotte.babal.game.domain.Music;
import com.m2dl.cracotte.babal.scores.ScoresActivity;

import static com.m2dl.cracotte.babal.R.*;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final float INITIAL_BALL_RADIUS = 30;
    private static final float INITIAL_BALL_SPEED = 2;
    private static final float INITIAL_BALL_ACCELERATION = (float) 1.003;
    public static final int MENU_LINES_WIDTH = 5;
    public static final int MENU_HEIGHT = 200;
    private static final int DEFAULT_TEXT_SIZE = 50;
    private static final String TEXT_MENU_SCORE = "Score";
    private static final String TEXT_MENU_CLICK_VALUE = "Valeur d'un clic";

    private GameThread thread;
    private Ball ball;
    private Music music;

    private float screenHeight;
    private float screenWidth;

    private int backgroundColor;
    private int menuColor;

    private long score;
    private int currentPoints;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        initThread();
        initGame();
    }

    private void initThread() {
        thread = new GameThread(getHolder(), this);
    }

    private void initGame() {
        initGameArea();
        initBall();
        initScore();
        initMusic();
        startThread();
        startMusic();
    }

    private void initGameArea() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    private void initBall() {
        ball = new Ball();
        ball.setPositionInX(getMiddleX());
        ball.setPositionInY(getMiddleY());
        ball.setDirection(Direction.NORTH);
        ball.setSpeed(INITIAL_BALL_SPEED);
        ball.setRadius(INITIAL_BALL_RADIUS);
        ball.setAcceleration(INITIAL_BALL_ACCELERATION);
    }

    private void initScore() {
        score = 0;
        currentPoints = 1;
    }

    public void initMusic() {
        music = new Music(getContext(), raw.music_game_1);
    }

    public void startThread() {
        thread.setRunning(true);
    }

    public void startMusic() {
        music.start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            drawBackground(canvas);
            drawBall(canvas);
            drawScoreMenu(canvas);
            drawMenu(canvas);
        }
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawColor(backgroundColor);
    }

    public void drawBall(Canvas canvas) {
        ball.drawInside(canvas);
    }

    public void drawScoreMenu(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(menuColor);
        for (int i = 0; i<= MENU_LINES_WIDTH; i++) {
            canvas.drawLine(0, MENU_HEIGHT + i, screenWidth, MENU_HEIGHT + i, paint);
        }
    }

    public void drawMenu(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(menuColor);
        paint.setTextSize(DEFAULT_TEXT_SIZE);
        int leftMarge = 100;
        int topMarge = (MENU_HEIGHT /3)*2;
        canvas.drawText(TEXT_MENU_CLICK_VALUE + " : " + currentPoints, leftMarge, topMarge, paint);
        canvas.drawText(TEXT_MENU_SCORE + " : " + score, screenWidth - (4*leftMarge), topMarge, paint);
    }

    public void update(){
        updateBallPosition();
        updateBallSpeed();
        updateCurrentPoints();
        updateColors();
        if (ball.isOutOf(this)) {
            endTheGame();
        }
    }

    private void updateBallPosition() {
        ball.move();
    }

    private void updateBallSpeed(){
        ball.speedUp();
    }

    public void updateCurrentPoints(){
        currentPoints = (int) Math.ceil(ball.getSpeed() / 5);
    }

    private void updateColors() {
        updateBackgroundColor();
        updateBallColor();
        updateMenuColor();
    }

    private void updateBackgroundColor() {
        backgroundColor = Color.WHITE;
    }

    private void updateBallColor() {
        float x = ball.getPositionInX();
        float y = ball.getPositionInY();
        float positionXPercentage = (x < screenWidth && x > 0) ? x / screenWidth * 100f : 0f;
        float positionYPercentage = (y < screenHeight && y > 0) ? y / screenHeight * 100f : 0f;
        int red = Math.round(positionXPercentage * (255f/100f));
        int green = 0;
        int blue = Math.round(positionYPercentage * (255f/100f));
        ball.setColor(Color.rgb(red, green, blue));
    }

    private void updateMenuColor(){
        menuColor = Color.BLACK;
    }

    private void endTheGame() {
        thread.setRunning(false);
        stopMusic();
        navigateToScoresActivity();
    }

    public void stopMusic() {
        music.stop();
    }

    public void navigateToScoresActivity() {
        Context context = getContext();
        Activity gameActivity = (Activity) context;
        Intent scoresIntent = new Intent().setClass(context, ScoresActivity.class);
        scoresIntent.putExtra("scorePerformed", score);
        scoresIntent.putExtra("hasNewScore", true);
        context.startActivity(scoresIntent);
        gameActivity.finish();
    }

    public void touchedScreenEvent() {
        changeBallDirection();
        incrementScore();
    }

    public void changeBallDirection() {
        ball.changeDirection();
    }

    public void incrementScore(){
        score += currentPoints;
    }

    public void resetSpeedAndAccelerate() {
        ball.setSpeed(INITIAL_BALL_SPEED);
        ball.accelerate(0.001f);
    }

    public void pauseMusic() {
        music.pause();
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

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getMiddleX() {
        return screenWidth / 2;
    }

    public float getMiddleY() {
        return screenHeight / 2 + MENU_HEIGHT;
    }
}
