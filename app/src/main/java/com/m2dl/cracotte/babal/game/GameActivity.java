package com.m2dl.cracotte.babal.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.m2dl.cracotte.babal.game.listeners.AccelerometerSensorListener;
import com.m2dl.cracotte.babal.game.listeners.LightSensorListener;
import com.m2dl.cracotte.babal.utils.services.MusicToggleService;

public class GameActivity extends Activity {
    private GameView gameView;
    private SensorManager sensorManager;
    private AccelerometerSensorListener accelerometerSensorListener;
    private LightSensorListener lightSensorListener;

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
        if (MusicToggleService.isMusicAllowed(this)) {
            gameView.pauseMusic();
        }
        finish(); //Le jeu ne gère pas le resume pour le moment. Donc si on pause, on ferme tout.
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAccelerometer();
        initTouchScreen();
        initLightSensor();
        if (MusicToggleService.isMusicAllowed(this)) {
            gameView.startMusic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyAccelerometer();
        destroyLightSensor();
        if (MusicToggleService.isMusicAllowed(this)) {
            gameView.stopMusic();
        }
    }

    private void initListeners() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensorListener = new AccelerometerSensorListener(gameView);
        lightSensorListener = new LightSensorListener(gameView);
    }

    private void initAccelerometer() {
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(accelerometerSensorListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTouchScreen() {
        gameView.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                gameView.touchedScreenEvent(event.getX(), event.getY());
            }
            return true;
        });
        gameView.setOnClickListener(listener -> gameView.touchedScreenEvent(listener.getPivotX(), listener.getPivotY()));
    }

    private void initLightSensor() {
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private void destroyAccelerometer() {
        sensorManager.unregisterListener(accelerometerSensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
    }

    private void destroyLightSensor() {
        sensorManager.unregisterListener(lightSensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
    }
}
