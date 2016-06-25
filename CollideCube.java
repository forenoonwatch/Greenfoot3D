import greenfoot.GreenfootImage;

import java.io.IOException;

/**
 * Write a description of class CollideCube here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CollideCube extends MeshObject 
{
    private BoundingBox box;
    private boolean anchored;
    
    public CollideCube() throws IOException
    {
        super(new Mesh("models/cube.obj"), new GreenfootImage("bricks.jpg"));
        
        box = new BoundingBox();
        anchored = false;
        
        add(box);
    }
    
    public void physics(World3D world, float delta)
    {
        if (anchored)
            return;
        
        for (GameObject obj : world.getRootObject().getChildren())
        {
            if (obj instanceof CollideCube)
            {
                CollideCube cube = (CollideCube)obj;
                
                if (cube.getAnchored())
                    continue;
            }
        }
    }
    
    public BoundingBox getBoundingBox()
    {
        return box;
    }
    
    public void setAnchored(boolean b)
    {
        anchored = b;
    }
    
    public boolean getAnchored()
    {
        return anchored;
    }
}
