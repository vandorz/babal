package com.m2dl.cracotte.babal.game.domain;

import static com.m2dl.cracotte.babal.game.domain.BonusType.getRandomBonusType;

public class Bonus extends GameObject{
    private float initialXPosition;
    private float initialYPosition;
    private float maxLeftDistance;
    private float maxRightDistance;
    private boolean enable;
    private BonusType bonusType;

    public Bonus() {
        super();
        this.initialXPosition = 0.0f;
        this.initialYPosition = 0.0f;
        this.positionInX = 0.0f;
        this.positionInY = 0.0f;
        this.maxLeftDistance = 0.0f;
        this.maxRightDistance = 0.0f;
        this.direction = Direction.EAST;
        this.enable = Boolean.TRUE;
        this.bonusType = BonusType.BOUNCE;
    }

    public Bonus(float initialXPosition, float initialYPosition, float maxLeftDistance, float maxRightDistance, Direction direction) {
        super();
        this.initialXPosition = initialXPosition;
        this.initialYPosition = initialYPosition;
        this.positionInX = initialXPosition;
        this.positionInY = initialYPosition;
        this.maxLeftDistance = maxLeftDistance;
        this.maxRightDistance = maxRightDistance;
        this.direction = direction;
        this.enable = true;
        this.bonusType = BonusType.getRandomBonusType();
    }

    public void move() {
        float yMovement = speed;
        float xMovement = 0.0f;
        if (direction.equals(Direction.EAST)) {
            xMovement = speed*2;
        } else if (direction.equals(Direction.WEST)) {
            xMovement = -speed*2;
        }
        moveInX(xMovement);
        moveInY(yMovement);
        changeDirection();
    }

    public void changeDirection() {
        if (direction.equals(Direction.EAST) && positionInX > initialXPosition + maxRightDistance) {
            direction = Direction.WEST;
        } else if (direction.equals(Direction.WEST) && positionInX < initialXPosition - maxLeftDistance) {
            direction = Direction.EAST;
        }
    }

    public boolean isTouched(float xPosition, float yPosition){
        return (xPosition > positionInX - radius && xPosition < positionInX + radius)
                && (yPosition > positionInY - radius && yPosition < positionInY + radius);
    }

    public float getInitialXPosition() {
        return initialXPosition;
    }

    public void setInitialXPosition(float initialXPosition) {
        this.initialXPosition = initialXPosition;
    }

    public float getInitialYPosition() {
        return initialYPosition;
    }

    public void setInitialYPosition(float initialYPosition) {
        this.initialYPosition = initialYPosition;
    }

    public float getMaxLeftDistance() {
        return maxLeftDistance;
    }

    public void setMaxLeftDistance(float maxLeftDistance) {
        this.maxLeftDistance = maxLeftDistance;
    }

    public float getMaxRightDistance() {
        return maxRightDistance;
    }

    public void setMaxRightDistance(float maxRightDistance) {
        this.maxRightDistance = maxRightDistance;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
    }
}
