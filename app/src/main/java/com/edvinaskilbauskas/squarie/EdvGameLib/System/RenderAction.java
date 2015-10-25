package com.edvinaskilbauskas.squarie.EdvGameLib.System;

/**
 * Created by pufix on 7/21/13.
 */
public class RenderAction {
    RenderableObject renderable;
    ShaderProgram shader;
    float x;
    float y;
    float z;
    float scaleX;
    float scaleY;
    float scaleZ;
    float r;
    float g;
    float b;
    float a;
    float angle;

    public RenderAction(){

    }

    public void setup(RenderableObject object, float x, float y,float z, float r, float g, float b, float a, float scaleX, float scaleY, float scaleZ, float angle, ShaderProgram shader){
        renderable = object;
        this.x = x;
        this.y = y;
        this.z = z;

        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;

        this.angle = angle;
        this.shader = shader;
    }
}
