import greenfoot.*;

import java.awt.Color;

/**
 * Write a description of class GUITextButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUITextButton extends GUITextObject 
{
    private boolean isPressed;
    private boolean lastPressed;
    
    private boolean buttonPressed;
    private boolean buttonReleased;
    
    public GUITextButton()
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
        Color drawColor = getColor();
        
        lastPressed = isPressed;
        
        if (mouseInfo != null && isWithinBounds(mouseInfo.getX(), mouseInfo.getY()))
        {
            drawColor = drawColor.darker();
            
            if (mouseInfo.getButton() == 1)
            {
                drawColor = drawColor.darker();
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
        
        if (getShowBackground())
        {
            rc.setColor(drawColor);
            rc.fillRect((int)pos.getX(), (int)pos.getY(), (int)size.getX(), (int)size.getY());
        }
        
        rc.setFont(getFont());
        rc.setColor(getTextColor());
        rc.drawString(getText(), (int)(pos.getX() + size.getX() / 2 - (getText().length()/2 * (getFont().getSize() / 2))), (int)((pos.getY() + size.getY() / 2) + getFont().getSize() / 2));
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
