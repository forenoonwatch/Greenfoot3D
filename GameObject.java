import java.util.ArrayList;

/**
 * Write a description of class GameObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameObject  
{
    private Transform transform;
    private GameObject parent;
    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;
    
    public GameObject()
    {
        transform = new Transform();
        children = new ArrayList<GameObject>();
        components = new ArrayList<GameComponent>();
    }
    
    public void update(World3D world, float deltaTime) {}
    
    public void physics(World3D world, float deltaTime) {}
    
    public void updateChildren(World3D world, float deltaTime)
    {
        for (GameObject obj : children)
        {
            obj.update(world, deltaTime);
            obj.updateChildren(world, deltaTime);
        }
        
        for (GameComponent component : components)
        {
            component.update(world, deltaTime);
        }
    }
    
    public void physicsChildren(World3D world, float deltaTime)
    {
        for (GameComponent component : components)
        {
            component.physics(world, deltaTime);
        }
    }
    
    public void setParent(GameObject obj)
    {
        getParent().remove(this);
        obj.getChildren().add(this);
        transform.setParent(obj.getTransform());
        parent = obj;
    }
    
    public void add(GameObject obj)
    {
        children.add(obj);
        obj.setParent(this);
    }
    
    public void add(GameComponent component)
    {
        component.setParent(this);
        components.add(component);
    }
    
    public void remove(GameObject obj)
    {
        getChildren().remove(obj);
    }
    
    public void remove(GameComponent component)
    {
        component.setParent(null);
        components.remove(component);
    }
    
    public Transform getTransform()
    {
        return transform;
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
    
    public ArrayList<GameObject> getChildren()
    {
        return children;
    }
    
    public ArrayList<GameComponent> getComponents()
    {
        return components;
    }
}
