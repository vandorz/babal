package com.m2dl.cracotte.babal.game.domain;

public enum BonusType {
    BOUNCE;

    public static BonusType getRandomBonusType() {
        return values()[(int) (Math.random() * values().length)];
    }
}
