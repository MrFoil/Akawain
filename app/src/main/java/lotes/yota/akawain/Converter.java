package lotes.yota.akawain;

import com.owens.oobjloader.parser.Parse;

import com.owens.oobjloader.builder.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by foil on 05.11.15.
 */
public class Converter {

    private Logger log = Logger.getLogger(Converter.class.getName());

    private float[] vertices, normals;

    public Converter(){

    }

    public void parseBuilderObj( Build builder ){



        ArrayList<Face> faces = builder.faces;


        int facesNum = faces.size();

        this.vertices = new float[facesNum * 3 * 3];
        this.normals = new float[facesNum * 3 * 3];

        int counter = 0;

        Iterator<Face> faceIt = faces.iterator();

        while(faceIt.hasNext()){
            Face curFace = faceIt.next();

            log.log(Level.INFO, "Loaded FACE " + curFace);

            Iterator<FaceVertex> fvIt = curFace.vertices.iterator();

            while(fvIt.hasNext()){
                FaceVertex fv = fvIt.next();

                this.vertices[counter] = fv.v.x;
                this.vertices[counter + 1] = fv.v.y;
                this.vertices[counter + 2] = fv.v.z;

                this.normals[counter] = fv.n.x;
                this.normals[counter + 1] = fv.n.y;
                this.normals[counter + 2] = fv.n.z;

                counter += 3;
            }



        }

        log.log(Level.INFO, "FACES VERTICES COUNT" + counter);
    }


    public float[] getVerticesData(){
        return this.vertices;
    }

    public float[] getNormalsData(){
        return this.normals;
    }

}


