package com.m2dl.cracotte.babal.scores.domain;

public class Score implements Comparable {
    private String playerName;
    private Long score;

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
    public int compareTo(Object object) {
        if (object instanceof Score) {
            Score scoreObject = (Score) object;
            if (score.equals(scoreObject.getScore())) {
                return scoreObject.playerName.compareTo(playerName);
            } else {
                return scoreObject.getScore().compareTo(score);
            }
        } else {
            return -1;
        }
    }
}
