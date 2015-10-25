package com.edvinaskilbauskas.squarie;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.RenderSystem;
import com.edvinaskilbauskas.squarie.Entities.SmoothMovement;
import com.edvinaskilbauskas.squarie.Entities.SmoothTransition;

/**
 * Created by edvinas on 1/28/15.
 */
public class ScoreBoard {
    private SmoothMovement m_position;
    private SmoothTransition m_scoreTransition;

    public ScoreBoard(){
        m_position = new SmoothMovement();
        m_scoreTransition = new SmoothTransition();
    }

    public void start(){
        //gameOverText.startTransition(4.5f, -2.0f, 4.5f,3.0f, 1.32f);
        //scoreBoard.startTransition(4.5f, 18.0f, 4.5f,6.5f, 1.32f);
    }

    public void end(){

    }

    public void render(RenderSystem renderSystem){
        //float scoreX = logicManager.scoreBoard.position.x;
        //float scoreY = logicManager.scoreBoard.position.y;

        //textRenderer.setColor(0,0,0,1);
        //renderText(logicManager.gameOverText.position.x,logicManager.gameOverText.position.y, fontNormal, "Game Over");

        //renderSystem.renderObject(Assets.square,scoreX,scoreY,0, 0.0f,0.0f,0.0f,0.2f, 9.0f,3.0f);

        //textRenderer.setScale(0.75f);
        //renderText(scoreX-2.25f,scoreY-1.10f, fontBold, "Score");
        //renderText(scoreX+2.25f,scoreY-1.10f, fontBold, "Best");

        //textRenderer.setScale(1.0f);
       // renderText(scoreX-2.25f,scoreY-0.10f, fontNormal, "" + Global.score);
        //renderText(scoreX+2.25f,scoreY-0.10f, fontNormal, "" + Global.best);
    }

    public void update(float deltaTime){

    }
}
