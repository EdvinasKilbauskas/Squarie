package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Color;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector3;

/**
 * Created by pufix on 7/20/13.
 */
public class RenderableObject3D extends RenderableObject{

    public RenderableObject3D(Texture texture, int vertexCount){
        super(texture);
        vertexPositionData = new float[vertexCount*3];
        vertexColorData = new float[vertexCount*4];
        vertexTexCoordData = new float[vertexCount*2];
        this.vertexCount = vertexCount;
    }

    public void setVertexAttributes(int vertexId, float x, float y, float z, float r, float g, float b, float a, float u, float v){
        setVertexPosition(vertexId, x, y, z);
        setVertexColor(vertexId, r,b,g,a);
        setVertexTextureCoords(vertexId, u, v);
    }

    public void setVertexAttributes(int vertexId, Vector3 position, Color color, Vector2 texCoords){
        setVertexPosition(vertexId, position.x, position.y, position.z);
        setVertexColor(vertexId, color.r,color.b,color.g,color.a);
        setVertexTextureCoords(vertexId, texCoords.x, texCoords.y);
    }


    public void setVertexPosition(int vertexId, float x, float y, float z){
        vertexPositionData[(vertexId*3)] = x;
        vertexPositionData[(vertexId*3)+1] = y;
        vertexPositionData[(vertexId*3)+2] = z;
    }

    public void setVertexPosition(int vertexId, Vector3 position){
        setVertexPosition(vertexId, position.x, position.y, position.z);
    }

    public void getVertexPosition(int vertexId, Vector3 outVec){
        outVec.x = vertexPositionData[(vertexId*3)];
        outVec.y = vertexPositionData[(vertexId*3)+1];
        outVec.z = vertexPositionData[(vertexId*3)+2];
    }

    // rescales vertex position data
    public void scale(float x, float y, float z){
        int len = vertexPositionData.length;
        for(int i = 0; i < len; i+=3){
            vertexPositionData[i] *= x;
            vertexPositionData[i+1] *= y;
            vertexPositionData[i+2] *= z;
        }
    }
}
