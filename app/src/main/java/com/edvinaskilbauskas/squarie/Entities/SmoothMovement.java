package com.edvinaskilbauskas.squarie.Entities;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;
import com.edvinaskilbauskas.squarie.SinusoidWave;

/**
 * Created by edvinas on 1/24/15.
 */
public class SmoothMovement {
    public Vector2 position;
    private SmoothTransition xTransition;
    private SmoothTransition yTransition;

    public SmoothMovement(){
        xTransition = new SmoothTransition();
        yTransition = new SmoothTransition();
        position = new Vector2();

    }

    public boolean isTransitioning(){
        return xTransition.isTransitioning();
    }

    public void startTransition(float x1, float y1, float x2, float y2, float time){
        if(isTransitioning() == false) {
            xTransition.startTransition(x1, x2, time);
            yTransition.startTransition(y1, y2, time);
        }

    }

    public void update(float deltaTime){
        xTransition.update(deltaTime);
        yTransition.update(deltaTime);
        if(isTransitioning()){
            position.x = xTransition.getValue();
            position.y = yTransition.getValue();
        }

    }
}
