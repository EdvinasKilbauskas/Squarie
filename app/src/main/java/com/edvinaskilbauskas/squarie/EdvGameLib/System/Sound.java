package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.media.SoundPool;

/**
 * Created by edvinas on 10/12/15.
 */
public class Sound {
    int soundID;
    SoundPool player;
    float volume;

    public Sound(int soundID, SoundPool player){
        this.soundID = soundID;
        this.player = player;
        volume = 1;
    }

    public void setVolume(float vol){
        this.volume = vol;
    }

    public void play(boolean isLooping, float speed){
        if(isLooping == true)
            player.play(soundID, volume, volume, 1, 1, speed);
        else if(isLooping == false)
            player.play(soundID, volume, volume, 1, 0, speed);
    }

    public void stop(){
        player.stop(soundID);
    }
}