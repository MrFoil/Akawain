package lotes.yota.akawain;

import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * Created by foil on 30.10.15.
 */
public class Camera {
    private float view[], projection[], mvp[];
    private float ratio;
    private int mvpId;

    public Camera(int width, int height, int shaderId){
        ratio = (float) width/height;

        view = new float[16];
        projection = new float[16];
        mvp = new float[16];

        mvpId = GLES20.glGetUniformLocation(shaderId, "MVP");
    }

    public void setLookAt(float posX, float posY, float posZ, float dirX, float dirY, float dirZ){
        Matrix.setLookAtM(view, 0, posX, posY, posZ, dirX, dirY, dirZ, 0.0f, 1.0f, 0.0f);
    }

    public void setFrustumProjection(){
        Matrix.frustumM(projection, 0, -ratio, ratio, -1f, 1f, 1f, 25f);
    }

    public void calculateMVP(){
        Matrix.multiplyMM(mvp, 0, projection, 0, view, 0);
    }

    public void sendMVP(){
        GLES20.glUniformMatrix4fv(mvpId, 1, false, mvp, 0);
    }
}
