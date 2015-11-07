package lotes.yota.akawain;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Calendar;
import java.util.logging.Logger;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

import com.owens.oobjloader.builder.Build;
import com.owens.oobjloader.parser.Parse;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


class MyGLRenderer implements GLSurfaceView.Renderer {
    long startTime = 0;
    private final Context activityContext;
    ShaderHandler program;
    Camera camera;
    Cube cube, anotherCube;
    Logger logger = Logger.getLogger(GLSurfaceView.Renderer.class.getName());

    // passing application context to new Renderer
    public MyGLRenderer(final Context context) {
        startTime = System.currentTimeMillis();
        activityContext = context;
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.37f, 0.24f, 0.66f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);


        WindowManager wm = (WindowManager) activityContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Loader loader = new Loader(activityContext);
        program = new ShaderHandler(loader.readFile(R.raw.vertexshader), loader.readFile(R.raw.fragmentshader));

        camera = new Camera(size.x, size.y, program.id);
        camera.setFrustumProjection();
        camera.setLookAt(2.0f, 1.5f, -2.0f, 0.0f, 0.0f, 0.0f);
        camera.calculateMVP();

        // Passing loader object to parser
        Parser parser = new Parser();
        // Parsing data

        Container container = new Container();

        container.save("cube", parser.Parse(loader.readFile(R.raw.cube)));

        Build builder = new Build();
        try{
            Parse parseObj = new Parse(loader.readFile(R.raw.cube), builder);
        }catch(IOException ioe){
        }

        Converter converter = new Converter();
        converter.parseBuilderObj(builder);

        float[] verticesData = converter.getVerticesData();
        float[] normalsData = converter.getNormalsData();


        // Extracting data for cube
        cube = new Cube(verticesData, normalsData, 0.0f, 0.0f, 0.0f, program.id);
        anotherCube = new Cube(verticesData, normalsData, 1f, -1f, 1f, program.id);
    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0.37f, 0.24f, 0.66f, 1.0f);

        GLES20.glUseProgram(program.id);

        float time = (System.currentTimeMillis() - startTime) * 0.00001f;
        //logger.log(INFO, "time: " + System.currentTimeMillis());

        camera.applyRotation(0.0f, 1.0f, 0.0f, System.currentTimeMillis()*0.0000000000001f);
        camera.calculateMVP();
        camera.sendMVP();
        cube.render();
        anotherCube.render();

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
}