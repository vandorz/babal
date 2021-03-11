package com.m2dl.cracotte.babal.game;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.m2dl.cracotte.babal.game.listeners.AccelerometerListener;

public class GameActivity extends Activity {
    private GameView gameView;
    private SensorManager sensorManager;
    private AccelerometerListener accelerometerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        initListeners();
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        destroyAccelerometer();
        gameView.pauseMusic();
        finish(); //Le jeu ne gère pas le resume pour le moment. Donc si on pause, on ferme tout.
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAccelerometer();
        initTouchScreen();
        gameView.startMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyAccelerometer();
        gameView.stopMusic();
    }

    private void initListeners() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerListener = new AccelerometerListener(gameView);
    }

    private void initAccelerometer() {
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    private void initTouchScreen() {
        gameView.setOnClickListener(listener -> gameView.touchedScreenEvent());
    }

    private void destroyAccelerometer() {
        sensorManager.unregisterListener(accelerometerListener, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
    }
}
