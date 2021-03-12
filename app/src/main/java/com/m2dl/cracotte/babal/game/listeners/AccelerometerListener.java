package com.m2dl.cracotte.babal.game.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.m2dl.cracotte.babal.game.GameView;

import java.util.Date;

public class AccelerometerListener implements SensorEventListener {
    private static final int ACCELERATION_THRESHOLD = 20;
    private static final long TIME_BETWEEN_RESETS = 3000;

    private final GameView gameView;

    private Date lastReset;

    public AccelerometerListener(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensor = sensorEvent.sensor.getType();
        float[] values = sensorEvent.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_LINEAR_ACCELERATION) {
                float accelerationOnX = values[0];
                float accelerationOnY = values[1];
                float accelerationOnZ = values[2];
                if (
                        Math.abs(accelerationOnX) > ACCELERATION_THRESHOLD ||
                        Math.abs(accelerationOnY) > ACCELERATION_THRESHOLD ||
                        Math.abs(accelerationOnZ) > ACCELERATION_THRESHOLD
                ) {
                    if (lastReset == null || new Date().getTime() - lastReset.getTime() > TIME_BETWEEN_RESETS) {
                        lastReset = new Date();
                        gameView.resetSpeedAndAccelerate();
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
