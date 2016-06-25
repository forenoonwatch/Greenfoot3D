import greenfoot.*;
import greenfoot.core.WorldHandler; // Discovered by me on 4/13/2015, this engine would be waaaay different if I discovered this earlier

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JPanel;

import java.io.IOException;

import java.util.ArrayList;

/**
 * Write a description of class World3D here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class World3D extends World
{
    private static final GreenfootImage BLANK_CURSOR = new GreenfootImage(1, 1);
    
    private final RenderContext renderContext;
    
    private GameObject rootObject;
    private GUIObject rootGUI;
    
    private Camera camera;
    
    private long frameRateUpdate = System.currentTimeMillis();
    private long lastRepaint = System.currentTimeMillis();
    private int fps = 0;
    private long frames = 0;
    private long current;
    
    private Vector4 lightDirection;
    private float constantLight;
    
    public World3D()
    {
        super(800, 600, 1);
        
        renderContext = new RenderContext(getWidth(), getHeight(), this);
        
        rootObject = new GameObject();
        rootGUI = new GUIObject();
        
        lightDirection = new Vector4(0, 0, -1);
        constantLight = 0.1f;
        
        camera = new Camera(Matrix4x4.initPerspective((float)Math.toRadians(70), (float)getWidth() / (float)getHeight(), 0.1f, 1000.0f));
        
        setBackground(renderContext);
    }
    
    public void act()
    {
        float delta = updateFrameRate();
        
        renderContext.clear();
        renderContext.clearZBuffer();
        
        update(delta);
        camera.update(this, delta);
        camera.updateChildren(this, delta);
        
        updateObjects(delta);
        physicsObjects(delta);
        
        updateGUIs(delta);
    }
    
    private float updateFrameRate() 
    {
        current = System.currentTimeMillis();
        long delta = current - lastRepaint;
        lastRepaint = current;
        
        if (current - frameRateUpdate > 500) 
        {
            fps = (int)(frames / ((float)(current - frameRateUpdate) / 1000));
            frameRateUpdate = current;
            frames = 0;
        }
        else
        {
            frames++;
        }
        
        return (float)delta;
    }
    
    public int getFPS()
    {
        return fps;
    }
    
    public void update(float delta)
    {
    }
    
    private void updateObjects(float delta)
    {
        for (GameObject obj : rootObject.getChildren())
        {
            obj.update(this, delta);
            obj.updateChildren(this, delta);
        }
    }
    
    private void physicsObjects(float delta)
    {
        for (GameObject obj : rootObject.getChildren())
        {
            obj.physics(this, delta);
        }
    }
    
    private void updateGUIs(float delta)
    {
        for (GUIObject gui : rootGUI.getChildren())
        {
            gui.update(this, delta);
            gui.updateChildren(this, delta);
        }
    }
    
    public void add(GameObject obj)
    {
        rootObject.add(obj);
    }
    
    public void add(GUIObject obj)
    {
        rootGUI.add(obj);
    }
    
    public void addAll(GameObject... objects)
    {
        for (GameObject obj : objects)
        {
            rootObject.add(obj);
        }
    }
    
    public void addAll(GUIObject... objects)
    {
        for (GUIObject obj : objects)
        {
            rootGUI.add(obj);
        }
    }
    
    public void remove(GameObject obj)
    {
        rootObject.remove(obj);
    }
    
    public void remove(GUIObject obj)
    {
        rootGUI.remove(obj);
    }
    
    public GameObject getRootObject()
    {
        return rootObject;
    }
    
    public GUIObject getRootGUI()
    {
        return rootGUI;
    }
    
    public Camera getCamera()
    {
        return camera;
    }
    
    public void setCamera(Camera camera)
    {
        this.camera = camera;
    }
    
    public RenderContext getRenderContext()
    {
        return renderContext;
    }
    
    public void setLightDirection(Vector4 direction)
    {
        lightDirection = direction;
    }
    
    public Vector4 getLightDirection()
    {
        return lightDirection;
    }
    
    public void setConstantLight(float percent)
    {
        constantLight = percent;
    }
    
    public float getConstantLight()
    {
        return constantLight;
    }
    
    public void setMouseCursor(GreenfootImage image)
    {
        setMouseCursor(image, 0, 0);
    }
    
    public void setMouseCursor(GreenfootImage image, int mouseX, int mouseY)
    {
        JPanel worldPanel = WorldHandler.getInstance().getWorldCanvas();
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Point cursorPoint = new Point(mouseX, mouseY);
        
        worldPanel.setCursor(tk.createCustomCursor(image.getAwtImage(), cursorPoint, "CustomCursor"));
    }
    
    public void setDefaultCursor()
    {
        JPanel worldPanel = WorldHandler.getInstance().getWorldCanvas();
        worldPanel.setCursor(Cursor.getDefaultCursor());
    }
    
    public void setBlankCursor()
    {
        setMouseCursor(BLANK_CURSOR);
    }
}
