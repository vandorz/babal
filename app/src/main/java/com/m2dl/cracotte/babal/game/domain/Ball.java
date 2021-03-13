package com.m2dl.cracotte.babal.game.domain;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.m2dl.cracotte.babal.game.GameView;

import static com.m2dl.cracotte.babal.game.domain.Direction.*;

public class Ball extends GameObject {

    private float acceleration;

    public Ball() {
        super();
        this.acceleration = 0.0f;
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

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }
}
