import greenfoot.*;

/**
 * Write a description of class GUIImageButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIImageButton extends GUIImageObject 
{
    private boolean isPressed;
    private boolean lastPressed;
    
    private boolean buttonPressed;
    private boolean buttonReleased;
    
    public GUIImageButton()
    {
        isPressed = false;
        lastPressed = false;
        
        buttonPressed = false;
        buttonReleased = false;
    }
    
    public void update(World3D world, float delta)
    {
        if (!isVisible())
        {
            isPressed = false;
            lastPressed = false;
            
            buttonPressed = false;
            buttonReleased = false;
            return;
        }
        
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        
        lastPressed = isPressed;
        
        if (mouseInfo != null && isWithinBounds(mouseInfo.getX(), mouseInfo.getY()))
        {
            if (mouseInfo.getButton() == 1)
            {
                isPressed = true;
            }
            else
            {
                isPressed = false;
            }
        }
        
        buttonPressed = isPressed && !lastPressed;
        buttonReleased = !isPressed && lastPressed;
        
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
    
    private boolean isWithinBounds(int mouseX, int mouseY)
    {
        Vector2 pos = getScreenPosition();
        Vector2 size = getSize();
        
        int rx = (int)(pos.getX() + size.getX());
        int ry = (int)(pos.getY() + size.getY());
        
        return mouseX > pos.getX() && mouseX < rx && mouseY > pos.getY() && mouseY < ry;
    }
    
    public boolean isButtonDown()
    {
        return isPressed;
    }
    
    public boolean isButtonClicked()
    {
        return buttonPressed;
    }
    
    public boolean isButtonReleased()
    {
        return buttonReleased;
    }
}
