package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by pufix on 7/5/13.
 */
public class Accelerometer implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float x;
    private float y;
    private float z;

    public Accelerometer(GameSystem system){
        //mSensorManager = (SensorManager) system.getSystemService(Context.SENSOR_SERVICE);
        //mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        x = event.values[0]/10.0f;
        y = event.values[1]/10.0f;
        z = event.values[2]/10.0f;
    }

    public float getAccelerationX(){
        return x;
    }

    public float getAccelerationY(){
        return y;
    }

    public float getAccelerationZ(){
        return z;
    }
}
