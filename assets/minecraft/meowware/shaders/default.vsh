#version 120

uniform vec2 screenSize;

varying vec2 texCoord;

void main()
{
    vec4 position = gl_ModelViewProjectionMatrix * gl_Vertex;

    texCoord = gl_Vertex.xy / screenSize;
    texCoord.y = 1.0 - texCoord.y;

    gl_Position = position;
}