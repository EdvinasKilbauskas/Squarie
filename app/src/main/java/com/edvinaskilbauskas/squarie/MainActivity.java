package com.edvinaskilbauskas.squarie;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.Font;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.GameSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.ShaderProgram;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.TextRenderer;


public class MainActivity extends GameSystem {
    RenderManager renderManager;
    InputManager inputManager;
    LogicManager logicManager;

    @Override
    protected void creating(){
        initializeSystem(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);

        Global.scoreManager = new ScoreManager(this);
        renderManager = new RenderManager(this);
        renderManager.loadAssets();
        logicManager = LogicManager.getInstance(this);
        inputManager = new InputManager(this);
    }

    protected void updating(float deltaTime){
        inputManager.update(deltaTime);
        logicManager.update(deltaTime);
        renderManager.render(deltaTime);
    }

    protected void resuming(){
        renderManager.loadAssets();
    }

    protected void pausing() {

    }
}
