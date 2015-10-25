package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.Buffer;

/**
 * Created by pufix on 7/31/13.
 */
public class ShaderAttributes {
    private final static int ATTRIBUTE_COUNT = 4;
    public enum Attribute {Position, Color, TextureCoords, Normal, MVPMatrix, ProjectionMatrix};
    private int attributeIDs[];
    private boolean attributesEnabled[];
    private int shaderProgramID;
    private int MVPMatrixID;
    private int projectionMatrixID;

    public ShaderAttributes(int shaderProgramID){
        this.shaderProgramID = shaderProgramID;
        attributeIDs = new int[ATTRIBUTE_COUNT];
        attributesEnabled = new boolean[ATTRIBUTE_COUNT];

        setupIDs();
    }

    private void setupIDs(){
        attributeIDs[Attribute.Position.ordinal()] = GLES20.glGetAttribLocation(shaderProgramID, "position");
        attributeIDs[Attribute.Color.ordinal()] = GLES20.glGetAttribLocation(shaderProgramID, "color");
        attributeIDs[Attribute.TextureCoords.ordinal()] = GLES20.glGetAttribLocation(shaderProgramID, "textureCoords");
        attributeIDs[Attribute.Normal.ordinal()] = GLES20.glGetAttribLocation(shaderProgramID, "normal");
        MVPMatrixID = GLES20.glGetUniformLocation(shaderProgramID, "MVPMatrix");
        projectionMatrixID = GLES20.glGetUniformLocation(shaderProgramID, "projectionMatrix");
        //"matrix", " " + MVPMatrixID);
    }

    public void enableAllAttributes(){
        for(int i = 0; i < ATTRIBUTE_COUNT; i++){
            enableAttribute(Attribute.values()[i]);
        }
    }

    public void disableAllAttributes(){
        for(int i = 0; i < ATTRIBUTE_COUNT; i++){
            disableAttribute(Attribute.values()[i]);
        }
    }

    public void enableAttribute(Attribute attribute){
        if(attributesEnabled[attribute.ordinal()] == false){
            GLES20.glEnableVertexAttribArray(getAttributeID(attribute));
            attributesEnabled[attribute.ordinal()] = true;
        }
    }

    public void disableAttribute(Attribute attribute){
        if(attributesEnabled[attribute.ordinal()] == true){
            GLES20.glDisableVertexAttribArray(getAttributeID(attribute));
            attributesEnabled[attribute.ordinal()] = false;
        }
    }

    public int getAttributeID(Attribute attribute){
        return attributeIDs[attribute.ordinal()];
    }

    public boolean attributeEnabled(Attribute attribute){
        return attributesEnabled[attribute.ordinal()];
    }

    public void setAttributeData(Attribute attribute, int size, int dataType, Buffer buffer){
        if(attributeEnabled(attribute)){
            GLES20.glVertexAttribPointer(getAttributeID(attribute), size, dataType, false, 0, buffer);
        }
    }

    public void setMVPMatrixData(float matrix[]){
        GLES20.glUniformMatrix4fv(MVPMatrixID, 1, false, matrix, 0);
    }

    public void setProjectionMatrix(float matrix[]){
        GLES20.glUniformMatrix4fv(projectionMatrixID, 1, false, matrix, 0);
    }

}
