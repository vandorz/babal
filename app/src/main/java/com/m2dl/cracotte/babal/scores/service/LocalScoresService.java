package com.m2dl.cracotte.babal.scores.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.m2dl.cracotte.babal.scores.domain.Score;
import com.m2dl.cracotte.babal.scores.domain.ScoresTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LocalScoresService {
    private final String SHAREDPREFERENCES_SCORES_TABLE = "scoresTable";
    private final String SHAREDPREFERENCES_NAME_TABLE = "nameTable";
    private final String SHAREDPREFERENCES_GLOBAL_TABLE = "globalTable";

    SharedPreferences sharedPreferencesScores;
    SharedPreferences sharedPreferencesNames;
    SharedPreferences sharedPreferencesGlobal;

    public LocalScoresService(Context context){
        sharedPreferencesScores = context.getSharedPreferences(SHAREDPREFERENCES_SCORES_TABLE, Context.MODE_PRIVATE);
        sharedPreferencesNames = context.getSharedPreferences(SHAREDPREFERENCES_NAME_TABLE, Context.MODE_PRIVATE);
        sharedPreferencesGlobal = context.getSharedPreferences(SHAREDPREFERENCES_GLOBAL_TABLE, Context.MODE_PRIVATE);
    }

    public ScoresTable getRegisteredScores(){
        Map<String, ?> allNamesMap = sharedPreferencesNames.getAll();

        ScoresTable scoresTable = new ScoresTable();
        scoresTable.setNbScores(sharedPreferencesGlobal.getLong("nbScores", 0));
        Map<String, Score> scoresMap = new HashMap<>();
        for (String currentNumberString : allNamesMap.keySet()){
            String currentName = sharedPreferencesNames.getString(currentNumberString, "Inconnu");
            long currentScore = sharedPreferencesScores.getLong(currentNumberString, 0);
            scoresMap.put(currentNumberString, new Score(currentName, currentScore));
        }
        scoresTable.setScores(scoresMap);
        return scoresTable;
    }

    public void publishNewScore(String playerName, long score){
        SharedPreferences.Editor editorScores = sharedPreferencesScores.edit();
        SharedPreferences.Editor editorNames = sharedPreferencesNames.edit();
        SharedPreferences.Editor editorGlobal = sharedPreferencesGlobal.edit();

        long nbScores = sharedPreferencesGlobal.getLong("nbScores", 0) + 1;
        String nbScoresString = nbScores + "";

        editorGlobal.putLong("nbScores", nbScores);
        editorNames.putString(nbScoresString, playerName);
        editorScores.putLong(nbScoresString, score);

        editorGlobal.apply();
        editorNames.apply();
        editorScores.apply();
    }

    public String getSavedPlayerName(){
        return sharedPreferencesGlobal.getString("playerName", "");
    }

    public void savePlayerName(String playerName){
        SharedPreferences.Editor editorGlobal = sharedPreferencesGlobal.edit();
        editorGlobal.putString("playerName", playerName);
        editorGlobal.apply();
    }
}
