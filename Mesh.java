import greenfoot.GreenfootImage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Write a description of class Mesh here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mesh  
{
    private final Vertex[] vertices;
    private final int[] indices;
    
    public Mesh(Vertex[] vertices, int[] indices)
    {
        this.vertices = vertices;
        this.indices = indices;
    }

    public Mesh(String fileName) throws IOException
    {
        IndexedModel model = new OBJModel(fileName).toIndexedModel();
        
        indices = new int[model.getIndices().size()];
        vertices = new Vertex[model.getPositions().size()];
        
        for (int i = 0; i < indices.length; i++)
        {
            indices[i] = (int)model.getIndices().get(i);
        }
        
        for (int i = 0; i < model.getPositions().size(); i++)
        {
            vertices[i] = new Vertex(model.getPositions().get(i), model.getNormals().get(i), model.getTexCoords().get(i));
        }
    }
    
    public void draw(RenderContext target, Matrix4x4 trans, Matrix4x4 viewProjection, GreenfootImage texture)
    {
        Matrix4x4 mvp = viewProjection.mul(trans);
        
        for (int i = 0; i < indices.length; i += 3)
        {
            Vertex v0 = vertices[indices[i]].transformBy(mvp, trans);
            Vertex v1 = vertices[indices[i + 1]].transformBy(mvp, trans);
            Vertex v2 = vertices[indices[i + 2]].transformBy(mvp, trans);
            
            target.drawTriangle(v0, v1, v2, texture);
        }
    }
    
    public Vertex[] getVertices()
    {
        return vertices;
    }
    
    public int[] getIndices()
    {
        return indices;
    }
}
