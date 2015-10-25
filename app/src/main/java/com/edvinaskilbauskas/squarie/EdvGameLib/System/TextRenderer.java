package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Color;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;

/**
 * Created by edvinas on 12/2/14.
 */
public class TextRenderer {
    private Font font;
    private Vector2 cursor;
    private Color color;
    private float scale;

    public enum TextAlignment {
        Left,Center,Right
    }
    private TextAlignment alignment;

    public TextRenderer(Font font){
        color = new Color();
        scale = 1.0f;
        this.font = font;
        alignment = TextAlignment.Right;
        cursor = new Vector2();
    }

    public void setFont(Font font){
        this.font = font;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public void setAlignment(TextAlignment alignment){
        this.alignment = alignment;
    }

    public void setColor(float r, float g, float b, float a){
        color.set(r,g,b,a);
    }

    public void renderText(RenderSystem renderSystem, float x, float y, String text){

        cursor.set(x,y);
        int length = text.length();

        float textWidth = 0;

        for(int i = 0; i < length; i++){
            Font.Glyph glyph = font.getGlyph(text.charAt(i));
            textWidth += glyph.xadvance*scale;
        }

        float textOffset = 0;

        switch(alignment){
            case Right:
                textOffset = 0;
                break;
            case Left:
                textOffset = -textWidth;
                break;

            case Center:
                textOffset = -textWidth / 2.0f;
                break;
        }

        for(int i = 0; i < length; i++){
                char ch = text.charAt(i);

                Font.Glyph glyph = font.getGlyph(ch);


                renderSystem.renderObject(glyph.renderable,cursor.x+glyph.xoffset*scale+textOffset,cursor.y+glyph.yoffset*scale,0,color.r,color.g,color.b,color.a,scale,scale);
                cursor.x += glyph.xadvance*scale;
        }

    }
}
