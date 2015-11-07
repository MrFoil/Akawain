package lotes.yota.akawain;

import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * Created by foil on 30.10.15.
 */
public class Camera {
    private float view[], projection[], mvp[], position[] = new float[4], direction[] = new float[4];
    private float ratio;
    private int mvpId;

    public Camera(int width, int height, int shaderId){
        ratio = (float) width/height;

        view = new float[16];
        projection = new float[16];
        mvp = new float[16];

        mvpId = GLES20.glGetUniformLocation(shaderId, "MVP");
    }

    private float[] getRotationMatrix(float vx, float vy, float vz, float angle){
        float s = (float)Math.sin(angle);
        float c = (float)Math.cos(angle);

        float[] res = {
                c + vx*vx*(1-c), vx*vy*(1-c)-vz*s, vx*vz*(1-c)+vy*s, 0.0f,
                vy*vx*(1-c)+vz*s, c + vy*vy*(1-c), vy*vz*(1-c) - vx*s, 0.0f,
                vz*vx*(1-c)-vy*s, vz*vy*(1-c)+vx*s, c + vz*vz*(1-c), 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f

        };

        return res;
    }

    public void setLookAt(float posX, float posY, float posZ, float dirX, float dirY, float dirZ){
        this.position[0] = posX;
        this.position[1] = posY;
        this.position[2] = posZ;
        this.position[3] = 1.0f;

        this.direction[0] = dirX;
        this.direction[1] = dirY;
        this.direction[2] = dirZ;
        this.direction[3] = 1.0f;


        Matrix.setLookAtM(view, 0, posX, posY, posZ, dirX, dirY, dirZ, 0.0f, 1.0f, 0.0f);
    }

    public void applyRotation(float axisX, float axisY, float axisZ, float angle){
        float[] rot = getRotationMatrix(axisX, axisY, axisZ, angle);
        float[] resultingPosition = new float[4];

        Matrix.multiplyMV(position, 0, rot, 0, position, 0);

        setLookAt(position[0], position[1], position[2], direction[0], direction[1], direction[2]);

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
