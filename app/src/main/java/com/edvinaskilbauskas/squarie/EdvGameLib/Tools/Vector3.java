package com.edvinaskilbauskas.squarie.EdvGameLib.Tools;

/**
 * Created by pufix on 7/20/13.
 */
public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    public Vector3 set(Vector3 vector){
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;

        return this;
    }


    public Vector3 add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

    public Vector3 add(Vector3 vector){
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;

        return this;
    }

    public Vector3 minus(float x, float y, float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return this;
    }

    public Vector3 minus(Vector3 vector){
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;

        return this;
    }

    public Vector3 mult(float scalar){
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;

        return this;
    }

    public Vector3 div(float divisor){
        this.x /= divisor;
        this.y /= divisor;
        this.z /= divisor;

        return this;
    }

    public float length(){
        return (float)(Math.sqrt(x * x + y * y + z * z));
    }

    public float dist(Vector3 vector){
        float x = vector.x - this.x;
        float y = vector.y - this.y;
        float z = vector.z - this.z;

        return (float) Math.sqrt(x * x + y * y + z * z);
    }


}
