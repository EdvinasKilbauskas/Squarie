package com.edvinaskilbauskas.squarie;

import android.util.Log;

import com.edvinaskilbauskas.squarie.EdvGameLib.System.GameSystem;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.RenderableObject2D;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.RenderableObjectFactory;
import com.edvinaskilbauskas.squarie.EdvGameLib.System.Texture;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Color;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;

/**
 * Created by edvinas on 11/29/14.
 */
public class Assets {
    public static RenderableObject2D square;
    public static RenderableObject2D platform;
    public static RenderableObject2D background;
    public static RenderableObject2D leaderboards;
    public static Texture leaderTexture;

    public static void setup(GameSystem gameSystem){
        square = RenderableObjectFactory.newRectangle(1,1);

        leaderTexture = new Texture(gameSystem,"leaderboards.png");
        leaderboards = RenderableObjectFactory.newRectangle(leaderTexture, 0.0f, 0.0f, 256.0f, 256.0f);
        leaderboards.scale(1.0f/leaderTexture.getWidth(),1.0f/leaderTexture.getHeight());
        leaderboards.setCenter(0.5f,0.5f);

        square.setCenter(0.5f,0.5f);

        platform = RenderableObjectFactory.newRectangle(1,1);
        platform.setCenter(0.5f,1.0f);


        createBackground(10,0.35f,0.75f);
    }

    private static void createBackground(int edges, float alpha1, float alpha2){
        float step = 360.0f/edges;
        float radius = 50.0f;
        Color color1 = new Color(1,1,1,alpha1);
        Color color2 = new Color(1,1,1,alpha2);
        Vector2 vector = new Vector2(radius,0);
        background = new RenderableObject2D(null, 2+edges*2);
        background.setVertexAttributes(0,0,0,color1.r,color1.g,color1.b,color1.a,0,0);
        background.setVertexAttributes(1,0,0,color2.r,color2.g,color2.b,color2.a,0,0);

        for(int i = 0; i < edges; i++){
            background.setVertexAttributes(2+i*2,vector.x,vector.y,color1.r,color1.g,color1.b,color1.a,0,0);
            background.setVertexAttributes(3+i*2,vector.x,vector.y,color2.r,color2.g,color2.b,color2.a,0,0);

            vector.rotate(step);
        }

        short [] indices = new short[edges*3];

        for(int i = 0; i < edges; i++){
            if(i%2 == 0){
                indices[(i*3)+0] = 0;
                indices[(i*3)+1] = (short)(2+2*i);
                indices[(i*3)+2] = (short)(4+2*i);
            }else if(i%2 == 1){
                indices[(i*3)+0] = 1;
                indices[(i*3)+1] = (short)(3+2*i);
                if(i == edges-1)
                    indices[(i*3)+2] = (short)(3);
                else indices[(i*3)+2] = (short)(5+2*i);
            }
        }


        background.setIndexData(indices);
    }
}
