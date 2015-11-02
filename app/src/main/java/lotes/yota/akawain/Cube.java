package lotes.yota.akawain;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Cube {
    private FloatBuffer vertexBuffer, rotationMatrixBuffer;
    private int vbo[] = new int [1];
    private float[] rotationMat = new float[16];

    public Cube(){
        float data[] = {
                -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,

                0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, //Bottom
                -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f,

                0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, //Right
                0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,

                0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, //Back
                -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,

                0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, //Top
                -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,

                0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, //Left
                -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,

                -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f
        };



        //to fill the OpenGL buffer, we need to perform our data in bytes.
        ByteBuffer temp = ByteBuffer.allocateDirect(data.length * 4);
        temp.order(ByteOrder.nativeOrder());
        vertexBuffer = temp.asFloatBuffer();
        vertexBuffer.put(data);

        //setting pointer on the beginning
        vertexBuffer.position(0);

        //creating OpenGL Buffer
        GLES20.glGenBuffers(1, vbo, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, data.length * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void acceptRotation(float deltaTime){
        float alpha = 0.0f;
        rotationMat[0] = (float)Math.cos(alpha + deltaTime);
        rotationMat[1] = (float)-Math.sin(alpha+deltaTime);
        rotationMat[2] = 0.0f;
        rotationMat[3] = 0.0f;

        rotationMat[4] = (float)Math.sin(alpha + deltaTime);
        rotationMat[5] = (float)Math.cos(alpha + deltaTime);
        rotationMat[6] = 0.0f;
        rotationMat[7] = 0.0f;

        rotationMat[8] = 0.0f;
        rotationMat[9] = 0.0f;
        rotationMat[10] = 1.0f;
        rotationMat[11] = 0.0f;

        rotationMat[12] = 0.0f;
        rotationMat[13] = 0.0f;
        rotationMat[14] = 0.0f;
        rotationMat[15] = 1.0f;

        ByteBuffer temp = ByteBuffer.allocateDirect(rotationMat.length * 4);
        temp.order(ByteOrder.nativeOrder());
        rotationMatrixBuffer = temp.asFloatBuffer();
        rotationMatrixBuffer.put(rotationMat);


    }

    public void pushRotationMat(int programId){
        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(programId, "Rotation"),
                1, false, rotationMatrixBuffer);
    }

    //render function
    public void render(int positionAttribute){
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        GLES20.glEnableVertexAttribArray(positionAttribute);
        GLES20.glVertexAttribPointer(positionAttribute, 3, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
    }
}
