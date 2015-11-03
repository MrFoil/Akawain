package lotes.yota.akawain;

import android.opengl.GLES20;
import android.util.Log;

class ShaderHandler{
    public int vShader, fShader, id;

    public int loadShader(String source, int type){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);

        // Get the compilation status.
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        // If the compilation failed, delete the shader.
        if (compileStatus[0] == 0) {
            Log.d("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            GLES20.glClearColor(0.0f, 0.6f, 2.0f, 0.0f);
            return 0;
        }

        return shader;
    }
    ShaderHandler(String vShaderSource, String fShaderSource){
        vShader = loadShader(vShaderSource, GLES20.GL_VERTEX_SHADER);
        fShader = loadShader(fShaderSource, GLES20.GL_FRAGMENT_SHADER);

        id = GLES20.glCreateProgram();
        GLES20.glAttachShader(id, vShader);
        GLES20.glAttachShader(id, fShader);
        GLES20.glLinkProgram(id);
        GLES20.glUseProgram(id);

        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(id, GLES20.GL_LINK_STATUS, linkStatus, 0);

        // If the link failed, delete the program.
        if (linkStatus[0] == 0)
        {
            GLES20.glDeleteProgram(id);
            id = 0;
        }
    }
    public void use(){
        GLES20.glUseProgram(id);
    }

    public void addUniform(){

    }

}