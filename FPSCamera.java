import greenfoot.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Robot;
import java.awt.AWTException;

/**
 * Write a description of class FPSCamera here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FPSCamera extends Camera 
{
    private boolean mouseLocked;
    private float turnSensitivity;
    private float moveSensitivity;
    private float moveSpeed;
    
    private GUIFrame calibFrame;
    
    private boolean calibrating;
    private boolean dragStarted;
    private Vector2 mouseOffset;
    
    private Robot robot;
    
    public FPSCamera(World3D world, Matrix4x4 projection)
    {
        super(projection);
        
        calibrating = true;
        dragStarted = false;
        
        mouseLocked = true;
        turnSensitivity = 5;
        moveSensitivity = 0.75f;
        moveSpeed = 10;
        
        initCalibFrame(world);
        
        try
        {
            robot = new Robot();
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }
    }
    
    private void initCalibFrame(World3D world)
    {
        calibFrame = new GUIFrame();
        calibFrame.setShowBackground(false);
        calibFrame.setSize(world.getWidth(), world.getHeight());
        
        GreenfootImage image = new GreenfootImage(10, 10);
        image.setColor(Color.RED);
        image.fillOval(0, 0, 10, 10);
        
        GUIImageLabel calibCircle = new GUIImageLabel();
        calibCircle.setShowBackground(false);
        calibCircle.setImage(image);
        calibCircle.setSize(10, 10);
        calibCircle.setPosition(world.getWidth() / 2 - 5, world.getHeight() / 2 - 5);
        calibFrame.add(calibCircle);
        
        Font font = new Font("Arial", 0, 20);
        
        GUILabel calibText1 = new GUILabel();
        calibText1.setTextColor(Color.WHITE);
        calibText1.setText("Click on the red dot and drag your mouse to the top left corner of your screen");
        calibText1.setSize(100, 20);
        calibText1.setFont(font);
        calibText1.setShowBackground(false);
        calibText1.setPosition(world.getWidth() / 2, (world.getHeight() / 2 - 10) - 100);
        calibFrame.add(calibText1);
        
        GUILabel calibText2 = new GUILabel();
        calibText2.setTextColor(Color.WHITE);
        calibText2.setText("then release your mouse");
        calibText2.setSize(100, 20);
        calibText2.setFont(font);
        calibText2.setShowBackground(false);
        calibText2.setPosition(world.getWidth() / 2 - 50, (world.getHeight() / 2 - 10) - 60);
        calibFrame.add(calibText2);
        
        world.add(calibFrame);
    }
    
    public void update(World3D world, float delta)
    {
        if (calibrating)
        {
            updateCalibration(world);
        }
        else
        {
            float deltaMS = delta / 1000;
            
            updateCameraTurn(world, deltaMS);
            updateCameraMove(world, deltaMS);
        }
    }
    
    private void updateCalibration(World3D world)
    {
        if (Greenfoot.mouseDragged(null) && !dragStarted)
        {
            dragStarted = true;
        }
        else if (dragStarted && Greenfoot.mouseDragEnded(null))
        {
            dragStarted = false;
            calibrating = false;
            
            MouseInfo mouseInfo = Greenfoot.getMouseInfo();
            mouseOffset = new Vector2(-mouseInfo.getX(), -mouseInfo.getY());
            
            if (calibFrame != null)
            {
                world.remove(calibFrame);
                calibFrame = null;
            }
            
            world.setBlankCursor();
            robot.mouseMove((int)(world.getWidth() / 2 + mouseOffset.getX()), (int)(world.getHeight() / 2 + mouseOffset.getY()));
        }
    }
    
    private void updateCameraTurn(World3D world, float deltaMS)
    {
        if (Greenfoot.isKeyDown("escape"))
        {
            mouseLocked = false;
            world.setDefaultCursor();
        }
        else if (!mouseLocked && Greenfoot.mousePressed(world))
        {
            mouseLocked = true;
            world.setBlankCursor();
        }
        
        if (mouseLocked)
        {
            MouseInfo mouseInfo = Greenfoot.getMouseInfo();
            
            if (mouseInfo != null)
            {
                float dx = (world.getWidth() / 2 - mouseInfo.getX()) * deltaMS;
                float dy = (world.getHeight() / 2 - mouseInfo.getY()) * deltaMS;
                
                if (dx != 0)
                {
                    getTransform().rotate(Vector4.UP, (float)Math.toRadians(-dx * turnSensitivity));
                }
                
                if (dy != 0)
                {
                    getTransform().rotate(new Vector4(getTransform().getRotation().getRight()), (float)Math.toRadians(-dy * turnSensitivity));
                }
            }
            
            robot.mouseMove((int)(world.getWidth() / 2 + mouseOffset.getX()), (int)(world.getHeight() / 2 + mouseOffset.getY()));
        }
    }
    
    private void updateCameraMove(World3D world, float deltaMS)
    {
        float sensX = moveSensitivity * deltaMS;
        float sensY = moveSensitivity * deltaMS;
        float speed = moveSpeed * deltaMS;
        
        if (Greenfoot.isKeyDown("w"))
        {
            move(getTransform().getRotation().getForward(), speed);
        }
        
        if (Greenfoot.isKeyDown("s"))
        {
            move(getTransform().getRotation().getBack(), speed);
        }
        
        if (Greenfoot.isKeyDown("a"))
        {
            move(getTransform().getRotation().getLeft(), speed);
        }
        
        if (Greenfoot.isKeyDown("d"))
        {
            move(getTransform().getRotation().getRight(), speed);
        }
    }
    
    private void move(Vector3 dir, float amt)
    {
        getTransform().setPosition(getTransform().getPosition().add(new Vector4(dir).mul(amt)));
    }
}
