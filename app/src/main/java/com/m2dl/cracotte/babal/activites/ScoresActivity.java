package com.m2dl.cracotte.babal.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.m2dl.cracotte.babal.R;
import com.m2dl.cracotte.babal.entities.Score;
import com.m2dl.cracotte.babal.entities.ScoreTable;

import java.util.HashMap;
import java.util.Map;

public class ScoresActivity extends Activity {
    private DatabaseReference mDatabase;
    private ScoreTable scoreTable;

    private final String TEXTE_AFFICHAGE_SCORE = "Votre score";

    private Button publishScoreButton;
    private Button playAgainButton;
    private Button menuButton;
    private TextView currentPersonnalScoreTextView;

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

    /**
     * Init data
     */
    private void initDatabase(){
        mDatabase = FirebaseDatabase.getInstance("https://babal-10a43-default-rtdb.firebaseio.com").getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot scoreTableDataSnapshot = dataSnapshot.child("tableauScores");

                ScoreTable newScoreTable = new ScoreTable();
                newScoreTable.setNbScores(scoreTableDataSnapshot.child("nbScores").getValue(Long.class));
                Map<String, Score> scoresMap = new HashMap<>();
                for (DataSnapshot currentChild : scoreTableDataSnapshot.child("scores").getChildren()){
                    String key = currentChild.getKey();
                    Score currentScore = currentChild.getValue(Score.class);
                    scoresMap.put(key, currentScore);
                }
                newScoreTable.setScores(scoresMap);
                scoreTable = newScoreTable;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initData(){
        initScore();
        initHasNewScore();
    }

    private void initScore(){
        this.score = this.getIntent().getIntExtra("scorePerformed", 0);
    }
    private void initHasNewScore(){
        this.hasNewScore = this.getIntent().getBooleanExtra("hasNewScore", false);
    }


    /**
     * Init components
     */
    private void initComponents(){
        initPublishScoreButton();
        initPlayAgainButton();
        initMenuButton();
        initCurrentPersonnalScoreTextView();
    }

    private void initPublishScoreButton(){
        this.publishScoreButton  = findViewById(R.id.score_button_publier);
        this.publishScoreButton.setOnClickListener(v -> {
            if (hasNewScore){
                scoreTable.setNbScores(scoreTable.getNbScores()+1);
                String playerName = "null";
                scoreTable.putScore((scoreTable.getNbScores()) + "", new Score(playerName, (long) score));
                mDatabase.child("tableauScores").setValue(scoreTable);
                hasNewScore = false;
            }else{
                //TODO ...
            }
        });
        if (!hasNewScore){
            this.publishScoreButton.setVisibility(View.INVISIBLE);
        }
    }

    private void initPlayAgainButton(){
        this.playAgainButton = findViewById(R.id.score_button_rejouer);
        this.playAgainButton.setOnClickListener(v -> {
            Intent gameIntent = new Intent().setClass(v.getContext(), GameActivity.class);
            startActivity(gameIntent);
            finish();
        });
        if (!hasNewScore){
            this.playAgainButton.setVisibility(View.INVISIBLE);
        }
    }

    private void initMenuButton(){
        this.menuButton = findViewById(R.id.score_button_menu);
        this.menuButton.setOnClickListener(v -> {
            Intent mainIntent = new Intent().setClass(v.getContext(), MainActivity.class);
            startActivity(mainIntent);
            finish();
        });
    }

    private void initCurrentPersonnalScoreTextView(){
        this.currentPersonnalScoreTextView = findViewById(R.id.score_textView_scoreRealise);
        String texte = TEXTE_AFFICHAGE_SCORE + " : " + this.score;
        this.currentPersonnalScoreTextView.setText(texte);
        if (!hasNewScore){
            this.currentPersonnalScoreTextView.setVisibility(View.INVISIBLE);
        }
    }

}
