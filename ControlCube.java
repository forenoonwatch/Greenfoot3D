import java.io.IOException;
import greenfoot.Greenfoot;

/**
 * Write a description of class ControlCube here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ControlCube extends CollideCube 
{
    private float speed = 5;
    private BoundingBox testCube;
    
    public ControlCube() throws IOException
    {
        testCube = new BoundingBox();
    }
    
    public void update(World3D world, float delta)
    {
        super.update(world, delta);
        
        float deltaMS = delta / 1000;
        
        float nSpeed = speed * deltaMS;
        
        Vector4 pos = new Vector4(0, 0, 0, 0);
        
        if (Greenfoot.isKeyDown("u"))
            pos = Vector4.FORWARD.mul(nSpeed);
        if (Greenfoot.isKeyDown("j"))
            pos = Vector4.BACK.mul(nSpeed);
        if (Greenfoot.isKeyDown("h"))
            pos = Vector4.LEFT.mul(nSpeed);
        if (Greenfoot.isKeyDown("k"))
            pos = Vector4.RIGHT.mul(nSpeed);
        
        Vector4 lastPos = getTransform().getPosition();
        getTransform().setPosition(getTransform().getPosition().add(pos));
        
        for (GameObject obj : world.getRootObject().getChildren())
        {
            if (obj != this && obj instanceof CollideCube)
            {
                CollideCube other = (CollideCube)obj;
                
                if (getBoundingBox().collidesWith(other.getBoundingBox()))
                {
                    getTransform().setPosition(lastPos);
                    return;
                }
            }
        }
        
        
    }
}
