package com.edvinaskilbauskas.squarie;

import android.util.Log;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.GameSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.InputSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.TouchEvent;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.TouchEvents;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Button;
import com.google.android.gms.games.Games;

/**
 * Created by edvinas on 11/29/14.
 */
public class InputManager {
    InputSystem inputManager;
    LogicManager logicManager;
    Button leaderboardsButton;
    GameSystem gameSystem;
    public InputManager(GameSystem gameSystem){
        this.gameSystem = gameSystem;
        inputManager = gameSystem.getInputSystem();
        logicManager = LogicManager.getInstance(gameSystem);

        leaderboardsButton = new Button(Global.SCREEN_WIDTH/2.0f,6.8f,2.2f,2.2f);
    }

   public void update(float deltaTime){
       TouchEvents touchEvents;

       touchEvents = inputManager.getTouchEvents();

       for(int i = 0; i < touchEvents.size(); i++){
           TouchEvent event = touchEvents.get(i);

           switch(Global.gameState){
               case MainScreen:
                   updateMainScreen(event);
                   break;
               case Game:
                   updateGame(event);
                   break;
               case Dead:
                   updateDead(event);
                   break;
           }
       }
   }

    public void updateDead(TouchEvent event){
        leaderboardsButton.update(event);
        if(logicManager.mainPlatform.isTransitioning() == false && logicManager.deathTransition == false){

            if (leaderboardsButton.isClickedDown()) {
                gameSystem.startLeaderboardActivity();
            }else if(event.action == TouchEvent.TouchAction.DOWN) {
                Global.gameState = GameState.TransitionFromDeath;
                Global.background.transitionColor(Global.random.nextFloat() * 0.75f, Global.random.nextFloat() * 0.75f, Global.random.nextFloat() * 0.75f, 1.0f);
                Global.background.startRotation();
            }
        }
    }

    public void updateGame(TouchEvent event){
        if(event.action == TouchEvent.TouchAction.DOWN){

            if(logicManager.mainPlatform.stringingUp == false) {
                if(Global.canSpringUp == true) {
                    Global.canSpringUp = false;
                    logicManager.mainPlatform.stringUp();
                }
            }
        }
        if(event.action == TouchEvent.TouchAction.UP){
            logicManager.mainPlatform.release();
        }
    }

    public void updateMainScreen(TouchEvent event){
        if(event.action == TouchEvent.TouchAction.DOWN){
            Global.gameState = GameState.TransitionToGame;
        }
    }

}
