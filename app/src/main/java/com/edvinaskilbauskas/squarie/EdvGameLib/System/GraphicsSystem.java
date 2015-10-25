package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by pufix on 6/23/13.
 */
public class GraphicsSystem {
    private ShaderProgram currentShader;
    private CameraES2 camera;

    @SuppressWarnings("all")
    public GraphicsSystem(float gameScreenWidth, float gameScreenHeight){
        this.camera = new CameraES2(gameScreenWidth, gameScreenHeight);
    }

    public void setShader(ShaderProgram shader){
        currentShader = shader;
    }

    public ShaderProgram getShader(){
        return currentShader;
    }

    public void update(){
        if(camera != null)
            camera.update();
    }

    public CameraES2 getCamera(){
        return camera;
    }

    public float getScreenWidth(){
        return camera.getFrustumWidth();
    }

    public float getScreenHeight(){
        return camera.getFrustumHeight();
    }

}
