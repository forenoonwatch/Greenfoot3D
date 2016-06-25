/**
 * Write a description of class GUIFrame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIFrame extends GUIObject 
{
    public void update(World3D world, float deltaTime)
    {
        if (isVisible())
        {
            RenderContext target = world.getRenderContext();
            
            Vector2 pos = getScreenPosition();
            Vector2 size = getSize();
            
            if (getShowBackground())
            {
                target.setColor(getColor());
                target.fillRect((int)pos.getX(), (int)pos.getY(), (int)size.getX(), (int)size.getY());
            }
        }
    }
}
