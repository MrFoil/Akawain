attribute vec3 a_Position;
uniform mat4 MVP;

void main()
{
    vec4 pos = MVP * vec4(a_Position.xyz, 1.0);
    gl_Position = pos;
}