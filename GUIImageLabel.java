/**
 * Write a description of class GUIImageLabel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIImageLabel extends GUIImageObject 
{
    public void update(World3D world, float delta)
    {
        if (isVisible())
        {
            RenderContext rc = world.getRenderContext();
            
            Vector2 pos = getScreenPosition();
            Vector2 size = getSize();
            
            if (getShowBackground() || getImage() == null)
            {
                rc.setColor(getColor());
                rc.fillRect((int)pos.getX(), (int)pos.getY(), (int)size.getX(), (int)size.getY());
            }
            
            if (getImage() != null)
            {
                rc.drawImage(getImage(), (int)pos.getX(), (int)pos.getY());
            }
        }
    }
}
