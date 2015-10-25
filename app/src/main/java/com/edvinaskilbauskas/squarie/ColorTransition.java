package com.edvinaskilbauskas.squarie;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Color;
import com.edvinaskilbauskas.squarie.Entities.SmoothTransition;

/**
 * Created by edvinas on 1/24/15.
 */
public class ColorTransition {
    private SmoothTransition m_redTransition;
    private SmoothTransition m_greenTransition;
    private SmoothTransition m_blueTransition;
    private SmoothTransition m_alphaTransition;
    private Color m_color;

    public ColorTransition(){
        m_redTransition = new SmoothTransition();
        m_greenTransition = new SmoothTransition();
        m_blueTransition = new SmoothTransition();
        m_alphaTransition = new SmoothTransition();
        m_color = new Color();
    }

    public boolean isTransitioning(){
        return m_redTransition.isTransitioning();
    }

    public void startTransitioning(float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2, float duration){
        m_redTransition.startTransition(r1,r2,duration);
        m_greenTransition.startTransition(g1,g2,duration);
        m_blueTransition.startTransition(b1,b2,duration);
        m_alphaTransition.startTransition(a1,a2,duration);
    }

    public void update(float deltaTime){
        m_redTransition.update(deltaTime);
        m_greenTransition.update(deltaTime);
        m_blueTransition.update(deltaTime);
        m_alphaTransition.update(deltaTime);

        if(isTransitioning()){
            m_color.set(m_redTransition.getValue(),
                        m_greenTransition.getValue(),
                        m_blueTransition.getValue(),
                        m_alphaTransition.getValue());
        }
    }

    public Color getColor(){
        return m_color;
    }

}
