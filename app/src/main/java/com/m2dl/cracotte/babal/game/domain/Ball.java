package com.m2dl.cracotte.babal.game.domain;

import com.m2dl.cracotte.babal.game.GameView;

import static com.m2dl.cracotte.babal.game.domain.Direction.*;

public class Ball extends GameObject {

    private float acceleration;
    private int bounceNumber;

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
            newDirection = Direction.getRandomDirection();
        }
        direction = newDirection;
    }

    public void reverseDirection(GameView gameView){
        switch (direction) {
            case NORTH:
                direction = SOUTH;
                break;
            case NORTH_EAST:
                if (isAtRightOf(gameView)){
                    direction = NORTH_WEST;
                }else{
                    direction = SOUTH_EAST;
                }
                break;
            case EAST:
                direction = WEST;
                break;
            case SOUTH_EAST:
                if (isAtRightOf(gameView)){
                    direction = SOUTH_WEST;
                }else{
                    direction = NORTH_EAST;
                }
                break;
            case SOUTH:
                direction = NORTH;
                break;
            case SOUTH_WEST:
                if (isAtLeftOf(gameView)){
                    direction = SOUTH_EAST;
                }else{
                    direction = NORTH_WEST;
                }
                break;
            case WEST:
                direction = EAST;
                break;
            case NORTH_WEST:
                if (isAtLeftOf(gameView)){
                    direction = NORTH_EAST;
                }else{
                    direction = SOUTH_WEST;
                }
                break;
        }
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public int getBounceNumber() {
        return bounceNumber;
    }

    public void setBounceNumber(int bounceNumber) {
        this.bounceNumber = bounceNumber;
    }
}
