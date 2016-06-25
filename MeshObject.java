import greenfoot.GreenfootImage;

/**
 * Write a description of class MeshObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MeshObject extends GameObject 
{
    private Mesh mesh;
    private GreenfootImage texture;
    
    public MeshObject(Mesh mesh, GreenfootImage texture)
    {
        this.mesh = mesh;
        this.texture = texture;
    }
    
    public void update(World3D world, float deltaTime)
    {
        mesh.draw(world.getRenderContext(), getTransform().getTransformation(), world.getCamera().getViewProjection(), texture);
    }
    
    public Mesh getMesh()
    {
        return mesh;
    }
    
    public GreenfootImage getTexture()
    {
        return texture;
    }
    
    public int[] getIndices()
    {
        return mesh.getIndices();
    }
    
    public Vertex[] getVertices()
    {
        return mesh.getVertices();
    }
    
    public Vertex[] getTransformedVertices()
    {
        Vertex[] out = new Vertex[getVertices().length];
        
        for (int i = 0; i < getVertices().length; i++)
        {
            Vertex v = getVertices()[i];
            out[i] = new Vertex(getTransform().getTransformation().transform(v.getPosition()), v.getNormal(), v.getTexCoords());
        }
        
        return out;
    }
}
