package com.edvinaskilbauskas.squarie.EdvGameLib.System;

/**
 * Created by pufix on 6/24/13.
 */
public class RenderSystem {
    private GraphicsRenderer graphicsRenderer;

    public RenderSystem(GraphicsSystem graphicsSystem){
        this.graphicsRenderer = new GraphicsRenderer(graphicsSystem);
    }

    public void renderObject(RenderableObject object, float x, float y, float z, float r, float g, float b, float a, float scaleX, float scaleY){
        graphicsRenderer.appendForRendering(object, x, y, z,  r,g,b,a,  scaleX, scaleY, 1, 0);
    }

    public void renderObject(RenderableObject object, float x, float y, float z, float r, float g, float b, float a, float scaleX, float scaleY, float angle){
        graphicsRenderer.appendForRendering(object, x, y, z,  r,g,b,a,  scaleX, scaleY, 1, angle);
    }

    public void renderObject(RenderableObject object, float x, float y){
        graphicsRenderer.appendForRendering(object, x, y, 0,  1,1,1,1,  1, 1, 1, 0);
    }


   // public void renderObject(RenderableObject object, float x, float y, float z, float r, float g, float b, float a, float scaleX, float scaleY, float scaleZ){
        //graphicsRenderer.renderObject(object, x, y, z,  r,g,b,a, scaleX, scaleY, scaleZ);
    //}

    //public void renderObject(RenderableObject2D object, float x, float y, float r, float g, float b, float a, float scaleX, float scaleY, float angle){
    //    graphicsRenderer.renderObject(object, x, y, r,g,b,a, scaleX,scaleY, angle);
    //}

    public void update(){
        graphicsRenderer.update();
    }

}
