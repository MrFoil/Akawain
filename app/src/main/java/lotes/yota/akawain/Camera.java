package lotes.yota.akawain;

import android.opengl.Matrix;

/**
 * Created by foil on 30.10.15.
 */
public class Camera {
    private float view[], projection[], mvp[];
    private int shaderId;
    private float ratio;

    public Camera(int width, int height, int shaderId){
        this.shaderId = shaderId;
        ratio = width/height;

        view = new float[16];
        projection = new float[16];
        mvp = new float[16];

    }

    public void setLookAt(float posX, float posY, float posZ, float dirX, float dirY, float dirZ){
        Matrix.setLookAtM(view, 0, posX, posY, posZ, dirX, dirY, dirZ, 0.0f, 1.0f, 0.0f);
    }

    public void setFrustumProjection(){
        Matrix.frustumM(projection, 0, -ratio, ratio, -1, 1, 1, 25);
    }
}
