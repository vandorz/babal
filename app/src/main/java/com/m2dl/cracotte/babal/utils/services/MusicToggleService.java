package com.m2dl.cracotte.babal.utils.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.m2dl.cracotte.babal.menu.MenuActivity;

public class MusicToggleService {
    private static final String SHAREDPREFERENCES_SOUND_STATE = "soundState";
    private static final String SOUND_STATUS_LABEL = "status";

    private SharedPreferences sharedPreferencesSoundState;

    private MenuActivity menuActivity;

    public MusicToggleService(MenuActivity menuActivity) {
        this.menuActivity = menuActivity;
        sharedPreferencesSoundState = menuActivity.getSharedPreferences(SHAREDPREFERENCES_SOUND_STATE, Context.MODE_PRIVATE);
    }

    public void updateMusicToggleIcon() {
        boolean musicStatus = sharedPreferencesSoundState.getBoolean(SOUND_STATUS_LABEL, true);
        if (musicStatus) {
            menuActivity.getMusicButton().setImageResource(android.R.drawable.ic_lock_silent_mode_off);
        } else {
            menuActivity.getMusicButton().setImageResource(android.R.drawable.ic_lock_silent_mode);
        }
    }

    public void invertMusicSettings() {
        SharedPreferences.Editor editorSoundState = sharedPreferencesSoundState.edit();
        boolean currentState = sharedPreferencesSoundState.getBoolean(SOUND_STATUS_LABEL, true);
        editorSoundState.putBoolean(SOUND_STATUS_LABEL, !currentState);
        editorSoundState.apply();
    }

    public static boolean isMusicAllowed(Context context) {
        return context.getSharedPreferences(SHAREDPREFERENCES_SOUND_STATE, Context.MODE_PRIVATE).getBoolean(SOUND_STATUS_LABEL, true);
    }
}
