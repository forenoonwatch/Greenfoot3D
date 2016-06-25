/**
 * Write a description of class PhysicsComponent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PhysicsComponent extends GameComponent 
{
    private Vector3 velocity;
    
    public PhysicsComponent()
    {
        velocity = new Vector3();
    }
    
    public PhysicsComponent(float x, float y, float z)
    {
        velocity = new Vector3(x, y, z);
    }
    
    public PhysicsComponent(Vector3 v3)
    {
        this(v3.getX(), v3.getY(), v3.getZ());
    }
    
    public void addVelocity(Vector3 vel)
    {
        velocity.add(vel);
    }
    
    public Vector3 getVelocity()
    {
        return velocity;
    }
    
    public void setVelocity(Vector3 vel)
    {
        velocity = vel;
    }
}
