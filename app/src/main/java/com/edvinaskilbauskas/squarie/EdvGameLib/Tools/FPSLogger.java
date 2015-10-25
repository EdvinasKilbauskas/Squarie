package com.edvinaskilbauskas.squarie.EdvGameLib.Tools;

/**
 * Created by pufix on 7/26/13.
 */
import android.util.Log;

public class FPSLogger {
    static long startTime = System.nanoTime();
    static int frames = 0;
    public static int lastFPS = 0;

    public static int logFrame() {
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            lastFPS = frames;
            frames = 0;
            startTime = System.nanoTime();
        }

        return lastFPS;
    }

    public static void sleep(int millis){
        startTime = System.nanoTime();
            while(System.nanoTime() - startTime <= millis * 1000000) {

            }

    }
}