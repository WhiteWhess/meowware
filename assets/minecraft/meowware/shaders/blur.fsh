#version 120

uniform sampler2D sampler;
uniform vec2 screenSize;

uniform float blur;
uniform float texelSize;

uniform vec4 effectBorders;

varying vec2 texCoord;

void main()
{
    if(gl_FragCoord.x < effectBorders.x || gl_FragCoord.x > effectBorders.z || (screenSize.y * texelSize) - gl_FragCoord.y < effectBorders.y || (screenSize.y * texelSize) - gl_FragCoord.y > effectBorders.w) {
        discard;
    }

    float offset = blur;

    vec4 sum = vec4(0.0);
    float divisor = 0.0;

    for(float h = -offset; h <= offset; h += 1.0 / texelSize) {
        for(float v = -offset; v <= offset; v += 1.0 / texelSize) {
                sum += texture2D(sampler, texCoord + vec2(h / screenSize.x, v / screenSize.y));
                divisor += 1.0;
            }
    }

    gl_FragColor = sum / divisor;
}