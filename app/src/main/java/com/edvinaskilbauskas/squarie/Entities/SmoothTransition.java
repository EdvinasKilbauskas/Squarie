package com.edvinaskilbauskas.squarie.Entities;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;
import com.edvinaskilbauskas.squarie.SinusoidWave;

/**
 * Created by edvinas on 11/30/14.
 */
public class SmoothTransition {

    private float currentValue;
    private float startValue;
    private float deltaValue;
    private boolean transitioning;
    private SinusoidWave sinusoid;

    public SmoothTransition(){
        currentValue = 0;
        startValue = 0;
        deltaValue = 0;
        sinusoid = new SinusoidWave();
        transitioning = false;
    }

    public boolean isTransitioning(){
        return transitioning;
    }

    public void startTransition(float start, float end, float time){
        //if(transitioning == false){
            sinusoid.setSpeed(1.0f/time);
            transitioning = true;
            currentValue = start;
            startValue = start;
            deltaValue = end-start;
            sinusoid.reset();
       // }
    }

    public float getValue(){
        return currentValue;
    }

    public void update(float deltaTime){
        if(transitioning) {
            sinusoid.update(deltaTime);

            currentValue = startValue + sinusoid.getValue() * deltaValue;

            if (sinusoid.getValue() == 1) {
                transitioning = false;
            }
        }
    }
}
