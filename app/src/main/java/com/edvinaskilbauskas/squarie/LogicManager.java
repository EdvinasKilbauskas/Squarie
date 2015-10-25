package com.edvinaskilbauskas.squarie;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.GameSystem;
import com.edvinaskilbauskas.squarie.Entities.Platform;
import com.edvinaskilbauskas.squarie.Entities.SmoothMovement;
import com.edvinaskilbauskas.squarie.Entities.Square;
import com.google.android.gms.games.Games;

import java.util.Random;

/**
 * Created by edvinas on 11/29/14.
 */
public class LogicManager {
    private GameSystem gameSystem;
    public Square square;
    public Platform mainPlatform;
    public Platform secondPlatform;
    public static LogicManager instance;
    public boolean touchingPlatform;
    public int transitionStage;
    public float transitionAmount;
    public SmoothMovement gameOverText;
    public SmoothMovement scoreBoard;
    public boolean deathTransition;


    private LogicManager(GameSystem gameSystem){
        this.gameSystem = gameSystem;
        square = new Square(4.5f,11.7f);
        mainPlatform = new Platform(4.5f, 3, 4);
        touchingPlatform = true;
        secondPlatform = new Platform(0,0,0);
        transitionStage = 1;
        transitionAmount = 0;
        deathTransition = false;
        gameOverText = new SmoothMovement();
        gameOverText.position.set(4.5f,-2.0f);

        scoreBoard = new SmoothMovement();
        scoreBoard.position.set(4.5f,-2.0f);
        Global.background.setColor(0,0.5f,1.0f,1.0f);
    }


    static LogicManager getInstance(GameSystem gameSystem){
        if(instance == null){
            instance = new LogicManager(gameSystem);
        }

        return instance;
    }

    public void connectToPlayServices(){
        //gameSystem.runOnUiThread(new Runnable(){
         //   public void run(){
                gameSystem.connectPlayServices();
        //    }
        //});
    }

    public void generatePlatform(Platform platform){
        float width = 0.25f+Global.random.nextFloat()*2.0f;
        float add = width/2.0f+mainPlatform.getBounds().right()+0.5f;
        float position = add + Global.random.nextFloat()*(Global.SCREEN_WIDTH-add-width/2.0f);
        platform.set(position, width, 4f);
    }


    public void update(float deltaTime){
        switch(Global.gameState){
            case MainScreen:
                break;

            case TransitionToGame:
                updateTransitionToGame(deltaTime);
                break;

            case Game:
                updateGame(deltaTime);
                break;

            case NextPlatformTransition:
                updateNextPlatformTransition(deltaTime);
                break;
            case Dead:
                updateDead(deltaTime);
                break;

            case TransitionFromDeath:
                updateTransitionFromDeath(deltaTime);
                break;
        }
    }


    public void updateTransitionFromDeath(float deltaTime){

        switch (transitionStage) {
            case 1:
                square.startTransition(square.position.x,square.position.y,1.5f,11.7f, 0.66f);
                mainPlatform.startTransition(mainPlatform.position.x,mainPlatform.position.y,1.5f,16 , 0.66f);
                scoreBoard.startTransition(scoreBoard.position.x,scoreBoard.position.y,4.5f,scoreBoard.position.y-9, 0.66f);
                gameOverText.startTransition(gameOverText.position.x,gameOverText.position.y,4.5f,gameOverText.position.y-9, 0.66f);

                scoreBoard.update(deltaTime);
                gameOverText.update(deltaTime);
                mainPlatform.update(deltaTime);
                square.update(deltaTime);

                if(scoreBoard.isTransitioning() == false){
                    transitionStage = 2;
                    generatePlatform(secondPlatform);
                    secondPlatform.rise();
                    Global.showAd = false;
                }
                break;

            case 2:
                secondPlatform.update(deltaTime);
                //Global.cameraShaker.setShakeAmount(0.1f);
                if(secondPlatform.isTransitioning() == false){
                    //Global.cameraShaker.setShakeAmount(0);
                    Global.scoreManager.restartScore();
                    Global.scoreAlpha = 0.0f;
                    Global.gameState = GameState.Game;
                    transitionStage = 1;
                }
                break;
        }
    }

    public void updateTransitionToGame(float deltaTime){
        switch (transitionStage) {
            case 1:
                square.startTransition(square.position.x, square.position.y, 1.5f,11.7f, 0.66f);
                mainPlatform.startTransition(mainPlatform.position.x, mainPlatform.position.y, 1.5f,16 , 0.66f);

                updatePlatforms(deltaTime);
                square.update(deltaTime);

                if(square.isTransitioning() == false){
                    transitionStage = 2;
                    generatePlatform(secondPlatform);
                    secondPlatform.rise();
                }
            break;

            case 2:
                secondPlatform.update(deltaTime);
                //Global.cameraShaker.setShakeAmount(0.1f);
                if(secondPlatform.isTransitioning() == false){
                    //Global.cameraShaker.setShakeAmount(0);
                    Global.gameState = GameState.Game;
                    transitionStage = 1;
                }
                break;
        }

    }

    public void updateGame(float deltaTime) {
        //if (mainPlatform.stringingUp == true && square.velocity.y == 0) {
        //    square.position.y += 5 * deltaTime;
       // }

        int steps = 20;
        for (int i = 0; i < steps; i++){
            float newDelta = deltaTime/steps;


            square.velocity.y += Global.gravity * newDelta;

            square.update(newDelta);
            updatePlatforms(newDelta);

            square.velocity.x += Global.windSpeed*5.0f*newDelta;

            // this if statement makes it so that the square couldn't jump on the main platform
            if(!(Global.canSpringUp == false && mainPlatform.stringingUp == false && mainPlatform.releasing == false))
                resolveCollision(square, mainPlatform);

            if(resolveCollision(square, secondPlatform)){
                if(secondPlatform.isRising() == false) {
                    onNextPlatform();
                    break;
                }
            }
        }

        if(square.getBounds().top() > Global.SCREEN_HEIGHT && secondPlatform.isRising() == false){
            onDeath();
        }

    }

    boolean resolveCollision(Square square, Platform platform){
        boolean touchedTop = false;

        if(square.getBounds().intersects(platform.getBounds())){
            // overlaping amounts
            float overX;
            float overY;

            // square is on the left side of the platform
            if(square.position.x <= platform.position.x){
                overX = Math.abs(square.getBounds().right()-platform.getBounds().left());
                overY = Math.abs(square.getBounds().bottom()-platform.getBounds().top());

                // square touched TOP LEFT side of the platform
                if(overX >= overY){
                    if(overX < 0.06f) return false;
                    touchedTop = true;
                    square.velocity.x = platform.velocity.x;
                    // fixes the problem where square sometimes gets stuck and doesn't jump
                    // because sometimes platform velocity changes to 0 BEFORE solving the collision, and square velocity
                    // gets assigned to zero
                    if(!(square.velocity.y < 0 && platform.velocity.y == 0))
                        square.velocity.y = platform.velocity.y;

                    square.position.y = platform.getBounds().top()-square.size.y/2.0f;
                    touchingPlatform = true;
                // square touched LEFT side of the platform
                }else{
                        if(square.velocity.x > 0)
                            square.velocity.x = platform.velocity.x;;
                        square.position.x -= overX;

                }
            // square is on the right side of the platform
            }else{
                // overlaping amounts
                overX = Math.abs(square.getBounds().left()-platform.getBounds().right());
                overY = Math.abs(square.getBounds().bottom()-platform.getBounds().top());

                // square touched TOP RIGHT side of the platform
                if(overX >= overY){
                    if(overX < 0.06f) return false;
                    touchedTop = true;
                    square.velocity.x = platform.velocity.x;

                    // fixes the problem where square sometimes gets stuck and doesn't jump
                    // because sometimes platform velocity changes to 0 BEFORE solving the collision, and square velocity
                    // gets assigned to zero
                    if(!(square.velocity.y < 0 && platform.velocity.y == 0))
                        square.velocity.y = platform.velocity.y;

                    square.position.y = platform.getBounds().top()-square.size.y/2.0f;
                    touchingPlatform = true;
                // square touched the RIGHT side of the platform
                }else{
                    if(square.velocity.x < 0)
                        square.velocity.x = platform.velocity.x;
                    square.position.x += overX;
                }
            }
        }

        return touchedTop;
    }

    public void onNextPlatform(){
        Global.canSpringUp = true;
        Global.gameState = GameState.NextPlatformTransition;
        Global.scoreManager.incrementScore(1);
        //Global.cameraShaker.startShake(Shaker.Type.Single, shakeAmount, shakteTime);
        transitionAmount = square.position.x - 1f;
    }

    public void onDeath(){
        Global.background.reverseRotation();
        Global.background.transitionColor(Global.deathBackgroundColor);
        Global.gameState = GameState.Dead;
        Global.showAd = true;
        Global.canSpringUp = true;
        Global.screenBlink = 1.0f;

        if(Global.scoreManager.getHighScore() < Global.scoreManager.getCurrentScore())
            Global.scoreManager.saveHighScore(Global.scoreManager.getCurrentScore());

        // google game services
        if(gameSystem.googleApiClient.isConnected() && gameSystem.googleApiClient != null && gameSystem.isNetworkAvailable())
            Games.Leaderboards.submitScore(gameSystem.googleApiClient, gameSystem.getString(R.string.leaderboard_id), Global.scoreManager.getHighScore());

        mainPlatform.startTransition(mainPlatform.position.x, mainPlatform.position.y, 0-mainPlatform.size.y/2.0f, mainPlatform.position.y + mainPlatform.size.y, 0.66f);
        secondPlatform.startTransition(secondPlatform.position.x, secondPlatform.position.y,9+secondPlatform.size.x/2.0f, secondPlatform.position.y + secondPlatform.size.y, 0.66f);

        gameOverText.startTransition(4.5f, -2.0f, 4.5f,3.0f, 1.32f);
        scoreBoard.startTransition(4.5f, 18.0f, 4.5f, 6.5f, 1.32f);

        deathTransition = true;
    }

    public void updatePlatforms(float deltaTime){
        mainPlatform.update(deltaTime);
        secondPlatform.update(deltaTime);
    }

    public void updateDead(float deltaTime){
        //updateGame(deltaTime);
        updatePlatforms(deltaTime);
        gameOverText.update(deltaTime);
        scoreBoard.update(deltaTime);
        square.update(deltaTime);

        if(deathTransition && mainPlatform.isTransitioning() == false){
            square.velocity.set(0,0);
            square.startTransition(4.5f, Global.SCREEN_HEIGHT+square.size.y/2.0f, 4.5f, 12f-square.size.y/2.0f, 0.66f);

            mainPlatform.size.set(3,4);
            mainPlatform.startTransition(4.5f, Global.SCREEN_HEIGHT + mainPlatform.size.y + square.size.y, 4.5f, Global.SCREEN_HEIGHT, 0.66f);

            deathTransition = false;
        }

        //Global.gameState = GameState.Game;
    }

    public void updateMainScreen(float deltaTime){


    }

    public void updateNextPlatformTransition(float deltaTime){
        square.startTransition(square.position.x, square.position.y, square.position.x - transitionAmount, square.position.y, 0.66f);
        mainPlatform.startTransition(mainPlatform.position.x, mainPlatform.position.y, mainPlatform.position.x - transitionAmount, mainPlatform.position.y+mainPlatform.size.y, 0.66f);
        secondPlatform.startTransition(secondPlatform.position.x, secondPlatform.position.y, secondPlatform.position.x - transitionAmount, secondPlatform.position.y, 0.66f);

        square.update(deltaTime);
        mainPlatform.update(deltaTime);
        secondPlatform.update(deltaTime);

        if(square.isTransitioning() == false){
            Platform temp = secondPlatform;
            secondPlatform = mainPlatform;
            mainPlatform = temp;

            generatePlatform(secondPlatform);
            secondPlatform.rise();
            Global.gameState = GameState.Game;
        }


    }
}
