/**
 * Write a description of class GameComponent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameComponent  
{
    private GameObject parent;
    
    public void update(World3D world, float deltaTime) {}
    
    public void physics(World3D world, float deltaTime) {}
    
    public void setParent(GameObject obj)
    {
        parent = obj;
    }
    
    public Transform getTransform()
    {
        return getParent().getTransform();
    }
    
    public GameObject getParent()
    {
        if (parent == null)
        {
            parent = new GameObject();
            parent.add(this);
        }
        
        return parent;
    }
}
