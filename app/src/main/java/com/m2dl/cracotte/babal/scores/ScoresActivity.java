package com.m2dl.cracotte.babal.scores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m2dl.cracotte.babal.R;
import com.m2dl.cracotte.babal.game.GameActivity;
import com.m2dl.cracotte.babal.menu.MenuActivity;
import com.m2dl.cracotte.babal.scores.domain.Score;
import com.m2dl.cracotte.babal.scores.domain.ScoresTable;
import com.m2dl.cracotte.babal.scores.service.ScoresService;

import java.util.Map;
import java.util.TreeSet;

public class ScoresActivity extends Activity {
    private static final int NB_SCORES_DISPLAYED = 10;
    private static final String TEXT_SCORE_DISPLAY = "Votre score";

    private ScoresTable scoresTable;
    String[] playerNamesList = new String[NB_SCORES_DISPLAYED];
    String[] playerScoresList = new String[NB_SCORES_DISPLAYED];

    private Button publishScoreButton;
    private Button playAgainButton;
    private Button menuButton;
    private TextView currentPersonnalScoreTextView;
    private RecyclerView recyclerView;

    private long score;
    private boolean hasNewScore;
    private ScoresService scoresService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initDatabase();
        initData();
        initComponents();
    }

    private void initDatabase() {
        scoresService = new ScoresService(this);
    }

    public void updateDynamicData(ScoresTable newScoresTable) {
        scoresTable = newScoresTable;
        initScoresDisplay();
        initRecyclerView();
    }

    private void initData() {
        initScore();
        initHasNewScore();
        initScoresDisplay();
    }

    private void initScore(){
        score = getIntent().getLongExtra("scorePerformed", 0);
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
            int i = 1;
            while (i<=NB_SCORES_DISPLAYED && i < scoresTable.getNbScores()){
                Score currentScore = treeSetScores.pollFirst();
                playerNamesList[i-1] = currentScore.getPlayerName();
                playerScoresList[i-1] = currentScore.getScore().toString();
                i++;
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
                hasNewScore = false;
                scoresService.publishNewScore("Joueur", score);
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
