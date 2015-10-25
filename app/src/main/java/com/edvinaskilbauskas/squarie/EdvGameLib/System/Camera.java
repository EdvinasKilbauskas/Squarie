package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector3;

/**
 * Created by pufix on 6/23/13.
 */
public abstract class Camera {
    public Vector3 position;
    protected GraphicsSystem graphicsSystem;

    public Camera(){
        position = new Vector3();
    }

    public void setGraphicsSystem(GraphicsSystem graphicsSystem){
        this.graphicsSystem = graphicsSystem;
    }

    public abstract void update();

}
