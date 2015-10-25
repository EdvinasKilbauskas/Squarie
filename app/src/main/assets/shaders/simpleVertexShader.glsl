uniform mat4 MVPMatrix; // model-view-projection matrix
uniform mat4 projectionMatrix;

attribute vec4 position;
attribute vec2 textureCoords;
attribute vec4 color;
attribute vec3 normal;

varying vec4 outColor;
varying vec2 outTexCoords;
varying vec3 outNormal;

void main()
{
    outNormal = normal;
    outTexCoords = textureCoords;
	outColor = color;
	gl_Position = MVPMatrix * position;
}
