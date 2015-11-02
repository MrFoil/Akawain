package lotes.yota.akawain;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import java.nio.FloatBuffer;
import java.util.Calendar;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


class MyGLRenderer implements GLSurfaceView.Renderer {
    private final Context activityContext;
    ShaderHandler program;
    Camera camera;
    Cube cube;
    int positionAttribute;


    // passing application context to new Renderer
    public MyGLRenderer(final Context context) {
        activityContext = context;
    }

    // Function for loading shaders from /res/raw folder
    protected String loadVertexShader() {
        return Loader.readRawFile(activityContext, R.raw.vertexshader);
    }

    protected String loadFragmentShader() {
        return Loader.readRawFile(activityContext, R.raw.fragmentshader);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.37f, 0.24f, 0.66f, 1.0f);

        WindowManager wm = (WindowManager) activityContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        program = new ShaderHandler(loadVertexShader(), loadFragmentShader());

        camera = new Camera(size.x, size.y, program.id);
        camera.setFrustumProjection();
        camera.setLookAt(1.5f, 3.0f, -1.0f, 0.0f, 0.0f, 0.0f);
        camera.calculateMVP();

        cube = new Cube();
        positionAttribute = GLES20.glGetAttribLocation(program.id, "a_Position");

    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0.37f, 0.24f, 0.66f, 1.0f);

        GLES20.glUseProgram(program.id);

        camera.sendMVP();
        cube.render(positionAttribute);

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
}