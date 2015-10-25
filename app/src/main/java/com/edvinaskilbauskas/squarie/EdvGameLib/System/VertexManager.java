package com.edvinaskilbauskas.squarie.EdvGameLib.System;

/**
 * Created by pufix on 6/30/13.
 */

import android.opengl.GLES20;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.ShaderAttributes.Attribute;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pufix on 6/24/13.
 */
public class VertexManager {

    private GraphicsSystem graphicsSystem;
    private FloatBuffer vertexPositionData;
    private FloatBuffer vertexColorData;
    private FloatBuffer vertexTexCoordData;
    private ShortBuffer indexData;
    private int vertexCount;
    private int indexCount;
    private boolean hasColors;
    private boolean hasTexture;
    private boolean hasIndices;
    private final static int dimensions = 3;

    public VertexManager(GraphicsSystem graphicsSystem, int verticesCount, int indicesCount, boolean hasColors, boolean hasTexture){
        this.graphicsSystem = graphicsSystem;
        this.vertexCount = verticesCount;
        this.indexCount = indicesCount;
        this.hasColors = hasColors;
        this.hasTexture = hasTexture;

        vertexPositionData = ByteBuffer.allocateDirect(verticesCount * dimensions * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();

        if(hasColors)
            vertexColorData = ByteBuffer.allocateDirect(verticesCount * 4 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();

        if(hasTexture)
            vertexTexCoordData = ByteBuffer.allocateDirect(verticesCount * 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();

        if(indicesCount > 0){
            hasIndices = true;
            indexData = ByteBuffer.allocateDirect(indicesCount * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        }
    }

    public void appendVertexData(float vertexPosition[], float vertexColor[], float vertexTexCoords[]){
        this.vertexPositionData.put(vertexPosition);
        if(hasColors)
            this.vertexColorData.put(vertexColor);
        if(hasTexture)
            this.vertexTexCoordData.put(vertexTexCoords);
    }


    public void appendVertexPositionData(float vertexPositionData[]){
        this.vertexPositionData.put(vertexPositionData);
    }

    public void appendVertexTexCoordData(float vertexTexCoordData[]){
        if(hasTexture)
            this.vertexTexCoordData.put(vertexTexCoordData);
    }

    public void appendVertexColorData(float vertexColorData[]){
        if(hasColors)
            this.vertexColorData.put(vertexColorData);
    }


    public void appendVertexColorData(float vertexColorData){
        if(hasColors)
            this.vertexColorData.put(vertexColorData);
    }

    public void appendVertexTexCoordData(float vertexTexCoordData){
        if(hasTexture)
            this.vertexTexCoordData.put(vertexTexCoordData);
    }

    public void appendVertexPositionData(float vertexPositionData){
        this.vertexPositionData.put(vertexPositionData);
    }


    public void appendVertexPositionData(float vertexPositionData[], int count){
        this.vertexPositionData.put(vertexPositionData, 0, count);
    }

    public void appendVertexColorData(float vertexColorData[], int count){
        if(hasColors)
            this.vertexColorData.put(vertexColorData, 0, count);
    }

    public void appendVertexTexCoordData(float vertexTexCoordData [], int count){
        if(hasTexture)
            this.vertexTexCoordData.put(vertexTexCoordData, 0, count);
    }


    public void appendIndexData(short indexData[]){
       this.indexData.put(indexData);
    }

    public void appendIndexData(short indexData){
        this.indexData.put(indexData);
    }

    public void flip(){
        this.vertexPositionData.flip();

        if(hasColors)
            this.vertexColorData.flip();
        if(hasTexture)
            this.vertexTexCoordData.flip();
        if(hasIndices)
            this.indexData.flip();

    }

    private void clearVertexData(){
        this.vertexPositionData.position(0);

        if(hasColors)
            this.vertexColorData.position(0);
        if(hasTexture)
            this.vertexTexCoordData.position(0);
        if(hasIndices)
            this.indexData.position(0);
    }


    public void bind(){
        clearVertexData();
        ShaderAttributes attributes = graphicsSystem.getShader().getAttributes();

        attributes.enableAttribute(Attribute.Position);

        attributes.setAttributeData(Attribute.Position, dimensions, GLES20.GL_FLOAT, vertexPositionData);

        if(hasColors){
            attributes.enableAttribute(Attribute.Color);
            attributes.setAttributeData(Attribute.Color, 4, GL10.GL_FLOAT, this.vertexColorData);
        }

        if(hasTexture){
            attributes.enableAttribute(Attribute.TextureCoords);
            attributes.setAttributeData(Attribute.TextureCoords, 2, GL10.GL_FLOAT, this.vertexTexCoordData);
        }
    }

    public void draw(int mode, int count){
        if(hasIndices){
            GLES20.glDrawElements(mode, count, GLES20.GL_UNSIGNED_SHORT, indexData);
        }else{
            GLES20.glDrawArrays(mode, 0, count);
        }
    }

}
