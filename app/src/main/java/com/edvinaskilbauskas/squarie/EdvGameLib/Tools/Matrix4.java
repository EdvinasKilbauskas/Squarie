package com.edvinaskilbauskas.squarie.EdvGameLib.Tools;

import android.opengl.Matrix;

public class Matrix4 {
	private float [] matrix;
	
	public Matrix4(){
		matrix = new float[16];
		Matrix.setIdentityM(matrix, 0);
	}
	
	public void set(float [] matrix){
		this.matrix = matrix;
	}
	
	public float [] toArray(){
		return this.matrix;
	}
	
	public void scale(float x, float y, float z){
		Matrix.scaleM(matrix, 0, x, y, z);
	}
	
	public void scale(Vector3 scale){
		Matrix.scaleM(matrix, 0, scale.x, scale.y, scale.z);
	}
	
	public void translate(float x, float y, float z){
		Matrix.translateM(matrix, 0, x, y, z);
	}
	
	public void translate(Vector3 vector){
		Matrix.translateM(matrix, 0, vector.x, vector.y, vector.z);
	}
	
	public void rotate(float angle, float x, float y, float z){
		Matrix.rotateM(matrix, 0, angle, x, y, z);
	}
	
	public void rotate(float x, float y, float z){
		Matrix.rotateM(matrix, 0, x, 1, 0, 0);
		Matrix.rotateM(matrix, 0, y, 0, 1, 0);
		Matrix.rotateM(matrix, 0, z, 0, 0, 1);
	}
	
	public void rotate(Vector3 rotation){
		Matrix.rotateM(matrix, 0, rotation.x, 1, 0, 0);
		Matrix.rotateM(matrix, 0, rotation.y, 0, 1, 0);
		Matrix.rotateM(matrix, 0, rotation.z, 0, 0, 1);
	}
	
	public void loadIdentity(){
		Matrix.setIdentityM(matrix, 0);
	}
	
	public void setFrustum(float left, float right, float bottom, float top, float near, float far){
		Matrix.frustumM(this.matrix, 0, left, right, bottom, top, near, far);
	}
	
	public void setOrtho(float left, float right, float bottom, float top, float near, float far){
		Matrix.orthoM(this.matrix, 0, left, right, bottom, top, near, far);
	}
	
	public void multiply(Matrix4 matrix){
		Matrix.multiplyMM(this.matrix, 0, matrix.toArray(), 0, this.matrix, 0);
	}
	
	public void setLookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY,float centerZ, float upX, float upY, float upZ){
		Matrix.setLookAtM(this.matrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
	}
}
