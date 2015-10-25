package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Color;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;

/**
 * Created by pufix on 7/20/13.
 */
public abstract class RenderableObject {
    public float vertexPositionData[];
    public float vertexColorData[];
    public float vertexTexCoordData[];
    public short indexData[];
    protected int vertexCount;
    protected int indexCount;
    private Texture texture;
    private Vector2 center;

    public RenderableObject(Texture texture){
        this.vertexCount = 0;
        this.texture = texture;
        center = new Vector2();
    }

    public Texture getTexture(){
        return texture;
    }

    public boolean hasTexture(){
        return texture == null ? false : true;
    }

    public void setVertexColor(int vertexId, float r, float g, float b, float a){
        vertexColorData[(vertexId*4)] = r;
        vertexColorData[(vertexId*4)+1] = g;
        vertexColorData[(vertexId*4)+2] = b;
        vertexColorData[(vertexId*4)+3] = a;
    }

    public void setVertexTextureCoords(int vertexId, float u, float v){
        if(texture != null){
            vertexTexCoordData[(vertexId*2)] = u/texture.getWidth();
            vertexTexCoordData[(vertexId*2)+1] = v/texture.getHeight();
        }
    }

    public void setVertexColor(int vertexId, Color color){
        setVertexColor(vertexId, color.r, color.g, color.b, color.a);
    }

    public void getVertexColor(int vertexId, Color dest){
        dest.r = vertexColorData[(vertexId*4)];
        dest.g = vertexColorData[(vertexId*4)+1];
        dest.b = vertexColorData[(vertexId*4)+2];
        dest.a = vertexColorData[(vertexId*4)+3];
    }

    public void getVertexTextureCoords(int vertexId, Vector2 dest){
        dest.x = vertexTexCoordData[(vertexId*2)];
        dest.y = vertexTexCoordData[(vertexId*2)+1];
    }

    public void setCenter(float x, float y){
        center.set(x,y);
    }

    public Vector2 getCenter(){
        return center;
    }

    /**
     * Sets color for ALL vertices
     * @param color
     */
    public void setColor(Color color){
        for(int i = 0; i < getVertexCount(); i++){
            setVertexColor(i,color);
        }
    }

    /**
     * Sets color for ALL vertices
     * @param r red component
     * @param g green component
     * @param b blue component
     * @param a alpha component
     */
    public void setColor(float r, float g, float b, float a){
        for(int i = 0; i < getVertexCount(); i++){
            setVertexColor(i,r,g,b,a);
        }
    }

    public void setIndexData(short indexData []){
        this.indexData = indexData;
        this.indexCount = indexData.length;
    }

    public int getIndexCount(){
        return indexCount;
    }

    public int getVertexCount(){
        return vertexCount;
    }
}
