package com.m2dl.cracotte.babal.game.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.m2dl.cracotte.babal.game.GameView;

public class LightSensorListener implements SensorEventListener {

    private GameView gameView;

    public LightSensorListener(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensor = sensorEvent.sensor.getType();
        float[] values = sensorEvent.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_LIGHT) {
                float lightMeasurement = values[0];
                updateGameViewLightMeasurement(lightMeasurement);
            }
        }
    }

    public void updateGameViewLightMeasurement(float lightMeasurement) {
        gameView.updateLightMeasurement(lightMeasurement);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
