package com.m2dl.cracotte.babal.rules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.m2dl.cracotte.babal.R;
import com.m2dl.cracotte.babal.menu.MenuActivity;

public class RulesActivity extends Activity {

    private Button backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        initComponents();
    }

    private void initComponents(){
        initBackButton();
    }

    private void initBackButton(){
        backButton = findViewById(R.id.rules_button_back);
        backButton.setOnClickListener(listener -> {
            Intent menuIntent = new Intent().setClass(listener.getContext(), MenuActivity.class);
            startActivity(menuIntent);
            finish();
        });
    }
}
