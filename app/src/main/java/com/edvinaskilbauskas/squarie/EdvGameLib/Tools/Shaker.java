package com.edvinaskilbauskas.squarie.EdvGameLib.Tools;

import java.util.Random;

/**
 * Created by pufix-pc on 2/13/14.
 */
public class Shaker {
    private float shakeSize;
    private float currentShakeValue;
    private float time;
    private Random random;
    private boolean shaking;
    public enum Type {Single,Continuous}
    private Type type;

    public Shaker(){
        random = new Random();
        shakeSize = 0;
        currentShakeValue = 0;
        shaking = false;
    }

    // returns shake value
    public float getValue(){
        return (random.nextFloat()-0.5f)*currentShakeValue;
    }

    public void startShake(Type type, float shakeSize, float time){
        this.shakeSize = shakeSize;
        this.type = type;
        this.time = time;
        this.currentShakeValue = shakeSize;
        shaking = true;
    }

    public void stop(){
        time = 0;
        shakeSize = 0;
        currentShakeValue = 0;
        shaking = false;
    }

    public void update(float deltaTime){
        if(shaking) {
            if(type == Type.Single) {
                currentShakeValue -= shakeSize * (deltaTime / time);
                if(currentShakeValue <= 0) stop();
            }else if(type == Type.Continuous) {
                time -= deltaTime;
                if(time <= 0) stop();
            }

        }
    }



}
