/**
 * Write a description of class BoundingBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoundingBox extends GameComponent 
{
    private Vector3 size;
    
    public BoundingBox()
    {
        size = new Vector3(1, 1, 1);
    }
    
    public BoundingBox(Vector3 size)
    {
        this.size = size;
    }
    
    public BoundingBox(float x, float y, float z)
    {
        size = new Vector3(x, y, z);
    }
    
    public boolean collidesWith(BoundingBox box)
    {
        Vector4 posA = getTransform().getTransformedPosition();
        Vector4 posB = box.getTransform().getTransformedPosition();
        
        boolean cx = Math.abs(posA.getX() - posB.getX()) <= size.getX() + box.getSize().getX();
        boolean cy = Math.abs(posA.getY() - posB.getY()) <= size.getY() + box.getSize().getY();
        boolean cz = Math.abs(posA.getZ() - posB.getZ()) <= size.getZ() + box.getSize().getZ();
        
        return cx && cy && cz;
    }
    
    public Vector3 getSize()
    {
        return size;
    }
    
    public void setSize(Vector3 size)
    {
        this.size = size;
    }
}
