package com.edvinaskilbauskas.squarie.Entities;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.RenderSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Color;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Rectangle;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;
import com.edvinaskilbauskas.squarie.Assets;
import com.edvinaskilbauskas.squarie.Global;

/**
 * Created by edvinas on 11/29/14.
 */
public class Square extends GameEntity {
    private final float WIDTH = 0.6f;
    private final float HEIGHT = 0.6f;
    private final Color color = new Color(0.7f,0.0f,0.0f,1);

    public Vector2 acceleration;
    public Vector2 velocity;
    public Vector2 size;

    public Square(float x, float y){
        bounds = new Rectangle(0,0,0,0);
        size = new Vector2(WIDTH,HEIGHT);
        velocity = new Vector2();
        acceleration = new Vector2();

        position.set(x,y);
    }

    public void render(RenderSystem renderSystem){
        renderSystem.renderObject(Assets.square,position.x,position.y,0,color.r,color.g,color.b,color.a,size.x,size.y);
    }

    public void update(float deltaTime){
        super.update(deltaTime);

        velocity.x += acceleration.x * deltaTime;
        velocity.y += acceleration.y * deltaTime;

        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;

        if(velocity.x >= Global.windSpeed)
            velocity.x = Global.windSpeed;


        bounds.setCentered(position.x,position.y,WIDTH,HEIGHT);
    }

}
