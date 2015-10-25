package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.opengl.GLES20;
import android.util.Log;

import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Matrix4;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Rectangle;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector2;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.Vector3;

// TODO fix all the mess down there!
public class CameraES2 {
	private Matrix4 modelViewProjectionMatrix;
	private Matrix4 projectionMatrix;
	private Matrix4 viewMatrix;
	private Matrix4 modelMatrix;
    private float frustumWidth;
    private float frustumHeight;

	private Vector3 center;
    private Vector3 upperLeft;
	private Vector3 lookingAt;
    private Rectangle bounds;

    private float zoom;


	public CameraES2(float frustumWidth, float frustumHeight){
		projectionMatrix = new Matrix4();
		viewMatrix = new Matrix4();
		modelMatrix = new Matrix4();
		modelViewProjectionMatrix = new Matrix4();

        this.frustumWidth = frustumWidth;
        this.frustumHeight = frustumHeight;
        this.bounds = new Rectangle(0,0,0,0);
        upperLeft = new Vector3(0,0,0);
		center = new Vector3(frustumWidth/2.0f,frustumHeight/2.0f,0);
		lookingAt = new Vector3(0,0,-1);

        zoom = 1.0f;
	}
	
	public Matrix4 getProjectionMatrix(){
		return projectionMatrix;
	}
	
	public Matrix4 getMVPMatrix(){
		return modelViewProjectionMatrix;
	}
	
	public Matrix4 getModelMatrix(){
		return modelMatrix;
	}

    public Rectangle getBounds(){
        return bounds;
    }

    public Vector2 toWorldCoordinates(Vector2 localPoint){
        localPoint.add(upperLeft.x,upperLeft.y);
        return localPoint;
    }

    public float getFrustumWidth(){
        return frustumWidth*zoom;
    }

    public float getFrustumHeight(){
        return frustumHeight*zoom;
    }
	
	public void setupProjection(float fieldOfView, float near, float far){
        /*projectionMatrix.loadIdentity();
		
        float ratio = (float) width / height;     
        float top = (float) (Math.tan(fieldOfView * Math.PI / 360.0f) * near);
        float bottom = -top;
        float left = ratio * bottom;
        float right = ratio * top;
        projectionMatrix.setFrustum(left, right, bottom, top, near, far);
        */
	}
	
	public void setupOrthographic(float width, float height, float near, float far){
        this.frustumWidth = width;
        this.frustumHeight = height;
        projectionMatrix.loadIdentity();
		
		float halfWidth = (frustumWidth/2);
		float halfHeight = (frustumHeight/2);
		projectionMatrix.loadIdentity();
		projectionMatrix.setOrtho(-halfWidth+center.x, halfWidth+center.x,
                                   halfHeight+center.y, -halfHeight+center.y, near, far);
	}
	
	public void setCenter(float x, float y, float z){
        center.set(x,y,z);
        bounds.setCentered(center.x,center.y,frustumWidth,frustumHeight);
        upperLeft.set(center.x - frustumWidth / 2.0f, center.y - frustumHeight / 2.0f, 0);
	}
	
	public Vector3 getCenter(){
		return center;
	}
    
    public Vector3 getUpperLeft(){

        return upperLeft;

    }
	
	public void lookAt(float x, float y, float z){
		lookingAt.set(x,y,z);
	}
	
	 void update(){
        //viewMatrix.setLookAt(position.x, position.y, position.z,	// eye coords
		//					lookingAt.x, lookingAt.y, lookingAt.z,	// center coords
		//					0f, 1.0f, 0.0f); // up vector


        setupOrthographic(frustumWidth, frustumHeight, -16, 16);

       	modelViewProjectionMatrix.loadIdentity();
       	
   		modelViewProjectionMatrix.multiply(modelMatrix);
   		//modelViewProjectionMatrix.multiply(viewMatrix);
       	modelViewProjectionMatrix.multiply(projectionMatrix);
	}
	
}
