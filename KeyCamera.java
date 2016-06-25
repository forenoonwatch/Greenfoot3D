import greenfoot.Greenfoot;

/**
 * Write a description of class KeyCamera here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KeyCamera extends Camera 
{
    private static final Vector2 sensitivity = new Vector2(2.66f, 2);
    private static final float moveSpeed = 5;
    
    public KeyCamera(Matrix4x4 projection)
    {
        super(projection);
    }
    
    public void update(World3D world, float delta)
    {
        float deltaMS = delta / 1000;
        
        float sensX = sensitivity.getX() * deltaMS;
        float sensY = sensitivity.getY() * deltaMS;
        float speed = moveSpeed * deltaMS;
        
        if (Greenfoot.isKeyDown("w"))
        {
            move(getTransform().getRotation().getForward(), speed);
        }
        
        if (Greenfoot.isKeyDown("s"))
        {
            move(getTransform().getRotation().getBack(), speed);
        }
        
        if (Greenfoot.isKeyDown("a"))
        {
            move(getTransform().getRotation().getLeft(), speed);
        }
        
        if (Greenfoot.isKeyDown("d"))
        {
            move(getTransform().getRotation().getRight(), speed);
        }
        
        if (Greenfoot.isKeyDown("up"))
        {
            turn(getTransform().getRotation().getRight(), -sensY);
        }
        
        if (Greenfoot.isKeyDown("down"))
        {
            turn(getTransform().getRotation().getRight(), sensY);
        }
        
        if (Greenfoot.isKeyDown("left"))
        {
            turn(Vector3.UP, -sensX);
        }
        
        if (Greenfoot.isKeyDown("right"))
        {
            turn(Vector3.UP, sensX);
        }
    }
    
    private void move(Vector3 dir, float amt)
    {
        getTransform().setPosition(getTransform().getPosition().add(new Vector4(dir).mul(amt)));
    }
    
    private void turn(Vector3 axis, float angle)
    {
        getTransform().rotate(new Vector4(axis), angle);
    }
}
