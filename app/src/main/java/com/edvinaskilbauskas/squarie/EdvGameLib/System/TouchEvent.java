package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;

/**
 * Created by pufix on 6/23/13.
 */
public class TouchEvent{
    public enum TouchAction{UP, DOWN, MOVE, NONE};

    public int id;
    public TouchAction action;
    public Vector2 position;
    public Vector2 rawPosition;
    public Vector2 worldPosition;

    public TouchEvent(){
        position = new Vector2();
        rawPosition = new Vector2();
        worldPosition = new Vector2();
    }

    public void set(TouchEvent event){
        id = event.id;
        action = event.action;
        position.set(event.position);
        rawPosition.set(event.rawPosition);
        worldPosition.set(event.worldPosition);
    }
}