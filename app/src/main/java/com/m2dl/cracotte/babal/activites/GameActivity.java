package com.m2dl.cracotte.babal.activites;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.m2dl.cracotte.babal.game.GameView;
import com.m2dl.cracotte.babal.listeners.AccelerometerListener;

public class GameActivity extends Activity {

    private SensorManager sensorManager;
    private AccelerometerListener accelerometerListener;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        initListeners();
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAccelerometer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyAccelerometer();
    }

    private void initListeners() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerListener = new AccelerometerListener(gameView);
    }

    private void initAccelerometer() {
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    private void destroyAccelerometer() {
        sensorManager.unregisterListener(accelerometerListener, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
    }

}
