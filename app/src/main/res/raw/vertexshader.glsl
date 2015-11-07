#version 100
attribute vec3 a_Position;
attribute vec3 a_Normal;
uniform vec3 u_Translation;
uniform mat4 MVP;

varying vec3 normals;

void main()
{
    vec3 pos = a_Position + u_Translation;
    normals = a_Normal;
    gl_Position = MVP * vec4(pos.xyz, 1.0);
}