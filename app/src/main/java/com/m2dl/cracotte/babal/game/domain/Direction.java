package com.m2dl.cracotte.babal.game.domain;

public enum Direction {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public static Direction getRandom(){
        return values()[(int) (Math.random() * values().length)];
    }
}
