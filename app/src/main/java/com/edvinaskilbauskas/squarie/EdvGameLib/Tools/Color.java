package com.edvinaskilbauskas.squarie.EdvGameLib.Tools;

/**
 * Created by pufix on 6/24/13.
 */
public class Color {
    public float r;
    public float g;
    public float b;
    public float a;

    public Color(){
        r = 1.0f;
        g = 1.0f;
        b = 1.0f;
        a = 1.0f;
    }

    public Color(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void set(Color color){
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    public void set(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
}
