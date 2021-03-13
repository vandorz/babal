package com.m2dl.cracotte.babal.game.domain;

public class Bonus extends GameObject{
    private float initialXPosition;
    private float initialYPosition;
    private float maxLeftDistance;
    private float maxRightDistance;

    public Bonus(){
        super();
        this.initialXPosition = 0.0f;
        this.initialYPosition = 0.0f;
        this.positionInX = 0.0f;
        this.positionInY = 0.0f;
        this.maxLeftDistance = 0.0f;
        this.maxRightDistance = 0.0f;
        this.direction = Direction.EAST;
    }

    public Bonus(float initialXPosition, float initialYPosition, float maxLeftDistance, float maxRightDistance, Direction direction){
        super();
        this.initialXPosition = initialXPosition;
        this.initialYPosition = initialYPosition;
        this.positionInX = initialXPosition;
        this.positionInY = initialYPosition;
        this.maxLeftDistance = maxLeftDistance;
        this.maxRightDistance = maxRightDistance;
        this.direction = direction;
    }

    public void move() {
        float yMovement = speed;
        float xMovement = 0.0f;
        if (direction.equals(Direction.EAST)){
            xMovement = speed*2;
        }else if (direction.equals(Direction.WEST)){
            xMovement = -speed*2;
        }
        moveInX(xMovement);
        moveInY(yMovement);
        changeDirection();
    }

    public void changeDirection(){
        if (direction.equals(Direction.EAST) && positionInX > initialXPosition + maxRightDistance){
            direction = Direction.WEST;
        }else if(direction.equals(Direction.WEST) && positionInX < initialXPosition - maxLeftDistance){
            direction = Direction.EAST;
        }
    }


}
