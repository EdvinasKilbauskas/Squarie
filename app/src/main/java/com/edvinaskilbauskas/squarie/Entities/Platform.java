package com.edvinaskilbauskas.squarie.Entities;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.RenderSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Color;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Rectangle;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;
import com.edvinaskilbauskas.squarie.Assets;
import com.edvinaskilbauskas.squarie.Global;
import com.edvinaskilbauskas.squarie.SinusoidWave;

/**
 * Created by edvinas on 11/29/14.
 */
public class Platform extends GameEntity {
    public Vector2 originalSize;
    public Vector2 velocity;
    public Vector2 acceleration;
    public Vector2 size;
    private Color color;
    private int maxStringVelocity;
    public boolean stringingUp;
    public boolean releasing;
    private float tensionAmount;


    public Platform(float pos, float width, float height){
        bounds = new Rectangle(0,0,0,0);
        color = new Color(0,0,0.0f,1);
        size = new Vector2(width,height);
        originalSize = new Vector2(width,height);
        velocity = new Vector2();
        acceleration = new Vector2();
        position.set(pos,Global.SCREEN_HEIGHT);
        stringingUp = false;
        releasing = false;
    }

    public void set(float pos, float width, float height){
        position.set(pos, Global.SCREEN_HEIGHT);
        originalSize.set(width,height);
        size.set(width,height);
    }

    public void stringUp(){
        stringingUp = true;
        releasing = false;
        acceleration.y = 140.0f;
    }

    public void release(){
        stringingUp = false;
        releasing = true;
        acceleration.y = 0;
    }

    public boolean isRising(){
        return isTransitioning();
    }

    public void rise(){
        if(isTransitioning() == false) {
            position.y += size.y;
            startTransition(position.x, position.y, position.x, position.y - size.y, 0.66f);
        }
    }


    public void setColor(float r, float g, float b, float a){
        color.set(r,g,b,a);
    }

    public void render(RenderSystem renderSystem){
        renderSystem.renderObject(Assets.platform,position.x,position.y,0,color.r,color.g,color.b,color.a, size.x, size.y);
    }

    public void update(float deltaTime){
        super.update(deltaTime);

        velocity.y += acceleration.y*deltaTime;
        position.y += velocity.y*deltaTime;

        if(velocity.y > 5.0f)
            velocity.y = 5.0f;

        if(releasing == true){
                velocity.y -= 500 * deltaTime;
                if(position.y <= 16){
                    position.y = 16;
                    velocity.y = 0;
                    //acceleration.y = -50.0f;
                    releasing = false;
                }

        }

        if(position.y >= Global.SCREEN_HEIGHT+size.y){
            if (stringingUp == true)
                release();

        }


        bounds.setSides(position.x - size.x/2.0f, position.y - size.y, position.x + size.x/2.0f, position.y);
    }
}
