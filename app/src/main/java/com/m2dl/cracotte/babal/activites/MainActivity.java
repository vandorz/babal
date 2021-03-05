package com.m2dl.cracotte.babal.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.m2dl.cracotte.babal.R;
import game.GameView;

public class MainActivity extends Activity {
    private Button playButton;
    private Button leaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents(){
        initPlayButton();
        initLeaveButton();
    }

    private void initPlayButton(){
        playButton = findViewById(R.id.menu_button_lancerJeu);
        System.out.println(playButton);
        playButton.setOnClickListener(v -> {
            Intent gameIntent = new Intent().setClass(v.getContext(), GameActivity.class);
            startActivity(gameIntent);
            finish();
        });
    }

    private void initLeaveButton(){
        leaveButton = findViewById(R.id.menu_button_quitterJeu);
        leaveButton.setOnClickListener(v -> finish());
    }
}