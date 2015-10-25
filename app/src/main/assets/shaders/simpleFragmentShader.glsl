precision highp float;

uniform sampler2D texture;

varying vec4 outColor;
varying vec2 outTexCoords;
varying vec3 outNormal;

void main()
{
    vec4 color = texture2D(texture, outTexCoords) * outColor;
    gl_FragColor = vec4(color.r,color.g,color.b,color.a);//color.a);
}


