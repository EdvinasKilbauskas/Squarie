package com.edvinaskilbauskas.squarie.Entities;

import com.edvinaskilbauskas.squarie.Assets;
import com.edvinaskilbauskas.squarie.ColorTransition;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.RenderSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Color;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;
import com.edvinaskilbauskas.squarie.Global;

/**
 * Created by edvinas on 1/23/15.
 */
public class Background extends GameEntity{
    private float m_angle;
    private float m_rotateSpeed;
    private Vector2 m_position;
    private float m_acceleration;
    private final float ROTATION_SPEED = 15;
    private final float COLOR_TRANSITION_SPEED = 1.5f;
    private final float ACCELERATION_TIME = 1.5f;
    private final float ACCELERATION_SPEED = ROTATION_SPEED/ACCELERATION_TIME;
    private Color m_color;
    private ColorTransition m_colorTransition;

    public Background(){
        m_position = new Vector2(Global.SCREEN_WIDTH/2.0f, Global.SCREEN_HEIGHT/2.0f);
        m_color = new Color(0.0f,0.0f,0.0f,1);
        m_angle = 0;
        m_colorTransition = new ColorTransition();
        m_rotateSpeed = 0;
        m_acceleration = 0;
    }

    public void startRotation(){
        m_acceleration = ACCELERATION_SPEED;
    }

    public void reverseRotation(){
        m_acceleration = -ACCELERATION_SPEED;
    }

    public void stopRotation(){
        m_acceleration = 0;
    }


    public void transitionColor(float r, float g, float b, float a){
        m_colorTransition.startTransitioning(m_color.r, m_color.g, m_color.b, m_color.a,
                                             r,g,b,a, COLOR_TRANSITION_SPEED);
    }

    public void transitionColor(Color color){
        m_colorTransition.startTransitioning(m_color.r, m_color.g, m_color.b, m_color.a,
                color.r, color.g, color.b, color.a, COLOR_TRANSITION_SPEED);
    }

    public void setColor(float r, float g, float b, float a){
        m_color.set(r,g,b,a);
    }

    public void update(float deltaTime){
        if(m_acceleration > 0){
            m_rotateSpeed += m_acceleration*deltaTime;
            if(m_rotateSpeed >= ROTATION_SPEED) m_rotateSpeed = ROTATION_SPEED;

        }else if(m_acceleration < 0){
            m_rotateSpeed += m_acceleration*deltaTime;
            if(m_rotateSpeed <= -ROTATION_SPEED/3.0f) m_rotateSpeed = -ROTATION_SPEED/3.0f;

            // m_acceleration == 0
        }else{
            m_rotateSpeed *= deltaTime;

        }


        if(m_colorTransition.isTransitioning()) {
            m_colorTransition.update(deltaTime);
            m_color.set(m_colorTransition.getColor());
        }

        m_angle += m_rotateSpeed*deltaTime;
    }

    public void render(RenderSystem renderSystem){
        renderSystem.renderObject(Assets.square, Global.SCREEN_WIDTH/2.0f, Global.SCREEN_HEIGHT/2.0f, 0, m_color.r, m_color.g, m_color.b, m_color.a, Global.SCREEN_WIDTH+10, Global.SCREEN_HEIGHT+10);
        renderSystem.renderObject(Assets.background, m_position.x, m_position.y, 0, 1, 1, 1, 1, 1,1, m_angle);
    }
}
