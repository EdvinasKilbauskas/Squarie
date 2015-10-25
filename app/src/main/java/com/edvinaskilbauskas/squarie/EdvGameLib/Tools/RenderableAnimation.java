package com.edvinaskilbauskas.squarie.EdvGameLib.Tools;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.RenderableObject2D;

/**
 * Created by pufix-pc on 11/26/13.
 */
public class RenderableAnimation {
    private RenderableObject2D frames[];
    private double frameDuration;
    private double timeSinceLastFrame;
    private int frameCount;
    private int currentFrameId;
    private boolean looping;
    private boolean ended;
    private boolean stopped;

    public RenderableAnimation(float frameDuration,boolean looping, RenderableObject2D ... frames){
        this.frames = frames;
        this.frameCount = frames.length;
        this.frameDuration = frameDuration;
        this.timeSinceLastFrame = 0;
        this.currentFrameId = 0;
        this.looping = looping;
        this.ended = false;
        this.stopped = false;
    }

    public void update(float deltaTime){
        if(stopped == false){
            this.timeSinceLastFrame += deltaTime;

            if(timeSinceLastFrame >= frameDuration){
                timeSinceLastFrame %= frameDuration;
                currentFrameId++;
            }

            if(currentFrameId == frameCount){
                if(looping == true)
                    currentFrameId = 0;
                else if(looping == false){
                    currentFrameId = frameCount-1;
                    ended = true;
                }
            }
        }
    }

    public int getCurrentFrameId(){
        return currentFrameId;
    }

    public void reset(){
        currentFrameId = 0;
        timeSinceLastFrame = 0;
        ended = false;
    }

    public void stop(){
        stopped = true;
    }

    public void resume(){
        stopped = false;
    }

    public void setLooping(boolean looping){
        this.looping = looping;
    }

    public void setFrameDuration(float duration){
        this.frameDuration = duration;
    }

    public boolean ended(){
        return ended;
    }


    public RenderableObject2D getFrame(){
        return frames[currentFrameId];
    }

}