package com.m2dl.cracotte.babal.game.domain;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
    private MediaPlayer mediaPlayer;

    public Music(Context context, int resourceId) {
        this.mediaPlayer = MediaPlayer.create(context, resourceId);
    }

    public void start() {
        if (mediaPlayer != null && Boolean.FALSE.equals(mediaPlayer.isPlaying())) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    public void pause() {
        if (mediaPlayer != null && Boolean.TRUE.equals(mediaPlayer.isPlaying())) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }

    public void stop() {
        if (mediaPlayer != null && Boolean.TRUE.equals(mediaPlayer.isPlaying())) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }
}
