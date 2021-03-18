package com.m2dl.cracotte.babal.game.domain;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.m2dl.cracotte.babal.game.GameView;

public class GameObject {
    protected Direction direction;
    protected float positionInX;
    protected float positionInY;
    protected float speed;
    protected float radius;
    protected int color;
    protected int opacity;

    public GameObject() {
        this.positionInX = 0.0f;
        this.positionInY = 0.0f;
        this.direction = null;
        this.speed = 0.0f;
        this.radius = 0.0f;
        this.color = 0;
    }

    public void drawInside(Canvas canvas, Bitmap ballBitmap) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAlpha(opacity);
        canvas.drawCircle(positionInX, positionInY, radius, paint);
        canvas.drawBitmap(ballBitmap, positionInX-radius, positionInY-radius, paint);
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

    public boolean isOutOf(GameView gameView) {
        return isAtTopOf(gameView) || isAtBottomOf(gameView) || isAtLeftOf(gameView) || isAtRightOf(gameView);
    }

    public boolean isAtTopOf(GameView gameView) {
        return positionInY < GameView.MENU_HEIGHT + radius;
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

    public boolean touchedObject(float xPosition, float yPosition) {
        return (xPosition > positionInX - radius
                && xPosition < positionInX + radius
                && yPosition > positionInY - radius
                && yPosition < positionInY + radius);
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }
}
