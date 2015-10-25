package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pufix on 6/26/13.
 */
public class TouchInputCollector implements View.OnTouchListener {
    private Pool<TouchEvent> touchEventPool;
    private TouchEvents touchEvents;
    private TouchEvents touchEventsReturn;
    float gameScreenWidth;
    float gameScreenHeight;
    private CameraES2 camera;

    public TouchInputCollector(View view, GraphicsSystem graphicsSystem){
        view.setOnTouchListener(this);

        this.gameScreenWidth = graphicsSystem.getScreenWidth();
        this.gameScreenHeight = graphicsSystem.getScreenHeight();

        camera = graphicsSystem.getCamera();

        touchEventPool = new Pool<TouchEvent>(TouchEvent.class,100);
        touchEvents = new TouchEvents();
        touchEventsReturn = new TouchEvents();
    }


    public synchronized boolean onTouch(View view, MotionEvent motionEvent){
        int action = motionEvent.getActionMasked();
        int index = motionEvent.getActionIndex();

        TouchEvent touchEvent;

        float rawX;
        float rawY;

        switch(action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                touchEvent = touchEventPool.getObject();
                touchEvent.action = TouchEvent.TouchAction.DOWN;
                touchEvent.id = motionEvent.getPointerId(index);

                rawX = motionEvent.getX(index);
                rawY = motionEvent.getY(index);

                touchEvent.rawPosition.set(rawX,rawY);

                touchEvent.position.set((rawX/view.getWidth())*gameScreenWidth,
                                        (rawY/view.getHeight())*gameScreenHeight);

                touchEvent.worldPosition.set(touchEvent.position);
                touchEvent.worldPosition.add(camera.getUpperLeft().x,camera.getUpperLeft().y);

                touchEvents.add(touchEvent);

                //"TouchEvent","ID: " + touchEvent.id + " down at X: " + touchEvent.position.x + " Y: " + touchEvent.position.y);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:

                touchEvent = touchEventPool.getObject();
                touchEvent.action = TouchEvent.TouchAction.UP;
                touchEvent.id = motionEvent.getPointerId(index);

                rawX = motionEvent.getX(index);
                rawY = motionEvent.getY(index);

                touchEvent.rawPosition.set(rawX,rawY);

                touchEvent.position.set((rawX/view.getWidth())*gameScreenWidth,
                        (rawY/view.getHeight())*gameScreenHeight);

                touchEvent.worldPosition.set(touchEvent.position);
                touchEvent.worldPosition.add(camera.getUpperLeft().x,camera.getUpperLeft().y);

                touchEvents.add(touchEvent);

                //"TouchEvent","ID: " + touchEvent.id + " up at X: " + touchEvent.position.x + " Y: " + touchEvent.position.y);
                break;

            case MotionEvent.ACTION_MOVE:
                int size = motionEvent.getPointerCount();

                for(int i = 0; i < size; i++){
                    touchEvent = touchEventPool.getObject();
                    touchEvent.action = TouchEvent.TouchAction.MOVE;

                    touchEvent.id = motionEvent.getPointerId(i);

                    rawX = motionEvent.getX(i);
                    rawY = motionEvent.getY(i);

                    touchEvent.rawPosition.set(rawX,rawY);
                    touchEvent.position.set((rawX/view.getWidth())*gameScreenWidth,
                            (rawY/view.getHeight())*gameScreenHeight);

                    touchEvent.worldPosition.set(touchEvent.position);
                    touchEvent.worldPosition.add(camera.getUpperLeft().x,camera.getUpperLeft().y);

                    touchEvents.add(touchEvent);
                   // "TouchEvent","ID: " + touchEvent.id + " move at X: " + touchEvent.position.x + " Y: " + touchEvent.position.y);

                }

                break;
        }

        return true;
    }

    public synchronized void update(){
       //for(int i = 0; i < touchEvents.size(); i++){
       //    touchEventPool.giveBackObject(touchEvents.get(i));
       // }
       // touchEvents.clear();
    }

    public synchronized TouchEvents getTouchEvents(){
        touchEventsReturn.clear();
        for(int i = 0; i < touchEvents.size(); i++){
            touchEventPool.giveBackObject(touchEvents.get(i));
            touchEventsReturn.add(touchEvents.get(i));
        }
        touchEvents.clear();
        return touchEventsReturn;
    }
}

