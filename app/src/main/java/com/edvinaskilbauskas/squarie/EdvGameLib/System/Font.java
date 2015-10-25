package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by edvinas on 12/1/14.
 */
public class Font {
    private Texture texture;
    private HashMap<Integer, Glyph> glyphMap;
    private ArrayList<Glyph> glyphList;

    public class Glyph{
        public RenderableObject2D renderable;
        public int id;
        public float xadvance;
        public float xoffset;
        public float yoffset;
        public float width;
        public float height;
    }

    public Font(GameSystem system, String fontFile){
        int pathEnd = fontFile.lastIndexOf('/');
        String path = fontFile.substring(0,pathEnd+1);

        DataInputStream fontStream = new DataInputStream(system.getFileIOSystem().openAsset(fontFile));

        StringBuilder builder = new StringBuilder();
        try{
            while(fontStream.available() > 0){
                builder.append((char)fontStream.read());
            }
        }catch(IOException e) {e.printStackTrace();}

        String fontData = builder.toString();

        String [] tokens = fontData.split("\\s+");


        glyphMap = new HashMap<Integer, Glyph>();
        glyphList = new ArrayList<Glyph>();

        int x = 0;
        int y = 0;
        int xoffset = 0;
        int yoffset = 0;

        Glyph glyph = new Glyph();
        for(int i = 0; i < tokens.length; i++){
            String token[] = tokens[i].split("=");

            String param = token[0];

            if(param.equals("id")){
                glyph.id = Integer.parseInt(token[1]);

            }else if(param.equals("x")){
                x = Integer.parseInt(token[1]);;
            }
            else if(param.equals("y")){
                y = Integer.parseInt(token[1]);
            }
            else if(param.equals("width")){
                glyph.width = Integer.parseInt(token[1]);;
            }
            else if(param.equals("height")){
                glyph.height = Integer.parseInt(token[1]);
            }
            else if(param.equals("xoffset")){
                xoffset = Integer.parseInt(token[1]);
            }
            else if(param.equals("yoffset")){
                yoffset = Integer.parseInt(token[1]);
            }
            else if(param.equals("xadvance")){
                int value = Integer.parseInt(token[1]);

                glyph.renderable = RenderableObjectFactory.newRectangle(texture,x,y,glyph.width,glyph.height);
                glyph.xadvance = value;
                glyph.xoffset = xoffset;
                glyph.yoffset = yoffset;

                glyphMap.put(glyph.id,glyph);
                glyphList.add(glyph);
                glyph = new Glyph();

            }else if(param.equals("file")){
                String image = token[1].replaceAll("\"","");
                texture = new Texture(system,path+image);
            }
        }

    }

    public Glyph getGlyph(char ch){
        return glyphMap.get((int)ch);
    }

    public void scale(float scale){
        for(int i = 0; i < glyphList.size(); i++){
            Glyph glyph = glyphList.get(i);

            glyph.renderable.scale(scale,scale);
            glyph.xoffset *= scale;
            glyph.yoffset *= scale;
            glyph.xadvance *= scale;
            glyph.width *= scale;
            glyph.height *= scale;
        }
    }
    /*
    // Compute the source rect
Rect src;
src.left   = ch.x;
src.top    = ch.y;
src.right  = ch.x + ch.width;
src.bottom = ch.y + ch.height;

// Compute the destination rect
Rect dst;
dst.left   = cursor.x + ch.xoffset;
dst.top    = cursor.y + ch.yoffset;
dst.right  = dst.left + ch.width;
dst.bottom = dst.top + ch.height;
     */
}
