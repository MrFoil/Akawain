attribute vec3 a_Position;
uniform vec3 u_Translation;
uniform mat4 MVP;

void main()
{
    vec3 pos = a_Position + u_Translation;
    gl_Position = MVP * vec4(pos.xyz, 1.0);
}