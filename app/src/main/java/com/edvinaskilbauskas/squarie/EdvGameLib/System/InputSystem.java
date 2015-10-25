package com.edvinaskilbauskas.squarie.EdvGameLib.System;
import android.view.View;


import java.util.List;

/**
 * Created by pufix on 6/23/13.
 */
public class InputSystem{
    private TouchInputCollector inputCollector;
    private Accelerometer accelerometer;

    public InputSystem(GameSystem gameSystem, View view){
        inputCollector = new TouchInputCollector(view, gameSystem.getGraphicsSystem());
        accelerometer = new Accelerometer(gameSystem);
    }

    public void update(){
        inputCollector.update();
    }

    public float getAccelerationX(){
        return accelerometer.getAccelerationX();
    }

    public float getAccelerationY(){
        return accelerometer.getAccelerationY();
    }

    public float getAccelerationZ(){
        return accelerometer.getAccelerationZ();
    }

    public TouchEvents getTouchEvents(){
        //inputCollector.update();
        return inputCollector.getTouchEvents();
    }
}
