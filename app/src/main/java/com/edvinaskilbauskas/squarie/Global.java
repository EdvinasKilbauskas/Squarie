package com.edvinaskilbauskas.squarie;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Color;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Shaker;
import com.edvinaskilbauskas.squarie.Entities.Background;

import java.util.Random;

/**
 * Created by edvinas on 11/29/14.
 */
public class Global {
    public static final float gravity = 150.0f;
    public static final float SCREEN_WIDTH = 9;
    public static final float SCREEN_HEIGHT = 16;
    public static final float windSpeed = 11f;

    public static Shaker cameraShaker = new Shaker();
    public static boolean showAd = false;
    public static float scoreAlpha = 0.0f;
    public static Color deathBackgroundColor = new Color(1.0f,0.25f,0.25f,1.0f);

    public static float screenBlink = 0.0f;
    public static GameState gameState = GameState.TransitionToMainScreen;
    public static boolean canSpringUp = true;
    public static Random random = new Random();
    public static Background background = new Background();

    public static ScoreManager scoreManager;
}
