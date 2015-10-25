package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;

/**
 * Created by pufix on 6/26/13.
 */
public class RenderableObject2D extends RenderableObject{

    public RenderableObject2D(Texture texture, int vertexCount){
        super(texture);
        vertexPositionData = new float[vertexCount*2];
        vertexColorData = new float[vertexCount*4];
        vertexTexCoordData = new float[vertexCount*2];
        this.vertexCount = vertexCount;
    }

    public RenderableObject2D(int vertexCount){
        super(null);
        vertexPositionData = new float[vertexCount*2];
        vertexColorData = new float[vertexCount*4];
        vertexTexCoordData = new float[vertexCount*2];
        this.vertexCount = vertexCount;
    }

    public void setVertexAttributes(int vertexId, float x, float y, float r, float g, float b, float a, float u, float v){
        setVertexPosition(vertexId, x, y);
        setVertexColor(vertexId, r,b,g,a);
        setVertexTextureCoords(vertexId, u, v);
    }

    public void setVertexPosition(int vertexId, float x, float y){
        vertexPositionData[(vertexId*2)] = x;
        vertexPositionData[(vertexId*2)+1] = y;
    }

    public void getVertexPosition(int vertexId, Vector2 dest){
        dest.x = vertexPositionData[(vertexId*2)];
        dest.y = vertexPositionData[(vertexId*2)+1];
    }

    // rescales vertex position data
    public void scale(float x, float y){
        int len = vertexPositionData.length;
        for(int i = 0; i < len; i+=2){
            vertexPositionData[i] *= x;
            vertexPositionData[i+1] *= y;
        }
    }


}
