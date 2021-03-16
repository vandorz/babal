package com.m2dl.cracotte.babal.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.m2dl.cracotte.babal.R;
import com.m2dl.cracotte.babal.game.GameActivity;
import com.m2dl.cracotte.babal.rules.RulesActivity;
import com.m2dl.cracotte.babal.scores.ScoresActivity;
import com.m2dl.cracotte.babal.utils.services.MusicToggleService;

public class MenuActivity extends Activity {
    private Button playButton;
    private Button rulesButton;
    private Button scoresButton;
    private Button leaveButton;
    private ImageView musicButton;
    private MusicToggleService musicToggleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initComponents();
    }

    private void initComponents() {
        initPlayButton();
        initRulesButton();
        initScoresButton();
        initLeaveButton();
        initMusicButton();
    }

    private void initMusicButton() {
        musicToggleService = new MusicToggleService(this);
        musicButton = findViewById(R.id.menu_button_music);
        musicToggleService.updateMusicToggleIcon();
        musicButton.setOnClickListener(listener -> {
            musicToggleService.invertMusicSettings();
            musicToggleService.updateMusicToggleIcon();
        });
    }

    private void initPlayButton() {
        playButton = findViewById(R.id.menu_button_lancerJeu);
        playButton.setOnClickListener(listener -> {
            Intent gameIntent = new Intent().setClass(listener.getContext(), GameActivity.class);
            startActivity(gameIntent);
            finish();
        });
    }

    private void initRulesButton() {
        rulesButton = findViewById(R.id.menu_button_rules);
        rulesButton.setOnClickListener(listener -> {
            Intent rulesIntent = new Intent().setClass(listener.getContext(), RulesActivity.class);
            startActivity(rulesIntent);
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

    public ImageView getMusicButton() {
        return musicButton;
    }
}