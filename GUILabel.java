import java.awt.Color;
import java.awt.Font;

/**
 * Write a description of class GUILabel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUILabel extends GUITextObject 
{
    public void update(World3D world, float delta)
    {
        if (isVisible())
        {
            RenderContext rc = world.getRenderContext();
        
            Vector2 pos = getScreenPosition();
            Vector2 size = getSize();
            
            if (getShowBackground())
            {
                rc.setColor(getColor());
                rc.fillRect((int)pos.getX(), (int)pos.getY(), (int)size.getX(), (int)size.getY());
            }
            
            rc.setFont(getFont());
            rc.setColor(getTextColor());
            rc.drawString(getText(), (int)(pos.getX() + size.getX() / 2 - (getText().length()/2 * (getFont().getSize() / 2))), (int)((pos.getY() + size.getY() / 2) + getFont().getSize() / 2));
        }
    }
}
