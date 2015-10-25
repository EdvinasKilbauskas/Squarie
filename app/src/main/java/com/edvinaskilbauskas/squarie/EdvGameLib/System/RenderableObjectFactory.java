package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;

import java.util.Random;

/**
 * Created by pufix on 6/30/13.
 */
public class RenderableObjectFactory {


    public static RenderableObject2D newRectangle(float width, float height){
        RenderableObject2D object = new RenderableObject2D(4);

        object.setVertexAttributes(0,0,0, 1,1,1,1, 0,0);
        object.setVertexAttributes(1, width,0, 1,1,1,1, 16,0);
        object.setVertexAttributes(2,0, height, 1,1,1,1, 0,16);
        object.setVertexAttributes(3, width, height, 1,1,1,1, 16,16);

        object.setIndexData(new short[]{
                0,1,2,2,3,1
        });
        return object;
    }

    /*public static RenderableObject2D newRectangle(Texture texture, float texUpperLeftX, float texUpperLeftY, float texWidth, float texHeight, float width, float height){
        RenderableObject2D object = new RenderableObject2D(texture, 4);

        // I shrink the texture area by 0.1 pixel because it seem to fix the problem when you can see gaps between texturized tiles.
        object.setVertexAttributes(0,-width/2,-height/2, 1,1,1,1, texUpperLeftX+0.1f,         texUpperLeftY+0.1f);
        object.setVertexAttributes(1, width/2,-height/2, 1,1,1,1, texUpperLeftX+texWidth-0.1f,texUpperLeftY+0.1f);
        object.setVertexAttributes(2,-width/2, height/2, 1,1,1,1, texUpperLeftX+0.1f,         texUpperLeftY+texHeight-0.1f);
        object.setVertexAttributes(3, width/2, height/2, 1,1,1,1, texUpperLeftX+texWidth-0.1f,texUpperLeftY+texHeight-0.1f);

        object.setIndexData(new short[]{
                0,1,2,2,3,1
        });
        return object;
    }*/

    public static RenderableObject2D newRectangle(Texture texture, float texUpperLeftX, float texUpperLeftY, float texWidth, float texHeight){
        RenderableObject2D object = new RenderableObject2D(texture, 4);

        // I shrink the texture area by 0.1 pixel because it seem to fix the problem when you can see gaps between texturized tiles.
        object.setVertexAttributes(0,0,0, 1,1,1,1, texUpperLeftX+0.1f,         texUpperLeftY+0.1f);
        object.setVertexAttributes(1, 1,0, 1,1,1,1, texUpperLeftX+texWidth-0.1f,texUpperLeftY+0.1f);
        object.setVertexAttributes(2,0, 1, 1,1,1,1, texUpperLeftX+0.1f,         texUpperLeftY+texHeight-0.1f);
        object.setVertexAttributes(3, 1, 1, 1,1,1,1, texUpperLeftX+texWidth-0.1f,texUpperLeftY+texHeight-0.1f);

        //float length = (float)Math.sqrt(texWidth*texWidth+texHeight*texHeight);
        object.scale(texWidth,texHeight); // scales to unit length
        object.setIndexData(new short[]{
                0,1,2,2,3,1
        });
        return object;
    }

    /**
     * Create new RenderableObject2D in shape of circle.
     *
     * @param detailLevel amount of sides on the circle, the lower, the faster it will be generated and drawn
     * @return
     */
    public static RenderableObject2D newCircle(int detailLevel, float width, float height){
        RenderableObject2D object = new RenderableObject2D(detailLevel + 1);

        if(detailLevel < 3)
            detailLevel = 3;

        float sliceSize = 360.0f/detailLevel;

        object.setVertexAttributes(0, 0, 0,
                1, 1, 1, 1,
                0, 0);
        for(int i = 1; i < detailLevel+1; i++){
            object.setVertexAttributes(i, (float) Math.cos(Math.toRadians((i - 1) * sliceSize))*(width/2),
                    (float) Math.sin(Math.toRadians((i - 1) * sliceSize))*(height/2),
                    1,1,1,1,
                    0,0);
        }

        int indexSize = detailLevel*3;
        short indexData[] = new short[indexSize];
        for(int i = 0, j = 0; i < indexSize-3; i+=3, j++){
            indexData[i+0] = (short) 0;
            indexData[i+1] = (short) (j+1);
            indexData[i+2] = (short) (j+2);
        }
        indexData[indexSize - 3] = 0;
        indexData[indexSize - 2] = (short)detailLevel;
        indexData[indexSize - 1] = 1;

        object.setIndexData(indexData);

        return object;
    }

    public static RenderableObject2D newCircle(Texture texture, float texX, float texY, float texWidth, float texHeight, int detailLevel, float width, float height){
        RenderableObject2D object = new RenderableObject2D(texture, detailLevel + 1);

        if(detailLevel < 3)
            detailLevel = 3;

        float sliceSize = 360.0f/detailLevel;

        object.setVertexAttributes(0, 0, 0,
                1, 1, 1, 1,
                texX, texY);
        for(int i = 1; i < detailLevel+1; i++){
            object.setVertexAttributes(i, (float) Math.cos(Math.toRadians((i - 1) * sliceSize))*(width/2),
                                        (float) Math.sin(Math.toRadians((i - 1) * sliceSize))*(height/2),
                                        1,1,1,1,
                                        (float)(Math.cos(Math.toRadians((i - 1) * sliceSize))*(texWidth/2))+texX,
                                        (float)(Math.sin(Math.toRadians((i - 1) * sliceSize))*(texHeight/2))+texY);
        }

        Vector2 tex = new Vector2();
        for(int i = 0; i < detailLevel+1; i++){
            object.getVertexTextureCoords(i,tex);
        }

        int indexSize = detailLevel*3;
        short indexData[] = new short[indexSize];
        for(int i = 0, j = 0; i < indexSize-3; i+=3, j++){
            indexData[i+0] = (short) 0;
            indexData[i+1] = (short) (j+1);
            indexData[i+2] = (short) (j+2);
        }
        indexData[indexSize - 3] = 0;
        indexData[indexSize - 2] = (short)detailLevel;
        indexData[indexSize - 1] = 1;

        object.setIndexData(indexData);

        return object;
    }


    public static RenderableObject3D newCube(float width, float height, float depth){
        RenderableObject3D renderable = new RenderableObject3D(null, 8);

        float halfWidth = width/2.0f;
        float halfHeight = height/2.0f;
        float halfDepth = depth/2.0f;

        renderable.setVertexAttributes(0, -halfWidth, halfHeight,-halfDepth, 1,1,1,1, 0,0);
        renderable.setVertexAttributes(1,  halfWidth, halfHeight,-halfDepth, 1,1,1,1, 0,0);
        renderable.setVertexAttributes(2, -halfWidth,-halfHeight,-halfDepth, 1,1,1,1, 0,0);
        renderable.setVertexAttributes(3,  halfWidth,-halfHeight,-halfDepth, 1,1,1,1, 0,0);
        renderable.setVertexAttributes(4, -halfWidth, halfHeight, halfDepth, 1,1,1,1, 0,0);
        renderable.setVertexAttributes(5,  halfWidth, halfHeight, halfDepth, 1,1,1,1, 0,0);
        renderable.setVertexAttributes(6, -halfWidth,-halfHeight, halfDepth, 1,1,1,1, 0,0);
        renderable.setVertexAttributes(7,  halfWidth,-halfHeight, halfDepth, 1,1,1,1, 0,0);

        renderable.setIndexData(new short[]{
                0,1,2,
                2,3,1,
                1,5,3,
                3,7,5,
                4,5,6,
                7,6,4,
                0,2,6,
                6,4,0,
                0,1,4,
                4,5,1,
                2,3,7,
                7,6,2
        });

        return renderable;
    }

    public static RenderableObject3D loadObject(String path){

        return null;
    }
}
