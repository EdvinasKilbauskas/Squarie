package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.opengl.GLES20;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;

public class ShaderProgram {
    private ShaderAttributes attributes;
	private int programID;

	public ShaderProgram(FileIOSystem ioSystem, String vertexShaderDir, String fragmentShaderDir){
		int vertexShaderID = loadShader(ioSystem, GLES20.GL_VERTEX_SHADER, vertexShaderDir);
		int fragmentShaderID = loadShader(ioSystem, GLES20.GL_FRAGMENT_SHADER, fragmentShaderDir);
        this.programID = GLES20.glCreateProgram();;
		
		GLES20.glAttachShader(programID, vertexShaderID);
		GLES20.glAttachShader(programID, fragmentShaderID);
		GLES20.glLinkProgram(programID);

        attributes = new ShaderAttributes(programID);
	}

	public int getProgramID(){
		return programID;
	}

    public ShaderAttributes getAttributes(){
        return attributes;
    }
	
    private int loadShader(FileIOSystem ioSystem, int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        DataInputStream inputStream = new DataInputStream(ioSystem.openAsset(source));
        StringBuilder builder = new StringBuilder();
        try{
            while(inputStream.available() > 0){
			    builder.append((char)inputStream.read());
			}
		}catch(IOException e) {e.printStackTrace();}

        GLES20.glShaderSource(shader, builder.toString());
        GLES20.glCompileShader(shader);
      
        int [] status = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, status, 0);


        
        return shader;
    }
}
