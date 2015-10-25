package com.edvinaskilbauskas.squarie.EdvGameLib.Tools;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.TouchEvent;

/**
 * UI button.
 *
 * @author Edvinas Kilbauskas
 *
 */

public class Button{
    private Rectangle bounds;
    private TouchEvent lastTouchEvent;
    private boolean wasClickedDown;
    private Vector2 position;
    private float width;
    private float height;
    private boolean heldDown;
    private int touchedDownID;
    private boolean on;

    /**
     * Creates new button
     *
     * @param x button center
     * @param y button center
     * @param width
     * @param height
     */
    public Button(float centerx, float centery, float width, float height){
        position = new Vector2(centerx,centerx);
        this.width = width;
        this.height = height;

        bounds = new Rectangle(centerx,centery,width,height);
        bounds.setCentered(centerx,centery,width,height);

        lastTouchEvent = new TouchEvent();
        lastTouchEvent.id = -2;
        touchedDownID = -1;
        wasClickedDown = false;
        heldDown = false;
        on = false;
    }

    public void update(TouchEvent event){
        lastTouchEvent = event;

        if(isClickedDown() == true){
            wasClickedDown = true;
        }
        if(isHeldDown() == false){
            wasClickedDown = false;
        }

        // if finger is down, we register the ID of touch even and set heldDown to true
        if(isClickedDown()){
            heldDown = true;
            touchedDownID = lastTouchEvent.id;
        }

        // if finger is up, and the touchID of the current touch is same as the touch event when user clicked the button
        // then we set heldDown to false and ID to negative.
        if((lastTouchEvent.id == touchedDownID && (isReleased() || isInside(lastTouchEvent) == false))){
            on = !on;
            heldDown = false;
            touchedDownID = -1;
        }
    }


    public boolean isOn(){
        return on;
    }

    public void reset(){
        lastTouchEvent = null;
    }

    /**
     * Tests if the button is being held touched.
     *
     * @return z
     */
    public boolean isHeldDown(){
        return heldDown;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    private boolean isInside(TouchEvent event){
        if(lastTouchEvent != null){
            if(bounds.pointInside(event.position.x, event.position.y)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * Test if button was released.
     *
     * @return
     */
    public boolean isReleased(){
        if(lastTouchEvent != null){
            if(isInside(lastTouchEvent) &&
                    lastTouchEvent.action == TouchEvent.TouchAction.UP){
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if button was clicked, and then released.
     *
     * @return
     */
    public boolean isClicked(){
        if(isInside(lastTouchEvent) &&
                lastTouchEvent.action == TouchEvent.TouchAction.UP && wasClickedDown == true){
            wasClickedDown = false;
            return true;
        }

        return false;
    }

    /**
     * Checks if button clicked down
     *
     * @return
     */
    public boolean isClickedDown(){
        if(lastTouchEvent != null){
            if(isInside(lastTouchEvent) &&
                    lastTouchEvent.action == TouchEvent.TouchAction.DOWN){
                return true;
            }
        }

        return false;
    }


    public Vector2 getPosition(){
        return position;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }
}