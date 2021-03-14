package com.m2dl.cracotte.babal.menu.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.m2dl.cracotte.babal.R;
import com.m2dl.cracotte.babal.menu.MenuActivity;

public class MusicToggleService {
    private final String SHAREDPREFERENCES_SOUND_STATE = "soundState";
    private final String SOUND_STATUS_LABEL = "status";

    private SharedPreferences sharedPreferencesSoundState;

    private MenuActivity menuActivity;

    public MusicToggleService(MenuActivity menuActivity) {
        this.menuActivity = menuActivity;
        sharedPreferencesSoundState = menuActivity.getSharedPreferences(SHAREDPREFERENCES_SOUND_STATE, Context.MODE_PRIVATE);
    }

    public void updateMusicToggleIcon() {
        boolean musicStatus = sharedPreferencesSoundState.getBoolean(SOUND_STATUS_LABEL, true);
        if (musicStatus) {
            Log.d("MUSIC-TOGGLE", "Should be activated");
            menuActivity.getMusicButton().setImageResource(android.R.drawable.ic_lock_silent_mode_off);
        } else {
            menuActivity.getMusicButton().setImageResource(android.R.drawable.ic_lock_silent_mode);
            Log.d("MUSIC-TOGGLE", "Should be deactivated");
        }
    }

    public void invertMusicSettings() {
        SharedPreferences.Editor editorSoundState = sharedPreferencesSoundState.edit();
        boolean currentState = sharedPreferencesSoundState.getBoolean(SOUND_STATUS_LABEL, true);
        editorSoundState.putBoolean(SOUND_STATUS_LABEL, !currentState);
        editorSoundState.apply();
    }
}
