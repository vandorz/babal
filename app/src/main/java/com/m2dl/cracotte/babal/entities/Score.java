package com.m2dl.cracotte.babal.entities;

public class Score {
    private String playerName;
    private Long score;

    public Score(){

    }

    public Score(String playerName, Long score){
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
