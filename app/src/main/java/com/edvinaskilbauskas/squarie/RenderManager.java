package com.edvinaskilbauskas.squarie;

import android.opengl.GLES20;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.Camera;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.CameraES2;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.FileIOSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.Font;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.GameSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.GraphicsSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.RenderSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.ShaderProgram;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.TextRenderer;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Shaker;
import com.edvinaskilbauskas.squarie.Entities.Background;

/**
 * Created by edvinas on 11/29/14.
 */
public class RenderManager {
    GraphicsSystem graphicsSystem;
    RenderSystem renderSystem;
    LogicManager logicManager;
    FileIOSystem fileIOSystem;
    GameSystem gameSystem;
    CameraES2 camera;
    Font fontNormal;
    Font fontBold;
    TextRenderer textRenderer;
    ShaderProgram shapeShader;
    ShaderProgram textureShader;
    private boolean playServicesConnected;

    float titleTextAlpha;
    float screenAlpha;

    public RenderManager(GameSystem gameSystem){
        renderSystem = gameSystem.getRenderSystem();
        graphicsSystem = gameSystem.getGraphicsSystem();
        logicManager = LogicManager.getInstance(gameSystem);
        this.gameSystem = gameSystem;
        fileIOSystem = gameSystem.getFileIOSystem();
        camera = graphicsSystem.getCamera();
        titleTextAlpha = 1.0f;
        screenAlpha = 1.0f;
        playServicesConnected = false;
    }

    public void render(float deltaTime){
        GLES20.glClearColor(0.25f, 0.25f, 0.25f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_ALPHA_BITS);

        Global.cameraShaker.update(deltaTime);
        camera.setCenter(Global.SCREEN_WIDTH / 2.0f + Global.cameraShaker.getValue(), Global.SCREEN_HEIGHT / 2.0f + Global.cameraShaker.getValue(), 0);

        graphicsSystem.setShader(shapeShader);

        Global.background.update(deltaTime);
        Global.background.render(renderSystem);

        if (Global.gameState == GameState.TransitionToGame ||
                Global.gameState == GameState.TransitionToMainScreen ||
                Global.gameState == GameState.MainScreen){
            renderText(4.5f,4.5f, fontNormal, "Squarie");
        }else{
            // always keep connected to play services
            if(playServicesConnected == false)
                playServicesConnected = gameSystem.connectPlayServices();
        }


        renderGame(deltaTime);
        renderDead();

        int score = Global.scoreManager.getCurrentScore();

        switch(Global.gameState){
            case TransitionToMainScreen:
                transitionToMainScreen(deltaTime);

                break;
            case MainScreen:

                break;
            case TransitionToGame:
                renderTransitionToGame(deltaTime);
                break;

            case Dead:
                //renderDead();
                break;

            case Game:
                Global.scoreAlpha += 1.0f * deltaTime;
                if(Global.scoreAlpha > 1.0f) Global.scoreAlpha = 1.0f;
                textRenderer.setColor(0, 0, 0, Global.scoreAlpha);

                if(score == 69){
                    renderText(5.5f, 4.5f, fontNormal, ";)");
                }
                renderText(4.5f, 4.5f, fontNormal, "" + score);

                break;

            case NextPlatformTransition:
                textRenderer.setColor(0, 0, 0, 1);

                if(score == 69){
                    renderText(5.5f, 4.5f, fontNormal, ";)");
                }
                renderText(4.5f, 4.5f, fontNormal, "" + score);

                break;
        }

        Global.screenBlink -= 1.5f*deltaTime;   // blink screen after death speed
        if(Global.screenBlink < 0) Global.screenBlink = 0;

        renderSystem.renderObject(Assets.square, 4.5f, 8, 0, 1.0f, 1.0f, 1.0f, Global.screenBlink, 9, 16);

    }

    public void renderText(float x, float y, Font font, String str){
        textRenderer.setFont(font);
        graphicsSystem.setShader(textureShader);
        textRenderer.renderText(renderSystem,x,y,str);
        graphicsSystem.setShader(shapeShader);
    }

    public void transitionToMainScreen(float deltaTime){
        Global.background.startRotation();
        screenAlpha -= deltaTime*0.7f;
        renderSystem.renderObject(Assets.square,Global.SCREEN_WIDTH/2.0f, Global.SCREEN_HEIGHT/2.0f,0, 0,0,0,screenAlpha,Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
        if(screenAlpha <= 0){
            screenAlpha = 0;
            logicManager.connectToPlayServices();
            Global.gameState = GameState.MainScreen;
        }
    }

    public void loadAssets(){
        textureShader = new ShaderProgram(fileIOSystem,"shaders/simpleVertexShader.glsl","shaders/simpleFragmentShader.glsl");
        shapeShader = new ShaderProgram(fileIOSystem,"shaders/simpleVertexShader.glsl","shaders/noTextureFragmentShader.glsl");

        fontNormal = new Font(gameSystem, "fonts/Walkway.fnt");
        fontBold = new Font(gameSystem, "fonts/WalkwayBold.fnt");

        fontBold.scale(0.0055f);
        fontNormal.scale(0.0055f);

        textRenderer = new TextRenderer(fontNormal);

        textRenderer.setColor(0,0,0,1);
        textRenderer.setAlignment(TextRenderer.TextAlignment.Center);

        Assets.setup(gameSystem);
    }

    public void renderDead(){
        float scoreX = logicManager.scoreBoard.position.x;
        float scoreY = logicManager.scoreBoard.position.y;

        textRenderer.setColor(0, 0, 0, 1);
        renderText(logicManager.gameOverText.position.x, logicManager.gameOverText.position.y, fontNormal, "Game Over");

        renderSystem.renderObject(Assets.square, scoreX, scoreY, 0, 0.0f, 0.0f, 0.0f, 0.2f, 9.0f, 3.0f);

        textRenderer.setScale(0.75f);
        renderText(scoreX - 2.75f, scoreY - 1.10f, fontBold, "Score");
        renderText(scoreX + 2.75f, scoreY - 1.10f, fontBold, "Best");

        graphicsSystem.setShader(textureShader);
        renderSystem.renderObject(Assets.leaderboards, scoreX, scoreY+0.3f, 0, 1.0f, 1.0f, 1.0f, 1.0f,  2.2f,2.2f);
        graphicsSystem.setShader(shapeShader);

        textRenderer.setScale(1.0f);
        renderText(scoreX-2.75f,scoreY-0.10f, fontNormal, "" + Global.scoreManager.getCurrentScore());
        renderText(scoreX+2.75f,scoreY-0.10f, fontNormal, "" + Global.scoreManager.getHighScore());
    }

    public void renderGame(float deltaTime){
        logicManager.mainPlatform.render(renderSystem);
        logicManager.secondPlatform.render(renderSystem);
        logicManager.square.render(renderSystem);
    }

    public void renderTransitionToGame(float deltaTime){
        titleTextAlpha -= 1.5f * deltaTime;
        if(titleTextAlpha < 0) titleTextAlpha = 0;
        textRenderer.setColor(0, 0, 0, titleTextAlpha);
    }
}
