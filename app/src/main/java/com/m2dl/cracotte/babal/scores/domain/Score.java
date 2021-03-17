package com.m2dl.cracotte.babal.scores.domain;

public class Score implements Comparable<Score> {
    private String playerName;
    private Long score;

    public Score() {

    }

    public Score(String playerName, Long score) {
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

    @Override
    public int compareTo(Score object) {
        if (object != null) {
            if (score.equals(object.getScore())) {
                return object.playerName.compareTo(playerName);
            } else {
                return object.getScore().compareTo(score);
            }
        } else {
            return -1;
        }
    }
}
