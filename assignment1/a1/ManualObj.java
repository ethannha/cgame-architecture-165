package a1; 

import tage.*; 
import tage.shapes.*; 
import org.joml.*; 
 
public class ManualObj extends ManualObject 
{ 
    // indexed ObjShape definitions in TAGE must be defined as Vector3f/2f 
    // the indexes themselves are stored in an int array 
    private Vector3f[] vertices = new Vector3f[8]; 
    private Vector2f[] texcoords = new Vector2f[8]; 
    private Vector3f[] normals = new Vector3f[8]; 
    private int[] indices = new int[] 
    {   0, 1, 2, 
        0, 2, 3,
        0, 3, 5,
        0, 4, 5,
        3, 2, 6,
        3, 5, 6,
        2, 1, 7,
        2, 6, 7,
        1, 0, 4,
        1, 7, 4,
        4, 7, 6,
        4, 5, 6
    }; 
 
    public ManualObj() 
    {   super(); 
        vertices[0] = (new Vector3f()).set(1.8f, 1.0f, 1.8f); //top
        vertices[1] = (new Vector3f()).set(1.8f, 1.0f, -1.8f); 
        vertices[2] = (new Vector3f()).set(-1.8f, 1.0f, -1.8f); 
        vertices[3] = (new Vector3f()).set(-1.8f, 1.0f, 1.8f); 
        vertices[4] = (new Vector3f()).set(2.0f, -1.0f, 2.0f);  //bottom
        vertices[5] = (new Vector3f()).set(-2.0f, -1.0f, 2.0f); 
        vertices[6] = (new Vector3f()).set(-2.0f, -1.0f, -2.0f); 
        vertices[7] = (new Vector3f()).set(2.0f, -1.0f, -2.0f); 

        texcoords[0] = (new Vector2f()).set(0f, 0f); 
        texcoords[1] = (new Vector2f()).set(0f, 1f); 
        texcoords[2] = (new Vector2f()).set(1f, 1f); 
        texcoords[3] = (new Vector2f()).set(1f, 0f); 
        texcoords[4] = (new Vector2f()).set(0f, 0f); 
        texcoords[5] = (new Vector2f()).set(0f, 1f); 
        texcoords[6] = (new Vector2f()).set(1f, 1f); 
        texcoords[7] = (new Vector2f()).set(1f, 0f); 

        normals[0] = (new Vector3f()).set(0f, 0f, 1f); 
        normals[1] = (new Vector3f()).set(0f, 0f, 1f); 
        normals[2] = (new Vector3f()).set(0f, 0f, 1f); 
        normals[3] = (new Vector3f()).set(0f, 0f, 1f); 
        normals[4] = (new Vector3f()).set(0f, 0f, 1f); 
        normals[5] = (new Vector3f()).set(0f, 0f, 1f); 
        normals[6] = (new Vector3f()).set(0f, 0f, 1f); 
        normals[7] = (new Vector3f()).set(0f, 0f, 1f); 

        setNumVertices(36); 
        setVerticesIndexed(indices, vertices); 
        setTexCoordsIndexed(indices, texcoords); 
        setNormalsIndexed(indices, normals); 
        setMatAmb(Utils.goldAmbient()); 
        setMatDif(Utils.goldDiffuse()); 
        setMatSpe(Utils.goldSpecular()); 
        setMatShi(Utils.goldShininess()); 
    } 
} 