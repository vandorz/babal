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

import com.m2dl.cracotte.babal.R;
import com.m2dl.cracotte.babal.activites.ScoresActivity;
import com.m2dl.cracotte.babal.utils.Direction;

import static com.m2dl.cracotte.babal.R.*;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private final GameThread thread;
    private MediaPlayer mediaPlayer;

    private final float INITIAL_BALL_RADIUS = 30;
    private final float INITIAL_BALL_SPEED = 2;
    private final float INITIAL_BALL_ACCELERATION = (float) 1.003;
    private final int MENU_LINES_WIDTH = 5;
    private final int MENU_HEIGHT = 200;
    private final int DEFAULT_TEXT_SIZE = 50;
    private final String TEXTE_MENU_SCORE = "Score";
    private final String TEXTE_MENU_VALEUR_CLIC = "Valeur d'un clic";

    private float screenHeight;
    private float screenWidth;
    private float ball_pos_x;
    private float ball_pos_y;
    private float ballSpeed;
    private float ballRadius;
    private float ballAcceleration;
    private Direction ballDirection;

    private int backgroundColor;
    private int ballColor;
    private int defaultColor;

    private int score;
    private int currentPoints;

    /**
     * Constructeur
     */
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        this.thread = new GameThread(getHolder(), this);
        initMediaPlayer();
        initGame();
    }

    /**
     * Initialisations du jeu
     */
    private void initGame(){
        initGameArea();
        initBall();
        initScore();
        thread.setRunning(true);
        initMediaPlayer();
        startMusic();
    }

    private void initGameArea(){
        screenWidth = this.getResources().getDisplayMetrics().widthPixels;
        screenHeight = this.getResources().getDisplayMetrics().heightPixels;
    }

    private void initBall(){
        this.ball_pos_x = getMiddleX();
        this.ball_pos_y = getMiddleY();
        this.ballDirection = Direction.NORTH;
        this.ballRadius = INITIAL_BALL_RADIUS;
        this.ballAcceleration = INITIAL_BALL_ACCELERATION;
        resetSpeed();
    }

    private void initScore(){
        this.score = 0;
        this.currentPoints = 1;
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
        return (getPixelHeight() + MENU_HEIGHT) / 2;
    }

    /**
     * Couleurs
     */
    private void processColors() {
        processBackgroundColor();
        processBallColor();
        processDefaultColor();
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

    private void processDefaultColor(){
        this.defaultColor = Color.BLACK;
    }
    /**
     * Interface
     */
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
            canvas.drawColor(this.backgroundColor);
            drawBall(canvas);
            drawScoreMenu(canvas);
            drawMenu(canvas);
        }
    }

    public void drawBall(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(this.ballColor);
        canvas.drawCircle(ball_pos_x, ball_pos_y, this.ballRadius, paint);
    }

    public void drawScoreMenu(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(this.defaultColor);
        for (int i = 0; i<= MENU_LINES_WIDTH; i++){
            canvas.drawLine(0, MENU_HEIGHT + i, this.screenWidth, MENU_HEIGHT + i, paint);
        }
    }

    public void drawMenu(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(this.defaultColor);
        paint.setTextSize(DEFAULT_TEXT_SIZE);
        int leftMarge = 100;
        int topMarge = (MENU_HEIGHT /3)*2;
        canvas.drawText(TEXTE_MENU_VALEUR_CLIC + " : " + this.currentPoints, leftMarge, topMarge, paint);
        canvas.drawText(TEXTE_MENU_SCORE +" : " + this.score, this.screenWidth - (4*leftMarge), topMarge, paint);
    }

    /**
     * Update
     */
    public void update(){
        updateBallPosition();
        updateBallSpeed();
        updateCurrentPoints();
        processColors();
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

    public void resetSpeed(){
        this.ballSpeed = INITIAL_BALL_SPEED;
        this.ballAcceleration += 0.001;
    }
  
    private void updateBallSpeed(){
        this.ballSpeed *= this.ballAcceleration;
    }

    public void touchedScreenEvent(){
        changeBallDirection();
        updateScore();
    }

    public void changeBallDirection(){
        Direction nouvelleDirection = this.ballDirection;
        while (nouvelleDirection == this.ballDirection){
            nouvelleDirection = Direction.getRandom();
        }
        this.ballDirection = nouvelleDirection;
    }

    public void updateScore(){
        this.score += this.currentPoints;
    }

    public void updateCurrentPoints(){
        this.currentPoints = (int) Math.ceil(this.ballSpeed / 5);
    }

    private void assertBallInArea(){
        if (this.ball_pos_x < 0 + this.ballRadius || this.ball_pos_y < MENU_HEIGHT + MENU_LINES_WIDTH + this.ballRadius || this.ball_pos_x > screenWidth - ballRadius || this.ball_pos_y > screenHeight - ballRadius){
            stopGame();
        }
    }

    /**
     * Fin du jeu
     */
    private void stopGame(){
        thread.setRunning(false);
        stopMusic();
        launchScoresActivity();
    }

    public void launchScoresActivity(){
        Context context = getContext();
        Activity gameActivity = (Activity) context;
        Intent scoresIntent = new Intent().setClass(context, ScoresActivity.class);
        scoresIntent.putExtra("scorePerformed", this.score);
        context.startActivity(scoresIntent);
        gameActivity.finish();
    }

    /**
     * Musique
     */

    public void initMediaPlayer(){
        mediaPlayer = MediaPlayer.create(this.getContext(), raw.music_game_1);
    }

    public void startMusic(){
        if (mediaPlayer != null && Boolean.FALSE.equals(mediaPlayer.isPlaying())){
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    public void pauseMusic(){
        if (mediaPlayer != null && Boolean.TRUE.equals(mediaPlayer.isPlaying())) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }

    public void stopMusic(){
        if (mediaPlayer != null && Boolean.TRUE.equals(mediaPlayer.isPlaying())) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }



}
