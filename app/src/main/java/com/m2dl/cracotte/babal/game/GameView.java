package com.m2dl.cracotte.babal.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.m2dl.cracotte.babal.game.domain.Direction;
import com.m2dl.cracotte.babal.scores.ScoresActivity;

import static com.m2dl.cracotte.babal.R.*;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final float INITIAL_BALL_RADIUS = 30;
    private static final float INITIAL_BALL_SPEED = 2;
    private static final float INITIAL_BALL_ACCELERATION = (float) 1.003;
    private static final int MENU_LINES_WIDTH = 5;
    private static final int MENU_HEIGHT = 200;
    private static final int DEFAULT_TEXT_SIZE = 50;
    private static final String TEXT_MENU_SCORE = "Score";
    private static final String TEXT_MENU_CLICK_VALUE = "Valeur d'un clic";

    private GameThread thread;
    private MediaPlayer mediaPlayer;

    private float screenHeight;
    private float screenWidth;
    private float ballPositionInX;
    private float ballPositionInY;
    private float ballSpeed;
    private float ballRadius;
    private float ballAcceleration;
    private Direction ballDirection;

    private int backgroundColor;
    private int ballColor;
    private int defaultColor;

    private long score;
    private int currentPoints;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        initThread();
        initMediaPlayer();
        initGame();
    }

    private void initThread() {
        thread = new GameThread(getHolder(), this);
    }

    private void initGame() {
        initGameArea();
        initBall();
        initScore();
        thread.setRunning(true);
        initMediaPlayer();
        startMusic();
    }

    private void initGameArea() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    private void initBall() {
        ballPositionInX = getMiddleX();
        ballPositionInY = getMiddleY();
        ballDirection = Direction.NORTH;
        ballRadius = INITIAL_BALL_RADIUS;
        ballAcceleration = INITIAL_BALL_ACCELERATION;
        resetSpeed();
    }

    private void initScore() {
        score = 0;
        currentPoints = 1;
    }

    public int getPixelWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    public int getPixelHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    public int getMiddleX() {
        return getPixelWidth() / 2;
    }

    public int getMiddleY() {
        return (getPixelHeight() + MENU_HEIGHT) / 2;
    }

    private void processColors() {
        processBackgroundColor();
        processBallColor();
        processDefaultColor();
    }

    private void processBackgroundColor() {
        backgroundColor = Color.WHITE;
    }

    private void processBallColor() {
        float positionXPercentage = (ballPositionInX < getPixelWidth() && ballPositionInX > 0) ? ballPositionInX / getPixelWidth() * 100f : 0f;
        float positionYPercentage = (ballPositionInY < getPixelHeight() && ballPositionInY > 0) ? ballPositionInY / getPixelHeight() * 100f : 0f;
        int red = Math.round(positionXPercentage * (255f/100f));
        int green = 0;
        int blue = Math.round(positionYPercentage * (255f/100f));
        ballColor = Color.rgb(red, green, blue);
    }

    private void processDefaultColor(){
        defaultColor = Color.BLACK;
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


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(backgroundColor);
            drawBall(canvas);
            drawScoreMenu(canvas);
            drawMenu(canvas);
        }
    }

    public void drawBall(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(ballColor);
        canvas.drawCircle(ballPositionInX, ballPositionInY, ballRadius, paint);
    }

    public void drawScoreMenu(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(defaultColor);
        for (int i = 0; i<= MENU_LINES_WIDTH; i++) {
            canvas.drawLine(0, MENU_HEIGHT + i, screenWidth, MENU_HEIGHT + i, paint);
        }
    }

    public void drawMenu(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(defaultColor);
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
        processColors();
        if (isBallOut()) {
            endTheGame();
        }
    }

    private void updateBallPosition() {
        float x_movement = 0;
        float y_movement = 0;
        switch (ballDirection) {
            case NORTH:
                y_movement = -ballSpeed;
                break;
            case NORTH_EAST:
                x_movement = ballSpeed;
                y_movement = -ballSpeed;
                break;
            case EAST:
                x_movement = ballSpeed;
                break;
            case SOUTH_EAST:
                x_movement = ballSpeed;
                y_movement = ballSpeed;
                break;
            case SOUTH:
                y_movement = ballSpeed;
                break;
            case SOUTH_WEST:
                x_movement = -ballSpeed;
                y_movement = ballSpeed;
                break;
            case WEST:
                x_movement = -ballSpeed;
                break;
            case NORTH_WEST:
                x_movement = -ballSpeed;
                y_movement = -ballSpeed;
                break;
        }
        ballPositionInX += x_movement;
        ballPositionInY += y_movement;
    }

    public void resetSpeed() {
        ballSpeed = INITIAL_BALL_SPEED;
        ballAcceleration += 0.001;
    }
  
    private void updateBallSpeed(){
        ballSpeed *= ballAcceleration;
    }

    public void touchedScreenEvent() {
        changeBallDirection();
        updateScore();
    }

    public void changeBallDirection() {
        Direction nouvelleDirection = ballDirection;
        while (nouvelleDirection == ballDirection) {
            nouvelleDirection = Direction.getRandom();
        }
        ballDirection = nouvelleDirection;
    }

    public void updateScore(){
        score += currentPoints;
    }

    public void updateCurrentPoints(){
        currentPoints = (int) Math.ceil(ballSpeed / 5);
    }

    private boolean isBallOut() {
        return isBallAtTop() || isBallAtBottom() || isBallAtLeft() || isBallAtRight();
    }

    private boolean isBallAtBottom() {
        return ballPositionInY > screenHeight - ballRadius;
    }

    private boolean isBallAtTop() {
        return ballPositionInY < MENU_HEIGHT + MENU_LINES_WIDTH + ballRadius;
    }

    private boolean isBallAtLeft() {
        return ballPositionInX < 0 + ballRadius;
    }

    private boolean isBallAtRight() {
        return ballPositionInX > screenWidth - ballRadius;
    }

    private void endTheGame() {
        thread.setRunning(false);
        stopMusic();
        launchScoresActivity();
    }

    public void launchScoresActivity() {
        Context context = getContext();
        Activity gameActivity = (Activity) context;
        Intent scoresIntent = new Intent().setClass(context, ScoresActivity.class);
        scoresIntent.putExtra("scorePerformed", score);
        scoresIntent.putExtra("hasNewScore", true);
        context.startActivity(scoresIntent);
        gameActivity.finish();
    }

    public void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(getContext(), raw.music_game_1);
    }

    public void startMusic() {
        if (mediaPlayer != null && Boolean.FALSE.equals(mediaPlayer.isPlaying())) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null && Boolean.TRUE.equals(mediaPlayer.isPlaying())) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null && Boolean.TRUE.equals(mediaPlayer.isPlaying())) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }
}
