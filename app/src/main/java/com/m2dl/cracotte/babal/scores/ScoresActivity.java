package com.m2dl.cracotte.babal.scores;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.m2dl.cracotte.babal.R;
import com.m2dl.cracotte.babal.game.GameActivity;
import com.m2dl.cracotte.babal.menu.MenuActivity;
import com.m2dl.cracotte.babal.scores.domain.Score;
import com.m2dl.cracotte.babal.scores.domain.ScoresTable;
import com.m2dl.cracotte.babal.scores.service.GlobalScoresService;
import com.m2dl.cracotte.babal.scores.service.LocalScoresService;

import java.util.Map;
import java.util.TreeSet;

public class ScoresActivity extends Activity {
    private static final int NB_SCORES_DISPLAYED = 10;
    private static final String TEXT_SCORE_DISPLAY = "Votre score";

    private ScoresTable globalScoresTable;
    private ScoresTable localScoresTable;
    String[] playerNamesList = new String[NB_SCORES_DISPLAYED];
    String[] playerScoresList = new String[NB_SCORES_DISPLAYED];


    private Button publishScoreButton;
    private Button playAgainButton;
    private Button menuButton;
    private TextView currentPersonnalScoreTextView;
    private RecyclerView recyclerView;
    private TabLayout scoresTabLayout;

    private long score;
    private boolean hasNewScore;
    private GlobalScoresService globalScoresService;
    private LocalScoresService localScoresService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initDatabases();
        initData();
        initComponents();
        updateScoresDisplay();
    }

    private void initDatabases() {
        initGlobalDatabase();
        initLocalDatabase();
    }

    private void initGlobalDatabase(){
        globalScoresService = new GlobalScoresService(this);
    }

    private void initLocalDatabase(){
        localScoresService = new LocalScoresService(getApplicationContext());
    }

    public void receiveNewDataFromDatabase(ScoresTable newScoresTable){
        globalScoresTable = newScoresTable;
        updateDynamicData();
    }

    public void updateDynamicData() {
        updateScoresDisplay();
        updateRecyclerView();
    }

    private void initData() {
        initScore();
        initHasNewScore();
        initLocalScores();
    }

    private void initScore(){
        score = getIntent().getLongExtra("scorePerformed", 0);
    }

    private void initLocalScores(){
        localScoresTable = localScoresService.getRegisteredScores();
    }

    private void initHasNewScore() {
        hasNewScore = getIntent().getBooleanExtra("hasNewScore", false);
    }

    private void updateScoresDisplay() {
        ScoresTable scoresToShow;
        if (scoresTabLayout.getSelectedTabPosition() == 0){
            scoresToShow = localScoresTable;
        }else if (scoresTabLayout.getSelectedTabPosition() == 1){
            scoresToShow = globalScoresTable;
        }else{
            return; //TODO erreur
        }
        if (scoresToShow != null  && scoresToShow.getScores() != null) {
            TreeSet<Score> treeSetScores = new TreeSet<>();
            for (Map.Entry<String, Score> entry : scoresToShow.getScores().entrySet()) {
                treeSetScores.add(entry.getValue());
            }
            int i = 1;
            while (i<=NB_SCORES_DISPLAYED && i < scoresToShow.getNbScores()){
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
        updateRecyclerView();
        initTabLayout();
    }

    private void initPublishScoreButton() {
        publishScoreButton  = findViewById(R.id.score_button_publier);
        publishScoreButton.setOnClickListener(listener -> {
            if (hasNewScore && globalScoresTable != null) {
                hasNewScore = false;
                globalScoresService.publishNewScore("Joueur", score);
                localScoresService.publishNewScore("Joueur", score);
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

    private void updateRecyclerView() {
        recyclerView = findViewById(R.id.score_recyclerView_affichageScores);
        ScoresAdapter scoresAdapter = new ScoresAdapter(playerNamesList,playerScoresList);
        recyclerView.setAdapter(scoresAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initTabLayout(){
        scoresTabLayout = findViewById(R.id.score_tabLayout_scoresTabLayout);
        scoresTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateDynamicData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
