package lotes.yota.akawain;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Cube {
    private FloatBuffer vertexBuffer, rotationMatrixBuffer;
    private int vbo[] = new int [1];

    public Cube(){
       
        Parser p = new Parser();
        p.Parse();

        float[] data = p.getVertices();

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
    
    //render function
    public void render(int positionAttribute){
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
        GLES20.glEnableVertexAttribArray(positionAttribute);
        GLES20.glVertexAttribPointer(positionAttribute, 3, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 69);
    }
}
