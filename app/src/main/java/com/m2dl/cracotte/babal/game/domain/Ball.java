package com.m2dl.cracotte.babal.game.domain;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.m2dl.cracotte.babal.game.GameView;

import static com.m2dl.cracotte.babal.game.domain.Direction.*;

public class Ball {
    private Direction direction;
    private float positionInX;
    private float positionInY;
    private float speed;
    private float radius;
    private float acceleration;
    private int color;

    public Ball() {
        this.positionInX = 0.0f;
        this.positionInY = 0.0f;
        this.direction = null;
        this.speed = 0.0f;
        this.radius = 0.0f;
        this.acceleration = 0.0f;
        this.color = 0;
    }

    public void drawInside(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(positionInX, positionInY, radius, paint);
    }

    public void move() {
        float xMovement = 0.0f;
        float yMovement = 0.0f;
        switch (direction) {
            case NORTH:
                yMovement = -speed;
                break;
            case NORTH_EAST:
                xMovement = speed;
                yMovement = -speed;
                break;
            case EAST:
                xMovement = speed;
                break;
            case SOUTH_EAST:
                xMovement = speed;
                yMovement = speed;
                break;
            case SOUTH:
                yMovement = speed;
                break;
            case SOUTH_WEST:
                xMovement = -speed;
                yMovement = speed;
                break;
            case WEST:
                xMovement = -speed;
                break;
            case NORTH_WEST:
                xMovement = -speed;
                yMovement = -speed;
                break;
        }
        moveInX(xMovement);
        moveInY(yMovement);
    }

    public void moveInX(float movement) {
        positionInX += movement;
    }

    public void moveInY(float movement) {
        positionInY += movement;
    }

    public void accelerate(float acceleration) {
        this.acceleration += acceleration;
    }

    public void speedUp() {
        speed *= acceleration;
    }

    public void changeDirection() {
        Direction newDirection = direction;
        while (newDirection == direction) {
            newDirection = getRandom();
        }
        direction = newDirection;
    }

    public boolean isOutOf(GameView gameView) {
        return isAtTopOf(gameView) || isAtBottomOf(gameView) || isAtLeftOf(gameView) || isAtRightOf(gameView);
    }

    public boolean isAtTopOf(GameView gameView) {
        return positionInY < GameView.MENU_HEIGHT + GameView.MENU_LINES_WIDTH + radius;
    }

    public boolean isAtBottomOf(GameView gameView) {
        return positionInY > gameView.getScreenHeight() - radius;
    }

    public boolean isAtLeftOf(GameView gameView) {
        return positionInX < 0 + radius;
    }

    public boolean isAtRightOf(GameView gameView) {
        return positionInX > gameView.getScreenWidth() - radius;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getPositionInX() {
        return positionInX;
    }

    public void setPositionInX(float positionInX) {
        this.positionInX = positionInX;
    }

    public float getPositionInY() {
        return positionInY;
    }

    public void setPositionInY(float positionInY) {
        this.positionInY = positionInY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
