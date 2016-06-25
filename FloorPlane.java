import java.io.IOException;
import greenfoot.GreenfootImage;

/**
 * Write a description of class FloorPlane here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FloorPlane extends MeshObject 
{
    public FloorPlane() throws IOException
    {
        this(new GreenfootImage("bricks.jpg"));
    }
    
    public FloorPlane(GreenfootImage texture) throws IOException
    {
        super(new Mesh("models/floorPlane.obj"), texture);
    }
    
    @Override
    public void update(World3D world, float delta)
    {
        RenderContext rc = world.getRenderContext();
        
        rc.setAmbientLighting(false);
        getMesh().draw(rc, getTransform().getTransformation(), world.getCamera().getViewProjection(), getTexture());
        rc.setAmbientLighting(true);
    }
}
