package com.m2dl.cracotte.babal.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.m2dl.cracotte.babal.R;

public class ScoresActivity extends Activity {
    private final String TEXTE_AFFICHAGE_SCORE = "Votre score";

    private Button publishScoreButton;
    private Button playAgainButton;
    private Button menuButton;
    private TextView currentPersonnalScoreTextView;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initData();
        initComponents();
    }

    /**
     * Init data
     */
    private void initData(){
        initScore();
    }

    private void initScore(){
        this.score = this.getIntent().getIntExtra("scorePerformed", 0);
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
    }

    private void initPlayAgainButton(){
        this.playAgainButton = findViewById(R.id.score_button_rejouer);
        this.playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent().setClass(v.getContext(), GameActivity.class);
                startActivity(gameIntent);
                finish();
            }
        });
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
    }

}
