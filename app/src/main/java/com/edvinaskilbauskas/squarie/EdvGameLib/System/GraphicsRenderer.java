package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pufix on 6/27/13.
 */
public class GraphicsRenderer {
    private GraphicsSystem graphicsSystem;
    private VertexManager vertexManager;
    private int elementsDrawn;
    private int indexDataOffset;
    private ArrayList<RenderAction> renderBuffer;
    private Pool<RenderAction> renderPool;

    public GraphicsRenderer(GraphicsSystem graphicsSystem){
        vertexManager = new VertexManager(graphicsSystem,20000,40000,true,true);
        renderBuffer = new ArrayList<RenderAction>();
        renderPool = new Pool(RenderAction.class,2000);
        this.graphicsSystem = graphicsSystem;

        elementsDrawn = 0;
        indexDataOffset = 0;
    }

    public void appendForRendering(RenderableObject object, float x, float y,float z, float r, float g, float b, float a, float scaleX, float scaleY, float scaleZ, float angle){
        RenderAction renderAction = renderPool.getObject();
        renderAction.setup(object,x,y,z,r,g,b,a,scaleX,scaleY,scaleZ,angle,graphicsSystem.getShader());

        renderBuffer.add(renderAction);
    }

    public void renderObject(RenderableObject object, float x, float y,float z, float r, float g, float b, float a, float scaleX, float scaleY, float scaleZ){
        int size;

        if(object instanceof RenderableObject2D){
            size = object.getVertexCount()*2;
            for(int i = 0; i < size; i+=2){
                vertexManager.appendVertexPositionData(((object.vertexPositionData[i])*scaleX) + x - object.getCenter().x*scaleX);
                vertexManager.appendVertexPositionData((object.vertexPositionData[i+1]*scaleY)+ y - object.getCenter().y*scaleY);
                vertexManager.appendVertexPositionData(z);
            }
        }else if(object instanceof RenderableObject3D){
            size = object.getVertexCount()*3;
            for(int i = 0; i < size; i+=3){
                vertexManager.appendVertexPositionData((object.vertexPositionData[i]*scaleX) + x);
                vertexManager.appendVertexPositionData((object.vertexPositionData[i+1]*scaleY)+ y);
                vertexManager.appendVertexPositionData((object.vertexPositionData[i+2]*scaleZ)+ z);
            }
        }

        size = object.getIndexCount();
        for(int i = 0; i < size; i++){
            vertexManager.appendIndexData((short)(object.indexData[i]+indexDataOffset));
        }

        size = object.vertexColorData.length;

        for(int i = 0; i < size; i+=4){
            vertexManager.appendVertexColorData(object.vertexColorData[i]*r);
            vertexManager.appendVertexColorData(object.vertexColorData[i+1]*g);
            vertexManager.appendVertexColorData(object.vertexColorData[i+2]*b);
            vertexManager.appendVertexColorData(object.vertexColorData[i+3]*a);
        }

        size = object.vertexTexCoordData.length;

        for(int i = 0; i < size; i+=2){
            vertexManager.appendVertexTexCoordData(object.vertexTexCoordData[i]);
            vertexManager.appendVertexTexCoordData(object.vertexTexCoordData[i+1]);
        }

        indexDataOffset += object.getVertexCount();
        elementsDrawn += object.getIndexCount();
    }

    public void renderObject(RenderableObject object, float x, float y,float z, float r, float g, float b, float a, float scaleX, float scaleY, float scaleZ, float angle){
        float rotatedX;
        float rotatedY;
        float rawX;
        float rawY;
        angle = (float)Math.toRadians(angle);
        int size;

        if(object instanceof RenderableObject2D){
            size = object.getVertexCount()*2;
            for(int i = 0; i < size; i+=2){
                rawX = (object.vertexPositionData[i]-object.getCenter().x*scaleX)*scaleX;
                rawY = (object.vertexPositionData[i+1]-object.getCenter().y*scaleY)*scaleY;
                rotatedX = (float)((rawX * Math.cos(angle)) - (rawY * Math.sin(angle)));
                rotatedY = (float)((rawX * Math.sin(angle)) + (rawY * Math.cos(angle)));

                vertexManager.appendVertexPositionData((rotatedX) + x);
                vertexManager.appendVertexPositionData((rotatedY)+ y);
                vertexManager.appendVertexPositionData(z);
            }
        }else if(object instanceof RenderableObject3D){
            size = object.getVertexCount()*3;
            for(int i = 0; i < size; i+=3){
                vertexManager.appendVertexPositionData((object.vertexPositionData[i]*scaleX) + x);
                vertexManager.appendVertexPositionData((object.vertexPositionData[i+1]*scaleY)+ y);
                vertexManager.appendVertexPositionData((object.vertexPositionData[i+2]*scaleZ)+ z);
            }
        }

        size = object.getIndexCount();
        for(int i = 0; i < size; i++){
            vertexManager.appendIndexData((short)(object.indexData[i]+indexDataOffset));
        }

        size = object.vertexColorData.length;

        for(int i = 0; i < size; i+=4){
            vertexManager.appendVertexColorData(object.vertexColorData[i]*r);
            vertexManager.appendVertexColorData(object.vertexColorData[i+1]*g);
            vertexManager.appendVertexColorData(object.vertexColorData[i+2]*b);
            vertexManager.appendVertexColorData(object.vertexColorData[i+3]*a);
        }

        size = object.vertexTexCoordData.length;

        for(int i = 0; i < size; i+=2){
            vertexManager.appendVertexTexCoordData(object.vertexTexCoordData[i]);
            vertexManager.appendVertexTexCoordData(object.vertexTexCoordData[i+1]);
        }

        indexDataOffset += object.getVertexCount();
        elementsDrawn += object.getIndexCount();
    }

    /*public void renderObjectRotated(RenderableObject object, float x, float y, float r, float g, float b, float a, float scaleX, float scaleY, float angle){
        float rotatedX;
        float rotatedY;
        float rawX;
        float rawY;
        angle = (float)Math.toRadians(angle);
        int size = object.getVertexCount()*2;

        for(int i = 0; i < size; i+=2){
            rawX = object.vertexPositionData[i];
            rawY = object.vertexPositionData[i+1];
            rotatedX = (float)((rawX * Math.cos(angle)) - (rawY * Math.sin(angle)));
            rotatedY = (float)((rawX * Math.sin(angle)) + (rawY * Math.cos(angle)));

            vertexManager.appendVertexPositionData((rotatedX*scaleX) + x);
            vertexManager.appendVertexPositionData((rotatedY*scaleY) + y);
        }

        size = object.getIndexCount();
        for(int i = 0; i < size; i++){
            vertexManager.appendIndexData((short)(object.indexData[i]+indexDataOffset));
        }

        size = object.vertexColorData.length;

        for(int i = 0; i < size; i+=4){
            vertexManager.appendVertexColorData(object.vertexColorData[i]*r);
            vertexManager.appendVertexColorData(object.vertexColorData[i+1]*g);
            vertexManager.appendVertexColorData(object.vertexColorData[i+2]*b);
            vertexManager.appendVertexColorData(object.vertexColorData[i+3]*a);
        }

        indexDataOffset += object.getVertexCount();
        elementsDrawn += object.getIndexCount();
    }*/

    private void renderAllObjects(){
        boolean renderObjects;

        for(int i = 0; i < renderBuffer.size(); i++){
            RenderAction currentAction = renderBuffer.get(i);
            RenderAction nextAction = null;

            if(i+1 < renderBuffer.size()){
                nextAction = renderBuffer.get(i+1);
            }

            if(currentAction.renderable.hasTexture()){
                GLES20.glEnable(GLES20.GL_TEXTURE_2D);
                currentAction.renderable.getTexture().bind();
                //GLES20.glBlendFunc(GLES20.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
                GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            }
            else{
                GLES20.glDisable(GLES20.GL_TEXTURE_2D);
                //GLES20.glBlendFunc(GLES20.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
                GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            }

            if(currentAction.angle == 0)
                renderObject(currentAction.renderable, currentAction.x, currentAction.y, currentAction.z,
                        currentAction.r,currentAction.g,currentAction.b,currentAction.a,
                        currentAction.scaleX, currentAction.scaleY, currentAction.scaleZ);
            else
                renderObject(currentAction.renderable, currentAction.x, currentAction.y, currentAction.z,
                        currentAction.r,currentAction.g,currentAction.b,currentAction.a,
                        currentAction.scaleX, currentAction.scaleY, currentAction.scaleZ, currentAction.angle);

            if(nextAction != null &&
               (currentAction.renderable.getTexture() != nextAction.renderable.getTexture() ||
                currentAction.shader != nextAction.shader)){
                renderObjects = true;
            }else{
                renderObjects = false;
            }

            if(renderObjects == true || i == renderBuffer.size()-1){
                try{
                    GLES20.glUseProgram(currentAction.shader.getProgramID());
                }catch(NullPointerException e){
                    e.printStackTrace();
                    Log.e("Error", "Make sure you have set shader before rendering anything");
                }
                currentAction.shader.getAttributes().setMVPMatrixData(graphicsSystem.getCamera().getMVPMatrix().toArray());
                currentAction.shader.getAttributes().setProjectionMatrix(graphicsSystem.getCamera().getProjectionMatrix().toArray());
                vertexManager.bind();
                vertexManager.draw(GLES20.GL_TRIANGLES,elementsDrawn);
                elementsDrawn = 0;
                indexDataOffset = 0;
                if(currentAction.renderable.hasTexture()){
                    currentAction.renderable.getTexture().unbind();
                }
            }
        }
    }

    public void update(){
        renderAllObjects();

        for(int i = 0; i < renderBuffer.size(); i++){
            renderPool.giveBackObject(renderBuffer.get(i));
        }
        renderBuffer.clear();


    }
}

