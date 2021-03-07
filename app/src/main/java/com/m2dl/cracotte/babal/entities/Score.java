package com.m2dl.cracotte.babal.entities;

public class Score implements Comparable{
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

    @Override
    public int compareTo(Object object) {
        if (object instanceof Score){
            Score scoreObject = (Score) object;
            if (this.score.equals(scoreObject.getScore())){
                return scoreObject.playerName.compareTo(this.playerName);
            }else{
                return scoreObject.getScore().compareTo(this.score);
            }
        }else{
            return -1;
        }
    }
}
