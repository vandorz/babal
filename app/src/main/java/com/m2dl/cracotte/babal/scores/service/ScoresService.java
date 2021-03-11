package com.m2dl.cracotte.babal.scores.service;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.m2dl.cracotte.babal.scores.ScoresActivity;
import com.m2dl.cracotte.babal.scores.domain.Score;
import com.m2dl.cracotte.babal.scores.domain.ScoresTable;

import java.util.HashMap;
import java.util.Map;

public class ScoresService {
    private DatabaseReference database;
    private ScoresTable scoresTable;
    private ScoresActivity scoresActivity;

    public ScoresService(ScoresActivity scoresActivity){
        this.scoresActivity = scoresActivity;
        initDatabase();
    }

    private void initDatabase(){
        database = FirebaseDatabase.getInstance("https://babal-10a43-default-rtdb.firebaseio.com").getReference();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot scoreTableDataSnapshot = dataSnapshot.child("tableauScores");

                ScoresTable newScoresTable = new ScoresTable();
                newScoresTable.setNbScores(scoreTableDataSnapshot.child("nbScores").getValue(Long.class));
                Map<String, Score> scoresMap = new HashMap<>();
                for (DataSnapshot currentChild : scoreTableDataSnapshot.child("scores").getChildren()) {
                    String key = currentChild.getKey();
                    Score currentScore = currentChild.getValue(Score.class);
                    scoresMap.put(key, currentScore);
                }
                newScoresTable.setScores(scoresMap);
                scoresTable = newScoresTable;
                scoresActivity.updateDynamicData(scoresTable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void publishNewScore(String playerName, long score){
        scoresTable.setNbScores(scoresTable.getNbScores()+1);
        scoresTable.putScore((scoresTable.getNbScores()) + "", new Score(playerName, score));
        database.child("tableauScores").setValue(scoresTable);
    }
}
