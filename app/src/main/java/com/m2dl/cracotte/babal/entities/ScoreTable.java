package com.m2dl.cracotte.babal.entities;

import java.util.Map;

public class ScoreTable {
    private Long nbScores;
    private Map<String, Score> scores;

    public ScoreTable(){

    }

    public Long getNbScores() {
        return nbScores;
    }

    public void setNbScores(Long nbScores) {
        this.nbScores = nbScores;
    }

    public Map<String, Score> getScores() {
        return scores;
    }

    public void setScores(Map<String, Score> scores) {
        this.scores = scores;
    }

    public void putScore(String key, Score value){
        this.scores.put(key, value);
    }

}
