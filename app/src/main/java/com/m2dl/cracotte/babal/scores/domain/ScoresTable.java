package com.m2dl.cracotte.babal.scores.domain;

import java.util.Map;

public class ScoresTable {
    private Long nbScores;
    private Map<String, Score> scores;

    public ScoresTable() {

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

    public void putScore(String key, Score value) {
        scores.put(key, value);
    }
}
