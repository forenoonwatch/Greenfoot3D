import greenfoot.*;

import javax.swing.JOptionPane;

/**
 * Write a description of class GUITextBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUITextBox extends GUITextObject 
{
    private boolean isTyping;
    private boolean isUpdated;
    
    public GUITextBox()
    {
        isTyping = false;
        isUpdated = false;
    }
    
    public void update(World3D world, float delta)
    {
        if (!isVisible())
        {
            isTyping = false;
            isUpdated = false;
            return;
        }
        
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        
        if (mouseInfo != null && isWithinBounds(mouseInfo.getX(), mouseInfo.getY()) && !isTyping)
        {
            if (mouseInfo.getButton() == 1)
            {
                isTyping = true;
                showTextPane();
            }
        }
        
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
    
    private boolean isWithinBounds(int mouseX, int mouseY)
    {
        Vector2 pos = getScreenPosition();
        Vector2 size = getSize();
        
        int rx = (int)(pos.getX() + size.getX());
        int ry = (int)(pos.getY() + size.getY());
        
        return mouseX > pos.getX() && mouseX < rx && mouseY > pos.getY() && mouseY < ry;
    }
    
    private void showTextPane()
    {
        new Thread(new Runnable(){
            public void run()
            {
                String text = JOptionPane.showInputDialog(null, "Enter text");
                setText(text);
                isTyping = false;
            }
        }).start();
    }
    
    public boolean getTextUpdated()
    {
        return isUpdated;
    }
}
