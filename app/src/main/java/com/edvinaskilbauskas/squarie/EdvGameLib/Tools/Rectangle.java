package com.edvinaskilbauskas.squarie.EdvGameLib.Tools;

/**
 * Created by pufix on 6/23/13.
 */
public class Rectangle {
    public Vector2 topLeft;
    public float width;
    public float height;

    public Rectangle(float topleftx, float toplefty, float width, float height){
        topLeft = new Vector2(topleftx,toplefty);
        this.width = width;
        this.height = height;
    }

    public float top(){
        return topLeft.y;
    }

    public float bottom(){
        return topLeft.y+height;
    }

    public float left(){
        return topLeft.x;
    }

    public float right(){
        return topLeft.x+width;
    }

    public boolean intersects(Rectangle rect){
        if(this.left() >= rect.right()){
            return false;
        }
        if(this.right() <= rect.left()){
            return false;
        }
        if(this.top() >= rect.bottom()){
            return false;
        }
        if(this.bottom() <= rect.top()){
            return false;
        }

        return true;
    }

    public boolean pointInside(float pointX, float pointY){
        if(pointX >= left() && pointX <= right() &&
           pointY >= top() && pointY <= bottom()){
            return true;
        }else return false;
    }


    public void setTopLeft(float x, float y, float width, float height){
        topLeft.set(x,y);
        this.width = width;
        this.height = height;
    }

    public void setCentered(float centerx, float centery, float width, float height){
        topLeft.set(centerx-width/2.0f,centery-height/2.0f);
        this.width = width;
        this.height = height;
    }

    public void setSides(float left, float top, float right, float bottom){
        topLeft.set(left,top);
        this.width = right - left;
        this.height = bottom - top;
    }
}
