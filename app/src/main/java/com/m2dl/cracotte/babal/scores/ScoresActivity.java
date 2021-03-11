package com.m2dl.cracotte.babal.scores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.m2dl.cracotte.babal.R;
import com.m2dl.cracotte.babal.game.GameActivity;
import com.m2dl.cracotte.babal.menu.MenuActivity;
import com.m2dl.cracotte.babal.scores.domain.Score;
import com.m2dl.cracotte.babal.scores.domain.ScoresTable;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class ScoresActivity extends Activity {
    private static final int NB_SCORES_DISPLAYED = 10;
    private static final String TEXT_SCORE_DISPLAY = "Votre score";

    private DatabaseReference database;
    private ScoresTable scoresTable;
    String[] playerNamesList = new String[NB_SCORES_DISPLAYED];
    String[] playerScoresList = new String[NB_SCORES_DISPLAYED];

    private Button publishScoreButton;
    private Button playAgainButton;
    private Button menuButton;
    private TextView currentPersonnalScoreTextView;
    private RecyclerView recyclerView;

    private int score;
    private boolean hasNewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initDatabase();
        initData();
        initComponents();
    }

    private void initDatabase() {
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
                updateDynamicData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateDynamicData() {
        initScoresDisplay();
        initRecyclerView();
    }

    private void initData() {
        initScore();
        initHasNewScore();
        initScoresDisplay();
    }

    private void initScore(){
        score = getIntent().getIntExtra("scorePerformed", 0);
    }

    private void initHasNewScore() {
        hasNewScore = getIntent().getBooleanExtra("hasNewScore", false);
    }

    private void initScoresDisplay() {
        if (scoresTable != null) {
            TreeSet<Score> treeSetScores = new TreeSet<>();
            for (Map.Entry<String, Score> entry : scoresTable.getScores().entrySet()) {
                treeSetScores.add(entry.getValue());
            }
            for (int i = 1; i<= NB_SCORES_DISPLAYED; i++) {
                if (i >= scoresTable.getNbScores()) {
                    break;
                }
                Score currentScore = treeSetScores.pollFirst();
                playerNamesList[i-1] = currentScore.getPlayerName();
                playerScoresList[i-1] = currentScore.getScore().toString();
            }
        }
    }

    private void initComponents() {
        initPublishScoreButton();
        initPlayAgainButton();
        initMenuButton();
        initCurrentPersonnalScoreTextView();
        initRecyclerView();
    }

    private void initPublishScoreButton() {
        publishScoreButton  = findViewById(R.id.score_button_publier);
        publishScoreButton.setOnClickListener(listener -> {
            if (hasNewScore && scoresTable != null) {
                scoresTable.setNbScores(scoresTable.getNbScores()+1);
                String playerName = "null";
                scoresTable.putScore((scoresTable.getNbScores()) + "", new Score(playerName, (long) score));
                database.child("tableauScores").setValue(scoresTable);
                hasNewScore = false;
            } else {
                // TODO ...
            }
        });
        if (!hasNewScore) {
            publishScoreButton.setVisibility(View.INVISIBLE);
        }
    }

    private void initPlayAgainButton() {
        playAgainButton = findViewById(R.id.score_button_rejouer);
        playAgainButton.setOnClickListener(listener -> {
            Intent gameIntent = new Intent().setClass(listener.getContext(), GameActivity.class);
            startActivity(gameIntent);
            finish();
        });
        if (!hasNewScore){
            playAgainButton.setVisibility(View.INVISIBLE);
        }
    }

    private void initMenuButton() {
        menuButton = findViewById(R.id.score_button_menu);
        menuButton.setOnClickListener(listener -> {
            Intent mainIntent = new Intent().setClass(listener.getContext(), MenuActivity.class);
            startActivity(mainIntent);
            finish();
        });
    }

    private void initCurrentPersonnalScoreTextView() {
        currentPersonnalScoreTextView = findViewById(R.id.score_textView_scoreRealise);
        String text = TEXT_SCORE_DISPLAY + " : " + score;
        currentPersonnalScoreTextView.setText(text);
        if (!hasNewScore) {
            currentPersonnalScoreTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.score_recyclerView_affichageScores);
        ScoresAdapter scoresAdapter = new ScoresAdapter(playerNamesList,playerScoresList);
        recyclerView.setAdapter(scoresAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
