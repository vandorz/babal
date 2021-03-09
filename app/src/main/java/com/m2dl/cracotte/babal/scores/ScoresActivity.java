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
    private final int NB_SCORES_AFFICHES = 10;

    private DatabaseReference mDatabase;
    private ScoresTable scoresTable;
    String[] playerNamesList = new String[NB_SCORES_AFFICHES];
    String[] playerScoresList = new String[NB_SCORES_AFFICHES];

    private final String TEXTE_AFFICHAGE_SCORE = "Votre score";

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

    /**
     * Init data
     */
    private void initDatabase(){
        mDatabase = FirebaseDatabase.getInstance("https://babal-10a43-default-rtdb.firebaseio.com").getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot scoreTableDataSnapshot = dataSnapshot.child("tableauScores");


                ScoresTable newScoresTable = new ScoresTable();
                newScoresTable.setNbScores(scoreTableDataSnapshot.child("nbScores").getValue(Long.class));
                Map<String, Score> scoresMap = new HashMap<>();
                for (DataSnapshot currentChild : scoreTableDataSnapshot.child("scores").getChildren()){
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

    private void updateDynamicData(){
        initAffichageScores();
        initRecyclerView();
    }

    private void initData(){
        initScore();
        initHasNewScore();
        initAffichageScores();
    }

    private void initScore(){
        this.score = this.getIntent().getIntExtra("scorePerformed", 0);
    }

    private void initHasNewScore(){
        this.hasNewScore = this.getIntent().getBooleanExtra("hasNewScore", false);
    }

    private void initAffichageScores(){
        if (this.scoresTable != null){
            TreeSet<Score> treeSetScores = new TreeSet<>();
            for (Map.Entry<String, Score> entry : scoresTable.getScores().entrySet()){
                treeSetScores.add(entry.getValue());
            }


            for (int i = 1; i<=NB_SCORES_AFFICHES; i++){
                if (i >= this.scoresTable.getNbScores()){
                    break;
                }
                Score currentScore = treeSetScores.pollFirst();
                playerNamesList[i-1] = currentScore.getPlayerName();
                playerScoresList[i-1] = currentScore.getScore().toString();
            }


        }
    }


    /**
     * Init components
     */
    private void initComponents(){
        initPublishScoreButton();
        initPlayAgainButton();
        initMenuButton();
        initCurrentPersonnalScoreTextView();
        initRecyclerView();
    }

    private void initPublishScoreButton(){
        this.publishScoreButton  = findViewById(R.id.score_button_publier);
        this.publishScoreButton.setOnClickListener(v -> {
            if (hasNewScore && scoresTable != null){
                scoresTable.setNbScores(scoresTable.getNbScores()+1);
                String playerName = "null";
                scoresTable.putScore((scoresTable.getNbScores()) + "", new Score(playerName, (long) score));
                mDatabase.child("tableauScores").setValue(scoresTable);
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
            Intent mainIntent = new Intent().setClass(v.getContext(), MenuActivity.class);
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

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.score_recyclerView_affichageScores);
        ScoresAdapter scoresAdapter = new ScoresAdapter(playerNamesList,playerScoresList);
        recyclerView.setAdapter(scoresAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
