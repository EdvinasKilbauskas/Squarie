package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pufix on 6/25/13.
 */
public class Texture {
    private GameSystem gameSystem;
    private GraphicsSystem graphicsSystem;
    private Bitmap bitmap;
    private boolean isBound;
    private int id;
    private float width;
    private float height;

    public Texture(GameSystem gameSystem, String bitmapPath){
        this.gameSystem = gameSystem;
        this.graphicsSystem = gameSystem.getGraphicsSystem();

        bitmap = BitmapFactory.decodeStream(gameSystem.getFileIOSystem().openAsset(bitmapPath));

        width = bitmap.getWidth();
        height = bitmap.getHeight();
        isBound = false;

        GLES20.glEnable(GL10.GL_TEXTURE_2D);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glEnable(GL10.GL_BLEND);

       // GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);


        int ids [] = new int[1];
        GLES20.glGenTextures(1, ids, 0);
        id = ids[0];

        bind();

        GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        unbind();
    }

    public int getId(){
        return id;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public void bind(){
        if(isBound == false){
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, id);
            isBound = true;
        }
    }

    public void unbind(){
        if(isBound == true){
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            isBound = false;
        }
    }
}
