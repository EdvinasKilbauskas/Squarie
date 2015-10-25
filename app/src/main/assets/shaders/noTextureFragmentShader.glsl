precision lowp float;

uniform sampler2D texture;

varying vec4 outColor;
varying vec2 outTexCoords;
varying vec3 outNormal;

void main()
{
    gl_FragColor = outColor;
}
