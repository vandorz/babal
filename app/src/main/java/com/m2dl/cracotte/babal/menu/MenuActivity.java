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

    private void initComponents() {
        initPlayButton();
        initScoresButton();
        initLeaveButton();
    }

    private void initPlayButton() {
        playButton = findViewById(R.id.menu_button_lancerJeu);
        playButton.setOnClickListener(listener -> {
            Intent gameIntent = new Intent().setClass(listener.getContext(), GameActivity.class);
            startActivity(gameIntent);
            finish();
        });
    }

    private void initScoresButton() {
        scoresButton = findViewById(R.id.menu_button_scores);
        scoresButton.setOnClickListener(listener -> {
            Intent scoresIntent = new Intent().setClass(listener.getContext(), ScoresActivity.class);
            startActivity(scoresIntent);
            finish();
        });
    }

    private void initLeaveButton() {
        leaveButton = findViewById(R.id.menu_button_quitterJeu);
        leaveButton.setOnClickListener(v -> finish());
    }
}