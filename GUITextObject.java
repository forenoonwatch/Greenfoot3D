import java.awt.Color;
import java.awt.Font;

/**
 * Write a description of class GUITextObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUITextObject extends GUIObject 
{
    private String text;
    private Color textColor;
    private Font font;
    
    public GUITextObject()
    {
        text = "";
        textColor = Color.BLACK;
        font = new Font("Dialog", 0, 12);
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    public void setTextColor(Color color)
    {
        textColor = color;
    }
    
    public void setFont(Font font)
    {
        this.font = font;
    }
    
    public String getText()
    {
        return text;
    }
    
    public Color getTextColor()
    {
        return textColor;
    }
    
    public Font getFont()
    {
        return font;
    }
}
