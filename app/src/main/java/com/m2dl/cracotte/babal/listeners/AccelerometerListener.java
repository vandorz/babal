package com.m2dl.cracotte.babal.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.m2dl.cracotte.babal.game.GameView;

import java.util.Date;

public class AccelerometerListener implements SensorEventListener {

    private static final int ACCEL_THRESHOLD = 5;
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
            if(sensor == Sensor.TYPE_LINEAR_ACCELERATION){
                float accelX = values[0];
                float accelY = values[1];
                float accelZ = values[2];
                if(Math.abs(accelX) > ACCEL_THRESHOLD || Math.abs(accelY) > ACCEL_THRESHOLD || Math.abs(accelZ) > ACCEL_THRESHOLD) {
                    if(lastReset == null || new Date().getTime() - lastReset.getTime() > TIME_BETWEEN_RESETS) {
                        lastReset = new Date();
                        gameView.resetSpeed();
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
