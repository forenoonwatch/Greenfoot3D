import greenfoot.GreenfootImage;

/**
 * Write a description of class GUIImageObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIImageObject extends GUIObject 
{
    private GreenfootImage image;
    private boolean clipImage;
    
    public GUIImageObject()
    {
        clipImage =  true;
    }
    
    public void setImage(GreenfootImage image)
    {
        this.image = image;
    }
    
    public void setImage(String filePath)
    {
        image = new GreenfootImage(filePath);
    }
    
    public GreenfootImage getImage()
    {
        if (!clipImage)
        {
            return image;
        }
        else
        {
            GreenfootImage out = new GreenfootImage((int)getSize().getX(), (int)getSize().getY());
            out.drawImage(image, 0, 0);
            return out;
        }
    }
    
    public void setClipImage(boolean clipImage)
    {
        this.clipImage = clipImage;
    }
    
    public boolean getClipImage()
    {
        return clipImage;
    }
}
