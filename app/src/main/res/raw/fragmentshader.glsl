#version 100
precision highp float;
varying vec3 normals;

void main() {

    vec3 res = vec3(0.0);
    vec3 direction = vec3(1.0, 1.0, 2.0);
    float dirFactor = dot(normalize(normals), normalize(-direction));
    vec3 lightColor = vec3(1.0, 1.0, 1.0);

    vec3 matColor = vec3(0.7, 0.3, 0.2);

    vec3 amb = vec3(1.0);

    res =  clamp(0.5 * lightColor * matColor * dirFactor + 0.5 * amb, 0.0, 1.0);

    gl_FragColor = vec4(res, 1.0);
}