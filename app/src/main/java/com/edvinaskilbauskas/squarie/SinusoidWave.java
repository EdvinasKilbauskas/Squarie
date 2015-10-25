package com.edvinaskilbauskas.squarie;

/**
 * Created by edvinas on 11/29/14.
 */
public class SinusoidWave {
    private float angle;
    private float speed;
    private float output;

    public SinusoidWave(){
        setSpeed(1);
        angle = 180;
    }

    public void reset(){
        angle = 180;
    }

    public void setSpeed(float speed){
        this.speed = speed*180.0f;
    }

    public float getValue(){
        return output;
    }

    public void update(float deltaTime){
        angle -= deltaTime * speed;

        if (angle < 0) angle = 0;

        output = ((float) Math.cos(Math.toRadians(angle)) + 1) / 2.0f;
    }
}
