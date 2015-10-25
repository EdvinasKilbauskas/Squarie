package com.edvinaskilbauskas.squarie.EdvGameLib.Tools;

/**
 * Created by pufix on 6/23/13.
 */
public class Vector2 {
    public float x;
    public float y;

    public Vector2(){
        this.x = 0;
        this.y = 0;
    }

    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2 set(float x, float y){
        this.x = x;
        this.y = y;

        return this;
    }

    public Vector2 set(Vector2 vector){
        this.x = vector.x;
        this.y = vector.y;

        return this;
    }


    public Vector2 add(float x, float y){
        this.x += x;
        this.y += y;

        return this;
    }

    public Vector2 add(Vector2 vector){
        this.x += vector.x;
        this.y += vector.y;

        return this;
    }

    public Vector2 minus(float x, float y){
        this.x -= x;
        this.y -= y;

        return this;
    }

    public Vector2 minus(Vector2 vector){
        this.x -= vector.x;
        this.y -= vector.y;

        return this;
    }

    public Vector2 mult(float scalar){
        this.x *= scalar;
        this.y *= scalar;

        return this;
    }

    public Vector2 div(float divisor){
        this.x /= divisor;
        this.y /= divisor;

        return this;
    }

    public float length(){
        return (float)(Math.sqrt(x * x + y * y));
    }

    public Vector2 abs(){
        x = Math.abs(x);
        y = Math.abs(y);
        return this;
    }


    /**
     * @return angle of vector in degress
     */
    public float angle(){
        return (float) Math.toDegrees(Math.atan2(y, x));
    }

    public float dist(Vector2 vector){
        float x = vector.x - this.x;
        float y = vector.y - this.y;

        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     *
     * @param angle in degress
     */
    public void rotate(float angle){
        angle = (float) Math.toRadians(angle);
        float x = this.x;
        float y = this.y;
        this.x = (float)(x * Math.cos(angle)) - (float)(y * Math.sin(angle));
        this.y = (float)(x * Math.sin(angle)) + (float)(y * Math.cos(angle));
    }

    public static float Length(float x, float y){
        return (float)(Math.sqrt(x * x + y * y));
    }

    public static float Angle(float x, float y){
        return (float) Math.toDegrees(Math.atan2(y, x));
    }

}
