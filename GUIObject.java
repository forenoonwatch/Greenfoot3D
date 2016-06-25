import java.util.ArrayList;

import java.awt.Color;

/**
 * Write a description of class GUIObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIObject  
{
    private GUIObject parent;
    private ArrayList<GUIObject> children;
    
    private Vector2 size;
    private Vector2 position;
    
    private Color color;
    private boolean visible;
    
    private boolean showBackground;
    
    public GUIObject()
    {
        children = new ArrayList<GUIObject>();
        
        size = new Vector2(100, 100);
        position = new Vector2();
        
        color = Color.WHITE;
        visible = true;
        
        showBackground = true;
    }
    
    public void update(World3D world, float deltaTime) {}
    
    public void updateChildren(World3D world, float deltaTime)
    {
        for (GUIObject obj : children)
        {
            obj.update(world, deltaTime);
            obj.updateChildren(world, deltaTime);
        }
    }
    
    public void setParent(GUIObject obj)
    {
        getParent().remove(this);
        obj.getChildren().add(this);
        parent = obj;
    }
    
    public void add(GUIObject obj)
    {
        obj.setParent(this);
    }
    
    public void remove(GUIObject obj)
    {
        children.remove(obj);
    }
    
    public GUIObject getParent()
    {
        if (parent == null)
        {
            parent = new GUIObject();
            parent.getChildren().add(this);
        }
        
        return parent;
    }
    
    public ArrayList<GUIObject> getChildren()
    {
        return children;
    }
    
    public Vector2 getSize()
    {
        return size;
    }
    
    public Vector2 getPosition()
    {
        return position;
    }
    
    public Vector2 getScreenPosition()
    {
        if (parent == null)
        {
            return position;
        }
        
        return parent.getScreenPosition().add(position);
    }
    
    public void setSize(Vector2 size)
    {
        this.size = size;
    }
    
    public void setSize(float x, float y)
    {
        size = new Vector2(x, y);
    }
    
    public void setPosition(Vector2 position)
    {
        this.position = position;
    }
    
    public void setPosition(float x, float y)
    {
        position = new Vector2(x, y);
    }
    
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public boolean isVisible()
    {
        if (parent == null)
        {
            return visible;
        }
        
        return visible && getParent().isVisible();
    }
    
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    public void setShowBackground(boolean showBackground)
    {
        this.showBackground = showBackground;
    }
    
    public boolean getShowBackground()
    {
        return showBackground;
    }
}
