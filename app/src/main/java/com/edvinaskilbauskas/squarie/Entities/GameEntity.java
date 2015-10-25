package com.edvinaskilbauskas.squarie.Entities;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.RenderSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Rectangle;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;

/**
 * Created by pufix-pc on 11/26/13.
 */
public abstract class GameEntity extends SmoothMovement{
    protected Rectangle bounds;

    public GameEntity(){
        super();
        bounds = new Rectangle(0,0,0,0);
    }

    public void update(float deltaTime){
        super.update(deltaTime);

    }

    public abstract void render(RenderSystem renderSystem);

    public Rectangle getBounds(){
        return bounds;
    }
}
