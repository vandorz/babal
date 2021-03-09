package com.m2dl.cracotte.babal.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.m2dl.cracotte.babal.R;
import com.m2dl.cracotte.babal.game.GameActivity;
import com.m2dl.cracotte.babal.scores.ScoresActivity;

public class MenuActivity extends Activity {
    private Button playButton;
    private Button scoresButton;
    private Button leaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initComponents();
    }

    private void initComponents(){
        initPlayButton();
        initScoresButton();
        initLeaveButton();
    }

    private void initPlayButton(){
        this.playButton = findViewById(R.id.menu_button_lancerJeu);
        this.playButton.setOnClickListener(v -> {
            Intent gameIntent = new Intent().setClass(v.getContext(), GameActivity.class);
            startActivity(gameIntent);
            finish();
        });
    }

    private void initScoresButton(){
        this.playButton = findViewById(R.id.menu_button_scores);
        this.playButton.setOnClickListener(v -> {
            Intent scoresIntent = new Intent().setClass(v.getContext(), ScoresActivity.class);
            startActivity(scoresIntent);
            finish();
        });
    }

    private void initLeaveButton(){
        this.leaveButton = findViewById(R.id.menu_button_quitterJeu);
        this.leaveButton.setOnClickListener(v -> finish());
    }
}