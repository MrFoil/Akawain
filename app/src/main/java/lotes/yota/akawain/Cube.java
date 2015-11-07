package lotes.yota.akawain;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.logging.Logger;

public class Cube {

    private int translationId;
    private int vbo[] = new int [2];
    private int shaderId;
    private float pos[] = new float [3];
    private int numberOfVertices, positionAttributeId, normalAttributeId;

    public Cube(float[] vertexData, float[] normalData, float posX, float posY, float posZ, int shaderId) {
        this.shaderId = shaderId;

        numberOfVertices = vertexData.length/3;

        pos[0] = posX;
        pos[1] = posY;
        pos[2] = posZ;

        //To fill the OpenGL buffer, we need to perform our data in bytes.
        FloatBuffer vertexBuffer;
        ByteBuffer temp = ByteBuffer.allocateDirect(vertexData.length * 4);
        temp.order(ByteOrder.nativeOrder());
        vertexBuffer = temp.asFloatBuffer();
        vertexBuffer.put(vertexData);

        //Setting pointer on the beginning
        vertexBuffer.position(0);

        //Creating OpenGL Buffer for vertices
        GLES20.glGenBuffers(2, vbo, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        FloatBuffer normalBuffer;
        temp = ByteBuffer.allocateDirect(normalData.length*4);
        temp.order(ByteOrder.nativeOrder());
        normalBuffer = temp.asFloatBuffer();
        normalBuffer.put(normalData);

        normalBuffer.position(0);

        //GLES20.glGenBuffers(1, vbo, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[1]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, normalData.length * 4, normalBuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        //IDs for shader variables
        translationId = GLES20.glGetUniformLocation(this.shaderId, "u_Translation");
        positionAttributeId = GLES20.glGetAttribLocation(this.shaderId, "a_Position");
        normalAttributeId = GLES20.glGetAttribLocation(this.shaderId, "a_Normal");
    }
    
    //Render function
    public void render(){
        //Sending uniform variable "u_Translation"
        GLES20.glUniform3f(translationId, pos[0], pos[1], pos[2]);

        //Binding VBO and sending its content to shader for rendering
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        GLES20.glEnableVertexAttribArray(positionAttributeId);
        GLES20.glVertexAttribPointer(positionAttributeId, 3, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[1]);
        GLES20.glEnableVertexAttribArray(normalAttributeId);
        GLES20.glVertexAttribPointer(normalAttributeId, 3, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, numberOfVertices);
    }
}
