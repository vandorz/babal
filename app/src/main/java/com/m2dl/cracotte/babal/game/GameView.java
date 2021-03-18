package com.m2dl.cracotte.babal.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.m2dl.cracotte.babal.game.domain.Ball;
import com.m2dl.cracotte.babal.game.domain.Bonus;
import com.m2dl.cracotte.babal.game.domain.BonusType;
import com.m2dl.cracotte.babal.game.domain.Direction;
import com.m2dl.cracotte.babal.game.domain.Music;
import com.m2dl.cracotte.babal.scores.ScoresActivity;
import com.m2dl.cracotte.babal.utils.services.MusicToggleService;

import java.util.ArrayList;
import java.util.List;

import static com.m2dl.cracotte.babal.R.*;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int MENU_HEIGHT = 200;
    private static final float INITIAL_BALL_SPEED = 4;
    private static final float INITIAL_BALL_ACCELERATION = (float) 1.003;
    private static final int INITIAL_BALL_OPACITY = 255;
    private static final float LIGHT_LOWER_THRESHOLD = 1;
    private static final float BALL_OPACITY_DECREASE = (float) 0.5;
    private static final float BALL_OPACITY_INCREASE = (float) 1;

    private GameThread thread;
    private Ball ball;
    private List<Bonus> bonusList;
    private Music music;

    private float screenHeight;
    private float screenWidth;

    private int backgroundColor;
    private int menuLeftColor;
    private int menuRightColor;
    private int menuTextColor;

    private long score;
    private int currentPoints;

    private float lightMeasurement;

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
        initBonusList();
        initScore();
        if (MusicToggleService.isMusicAllowed(this.getContext())) {
            initMusic();
            startThread();
            startMusic();
        } else {
            startThread();
        }
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
        ball.setRadius(screenWidth/30);
        ball.setOpacity(INITIAL_BALL_OPACITY);
        ball.setAcceleration(INITIAL_BALL_ACCELERATION);
    }

    private void initBonusList() {
        bonusList = new ArrayList<>();
    }

    private void initBonus(){
        float randomX = (float) randomNumber(-100, (int) screenWidth + 100);
        float randomY = (float) randomNumber(-500, (int)getMiddleY());
        Bonus bonus = new Bonus(randomX, randomY, 200,200, Direction.EAST);
        bonus.setSpeed(INITIAL_BALL_SPEED);
        bonus.setRadius(screenWidth/20);
        bonus.setOpacity(INITIAL_BALL_OPACITY);
        bonus.setColor(Color.rgb(0,255,0));
        bonusList.add(bonus);
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
            drawAllBonus(canvas);
            drawScoreMenu(canvas);
        }
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawColor(backgroundColor);
    }

    public void drawBall(Canvas canvas) {
        Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawable.ic_ball), (int)ball.getRadius()*2, (int)ball.getRadius()*2, true);
        ball.drawInside(canvas, bitmap);
    }

    public void drawAllBonus(Canvas canvas) {
        for (Bonus currentBonus : bonusList) {
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawable.ic_bonus), (int)currentBonus.getRadius()*2, (int)currentBonus.getRadius()*2, true);
            currentBonus.drawInside(canvas, bitmap);
        }
    }

    public void drawScoreMenu(Canvas canvas) {
        String textPoints = getResources().getString(string.game_textView_valeurClic) + " : " + currentPoints;
        String textScores = getResources().getString(string.game_textView_scoreActuel) + " : " + score;
        String textRebonds = getResources().getString(string.game_textView_cptRebonds) + " : " + ball.getBounceNumber();

        float menuSeparation = screenWidth * ((float)(textPoints.length() * 100 / (textPoints.length() + textScores.length())) / 100.0f);
        float textSize = 40;
        float textTop = (float)MENU_HEIGHT / 2 + textSize / 2;
        float textHalfUpper = (float)MENU_HEIGHT / 4 + textSize / 2;
        float textHalfLower = (float)MENU_HEIGHT - textSize;

        Paint menuLeftBackgroundPaint = new Paint();
        menuLeftBackgroundPaint.setColor(menuLeftColor);
        Paint menuRightBackgroundPaint = new Paint();
        menuRightBackgroundPaint.setColor(menuRightColor);
        Paint menuTextPaint = new Paint();
        menuTextPaint.setColor(menuTextColor);
        menuTextPaint.setTextSize(textSize);
        menuTextPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawRect(0, 0, menuSeparation, MENU_HEIGHT, menuLeftBackgroundPaint);
        canvas.drawRect(menuSeparation, 0, screenWidth, MENU_HEIGHT, menuRightBackgroundPaint);
        canvas.drawText(textPoints, menuSeparation / 2, textHalfUpper, menuTextPaint);
        canvas.drawText(textRebonds, menuSeparation / 2, textHalfLower, menuTextPaint);
        canvas.drawText(textScores, ((screenWidth - menuSeparation) / 2) + menuSeparation, textTop, menuTextPaint);
    }

    public void update() {
        updateBallPosition();
        randomizeBonusCreation();
        updateAllBonusPosition();
        updateBallSpeed();
        updateCurrentPoints();
        updateColors();
        if (ball.isOutOf(this)) {
            if (ball.getBounceNumber() <= 0){
                endTheGame();
            } else {
                ball.setBounceNumber(ball.getBounceNumber() - 1);
                ball.reverseDirection(this);
                ball.move();
            }

        }
    }

    private void updateBallPosition() {
        ball.move();
    }

    private void randomizeBonusCreation(){
        int randomNumber = (int) randomNumber(0,500);
        if (randomNumber == 1){
            initBonus();
        }
    }

    private void updateAllBonusPosition(){
        for (Bonus currentBonus : bonusList){
            currentBonus.move();
        }
    }

    private void updateBallSpeed() {
        ball.speedUp();
    }

    public void updateCurrentPoints() {
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
        if (lightMeasurement > LIGHT_LOWER_THRESHOLD && ball.getOpacity() > 0) {
            ball.setOpacity((int)(ball.getOpacity() - BALL_OPACITY_DECREASE));
        } else if (ball.getOpacity() < 255) {
            ball.setOpacity((int)(ball.getOpacity() + BALL_OPACITY_INCREASE));
        }
    }

    private void updateMenuColor() {
        menuLeftColor = Color.rgb(0, 127, 255);
        menuRightColor = Color.rgb(15, 157, 232);
        menuTextColor = Color.WHITE;
    }

    private void endTheGame() {
        thread.setRunning(false);
        if (MusicToggleService.isMusicAllowed(this.getContext())) {
            stopMusic();
        }
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

    public void touchedScreenEvent(float xPosition, float yPosition) {
        changeBallDirection();
        incrementScore();
        checkIfBonusTouched(xPosition, yPosition);
    }

    public void changeBallDirection() {
        ball.changeDirection();
    }

    public void incrementScore() {
        score += currentPoints;
    }

    private void checkIfBonusTouched(float xPosition, float yPosition){
        List<Bonus> bonusToRemove = new ArrayList<>();
        for (Bonus bonus : bonusList){
            if (bonus.isTouched(xPosition, yPosition) && bonus.isEnable()){
                bonus.setEnable(false);
                bonusToRemove.add(bonus);
                performBonus(bonus.getBonusType());
            }
        }
        for (Bonus bonus : bonusToRemove){
            bonusList.remove(bonus);
        }
    }

    private void performBonus(BonusType bonusType){
        switch (bonusType){
            case BOUNCE:
                ball.setBounceNumber(ball.getBounceNumber() + 3);
                break;
        }
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

    public void updateLightMeasurement(float lightMeasurement) {
        this.lightMeasurement = lightMeasurement;
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

    private double randomNumber(int min, int max){
        return (Math.random()*(max-min+1)+min);
    }
}
